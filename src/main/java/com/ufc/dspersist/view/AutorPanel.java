package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.AutorController;
import com.ufc.dspersist.model.Autor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

@Slf4j
public class AutorPanel extends JPanel {

    private final AutorController autorController;

    public AutorPanel(AutorController autorController) {
        this.autorController = autorController;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        List<Autor> autores = autorController.getAllAuthors();

        JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT));
        line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        for (Autor autor : autores) {
            JButton autorButton = createAutorButton(autor);
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
    }

    private JButton createAutorButton(Autor autor) {
        JButton autorButton = new JButton(autor.getAuthorName());
        autorButton.setPreferredSize(new Dimension(150, 25));
        autorButton.setBorderPainted(false);

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

        return autorButton;
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
}
