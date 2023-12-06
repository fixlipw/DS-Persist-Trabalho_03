package com.ufc.dspersist.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.repository.AnotacaoDAO;
import com.ufc.dspersist.repository.LeituraDAO;
import com.ufc.dspersist.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class UsuarioController {

    private final UsuarioDAO usuarioRepository;
    private final LeituraDAO leituraRepository;
    private final AnotacaoDAO anotacaoRepository;

    private Usuario user;

    @Autowired
    public UsuarioController(UsuarioDAO usuarioRepository, AnotacaoDAO anotacaoRepository, LeituraDAO leituraRepository) {
        this.usuarioRepository = usuarioRepository;
        this.anotacaoRepository = anotacaoRepository;
        this.leituraRepository = leituraRepository;
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

        user = new Usuario();
        user.setUsername(username);
        user.setPassword(hashpsw);
        usuarioRepository.save(user);

    }

    public Usuario authUser(JTextField usrnField, JPasswordField pswField) {

        String username = usrnField.getText();
        user = usuarioRepository.findUsuarioByUsername(username);

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
        return usuarioRepository.findById(user.getId()).orElseThrow();
    }

    public int countLeiturasById(Usuario usuario) {
        return leituraRepository.countLeituraByUsuarioId(usuario.getId());
    }

    public int countAnotacoesById(Usuario usuario) {
        return anotacaoRepository.countAnotacaosByLeitura_Usuario_Id(usuario.getId());
    }

    public void saveUser(Usuario usuario) {

        if (usuario.getUsername().length() < 2) {
            throw new IllegalArgumentException("O nome do usuário deve ser maior que 2. Tente novamente.");
        }

        usuarioRepository.save(usuario);
    }

}
