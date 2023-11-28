package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.AutorController;
import com.ufc.dspersist.model.Autor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
public class AutorPanel extends JPanel {

    private final AutorController autorController;

    @Autowired
    public AutorPanel(AutorController autorController) {
        this.autorController = autorController;
    }

    private void setShowPanelButtons(JScrollPane scrollPane) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT));
        line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        List<Autor> autores = autorController.getAllAuthors();

        for (Autor autor : autores) {
            JButton autorButton = new JButton(autor.getAuthorName());
            autorButton.setBorderPainted(false);
            autorButton.setPreferredSize(new Dimension(150, 25));

            autorButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        showPopupMenu(autorButton, autor);
                    }
                }
            });

            autorButton.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    autorButton.setToolTipText(autor.getBrief());
                }
            });

            line.add(autorButton);

            if (line.getComponentCount() == 3) {
                add(line);
                line = new JPanel(new FlowLayout(FlowLayout.LEFT));
                line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            }
        }

        if (line.getComponentCount() > 0) {
            add(line);
        }

        scrollPane.createVerticalScrollBar();
        scrollPane.setViewportView(contentPanel);
    }


    private void showPopupMenu(JButton autorButton, Autor autor) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem update = new JMenuItem("Atualizar Descrição");
        JMenuItem delete = new JMenuItem("Deletar Autor");

        update.addActionListener(e -> {
            String newDescription = JOptionPane.showInputDialog("Insira a nova descrição:");
            if (newDescription != null && !newDescription.isEmpty()) {
                autorController.updateAutor(autor, newDescription);
                JOptionPane.showMessageDialog(null, "Descrição atualizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "A descrição não pode estar vazia. Tente novamente.");
            }
        });

        delete.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar este autor?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    autorController.deleteAutor(autor);
                    JOptionPane.showMessageDialog(null, "Autor deletado com sucesso!");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Erro ao deletar autor. Consulte o log para mais informações.");
                    log.error("Erro ao deletar autor: {}", exception.getMessage(), exception);
                }
            }
        });

        popupMenu.add(update);
        popupMenu.add(delete);
        popupMenu.show(autorButton, autorButton.getWidth(), 0);
    }

    public void setListarTodosAutoresViewPanel(JPanel cardPanel) {
        JPanel readAutoresCard = new JPanel(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Todos os autores");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        readAutoresCard.add(label, BorderLayout.NORTH);

        setShowPanelButtons(scrollPane);
        readAutoresCard.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(readAutoresCard, "readAutoresCard");
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
