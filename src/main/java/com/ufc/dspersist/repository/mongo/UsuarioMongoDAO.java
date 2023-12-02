package com.ufc.dspersist.repository.mongo;

import com.ufc.dspersist.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioMongoDAO extends MongoRepository<Usuario, String> {

    Usuario findUsuarioByUsername(String username);

    Optional<Usuario> findById(String id);

}
