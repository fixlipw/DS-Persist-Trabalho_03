package com.ufc.dspersist.service;

import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioDAO usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario createUser(String username, String hashpsw) {
        Usuario user = new Usuario();
        user.setUsername(username);
        user.setPassword(hashpsw);
        return usuarioRepository.save(user);
    }

    public void saveUser(Usuario user) {
        usuarioRepository.save(user);
    }

    public Usuario findUsuarioByUsername(String username) {
        return usuarioRepository.findUsuarioByUsername(username);
    }

    public Optional<Usuario> getUsuario(String id) {
        return Optional.ofNullable(usuarioRepository.findByIdNamedQuery(id));
    }

}
