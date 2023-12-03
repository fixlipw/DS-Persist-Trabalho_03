package com.ufc.dspersist.repository.jpa;

import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.repository.LeituraDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    //JPQL
    @Override
    @Query("select l from Leitura l where l.usuario.id = :usuarioId and l.status = 'LENDO'")
    List<Leitura> findLeiturasNaoLidasById(String usuarioId);

    //JPQL
    @Override
    @Query("select l FROM Leitura l where l.usuario.id = :usuarioId and l.status = 'NAO_LIDO'")
    List<Leitura> findLeiturasEmAndamentoById(String usuarioId);

    //Nativa
    @Override
    @Query(value = "select * from leituras l where l.usuario_id = :usuarioId and l.status = 'LIDO'", nativeQuery = true)
    List<Leitura> findLeiturasConcluidasById(String usuarioId);

    @Override
    Leitura getLeituraByTitle(String title);
}
