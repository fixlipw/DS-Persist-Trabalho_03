package com.ufc.dspersist.repository.mongo;

import com.ufc.dspersist.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioMongoDao extends MongoRepository<Usuario, String> {

    Usuario findUsuarioByUsernameMongo(String username);

    Optional<Usuario> findByIdMongo(String id);

}
