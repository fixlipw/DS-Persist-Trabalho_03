package com.ufc.dspersist.repository.mongo;

import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.repository.UsuarioDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioMongoDao extends MongoRepository<Usuario, String>, UsuarioDAO {

    Usuario findUsuarioByUsername(String username);

    Optional<Usuario> findById(String id);

}
