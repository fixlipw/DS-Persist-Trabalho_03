package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.AnotacaoController;
import com.ufc.dspersist.controller.AutorController;
import com.ufc.dspersist.controller.LeituraController;
import com.ufc.dspersist.controller.UsuarioController;
import com.ufc.dspersist.enumeration.BookStatus;
import com.ufc.dspersist.enumeration.BookType;
import com.ufc.dspersist.model.Anotacao;
import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class LeituraPanel {

    private final UsuarioController usuarioController;
    private final LeituraController leituraController;
    private final AnotacaoController anotacaoController;
    private final AutorController autorController;

    private JTextField titleField;
    private JTextField pagesQtdField;
    private JComboBox<Object> titleBox;
    private JComboBox<Object> autorBox;
    private JComboBox<Object> typeBox;
    private JComboBox<Object> statusBox;

    private final JPanel buttonPanel;

    @Autowired
    public LeituraPanel(UsuarioController usuarioController, LeituraController leituraController, AnotacaoController anotacaoController, AutorController autorController) {
        this.usuarioController = usuarioController;
        this.leituraController = leituraController;
        this.anotacaoController = anotacaoController;
        this.autorController = autorController;
        buttonPanel = new JPanel();
    }

    private void updateButtonPanel(JScrollPane scrollPane, List<Leitura> newLeituras) {
        buttonPanel.removeAll();
        int line = Math.max(3, (int) Math.ceil(newLeituras.size() / 3.0));
        buttonPanel.setLayout(new GridLayout(line, 3));

        for (Leitura leitura : newLeituras) {
            JButton leituraButton = createLeituraButton(leitura, newLeituras, scrollPane);
            buttonPanel.add(leituraButton);
        }

        scrollPane.createVerticalScrollBar();
        scrollPane.setViewportView(buttonPanel);

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private JButton createLeituraButton(Leitura leitura, List<Leitura> leituras, JScrollPane scrollPane)
    {
        JButton leituraButton = new JButton(leitura.getTitle());
        leituraButton.setPreferredSize(new Dimension(150, 75));

        leituraButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    doLeituraAction(leitura, leituras, scrollPane);
                }
            }
        });
        return leituraButton;
    }

    private void doLeituraAction(Leitura leitura, List<Leitura> leituras, JScrollPane scrollPane) {
        int option = JOptionPane.showOptionDialog(null,
                "<html>" + leitura.getTitle() +
                        "<br><b>ID:</b> " + leitura.getId() +
                        "<br><b>Autor:</b> " + leitura.getAuthorname() +
                        "<br><b>Páginas:</b> " + leitura.getPagesQtd() +
                        "<br><b>Tipo:</b> " + leitura.getType().getType() +
                        "<br><b>Status:</b> " + leitura.getStatus().getStatus() +
                        "</html>", "Informações", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null,
                new Object[] {"Deletar", "Anotar", "Cancelar"}, 0
        );

        if (option == 0) {
            SwingUtilities.invokeLater(() -> {
                int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar a leitura: \"" + leitura.getTitle() + "\"", "Confirmação", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    SwingWorker<Void, Void> worker = new SwingWorker<>() {
                        private Exception exception;

                        @Override
                        protected Void doInBackground() {
                            try {
                                leituraController.deleteLeitura(leitura);
                            } catch (Exception ex) {
                                exception = ex;
                            }
                            return null;
                        }

                        @Override
                        protected void done() {
                            if (exception != null) {
                                JOptionPane.showMessageDialog(null, "Erro ao excluir leitura. Consulte o log para mais informações.");
                                log.error("Erro ao leitura anotação: {}", exception.getMessage(), exception);
                            } else {
                                SwingWorker<List<Leitura>, Void> dataWorker = new SwingWorker<>() {

                                    @Override
                                    protected List<Leitura> doInBackground() {
                                        leituras.remove(leitura);
                                        return leituras;
                                    }

                                    @Override
                                    protected void done() {
                                        try {
                                            List<Leitura> newAnotacaoList = get();
                                            updateButtonPanel(scrollPane, newAnotacaoList);
                                            JOptionPane.showMessageDialog(null, "Leitura excluída com sucesso!");
                                        } catch (Exception ex) {
                                            JOptionPane.showMessageDialog(null, "Erro ao atualizar a lista. Consulte o log para mais informações.");
                                            log.error("Erro ao atualizar a lista: {}", ex.getMessage(), ex);
                                        }
                                    }
                                };
                                dataWorker.execute();
                            }
                        }
                    };
                    worker.execute();
                }
            });
        } else if (option == 1) {
            try {
                String annotation = JOptionPane.showInputDialog("Digite sua anotação:");
                if (annotation != null) {
                    anotacaoController.saveAnotacao(new Anotacao(), leitura, annotation);
                    JOptionPane.showMessageDialog(null, "Anotação adicionada com sucesso.");
                    log.info("Info: Anotação adicionada com sucesso.");
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Erro ao adicionar anotação. Tente Novamente.");
                log.error("Erro: {}", exception.getMessage(), exception);
            }
        }
    }


    public void setCreateLeiturasPanel(JPanel cardPanel) {
        JPanel createLeituraCard = new JPanel();

        List<Autor> authorsList = autorController.getAllAuthors();
        GridBagConstraints gbc = new GridBagConstraints();
        createLeituraCard.setLayout(new GridBagLayout());

        titleField = new JTextField();
        pagesQtdField = new JTextField("0");
        Object[] bookTypes = Arrays.stream(BookType.values()).map(BookType::getType).toArray();
        Object[] bookStatus = Arrays.stream(BookStatus.values()).map(BookStatus::getStatus).toArray();
        autorBox = new JComboBox<>(authorsList.stream().map(Autor::getAuthorName).toArray());
        typeBox = new JComboBox<>(bookTypes);
        statusBox = new JComboBox<>(bookStatus);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        createLeituraCard.add(new JLabel("Título"), gbc);
        gbc.gridx++;
        createLeituraCard.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        createLeituraCard.add(new JLabel("Nome do Autor:"), gbc);
        gbc.gridx++;
        createLeituraCard.add(autorBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        createLeituraCard.add(new JLabel("Quantidade de Páginas:"), gbc);
        gbc.gridx++;
        createLeituraCard.add(pagesQtdField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        createLeituraCard.add(new JLabel("Tipo:"), gbc);
        gbc.gridx++;
        createLeituraCard.add(typeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        createLeituraCard.add(new JLabel("Status"), gbc);
        gbc.gridx++;
        createLeituraCard.add(statusBox, gbc);

        JButton addButton = new JButton("Adicionar");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        createLeituraCard.add(addButton, gbc);

        addButton.addActionListener(e -> {
            try {
                Autor autor = null;
                for (Autor a : authorsList)
                    if (a.getAuthorName().equals(autorBox.getSelectedItem())) autor = a;

                if (autor != null) {

                    Usuario usuario;
                    try {
                        usuario = usuarioController.getUsuario();
                    } catch (NullPointerException exception) {
                        log.error("Erro: {}", exception.getMessage());
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                        return;
                    }

                    leituraController.saveLeitura(
                            usuario,
                            titleField.getText(),
                            autor,
                            pagesQtdField.getText(),
                            typeBox.getSelectedItem(),
                            statusBox.getSelectedItem()
                    );
                    JOptionPane.showMessageDialog(null, "Leitura adicionada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um autor válido.");
                }

            } catch (Exception ex) {
                log.error("Erro ao adicionar leitura: {}", ex.getMessage(), ex);
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar a leitura. Tente Novamente");
            } finally {
                titleField.setText("");
                pagesQtdField.setText("");
                typeBox.setSelectedIndex(-1);
                statusBox.setSelectedIndex(-1);
            }

        });

        cardPanel.add(createLeituraCard, "createLeituraCard");

    }

    public void setUpdateLeituraPanel(JPanel cardPanel) {
        JPanel updateLeituraCard = new JPanel();

        List<Autor> authorsList = autorController.getAllAuthors();
        GridBagConstraints gbc = new GridBagConstraints();
        updateLeituraCard.setLayout(new GridBagLayout());

        pagesQtdField = new JTextField("");

        Usuario usuario;
        try {
            usuario = usuarioController.getUsuario();
        } catch (NullPointerException e) {
            log.error("Erro: {}", e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }

        List<Leitura> leiturasList = leituraController.getAllLeiturasById(usuario);

        Object[] bookTypes = Arrays.stream(BookType.values()).map(BookType::getType).toArray();
        Object[] bookStatus = Arrays.stream(BookStatus.values()).map(BookStatus::getStatus).toArray();
        titleBox = new JComboBox<>(leiturasList.stream().map(Leitura::getTitle).toArray());
        autorBox = new JComboBox<>(authorsList.stream().map(Autor::getAuthorName).toArray());
        typeBox = new JComboBox<>(bookTypes);
        statusBox = new JComboBox<>(bookStatus);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        updateLeituraCard.add(new JLabel("Título"), gbc);
        gbc.gridx++;
        updateLeituraCard.add(titleBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        updateLeituraCard.add(new JLabel("Nome do Autor:"), gbc);
        gbc.gridx++;
        updateLeituraCard.add(autorBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        updateLeituraCard.add(new JLabel("Quantidade de Páginas:"), gbc);
        gbc.gridx++;
        updateLeituraCard.add(pagesQtdField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        updateLeituraCard.add(new JLabel("Tipo:"), gbc);
        gbc.gridx++;
        updateLeituraCard.add(typeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        updateLeituraCard.add(new JLabel("Status"), gbc);
        gbc.gridx++;
        updateLeituraCard.add(statusBox, gbc);

        titleBox.setSelectedIndex(-1);
        titleBox.addItemListener(e -> {
            Leitura leitura = leituraController.getLeituraByTitle((String) titleBox.getSelectedItem());
            pagesQtdField.setText(leitura.getPagesQtd().toString());
            statusBox.setSelectedIndex(-1);
            typeBox.setSelectedIndex(-1);
            autorBox.setSelectedIndex(-1);
        });

        JButton updateButton = new JButton("Atualizar");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        updateLeituraCard.add(updateButton, gbc);

        updateButton.addActionListener(e -> {
            try {
                String selectedTitle = (String) titleBox.getSelectedItem();
                String selectedAuthorName = (String) autorBox.getSelectedItem();

                Leitura selectedLeitura = leiturasList.stream().filter(leitura -> leitura.getTitle().equals(selectedTitle)).findFirst().orElse(null);

                Autor selectedAutor = authorsList.stream().filter(autor -> autor.getAuthorName().equals(selectedAuthorName)).findFirst().orElse(null);

                if (selectedLeitura != null && selectedAutor != null) {
                    selectedLeitura.setAutor(selectedAutor);
                    selectedLeitura.setPagesQtd(Integer.parseInt(pagesQtdField.getText()));
                    selectedLeitura.setType(BookType.getByType((String) typeBox.getSelectedItem()));
                    selectedLeitura.setStatus(BookStatus.getByStatus((String) statusBox.getSelectedItem()));

                    leituraController.saveLeitura(selectedLeitura);

                    JOptionPane.showMessageDialog(null, "Leitura atualizada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao encontrar a leitura ou autor correspondente.");
                }
            } catch (Exception exception) {
                log.error("Erro ao atualizar leitura: {}", exception.getMessage(), exception);
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar a leitura. Tente novamente.");
            } finally {
                titleBox.setSelectedIndex(-1);
                pagesQtdField.setText("");
                statusBox.setSelectedIndex(-1);
                typeBox.setSelectedIndex(-1);
                autorBox.setSelectedIndex(-1);
            }
        });

        cardPanel.add(updateLeituraCard, "updateLeituraCard");

    }

    public void setListarTodasLeiturasViewPanel(JPanel cardPanel) {
        JPanel viewLeiturasCard = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Todas as Leituras");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Usuario usuario;
        try {
            usuario = usuarioController.getUsuario();
        } catch (NullPointerException e) {
            log.error("Erro: {}", e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }

        viewLeiturasCard.add(label, BorderLayout.NORTH);
        List<Leitura> leituras = leituraController.getAllLeiturasById(usuario);
        log.info("Leituras: {}", leituras);
        updateButtonPanel(scrollPane, leituras);
        viewLeiturasCard.add(scrollPane, BorderLayout.CENTER);

        cardPanel.add(viewLeiturasCard, "viewLeiturasCard");
    }

    public void setListarLeiturasAndamentoViewPanel(JPanel cardPanel) {
        JPanel viewLeiturasCard = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Listagem de Leituras em Andamento");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Usuario usuario;
        try {
            usuario = usuarioController.getUsuario();
        } catch (NullPointerException e) {
            log.error("Erro: {}", e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }

        viewLeiturasCard.add(label, BorderLayout.NORTH);
        List<Leitura> leiturasAndamento = leituraController.getLeiturasEmAndamentoById(usuario);
        log.info("Leituras em andamento: {}", leiturasAndamento);
        updateButtonPanel(scrollPane, leiturasAndamento);
        viewLeiturasCard.add(scrollPane, BorderLayout.CENTER);

        cardPanel.add(viewLeiturasCard, "viewLeiturasCard");
    }

    public void setListarLeiturasConcluidasViewPanel(JPanel cardPanel) {
        JPanel viewLeiturasCard = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Listagem de Leituras Concluídas");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Usuario usuario;
        try {
            usuario = usuarioController.getUsuario();
        } catch (NullPointerException e) {
            log.error("Erro: {}", e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }

        viewLeiturasCard.add(label, BorderLayout.NORTH);
        List<Leitura> leiturasConcluidas = leituraController.getLeiturasConcluidasById(usuario);
        log.info("Leituras concluídas: {}", leiturasConcluidas);
        updateButtonPanel(scrollPane, leiturasConcluidas);
        viewLeiturasCard.add(scrollPane, BorderLayout.CENTER);

        cardPanel.add(viewLeiturasCard, "viewLeiturasCard");
    }

    public void setListarLeiturasNaoLidasViewPanel(JPanel cardPanel) {
        JPanel viewLeiturasCard = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Listagem de Leituras não Lidas");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Usuario usuario;
        try {
            usuario = usuarioController.getUsuario();
        } catch (NullPointerException e) {
            log.error("Erro: {}", e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }

        viewLeiturasCard.add(label, BorderLayout.NORTH);
        List<Leitura> leiturasNaoLidas = leituraController.getLeiturasNaoLidasById(usuario);
        log.info("Leituras não lidas: {}", leiturasNaoLidas);
        updateButtonPanel(scrollPane, leiturasNaoLidas);
        viewLeiturasCard.add(scrollPane, BorderLayout.CENTER);

        cardPanel.add(viewLeiturasCard, "viewLeiturasCard");
    }

    public void setListarLeiturasAbandonadasViewPanel(JPanel cardPanel) {
        JPanel viewLeiturasCard = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Listagem de Leituras Abandonadas");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Usuario usuario;
        try {
            usuario = usuarioController.getUsuario();
        } catch (NullPointerException e) {
            log.error("Erro: {}", e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }

        viewLeiturasCard.add(label, BorderLayout.NORTH);
        List<Leitura> leiturasAbandonadas = leituraController.getLeiturasAbandonadasById(usuario);
        log.info("Leituras abandonadas: {}", leiturasAbandonadas);
        updateButtonPanel(scrollPane, leiturasAbandonadas);
        viewLeiturasCard.add(scrollPane, BorderLayout.CENTER);

        cardPanel.add(viewLeiturasCard, "viewLeiturasCard");
    }

}
