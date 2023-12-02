package com.ufc.dspersist.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.service.IAnotacaoService;
import com.ufc.dspersist.service.ILeituraService;
import com.ufc.dspersist.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class UsuarioController {

    private final IUsuarioService IUsuarioService;
    private final ILeituraService ILeituraService;
    private final IAnotacaoService IAnotacaoService;

    private Usuario user;

    @Autowired
    public UsuarioController(IUsuarioService IUsuarioService, ILeituraService ILeituraService, IAnotacaoService IAnotacaoService) {
        this.IUsuarioService = IUsuarioService;
        this.ILeituraService = ILeituraService;
        this.IAnotacaoService = IAnotacaoService;
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

        user = IUsuarioService.createUser(username, hashpsw);

    }

    public Usuario authUser(JTextField usrnField, JPasswordField pswField) {

        String username = usrnField.getText();
        user = IUsuarioService.findUsuarioByUsername(username);

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
        return getUsuario(user);
    }

    public Usuario getUsuario(Usuario user) {
        return IUsuarioService.getUsuario(user.getId()).orElseThrow();
    }

    public int countLeiturasById(Usuario usuario) {
        return ILeituraService.getLeiturasQtd(usuario.getId());
    }

    public int countAnotacoesById(Usuario usuario) {
        return IAnotacaoService.countAllAnnotationByUserId(usuario.getId());
    }

    public void saveUser(Usuario usuario) {

        if (usuario.getUsername().length() < 2) {
            throw new IllegalArgumentException("O nome do usuário deve ser maior que 2. Tente novamente.");
        }

        IUsuarioService.saveUser(usuario);
    }

}
