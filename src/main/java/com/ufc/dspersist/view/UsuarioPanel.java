package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.UsuarioController;
import com.ufc.dspersist.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class UsuarioPanel extends JPanel {

    private final Usuario usuario;

    private final UsuarioController usuarioController;

    public UsuarioPanel(Usuario usuario, UsuarioController usuarioController) {
        this.usuario = usuario;
        this.usuarioController = usuarioController;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(idLabel, gbc);

        JTextField idField = new JTextField(usuario.getId().toString());
        idField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(idField, gbc);

        JLabel usernameLabel = new JLabel("Nome de Usuário:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(usuario.getUsername());
        usernameField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(usernameField, gbc);

        JLabel leiturasLabel = new JLabel("Leituras:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(leiturasLabel, gbc);

        int quantidadeLeituras = usuarioController.countLeiturasById(usuario);
        JLabel leiturasCountLabel = new JLabel(String.valueOf(quantidadeLeituras));
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(leiturasCountLabel, gbc);

        JLabel anotacoesLabel = new JLabel("Anotações:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(anotacoesLabel, gbc);

        int quantidadeAnotacoes = 0; // Update this logic to get the actual number of annotations
        JLabel anotacoesCountLabel = new JLabel(String.valueOf(quantidadeAnotacoes));
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(anotacoesCountLabel, gbc);

        JButton changePasswordButton = new JButton("Alterar Senha");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(changePasswordButton, gbc);

        JButton editProfileButton = new JButton("Alterar Nome de Usuário");
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(editProfileButton, gbc);

        JButton deleteProfileButton = new JButton("Apagar Usuário");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(deleteProfileButton, gbc);

        JButton logoutButton = new JButton("Sair");
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(logoutButton, gbc);

    }

}
