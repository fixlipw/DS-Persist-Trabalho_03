package com.ufc.dspersist.view;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ufc.dspersist.controller.UsuarioController;
import com.ufc.dspersist.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Slf4j
@Component
public class UsuarioPanel extends JPanel {

    private Usuario usuario;
    private JTextField idField;
    private JTextField usernameField;
    private JLabel leiturasCountLabel;
    private JLabel anotacoesCountLabel;
    private int quantidadeLeituras = 0;
    private int quantidadeAnotacoes = 0;

    private final UsuarioController usuarioController;

    @Autowired
    public UsuarioPanel (UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        initComponents();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        idField.setText(usuario.getId().toString());
        usernameField.setText(usuario.getUsername());
        quantidadeLeituras = usuarioController.countLeiturasById(usuario);
        quantidadeAnotacoes = usuarioController.countAnotacoesById(usuario);
        leiturasCountLabel.setText(Integer.toString(quantidadeLeituras));
        anotacoesCountLabel.setText(Integer.toString(quantidadeAnotacoes));
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

        idField = new JTextField("");
        idField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(idField, gbc);

        JLabel usernameLabel = new JLabel("Nome de Usuário:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(usernameLabel, gbc);

        usernameField = new JTextField("");
        usernameField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(usernameField, gbc);

        JLabel leiturasLabel = new JLabel("Leituras:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(leiturasLabel, gbc);

        leiturasCountLabel = new JLabel(String.valueOf(quantidadeLeituras));
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(leiturasCountLabel, gbc);

        JLabel anotacoesLabel = new JLabel("Anotações:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(anotacoesLabel, gbc);

        anotacoesCountLabel = new JLabel(String.valueOf(quantidadeAnotacoes));
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(anotacoesCountLabel, gbc);

        JButton changePasswordButton = new JButton("Alterar Senha");
        changePasswordButton.addActionListener(this::handleChangePasswordButton);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(changePasswordButton, gbc);

        JButton editProfileButton = new JButton("Alterar Nome de Usuário");
        editProfileButton.addActionListener(this::handleEditProfileButton);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(editProfileButton, gbc);

    }


    void handleChangePasswordButton(ActionEvent e) {
        JPasswordField passwordField = new JPasswordField();
        JPasswordField newPasswordField;
        Object[] message = {"Confirme sua senha:", passwordField};
        int option = JOptionPane.showConfirmDialog(this, message, "Alterar Senha", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {

            BCrypt.Result result;
            try {
                String hashpsw = usuario.getPassword();
                result = BCrypt.verifyer().verify(passwordField.getPassword(), hashpsw);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Caixa de texto vazia. Tente novamente.");
                log.error("Erro: {}", exception.getMessage(), exception);
                return;
            }

            if (!result.verified) {
                JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente.");
                log.error("Erro: {}", "Senha incorreta. Tente novamente");
                return;
            }

            newPasswordField = new JPasswordField();
            message = new Object[]{"Insira sua nova senha:", newPasswordField};

            option = JOptionPane.showConfirmDialog(this, message, "Alterar Senha", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {

                try {
                    String hashpsw = BCrypt.withDefaults().hashToString(12, newPasswordField.getPassword());
                    usuario.setPassword(hashpsw);
                    usuarioController.saveUser(usuario);
                    JOptionPane.showMessageDialog(null, "Senha alterada com sucesso!");
                    log.info("Info: Usuário alterou a senha com sucesso");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Erro ao alterar sua senha. Reporte este erro e tente novamente.");
                    log.error("Erro: {}", exception.getMessage(), exception);
                }
            }
        }
    }

    void handleEditProfileButton(ActionEvent e) {
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Confirme sua senha:", passwordField};
        int option = JOptionPane.showConfirmDialog(this, message, "Confirmar Senha", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            BCrypt.Result result;
            try {
                String hashpsw = usuario.getPassword();
                result = BCrypt.verifyer().verify(passwordField.getPassword(), hashpsw);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Caixa de texto vazia. Tente novamente.");
                log.error("Erro: {}", exception.getMessage(), exception);
                return;
            }

            if (!result.verified) {
                JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente.");
                log.error("Erro: {}", "Senha incorreta. Tente novamente");
                return;
            }

            String newUsername = JOptionPane.showInputDialog(this, "Insira o novo nome de usuário:");

            if (newUsername != null && !newUsername.isEmpty()) {
                usuario.setUsername(newUsername);
                usuarioController.saveUser(usuario);
                JOptionPane.showMessageDialog(null, "Nome de usuário alterado com sucesso!");
                log.info("Info: Nome de usuário alterado com sucesso");
            } else {
                JOptionPane.showMessageDialog(null, "Nome de usuário não pode ser vazio. Tente novamente.");
                log.error("Erro: Nome de usuário não pode ser vazio");
            }
        }
    }
}
