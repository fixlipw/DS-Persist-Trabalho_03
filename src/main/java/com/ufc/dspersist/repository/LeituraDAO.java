package com.ufc.dspersist.repository;

import com.ufc.dspersist.model.Leitura;

import java.util.List;

public interface LeituraDAO {
    List<Leitura> findLeiturasByUsuarioId(String id);

    int countLeituraByUsuarioId(String id);

    List<Leitura> findLeiturasAbandonadasById(String usuarioId);

    List<Leitura> findLeiturasNaoLidasById(String usuarioId);

    List<Leitura> findLeiturasEmAndamentoById(String usuarioId);

    List<Leitura> findLeiturasConcluidasById(String usuarioId);

    Leitura save(Leitura leitura);

    void delete(Leitura leitura);
}
