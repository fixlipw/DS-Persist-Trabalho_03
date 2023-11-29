package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.AnotacaoController;
import com.ufc.dspersist.controller.LeituraController;
import com.ufc.dspersist.controller.UsuarioController;
import com.ufc.dspersist.model.Anotacao;
import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Slf4j
@Component
public class AnotacaoPainel extends JPanel {

    private final AnotacaoController anotacaoController;
    private final UsuarioController usuarioController;
    private final LeituraController leituraController;

    private JPanel contentPanel;

    @Autowired
    public AnotacaoPainel(AnotacaoController anotacaoController, UsuarioController usuarioController, LeituraController leituraController) {
        this.anotacaoController = anotacaoController;
        this.usuarioController = usuarioController;
        this.leituraController = leituraController;
    }

    private void setShowPanelButtons(JScrollPane scrollPane) {

        Usuario usuario = usuarioController.getUsuario();
        List<Leitura> leituras = leituraController.getAllLeiturasById(usuario);

        int totalAnotacoes = leituras.stream()
                .mapToInt(leitura -> anotacaoController.getAllAnottation(leitura).size())
                .sum();

        contentPanel = new JPanel();
        int line = (int) Math.ceil(totalAnotacoes / 3.0);
        contentPanel.setLayout(new GridLayout(line, 3));

        for (Leitura leitura : leituras) {
            List<Anotacao> anotacoes = anotacaoController.getAllAnottation(leitura);
            if (anotacoes == null) continue;
            for (var anotacao : anotacoes) {
                JButton anottacionButton = new JButton("<html>" + leitura.getTitle() + "<br><b>Data:</b> " + anotacao.getDate() + "</html>");
                anottacionButton.setBorderPainted(false);
                anottacionButton.setPreferredSize(new Dimension(150, 75));

                anottacionButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            setPopupMenu(scrollPane, anottacionButton, anotacao, leitura);
                        } else if (e.getButton() == MouseEvent.BUTTON1) {
                            JOptionPane.showMessageDialog(null, "<html>" + "<b>Data:</b> " + anotacao.getDate() + "<br><b>Anotação:</b> " + anotacao.getAnnotation() + "</html>", "Informações", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

                contentPanel.add(anottacionButton);
            }

            scrollPane.createVerticalScrollBar();
            scrollPane.setViewportView(contentPanel);
        }
    }

    private void setPopupMenu(JScrollPane scrollPane, JButton anottacionButton, Anotacao anotacao, Leitura leitura) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem annotate = new JMenuItem("Atualizar Anotação");
        JMenuItem delete = new JMenuItem("Deletar Anotação");

        annotate.addActionListener(actionListener -> {
            String annotation = JOptionPane.showInputDialog("Digite sua anotação:");

            if (annotation != null) {
                anotacao.setAnnotation(annotation);
                try {
                    anotacaoController.saveAnotacao(anotacao, leitura, annotation);
                    JOptionPane.showMessageDialog(null, "Anotação atualizada com sucesso!");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar anotação: " + exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    log.error("Erro ao atualizar anotação: {}", exception.getMessage(), exception);
                }
            }
        });
        delete.addActionListener(actionListener -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar a anotação?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    anotacaoController.deleteAnotacao(anotacao);

                    Usuario usuario = usuarioController.getUsuario();
                    List<Leitura> updatedList = leituraController.getAllLeiturasById(usuario);
                    updateContentPanel(scrollPane, updatedList);

                    JOptionPane.showMessageDialog(null, "Anotação excluída com sucesso!");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir anotação. Consulte o log para mais informações.");
                    log.error("Erro ao excluir anotação: {}", exception.getMessage(), exception);
                }
            }
        });

        popupMenu.add(annotate);
        popupMenu.add(delete);
        popupMenu.show(anottacionButton, anottacionButton.getWidth(), 0);
    }

    public void updateContentPanel(JScrollPane scrollPane, List<Leitura> newLeituras) {
        contentPanel.removeAll();
        int rowNUmber = (int) Math.ceil(newLeituras.size() / 3.0);
        contentPanel.setLayout(new GridLayout(rowNUmber, 3));

        for (Leitura leitura : newLeituras) {
            List<Anotacao> anotacoes = anotacaoController.getAllAnottation(leitura);
            if (anotacoes == null) continue;
            for (var anotacao : anotacoes) {
                JButton anottacionButton = new JButton("<html>" + leitura.getTitle() + "<br><b>Data:</b> " + anotacao.getDate() + "</html>");
                anottacionButton.setBorderPainted(false);
                anottacionButton.setPreferredSize(new Dimension(150, 75));

                anottacionButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            setPopupMenu(scrollPane, anottacionButton, anotacao, leitura);
                        } else if (e.getButton() == MouseEvent.BUTTON1) {
                            JOptionPane.showMessageDialog(null, "<html>" + "<b>Data:</b> " + anotacao.getDate() + "<br><b>Anotação:</b> " + anotacao.getAnnotation() + "</html>", "Informações", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

                contentPanel.add(anottacionButton);
            }

            scrollPane.createVerticalScrollBar();
            scrollPane.setViewportView(contentPanel);

            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    public void setListarTodasAnotacoesViewPanel(JPanel cardPanel) {
        JPanel readAnotacaoCard = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Todas as Anotações");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        readAnotacaoCard.add(label, BorderLayout.NORTH);

        setShowPanelButtons(scrollPane);
        readAnotacaoCard.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(readAnotacaoCard, "readAnotacaoCard");
    }
}