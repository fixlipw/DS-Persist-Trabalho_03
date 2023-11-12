package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.AutorController;
import com.ufc.dspersist.model.Autor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutorPanel extends JPanel {

    private AutorController autorController;

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

        autorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPopupMenu(autorButton, autor);
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

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para atualizar descrição
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        null, "Tem certeza que deseja deletar este autor?",
                        "Confirmação", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {

                }
            }
        });

        popupMenu.add(update);
        popupMenu.add(delete);
        popupMenu.show(autorButton, autorButton.getWidth(), 0);
    }
}
