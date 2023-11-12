package com.ufc.dspersist.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.Optional;

@Component
public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final LeituraController leituraController;

    private Optional<Usuario> user;



    @Autowired
    public UsuarioController(UsuarioDAO usuarioDAO, LeituraController leituraController) {
        this.usuarioDAO = usuarioDAO;
        this.leituraController = leituraController;
    }


    public void createUser(JTextField usrnField, JPasswordField pswField, JPasswordField cpswField) {

        user = Optional.of(new Usuario());

        String username = usrnField.getText();
        String hashpsw = BCrypt.withDefaults().hashToString(12, cpswField.getPassword());

        user.get().setUsername(username);
        user.get().setPassword(hashpsw);

        BCrypt.Result result = BCrypt.verifyer().verify(pswField.getPassword(), hashpsw);

        if (result.verified) {
            usuarioDAO.save(user.get());
        }

    }

    public Optional<Usuario> authUser(JTextField usrnField, JPasswordField pswField) {

        String username = usrnField.getText();

        user = Optional.ofNullable(usuarioDAO.findUsuarioByUsernameIs(username));

        BCrypt.Result result = null;

        if (user.isPresent()) {
            result = BCrypt.verifyer().verify(pswField.getPassword(), user.get().getPassword());
        }

        if (result != null && result.verified) {
            JOptionPane.showMessageDialog(null, "Login Bem-Sucedido!");
            return user;
        }

        JOptionPane.showMessageDialog(null, "Login Mal-Sucedido... Verifique suas credenciais e tente novamente.");
        return Optional.empty();

    }

    public Usuario getUsuario(int id) {

        if (user.isPresent() && user.get().getId() == id) {
            return user.get();
        }

        return usuarioDAO.findById(id);

    }

    public int countLeiturasById(Usuario usuario) {
        return leituraController.getLeiturasQtd(usuario);
    }

}
