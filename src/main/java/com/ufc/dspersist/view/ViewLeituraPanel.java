package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.AnotacaoController;
import com.ufc.dspersist.controller.LeituraController;
import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ViewLeituraPanel extends JPanel {

    private final Usuario usuario;
    private final LeituraController leituraController;
    private final AnotacaoController anotacaoController;
    private final JPanel cardPanel;

    public ViewLeituraPanel(JPanel cardPanel, Usuario usuario, LeituraController leituraController, AnotacaoController anotacaoController) {
        this.usuario = usuario;
        this.cardPanel = cardPanel;
        this.leituraController = leituraController;
        this.anotacaoController = anotacaoController;
    }

    private void setShowPanelButtons(JScrollPane scrollPane, List<Leitura> leituras) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT));
        line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        for (Leitura leitura : leituras) {
            JButton leituraButton = new JButton(leitura.getTitle());
            leituraButton.setBorderPainted(false);
            leituraButton.setPreferredSize(new Dimension(150,75));

            leituraButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        JPopupMenu popupMenu = new JPopupMenu();

                        JMenuItem annotate = new JMenuItem("Criar Anotação");
                        JMenuItem delete = new JMenuItem("Deletar Leitura");

                        annotate.addActionListener(actionListener -> {
                            String annotation = JOptionPane.showInputDialog("Digite sua anotação:");
                            anotacaoController.saveAnotacao(leitura, annotation);
                        });
                        delete.addActionListener(actionListener -> {
                            int confirm = JOptionPane.showConfirmDialog(
                                    null, "Tem certeza que deseja deletar a leitura: \"" + leitura.getTitle() + "\"",
                                    "Confirmação", JOptionPane.YES_NO_OPTION);

                            if (confirm == JOptionPane.YES_OPTION) {

                            }
                        });
                        popupMenu.add(annotate);
                        popupMenu.add(delete);
                        popupMenu.show(leituraButton, e.getX(), e.getY());
                    }
                }
            });

            leituraButton.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    leituraButton.setToolTipText("<html>" + leitura.getTitle() +
                            "<br><b>ID:</b> " + leitura.getId() +
                            "<br><b>Autor:</b> " + leitura.getAuthorname() +
                            "<br><b>Páginas:</b> " + leitura.getPagesQtd() +
                            "<br><b>Tipo:</b> " + leitura.getType().getType() +
                            "<br><b>Status:</b> " + leitura.getStatus().getStatus() + "</html>");
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

    public void setListarTodasLeiturasViewPanel() {
        JPanel viewLeiturasCard = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Todas as Leituras");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        viewLeiturasCard.add(label, BorderLayout.NORTH);
        setShowPanelButtons(scrollPane, leituraController.getAllLeiturasById(usuario));
        viewLeiturasCard.add(scrollPane, BorderLayout.CENTER);

        cardPanel.add(viewLeiturasCard, "viewLeiturasCard");
    }

    public void setListarLeiturasAndamentoViewPanel() {
        JPanel viewLeiturasCard = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Listagem de Leituras em Andamento");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        viewLeiturasCard.add(label, BorderLayout.NORTH);
        setShowPanelButtons(scrollPane, new ArrayList<>());

        viewLeiturasCard.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(viewLeiturasCard, "viewLeiturasCard");
    }

    public void setListarLeiturasConcluidasViewPanel() {
        JPanel viewLeiturasCard = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Listagem de Leituras Concluídas");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        viewLeiturasCard.add(label, BorderLayout.NORTH);
        setShowPanelButtons(scrollPane, new ArrayList<>());

        viewLeiturasCard.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(viewLeiturasCard, "viewLeiturasCard");
    }


    public void setListarLeiturasAutorViewPanel() {
        JPanel viewLeiturasCard = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Listagem de Leituras por Autor");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        String authorName = JOptionPane.showInputDialog(this, "Insira o nome do autor:");

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        viewLeiturasCard.add(label, BorderLayout.NORTH);
        setShowPanelButtons(scrollPane, new ArrayList<>());

        viewLeiturasCard.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(viewLeiturasCard, "viewLeiturasCard");
    }

    public void setListarLeiturasTituloViewPanel() {
        JPanel viewLeiturasCard = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Listagem de Leituras por Título");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        String titulo = JOptionPane.showInputDialog(this, "Insira o título da leitura:");

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        viewLeiturasCard.add(label, BorderLayout.NORTH);
        setShowPanelButtons(scrollPane, new ArrayList<>());

        viewLeiturasCard.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(viewLeiturasCard, "viewLeiturasCard");
    }



}
