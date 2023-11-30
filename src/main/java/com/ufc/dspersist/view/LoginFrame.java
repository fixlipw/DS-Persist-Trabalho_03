package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.UsuarioController;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

@Log4j2
@Component
public class LoginFrame extends JFrame {

    private JPanel loginFramePanel;
    private JPanel loginPanel;
    private JPanel signPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTabbedPane tabbedPane;
    private JButton loginButton;
    private JButton signButton;

    private final UsuarioController usuarioController;
    private final MainFrame mainFrame;

    @Autowired
    public LoginFrame(MainFrame mainFrame, UsuarioController usuarioController) {
        super("BookStand");
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("bookstand.png")));
        setIconImage(icon.getImage());
        setSize(new Dimension(500, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.mainFrame = mainFrame;
        this.usuarioController = usuarioController;

        initComponents();
        setTabbedPane();

        setResizable(false);
        setVisible(true);
    }

    private void initComponents() {
        loginFramePanel = new JPanel();
        loginFramePanel.setLayout(new GridBagLayout());
        tabbedPane = new JTabbedPane();
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        signPanel = new JPanel();
        signPanel.setLayout(new GridBagLayout());
        loginButton = new JButton("Fazer Login");
        signButton = new JButton("Fazer Cadastro");
    }

    private void setTabbedPane() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        tabbedPane.addTab("Inicie uma sessão", null, loginPanel);
        tabbedPane.addTab("Faça seu cadastro", null, signPanel);

        loginFramePanel.add(tabbedPane, gbc);

        add(loginFramePanel);

        tabbedPane.setSelectedIndex(-1);

        tabbedPane.addChangeListener(this::handleTabChange);
    }

    private void setLoginPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        setCredentialFields(loginPanel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(loginButton, gbc);

        loginButton.addActionListener(this::handleLoginButton);
    }

    private void setSignPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        setCredentialFields(signPanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        signPanel.add(new JLabel("Confirme a senha:"), gbc);

        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        signPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        signPanel.add(signButton, gbc);

        signButton.addActionListener(this::handleSignButton);
    }

    private void setCredentialFields(JPanel credentialPanel, GridBagConstraints gbc) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        credentialPanel.add(new JLabel("Usuário:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        credentialPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        credentialPanel.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        credentialPanel.add(passwordField, gbc);
    }

    private void handleTabChange(ChangeEvent event) {

        if (tabbedPane.getSelectedIndex() == 0) {
            setLoginPanel();
            signPanel.removeAll();
        } else if (tabbedPane.getSelectedIndex() == 1) {
            setSignPanel();
            loginPanel.removeAll();
        } else {
            loginPanel.removeAll();
            signPanel.removeAll();
        }

    }

    private void handleLoginButton(ActionEvent actionEvent) {
        try {
            var usuario = usuarioController.authUser(usernameField, passwordField);

            if (usuario != null) {
                JOptionPane.showMessageDialog(null, "Bem-vindo(a), " + usuario.getUsername());
                log.info("Info: Usuário autenticado com sucesso.");
                mainFrame.setLoggedUsuario(usuario);
                setVisible(false);
                mainFrame.setVisible(true);
            }
        } catch (Exception e) {
            log.error("Erro: {}", e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void handleSignButton(ActionEvent actionEvent) {
        try {
            usuarioController.createUser(usernameField, passwordField, confirmPasswordField);
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            log.info("Info: Usuário cadastrado com sucesso.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            log.error("Erro: {}", e.getMessage(), e);
        }
    }

}
