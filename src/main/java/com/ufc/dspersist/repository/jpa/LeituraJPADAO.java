package com.ufc.dspersist.repository.jpa;

import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.repository.LeituraDAO;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public interface LeituraJPADAO extends JpaRepository<Leitura, String>, LeituraDAO {

    //Spring
    @Override
    List<Leitura> findLeiturasByUsuarioId(String id);

    //Spring
    @Override
    int countLeituraByUsuarioId(String id);

    //JPQL
    @Override
    @Query("select l from Leitura l where l.usuario.id = :usuarioId AND l.status = 'ABANDONADO'")
    List<Leitura> findLeiturasAbandonadasById(String usuarioId);

    //Named Query
    @Override
    List<Leitura> findLeiturasNaoLidasById(String usuarioId);

    //Named Query
    @Override
    List<Leitura> findLeiturasEmAndamentoById(String usuarioId);

    //Nativa
    @Override
    @Query(value = "select * from leituras l where l.usuario_id = :usuarioId and l.status = 'LIDO'", nativeQuery = true)
    List<Leitura> findLeiturasConcluidasById(String usuarioId);
}
