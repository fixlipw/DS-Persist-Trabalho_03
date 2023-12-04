package com.ufc.dspersist.view;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ufc.dspersist.controller.UsuarioController;
import com.ufc.dspersist.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Slf4j
@Component
public class UsuarioPanel extends JPanel {

    private final UsuarioController usuarioController;

    private final JPanel contentPanel;

    @Autowired
    public UsuarioPanel (UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        contentPanel = new JPanel();
    }


    public void setUsuarioPanel(JPanel cardPanel, Usuario usuario) {
        contentPanel.removeAll();

        contentPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(idLabel, gbc);

        JTextField idField = new JTextField(usuario.getId());
        idField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPanel.add(idField, gbc);

        JLabel usernameLabel = new JLabel("Nome de Usuário:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(usuario.getUsername());
        usernameField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPanel.add(usernameField, gbc);

        JLabel leiturasLabel = new JLabel("Leituras:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(leiturasLabel, gbc);

        int quantidadeLeituras = usuarioController.countLeiturasById(usuario);
        JLabel leiturasCountLabel = new JLabel(String.valueOf(quantidadeLeituras));
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPanel.add(leiturasCountLabel, gbc);

        JLabel anotacoesLabel = new JLabel("Anotações:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(anotacoesLabel, gbc);

        int quantidadeAnotacoes = usuarioController.countAnotacoesById(usuario);
        JLabel anotacoesCountLabel = new JLabel(String.valueOf(quantidadeAnotacoes));
        gbc.gridx = 1;
        gbc.gridy = 3;
        contentPanel.add(anotacoesCountLabel, gbc);

        JButton changePasswordButton = new JButton("Alterar Senha");
        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPanel.add(changePasswordButton, gbc);

        JButton editProfileButton = new JButton("Alterar Nome de Usuário");
        gbc.gridx = 1;
        gbc.gridy = 4;
        contentPanel.add(editProfileButton, gbc);

        cardPanel.add(contentPanel, "usuarioInfoPanel");

        changePasswordButton.addActionListener(e -> {
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
        });

        editProfileButton.addActionListener(e -> {
            JPasswordField passwordField = new JPasswordField();
            Object[] message = {"Confirme sua senha:", passwordField};
            int option = JOptionPane.showConfirmDialog(this, message, "Alterar nome de usuário", JOptionPane.OK_CANCEL_OPTION);

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
                    contentPanel.revalidate();
                    contentPanel.repaint();
                    setUsuarioPanel(cardPanel, usuario);
                } else {
                    JOptionPane.showMessageDialog(null, "Nome de usuário não pode ser vazio. Tente novamente.");
                    log.error("Erro: Nome de usuário não pode ser vazio");
                }
            }
        });
    }
}
