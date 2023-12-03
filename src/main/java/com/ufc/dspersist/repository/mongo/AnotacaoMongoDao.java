package com.ufc.dspersist.repository.mongo;

import com.ufc.dspersist.model.Anotacao;
import com.ufc.dspersist.repository.AnotacaoDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile({"!pg", "!sqlite"})
public interface AnotacaoMongoDao extends MongoRepository<Anotacao, String>, AnotacaoDAO {

     List<Anotacao> findAllByLeituraId(String userid);

     int countAnotacaosByUsuario(String id);


}
