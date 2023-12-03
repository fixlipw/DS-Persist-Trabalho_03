package com.ufc.dspersist.repository.mongo;

import com.ufc.dspersist.model.Autor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorMongoDao extends MongoRepository<Autor, String> {

}
