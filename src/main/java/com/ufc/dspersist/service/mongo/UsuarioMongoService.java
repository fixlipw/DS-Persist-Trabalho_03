package com.ufc.dspersist.service.mongo;

import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.repository.mongo.UsuarioMongoDAO;
import com.ufc.dspersist.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Profile({"!pg", "!sqlite"})
public class UsuarioMongoService implements IUsuarioService {

    private final UsuarioMongoDAO usuarioRepository;

    @Autowired
    public UsuarioMongoService(UsuarioMongoDAO usuarioRepository) {
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
        return usuarioRepository.findById(id);
    }

}
