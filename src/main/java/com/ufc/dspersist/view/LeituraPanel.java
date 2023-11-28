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
public class LeituraPanel extends JPanel {

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

    @Autowired
    public LeituraPanel(UsuarioController usuarioController, LeituraController leituraController, AnotacaoController anotacaoController, AutorController autorController) {
        this.usuarioController = usuarioController;
        this.leituraController = leituraController;
        this.anotacaoController = anotacaoController;
        this.autorController = autorController;
    }

    private void setShowPanelButtons(JScrollPane scrollPane, List<Leitura> leituras) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT));
        line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        for (Leitura leitura : leituras) {
            JButton leituraButton = new JButton(leitura.getTitle());
            leituraButton.setBorderPainted(false);
            leituraButton.setPreferredSize(new Dimension(150, 75));

            leituraButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        showPopupMenu(leituraButton, leitura);
                    } else if (e.getButton() == MouseEvent.BUTTON1) {
                        JOptionPane.showMessageDialog(null,
                                "<html>" + leitura.getTitle() +
                                        "<br><b>ID:</b> " + leitura.getId() +
                                        "<br><b>Autor:</b> " + leitura.getAuthorname() +
                                        "<br><b>Páginas:</b> " + leitura.getPagesQtd() +
                                        "<br><b>Tipo:</b> " + leitura.getType().getType() +
                                        "<br><b>Status:</b> " + leitura.getStatus().getStatus() +
                                        "</html>", "Informações", JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            });

            line.add(leituraButton);

            if (line.getComponentCount() == 3) {
                contentPanel.add(line);
                line = new JPanel(new FlowLayout(FlowLayout.LEFT));
                line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
            }
        }

        if (line.getComponentCount() > 0) {
            contentPanel.add(line);
        }

        scrollPane.createVerticalScrollBar();
        scrollPane.setViewportView(contentPanel);
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

                    JOptionPane.showMessageDialog(this, "Leitura adicionada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Selecione um autor válido.");
                }

            } catch (Exception ex) {
                log.error("Erro ao adicionar leitura: {}", ex.getMessage(), ex);
                JOptionPane.showMessageDialog(this, "Ocorreu um erro ao adicionar a leitura. Tente Novamente");
            }

        });

        cardPanel.add(createLeituraCard, "createLeituraCard");

    }

    public void setUpdateLeituraPanel(JPanel cardPanel) {
        JPanel updateLeituraCard = new JPanel();

        List<Autor> authorsList = autorController.getAllAuthors();
        GridBagConstraints gbc = new GridBagConstraints();
        updateLeituraCard.setLayout(new GridBagLayout());

        pagesQtdField = new JTextField("0");

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

                    JOptionPane.showMessageDialog(this, "Leitura atualizada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao encontrar a leitura ou autor correspondente.");
                }
            } catch (Exception exception) {
                log.error("Erro ao atualizar leitura: {}", exception.getMessage(), exception);
                JOptionPane.showMessageDialog(this, "Ocorreu um erro ao atualizar a leitura. Tente novamente.");
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
        setShowPanelButtons(scrollPane, leituras);
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
        setShowPanelButtons(scrollPane, leiturasAndamento);
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
        setShowPanelButtons(scrollPane, leiturasConcluidas);
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
        setShowPanelButtons(scrollPane, leiturasNaoLidas);
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
        setShowPanelButtons(scrollPane, leiturasAbandonadas);
        viewLeiturasCard.add(scrollPane, BorderLayout.CENTER);

        cardPanel.add(viewLeiturasCard, "viewLeiturasCard");
    }

    private void showPopupMenu(JButton leituraButton, Leitura leitura) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem annotate = new JMenuItem("Criar Anotação");
        JMenuItem delete = new JMenuItem("Deletar Leitura");

        annotate.addActionListener(actionListener -> {
            try {
                String annotation = JOptionPane.showInputDialog("Digite sua anotação:");
                anotacaoController.saveAnotacao(new Anotacao(), leitura, annotation);
                JOptionPane.showMessageDialog(null, "Anotação adicionada com sucesso.");
                log.info("Info: Anotação adicionada com sucesso.");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Erro ao adicionar anotação. Tente Novamente.");
                    log.error("Erro: {}", exception.getMessage(), exception);
            }
        });
        delete.addActionListener(actionListener -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar a leitura: \"" + leitura.getTitle() + "\"", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    leituraController.deleteLeitura(leitura);
                    JOptionPane.showMessageDialog(null, "Leitura excluída com sucesso!");
                    log.info("Info: Leitura " + leitura.getTitle() + " excluída com sucesso.");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir leitura. Tente novamente.");
                    log.error("Erro ao excluir leitura: {}", exception.getMessage(), exception);
                }
            }
        });
        popupMenu.add(annotate);
        popupMenu.add(delete);
        popupMenu.show(leituraButton, leituraButton.getWidth(), 0);
    }

}
