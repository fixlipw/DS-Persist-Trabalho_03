package com.ufc.dspersist.repository;

import com.ufc.dspersist.model.Leitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeituraDAO extends JpaRepository<Leitura, Integer> {

    // Spring
    List<Leitura> findLeiturasByUsuarioId(int id);

    // Spring
    int countLeituraByUsuarioId(int id);

    // JPQL
    @Query("select l from Leitura l where l.usuario.id = :usuarioId AND l.status = 'ABANDONADO'")
    List<Leitura> findLeiturasAbandonadasById(@Param("usuarioId") int usuarioId);

    // Named Query
    List<Leitura> findLeiturasNaoLidasById(@Param("usuarioId") int usuarioId);

    // Named Query
    List<Leitura> findLeiturasEmAndamentoById(@Param("usuarioId") int usuarioId);

    // Nativa
    @Query(value = "select * from leituras l where l.usuario_id = :usuarioId and l.status = 'LIDO'", nativeQuery = true)
    List<Leitura> findLeiturasConcluidasById(@Param("usuarioId") int usuarioId);

}
