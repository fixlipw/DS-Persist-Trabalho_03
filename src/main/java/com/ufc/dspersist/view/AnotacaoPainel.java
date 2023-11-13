package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.AnotacaoController;
import com.ufc.dspersist.controller.LeituraController;
import com.ufc.dspersist.model.Anotacao;
import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.model.Usuario;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Slf4j
public class AnotacaoPainel extends JPanel {

    private final Usuario usuario;
    private final AnotacaoController anotacaoController;
    private final LeituraController leituraController;

    public AnotacaoPainel(Usuario usuario, AnotacaoController anotacaoController, LeituraController leituraController) {
        this.usuario = usuario;
        this.anotacaoController = anotacaoController;
        this.leituraController = leituraController;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Todas as Leituras");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        setShowPanelButtons(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

    }

    private void setShowPanelButtons(JScrollPane scrollPane) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT));
        line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        List<Leitura> leituras = leituraController.getAllLeiturasById(usuario);

        for (Leitura leitura : leituras) {
            List<Anotacao> anotacoes = anotacaoController.getAllAnottationByUserId(leitura);
            if (anotacoes == null) continue;
            for (var anotacao : anotacoes) {
                JButton anottacionButton = new JButton("<html>" + leitura.getTitle() + "<br><b>Data:</b> " + anotacao.getDate() + "</html>");
                anottacionButton.setBorderPainted(false);
                anottacionButton.setPreferredSize(new Dimension(150, 75));

                anottacionButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            JPopupMenu popupMenu = new JPopupMenu();

                            JMenuItem annotate = new JMenuItem("Atualizar Anotação");
                            JMenuItem delete = new JMenuItem("Deletar Anotação");

                            annotate.addActionListener(actionListener -> {
                                String annotation = JOptionPane.showInputDialog("Digite sua anotação:");
                                anotacao.setAnnotation(annotation);
                                try {
                                    anotacaoController.saveAnotacao(anotacao, leitura, annotation);
                                    JOptionPane.showMessageDialog(null, "Anotação atualizada com sucesso!");
                                } catch (Exception exception) {
                                    JOptionPane.showMessageDialog(null, "Erro ao atualizar anotação. Consulte o log para mais informações.");
                                    log.error("Erro ao atualizar anotação: {}", exception.getMessage(), exception);
                                }
                            });
                            delete.addActionListener(actionListener -> {
                                int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar a anotação?", "Confirmação", JOptionPane.YES_NO_OPTION);

                                if (confirm == JOptionPane.YES_OPTION) {
                                    try {
                                        anotacaoController.deleteAnotacao(anotacao);
                                        JOptionPane.showMessageDialog(null, "Anotação excluída com sucesso!");
                                    } catch (Exception exception) {
                                        JOptionPane.showMessageDialog(null, "Erro ao excluir anotação. Consulte o log para mais informações.");
                                        log.error("Erro ao excluir anotação: {}", exception.getMessage(), exception);
                                    }
                                }
                            });

                            popupMenu.add(annotate);
                            popupMenu.add(delete);
                            popupMenu.show(anottacionButton, e.getX(), e.getY());
                        }
                    }
                });

                anottacionButton.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        anottacionButton.setToolTipText("<html>" + anotacao.getAnnotation() + "</html>");
                    }
                });

                line.add(anottacionButton);

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
    }


}