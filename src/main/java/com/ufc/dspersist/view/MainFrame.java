package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.AnotacaoController;
import com.ufc.dspersist.controller.AutorController;
import com.ufc.dspersist.controller.LeituraController;
import com.ufc.dspersist.controller.UsuarioController;
import com.ufc.dspersist.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class MainFrame extends JFrame {

    private Usuario usuario;
    private JPanel cardPanel;
    private JPanel usuarioInfoPanel;
    private JPanel autoresPanel;
    private JPanel formLeituraPanel;
    private ViewLeituraPanel viewLeituraPanel;
    private CardLayout cardLayout;

    private JMenuItem createLeituraMenuItem;
    private JMenuItem listarTodasLeiturasItem;
    private JMenuItem listarEmAndamentoItem;
    private JMenuItem listarConcluidasItem;
    private JMenuItem listarPorAutorItem;
    private JMenuItem listarPorTituloItem;

    private JMenuItem updateLeituraMenuItem;

    private JMenuItem usuarioMenuItem;
    private JMenuItem readAnotacaoMenuItem;
    private JMenuItem createAutorMenuItem;
    private JMenuItem readAutorMenuItem;

    private UsuarioController usuarioController;
    private LeituraController leituraController;
    private AutorController autorController;
    private AnotacaoController anotacaoController;

    @Autowired
    private void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    @Autowired
    private void setLeituraController(LeituraController leituraController) {
        this.leituraController = leituraController;
    }

    @Autowired
    public void setAutorController(AutorController autorController) {
        this.autorController = autorController;
    }

    @Autowired
    private void setAnotacaoController(AnotacaoController anotacaoController) {
        this.anotacaoController = anotacaoController;
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
        listarPorAutorItem = new JMenuItem("Leituras por Autor");
        listarPorTituloItem = new JMenuItem("Leituras por Título");
        readLeituraMenuItem.add(listarTodasLeiturasItem);
        readLeituraMenuItem.add(listarEmAndamentoItem);
        readLeituraMenuItem.add(listarConcluidasItem);
        readLeituraMenuItem.add(listarPorAutorItem);
        readLeituraMenuItem.add(listarPorTituloItem);
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
            usuarioInfoPanel = new UsuarioPanel(usuario, usuarioController);
            cardPanel.add(usuarioInfoPanel, "usuarioInfoPanel");
            cardLayout.show(cardPanel, "usuarioInfoPanel");
        });
        createLeituraMenuItem.addActionListener(e -> {
            formLeituraPanel = new CreateLeituraPanel(usuario, leituraController, autorController);
            cardPanel.add(formLeituraPanel, "createLeituraCard");
            cardLayout.show(cardPanel, "createLeituraCard");
        });
        updateLeituraMenuItem.addActionListener(e -> {
            formLeituraPanel = new UpdateLeituraPanel(usuario, leituraController, autorController);
            cardPanel.add(formLeituraPanel, "updateLeituraCard");
            cardLayout.show(cardPanel, "updateLeituraCard");
        });
        listarTodasLeiturasItem.addActionListener(e -> {
            viewLeituraPanel = new ViewLeituraPanel(cardPanel, usuario, leituraController, anotacaoController);
            viewLeituraPanel.setListarTodasLeiturasViewPanel();
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });
        listarEmAndamentoItem.addActionListener(e -> {
            viewLeituraPanel = new ViewLeituraPanel(cardPanel, usuario, leituraController, anotacaoController);
            viewLeituraPanel.setListarLeiturasAndamentoViewPanel();
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });
        listarConcluidasItem.addActionListener(e -> {
            viewLeituraPanel = new ViewLeituraPanel(cardPanel, usuario, leituraController, anotacaoController);
            viewLeituraPanel.setListarLeiturasConcluidasViewPanel();
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });
        listarPorTituloItem.addActionListener(e -> {
            viewLeituraPanel = new ViewLeituraPanel(cardPanel, usuario, leituraController, anotacaoController);
            viewLeituraPanel.setListarLeiturasTituloViewPanel();
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });
        listarPorAutorItem.addActionListener(e -> {
            viewLeituraPanel = new ViewLeituraPanel(cardPanel, usuario, leituraController, anotacaoController);
            viewLeituraPanel.setListarLeiturasAutorViewPanel();
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

    }

    private void setCreateAutoresCard() {
        autoresPanel = new JPanel();

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
            String autorName = authorNameField.getText();
            String autorBrief = autorBriefTextArea.getText();
            autorController.saveAutor(autorName, autorBrief);
            authorNameField.setText("");
            autorBriefTextArea.setText("");
        });

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(autorNameLabel).addComponent(autorBriefLabel));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(authorNameField).addComponent(scrollPane).addComponent(addButton));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(autorNameLabel).addComponent(authorNameField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(autorBriefLabel).addComponent(scrollPane));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(addButton));
        layout.setVerticalGroup(vGroup);

        cardPanel.add(autoresPanel, "createAutoresCard");
    }


    private void setReadAutoresCard() {
        autoresPanel = new AutorPanel(autorController);

        JScrollPane scrollPane = new JScrollPane(autoresPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        cardPanel.add(scrollPane, "readAutoresCard");
    }

    private void initComponents() {
        setMenu();
        setCardPanel();
        add(cardPanel, BorderLayout.CENTER);
    }

}
