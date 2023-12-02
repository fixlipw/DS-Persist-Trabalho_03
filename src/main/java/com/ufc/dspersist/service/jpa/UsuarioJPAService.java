package com.ufc.dspersist.service.jpa;

import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.repository.jpa.UsuarioJPADAO;
import com.ufc.dspersist.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
@Profile({"sqlite", "pg"})
public class UsuarioJPAService implements IUsuarioService {

    private final UsuarioJPADAO usuarioRepository;

    @Autowired
    public UsuarioJPAService(UsuarioJPADAO usuarioRepository) {
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
