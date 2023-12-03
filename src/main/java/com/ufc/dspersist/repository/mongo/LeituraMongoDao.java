package com.ufc.dspersist.repository.mongo;

import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.repository.LeituraDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile({"!pg", "!sqlite"})
public interface LeituraMongoDao extends MongoRepository<Leitura, String>, LeituraDAO {

    List<Leitura> findLeiturasByUsuarioId(String usuario_id);

    int countLeituraByUsuarioId(String usuario_id);

    List<Leitura> findLeiturasAbandonadasById(String id);

    List<Leitura> findLeiturasNaoLidasById(String usuarioId);

    List<Leitura> findLeiturasEmAndamentoById(String usuarioId);

    List<Leitura> findLeiturasConcluidasById(String id);

}
