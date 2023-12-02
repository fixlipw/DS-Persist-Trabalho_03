package com.ufc.dspersist.repository.mongo;

import com.ufc.dspersist.model.Leitura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeituraMongoDAO extends MongoRepository<Leitura, String> {

    List<Leitura> findLeiturasByUsuarioId(String usuario_id);

    int countLeituraByUsuarioId(String usuario_id);

    List<Leitura> findLeiturasAbandonadasById(String id);

    List<Leitura> findLeiturasNaoLidasById(String usuarioId);

    List<Leitura> findLeiturasEmAndamentoById(String usuarioId);

    List<Leitura> findLeiturasConcluidasById(String id);

}
