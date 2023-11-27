package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.AnotacaoController;
import com.ufc.dspersist.controller.AutorController;
import com.ufc.dspersist.controller.LeituraController;
import com.ufc.dspersist.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Slf4j
@Component
public class MainFrame extends JFrame {

    private Usuario usuario;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JMenuItem createLeituraMenuItem;
    private JMenuItem listarTodasLeiturasItem;
    private JMenuItem listarEmAndamentoItem;
    private JMenuItem listarConcluidasItem;
    private JMenuItem listarNaoLidoItem;
    private JMenuItem listarAbandonadoItem;
    private JMenuItem updateLeituraMenuItem;
    private JMenuItem usuarioMenuItem;
    private JMenuItem readAnotacaoMenuItem;
    private JMenuItem createAutorMenuItem;
    private JMenuItem readAutorMenuItem;

    private LeituraController leituraController;
    private AutorController autorController;
    private AnotacaoController anotacaoController;

    private UsuarioPanel usuarioPanel;
    private LeituraPanel leituraPanel;
    private AutorPanel autorPanel;
    private AnotacaoPainel anotacaoPanel;

    @Autowired
    private void setControllers(LeituraController leituraController, AutorController autorController, AnotacaoController anotacaoController) {
        this.leituraController = leituraController;
        this.autorController = autorController;
        this.anotacaoController = anotacaoController;
    }

    @Autowired
    private void setPanels(UsuarioPanel usuarioPanel, LeituraPanel leituraPanel) {
        this.usuarioPanel = usuarioPanel;
        this.leituraPanel = leituraPanel;
    }

    public MainFrame() {
        super("Tela inicial");
        setSize(new Dimension(500, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

        setResizable(false);
        setVisible(false);
    }


    public void setLoggedUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private void setMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu usuarioMenu = new JMenu("Usuário");
        JMenu leiturasMenu = new JMenu("Leituras");
        JMenu anotacoesMenu = new JMenu("Anotações");
        JMenu autoresMenu = new JMenu("Autores");

        usuarioMenuItem = new JMenuItem("Perfil de Usuário");

        createLeituraMenuItem = new JMenuItem("Criar Leitura");
        JMenu readLeituraMenuItem = new JMenu("Visualizar Leituras");
        listarTodasLeiturasItem = new JMenuItem("Todas as Leituras");
        listarEmAndamentoItem = new JMenuItem("Leituras em Andamento");
        listarConcluidasItem = new JMenuItem("Leituras Concluídas");
        listarNaoLidoItem = new JMenuItem("Leituras Não Lidas");
        listarAbandonadoItem = new JMenuItem("Leituras Abandonadas");
        readLeituraMenuItem.add(listarTodasLeiturasItem);
        readLeituraMenuItem.add(listarEmAndamentoItem);
        readLeituraMenuItem.add(listarConcluidasItem);
        readLeituraMenuItem.add(listarNaoLidoItem);
        readLeituraMenuItem.add(listarAbandonadoItem);
        updateLeituraMenuItem = new JMenuItem("Atualizar Leitura");

        readAnotacaoMenuItem = new JMenuItem("Visualizar Minhas Anotações");
        createAutorMenuItem = new JMenuItem("Inserir Autores");
        readAutorMenuItem = new JMenuItem("Visualizar Autores");

        usuarioMenu.add(usuarioMenuItem);

        leiturasMenu.add(createLeituraMenuItem);
        leiturasMenu.add(readLeituraMenuItem);
        leiturasMenu.add(updateLeituraMenuItem);
        anotacoesMenu.add(readAnotacaoMenuItem);
        autoresMenu.add(createAutorMenuItem);
        autoresMenu.add(readAutorMenuItem);

        menuBar.add(usuarioMenu);
        menuBar.add(leiturasMenu);
        menuBar.add(anotacoesMenu);
        menuBar.add(autoresMenu);

        setJMenuBar(menuBar);
    }

    private void setCardPanel() {
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        JPanel anotacoesCard = new JPanel();
        cardPanel.add(anotacoesCard, "anotacoesCard");

        usuarioMenuItem.addActionListener(e -> {
            usuarioPanel.setUsuario(usuario);
            cardPanel.add(usuarioPanel, "usuarioInfoPanel");
            cardLayout.show(cardPanel, "usuarioInfoPanel");
        });
        createLeituraMenuItem.addActionListener(e -> {
            leituraPanel.setCardPanel(cardPanel);
            leituraPanel.setCreateLeiturasPanel();
            cardLayout.show(cardPanel, "createLeituraCard");
        });
        updateLeituraMenuItem.addActionListener(e -> {
            leituraPanel.setCardPanel(cardPanel);
            leituraPanel.setUpdateLeituraPanel();
            cardLayout.show(cardPanel, "updateLeituraCard");
        });
        listarTodasLeiturasItem.addActionListener(e -> {
            leituraPanel.setCardPanel(cardPanel);
            leituraPanel.setListarTodasLeiturasViewPanel();
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });
        listarEmAndamentoItem.addActionListener(e -> {
            leituraPanel.setCardPanel(cardPanel);
            leituraPanel.setListarLeiturasAndamentoViewPanel();
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });
        listarConcluidasItem.addActionListener(e -> {
            leituraPanel.setCardPanel(cardPanel);
            leituraPanel.setListarLeiturasConcluidasViewPanel();
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });
        createAutorMenuItem.addActionListener(e -> {
            setCreateAutoresCard();
            cardLayout.show(cardPanel, "createAutoresCard");
        });
        readAutorMenuItem.addActionListener(e -> {
            setReadAutoresCard();
            cardLayout.show(cardPanel, "readAutoresCard");
        });
        readAnotacaoMenuItem.addActionListener(e -> {
            setReadAnotacaoCard();
            cardLayout.show(cardPanel, "readAnotacaoCard");
        });
        listarNaoLidoItem.addActionListener(e -> {
            leituraPanel.setCardPanel(cardPanel);
            leituraPanel.setListarLeiturasNaoLidasViewPanel();
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });
        listarAbandonadoItem.addActionListener(e -> {
            leituraPanel.setCardPanel(cardPanel);
            leituraPanel.setListarLeiturasAbandonadasViewPanel();
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });

    }

    private void setCreateAutoresCard() {
        JPanel autoresPanel = new JPanel();

        JLabel autorNameLabel = new JLabel("Nome do Autor:");
        JLabel autorBriefLabel = new JLabel("Breve Descrição:");

        JTextField authorNameField = new JTextField();
        JTextArea autorBriefTextArea = new JTextArea();
        JButton addButton = new JButton("Adicionar");

        GroupLayout layout = new GroupLayout(autoresPanel);
        autoresPanel.setLayout(layout);

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

        cardPanel.add(autoresPanel, "createAutoresCard");
    }

    private void setReadAutoresCard() {
        autorPanel = new AutorPanel(autorController);
        cardPanel.add(autorPanel, "readAutoresCard");
    }

    private void setReadAnotacaoCard() {
        anotacaoPanel = new AnotacaoPainel(anotacaoController, leituraController);
        cardPanel.add(anotacaoPanel, "readAnotacaoCard");
    }

    private void initComponents() {
        setMenu();
        setCardPanel();
        add(cardPanel, BorderLayout.CENTER);
    }

}
