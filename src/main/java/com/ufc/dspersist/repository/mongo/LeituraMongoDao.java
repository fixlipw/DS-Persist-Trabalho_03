package com.ufc.dspersist.repository.mongo;

import com.ufc.dspersist.model.Leitura;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeituraMongoDao extends MongoRepository<Leitura, String> {

    List<Leitura> findLeiturasByUsuarioIdMongo(String usuario_id);

    int countLeituraByUsuarioIdMongo(String usuario_id);

    List<Leitura> findLeiturasAbandonadasByIdMongo(String id);

    List<Leitura> findLeiturasNaoLidasByIdMongo(String usuarioId);

    List<Leitura> findLeiturasEmAndamentoByIdMongo(String usuarioId);

    List<Leitura> findLeiturasConcluidasByIdMongo(String id);

}
