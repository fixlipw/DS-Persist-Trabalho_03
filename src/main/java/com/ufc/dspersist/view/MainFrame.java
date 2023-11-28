package com.ufc.dspersist.view;

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

    private UsuarioPanel usuarioPanel;
    private LeituraPanel leituraPanel;
    private AutorPanel autorPanel;
    private AnotacaoPainel anotacaoPanel;

    @Autowired
    private void setPanels(UsuarioPanel usuarioPanel, LeituraPanel leituraPanel, AutorPanel autorPanel, AnotacaoPainel anotacaoPanel) {
        this.usuarioPanel = usuarioPanel;
        this.leituraPanel = leituraPanel;
        this.autorPanel = autorPanel;
        this.anotacaoPanel = anotacaoPanel;
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

        usuarioMenuItem.addActionListener(e -> {
            usuarioPanel.setUsuarioPanel(cardPanel, usuario);
            cardLayout.show(cardPanel, "usuarioInfoPanel");
        });
        createLeituraMenuItem.addActionListener(e -> {
            leituraPanel.setCreateLeiturasPanel(cardPanel);
            cardLayout.show(cardPanel, "createLeituraCard");
        });
        updateLeituraMenuItem.addActionListener(e -> {
            leituraPanel.setUpdateLeituraPanel(cardPanel);
            cardLayout.show(cardPanel, "updateLeituraCard");
        });
        listarTodasLeiturasItem.addActionListener(e -> {
            leituraPanel.setListarTodasLeiturasViewPanel(cardPanel);
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });
        listarEmAndamentoItem.addActionListener(e -> {
            leituraPanel.setListarLeiturasAndamentoViewPanel(cardPanel);
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });
        listarConcluidasItem.addActionListener(e -> {
            leituraPanel.setListarLeiturasConcluidasViewPanel(cardPanel);
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });
        createAutorMenuItem.addActionListener(e -> {
            autorPanel.setCreateAutoresPanel(cardPanel);
            cardLayout.show(cardPanel, "createAutoresCard");
        });
        readAutorMenuItem.addActionListener(e -> {
            autorPanel.setListarTodosAutoresViewPanel(cardPanel);
            cardLayout.show(cardPanel, "readAutoresCard");
        });
        readAnotacaoMenuItem.addActionListener(e -> {
            anotacaoPanel.setListarTodasAnotacoesViewPanel(cardPanel);
            cardLayout.show(cardPanel, "readAnotacaoCard");
        });
        listarNaoLidoItem.addActionListener(e -> {
            leituraPanel.setListarLeiturasNaoLidasViewPanel(cardPanel);
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });
        listarAbandonadoItem.addActionListener(e -> {
            leituraPanel.setListarLeiturasAbandonadasViewPanel(cardPanel);
            cardLayout.show(cardPanel, "viewLeiturasCard");
        });

    }

    private void initComponents() {
        setMenu();
        setCardPanel();
        add(cardPanel, BorderLayout.CENTER);
    }

}
