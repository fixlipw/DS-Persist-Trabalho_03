package com.ufc.dspersist.service;

import com.ufc.dspersist.model.Leitura;

import java.util.List;

public interface ILeituraService {
    int getLeiturasQtd(String id);

    void saveLeitura(Leitura leitura);

    void deleteLeitura(Leitura leitura);

    List<Leitura> getAllLeiturasById(String userId);

    List<Leitura> getLeiturasNaoLidasById(String userId);

    List<Leitura> getLeiturasAbandonadasById(String userId);

    List<Leitura> getLeiturasEmAndamentoById(String userId);

    List<Leitura> getLeiturasConcluidasById(String userId);
}
