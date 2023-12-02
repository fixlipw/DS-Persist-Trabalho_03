package com.ufc.dspersist.repository.jpa;

import com.ufc.dspersist.model.Leitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeituraJPADAO extends JpaRepository<Leitura, String> {

    // Spring
    List<Leitura> findLeiturasByUsuarioId(String id);

    // Spring
    int countLeituraByUsuarioId(String id);

    // JPQL
    @Query("select l from Leitura l where l.usuario.id = :usuarioId AND l.status = 'ABANDONADO'")
    List<Leitura> findLeiturasAbandonadasById(String usuarioId);

    // Named Query
    List<Leitura> findLeiturasNaoLidasById(String usuarioId);

    // Named Query
    List<Leitura> findLeiturasEmAndamentoById(String usuarioId);

    // Nativa
    @Query(value = "select * from leituras l where l.usuario_id = :usuarioId and l.status = 'LIDO'", nativeQuery = true)
    List<Leitura> findLeiturasConcluidasById(String usuarioId);

}
