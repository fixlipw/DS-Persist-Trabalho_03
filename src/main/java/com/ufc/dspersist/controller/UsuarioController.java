package com.ufc.dspersist.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.service.AnotacaoService;
import com.ufc.dspersist.service.LeituraService;
import com.ufc.dspersist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final LeituraService leituraService;
    private final AnotacaoService anotacaoService;

    private Usuario user;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, LeituraService leituraService, AnotacaoService anotacaoService) {
        this.usuarioService = usuarioService;
        this.leituraService = leituraService;
        this.anotacaoService = anotacaoService;
    }

    public void createUser(JTextField usrnField, JPasswordField pswField, JPasswordField cpswField) {

        String username = usrnField.getText();

        if (username.length() < 2) {
            throw new IllegalArgumentException("O nome do usuário deve ser maior que 2. Tente novamente.");
        } else if (pswField.getPassword().length < 8) {
            throw new IllegalArgumentException("A senha do usuário deve ser maior que 8. Tente novamente.");
        }

        String hashpsw = BCrypt.withDefaults().hashToString(12, cpswField.getPassword());

        BCrypt.Result result = BCrypt.verifyer().verify(pswField.getPassword(), hashpsw);

        if (!result.verified) {
            JOptionPane.showMessageDialog(null, "As senhas não coincidem. Tente novamente.");
            return;
        }

        user = usuarioService.createUser(username, hashpsw);

    }

    public Usuario authUser(JTextField usrnField, JPasswordField pswField) {

        String username = usrnField.getText();

        user = usuarioService.findUsuarioByUsername(username);

        BCrypt.Result result;

        if (user != null) {
            result = BCrypt.verifyer().verify(pswField.getPassword(), user.getPassword());

            if (result.verified) {
                return user;
            }

        }

        throw new NullPointerException("Usuário ou senha inválidos");

    }

    public Usuario getUsuario() {

        if (user != null) {
            return user;
        }

        throw new NullPointerException("Não há usuários logados");

    }

    public Usuario getUsuario(int id) {

        if (user != null && user.getId() == id) {
            return user;
        } else {
            return usuarioService.getUsuario(id);
        }

    }

    public int countLeiturasById(Usuario usuario) {
        return leituraService.getLeiturasQtd(usuario.getId());
    }

    public int countAnotacoesById(Usuario usuario) {
        return anotacaoService.countAllAnnotationByUserId(usuario.getId());
    }

    public void saveUser(Usuario usuario) {

        if (usuario.getUsername().length() < 2) {
            throw new IllegalArgumentException("O nome do usuário deve ser maior que 2. Tente novamente.");
        }

        usuarioService.saveUser(usuario);
    }

}
