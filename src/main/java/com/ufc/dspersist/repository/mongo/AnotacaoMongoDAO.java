package com.ufc.dspersist.repository.mongo;

import com.ufc.dspersist.model.Anotacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnotacaoMongoDAO extends MongoRepository<Anotacao, String> {

        List<Anotacao> findAllByLeituraId(String userid);

        int countAnotacaosByLeitura_Usuario_Id(String id);

}
