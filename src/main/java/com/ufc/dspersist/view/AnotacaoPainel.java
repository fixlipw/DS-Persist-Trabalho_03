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
public class AnotacaoPainel {

    private final AnotacaoController anotacaoController;
    private final UsuarioController usuarioController;
    private final LeituraController leituraController;

    private final JPanel buttonPanel;

    @Autowired
    public AnotacaoPainel(AnotacaoController anotacaoController, UsuarioController usuarioController, LeituraController leituraController) {
        this.anotacaoController = anotacaoController;
        this.usuarioController = usuarioController;
        this.leituraController = leituraController;
        buttonPanel = new JPanel();
    }

    private void updateButtonPanel(JScrollPane scrollPane, List<Leitura> newLeiturasAnotacoes) {
        buttonPanel.removeAll();
        int rowNUmber = (int) Math.ceil(newLeiturasAnotacoes.size() / 3.0);
        buttonPanel.setLayout(new GridLayout(rowNUmber, 3));

        for (Leitura leitura : newLeiturasAnotacoes) {
            List<Anotacao> anotacoes = anotacaoController.getAllAnottation(leitura);
            if (anotacoes == null) continue;
            for (var anotacao : anotacoes) {
                JButton anottacionButton = createAnotacaoButton(anotacao, leitura, scrollPane);
                buttonPanel.add(anottacionButton);
            }

            scrollPane.createVerticalScrollBar();
            scrollPane.setViewportView(buttonPanel);

            buttonPanel.revalidate();
            buttonPanel.repaint();
        }
    }

    private JButton createAnotacaoButton(Anotacao anotacao, Leitura leitura, JScrollPane scrollPane) {
        JButton anottacionButton = new JButton("<html>" + leitura.getTitle() + "<br><b>Data:</b> " + anotacao.getDate() + "</html>");
        anottacionButton.setBorderPainted(false);
        anottacionButton.setPreferredSize(new Dimension(150, 75));

        anottacionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    doAnotacaoAction(anotacao, leitura, scrollPane);
                }
            }
        });
        return anottacionButton;
    }

    private void doAnotacaoAction(Anotacao anotacao, Leitura leitura, JScrollPane scrollPane) {
        int option = JOptionPane.showOptionDialog(
                null,
                "<html>" + "<b>Data:</b> " + anotacao.getDate() + "<br><b>Anotação:</b> " + anotacao.getAnnotation() + "</html>",
                "Informações",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[] {"Deletar", "Atualizar", "Cancelar"},
                null
        );

        if (option == 0) {
            SwingUtilities.invokeLater(() -> {
                int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar a anotação?", "Confirmação", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    SwingWorker<Void, Void> worker = new SwingWorker<>() {
                        private Exception exception;

                        @Override
                        protected Void doInBackground() {
                            try {
                                anotacaoController.deleteAnotacao(anotacao);
                            } catch (Exception ex) {
                                exception = ex;
                            }
                            return null;
                        }

                        @Override
                        protected void done() {
                            if (exception != null) {
                                JOptionPane.showMessageDialog(null, "Erro ao excluir anotação. Consulte o log para mais informações.");
                                log.error("Erro ao excluir anotação: {}", exception.getMessage(), exception);
                            } else {
                                SwingWorker<List<Leitura>, Void> dataWorker = new SwingWorker<>() {

                                    @Override
                                    protected List<Leitura> doInBackground() {
                                        Usuario usuario = usuarioController.getUsuario();
                                        return leituraController.getAllLeiturasById(usuario);
                                    }

                                    @Override
                                    protected void done() {
                                        try {
                                            List<Leitura> newAnotacaoList = get();
                                            updateButtonPanel(scrollPane, newAnotacaoList);
                                            JOptionPane.showMessageDialog(null, "Anotação excluída com sucesso!");
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
        }
    }


    public void setListarTodasAnotacoesViewPanel(JPanel cardPanel) {
        JPanel readAnotacaoCard = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Todas as Anotações");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        readAnotacaoCard.add(label, BorderLayout.NORTH);

        Usuario usuario = usuarioController.getUsuario();
        List<Leitura> leituras = leituraController.getAllLeiturasById(usuario);

        updateButtonPanel(scrollPane, leituras);
        readAnotacaoCard.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(readAnotacaoCard, "readAnotacaoCard");
    }
}