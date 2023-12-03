package com.ufc.dspersist.repository.mongo;

import com.ufc.dspersist.model.Anotacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnotacaoMongoDao extends MongoRepository<Anotacao, String> {

     List<Anotacao> findAllByLeituraIdMongo(String userid);

     int countAnotacaosByLeitura_Usuario_IdMongo(String id);

}
