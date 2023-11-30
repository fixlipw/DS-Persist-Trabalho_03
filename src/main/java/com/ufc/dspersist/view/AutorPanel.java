package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.AutorController;
import com.ufc.dspersist.model.Autor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
public class AutorPanel {

    private final AutorController autorController;

    private final JPanel buttonPanel;

    @Autowired
    public AutorPanel(AutorController autorController) {
        this.autorController = autorController;
        buttonPanel = new JPanel();
    }


    private void updateButtonPanel(JScrollPane scrollPane, List<Autor> newAutores) {
        buttonPanel.removeAll();

        int line = Math.max(3, (int) Math.ceil(newAutores.size() / 3.0));
        buttonPanel.setLayout(new GridLayout(line, 3));

        for (Autor autor : newAutores) {
            JButton autorButton = createAutorButton(autor, scrollPane);
            buttonPanel.add(autorButton);
        }
        scrollPane.createVerticalScrollBar();
        scrollPane.setViewportView(buttonPanel);

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private JButton createAutorButton(Autor autor, JScrollPane scrollPane) {
        JButton autorButton = new JButton(autor.getAuthorName());
        autorButton.setBorderPainted(false);
        autorButton.setPreferredSize(new Dimension(150, 75));

        autorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    doAutorAction(autor, scrollPane);
                }
            }
        });

        return autorButton;
    }

    private void doAutorAction(Autor autor, JScrollPane scrollPane) {
        int option = JOptionPane.showOptionDialog(
                null,
                "<html>" + autor.getAuthorName() + "<br><b>Descrição:</b> " + autor.getBrief() + "</html>",
                "Informações",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[] {"Deletar", "Atualizar", "Cancelar"},
                null
        );

        if (option == 0) {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Tem certeza que deseja deletar este autor?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(() -> {
                    SwingWorker<Void, Void> worker = new SwingWorker<>() {
                        private Exception exception;

                        @Override
                        protected Void doInBackground() {
                            try {
                                autorController.deleteAutor(autor);
                            } catch (Exception ex) {
                                exception = ex;
                            }
                            return null;
                        }

                        @Override
                        protected void done() {
                            if (exception != null) {
                                JOptionPane.showMessageDialog(null,
                                        "Erro ao deletar autor. Consulte o log para mais informações.");
                                log.error("Erro ao deletar autor: {}", exception.getMessage(), exception);
                            } else {
                                SwingWorker<List<Autor>, Void> dataWorker = new SwingWorker<>() {
                                    @Override
                                    protected List<Autor> doInBackground() {
                                        return autorController.getAllAuthors();
                                    }

                                    @Override
                                    protected void done() {
                                        try {
                                            List<Autor> newAutorlist = get();
                                            updateButtonPanel(scrollPane, newAutorlist);
                                            JOptionPane.showMessageDialog(null,
                                                    "Autor deletado com sucesso!");
                                        } catch (Exception ex) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Erro ao atualizar a lista. Consulte o log para mais informações.");
                                            log.error("Erro ao atualizar a lista: {}", ex.getMessage(), ex);
                                        }
                                    }
                                };
                                dataWorker.execute();
                            }
                        }
                    };
                    worker.execute();
                });
            }
        } else if (option == 1) {
            String newDescription = JOptionPane.showInputDialog("Insira a nova descrição:");
            if (newDescription != null) {
                try {
                    autorController.updateAutor(autor, newDescription);
                    JOptionPane.showMessageDialog(null,
                            "Descrição atualizada com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "A descrição não pode estar vazia",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void setListarTodosAutoresViewPanel(JPanel cardPanel) {
        JPanel contentPanel = new JPanel();

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Todos os Autores");
        label.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.add(label, BorderLayout.NORTH);

        List<Autor> autores = autorController.getAllAuthors();

        updateButtonPanel(scrollPane, autores);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(contentPanel, "readAutoresCard");
    }


    public void setCreateAutoresPanel(JPanel cardPanel) {
        JPanel contentPanel = new JPanel();

        JLabel autorNameLabel = new JLabel("Nome do Autor:");
        JLabel autorBriefLabel = new JLabel("Breve Descrição:");
        JTextField authorNameField = new JTextField();
        JTextArea autorBriefTextArea = new JTextArea();
        JButton addButton = new JButton("Adicionar");

        GroupLayout layout = new GroupLayout(contentPanel);
        contentPanel.setLayout(layout);

        JScrollPane scrollPane = new JScrollPane(autorBriefTextArea);
        autorBriefTextArea.setLineWrap(true);

        addButton.addActionListener(e -> {
            try {
                String autorName = authorNameField.getText();
                String autorBrief = autorBriefTextArea.getText();
                autorController.saveAutor(autorName, autorBrief);
                authorNameField.setText("");
                autorBriefTextArea.setText("");
                JOptionPane.showMessageDialog(null, "Autor adicionado com sucesso.");
                log.info("Autor adicionado com sucesso");
            } catch (Exception ex) {
                log.error("Erro ao adicionar autor: {}", ex.getMessage(), ex);
                JOptionPane.showMessageDialog(null, "Erro ao adicionar autor. Tente Novamente.");
            }
        });

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(autorNameLabel).addComponent(autorBriefLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(authorNameField).addComponent(scrollPane).addComponent(addButton));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(autorNameLabel).addComponent(authorNameField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(autorBriefLabel).addComponent(scrollPane));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(addButton));
        layout.setVerticalGroup(vGroup);

        cardPanel.add(contentPanel, "createAutoresCard");
    }
}
