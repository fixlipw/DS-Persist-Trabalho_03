package com.ufc.dspersist.service.mongo;

import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.repository.mongo.UsuarioMongoDao;
import com.ufc.dspersist.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Profile({"!pg", "!sqlite"})
public class UsuarioMongoService implements IUsuarioService {

    private final UsuarioMongoDao usuarioRepository;

    @Autowired
    public UsuarioMongoService(UsuarioMongoDao usuarioRepository) {
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
        return usuarioRepository.findUsuarioByUsernameMongo(username);
    }

    public Optional<Usuario> getUsuario(String id) {
        return usuarioRepository.findByIdMongo(id);
    }

}
