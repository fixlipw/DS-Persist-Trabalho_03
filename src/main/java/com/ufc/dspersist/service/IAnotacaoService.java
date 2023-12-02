package com.ufc.dspersist.service;

import com.ufc.dspersist.model.Anotacao;

import java.util.List;

public interface IAnotacaoService {

    void saveAnotacao(Anotacao anotacao);

    void deleteAnotacao(Anotacao anotacao);

    List<Anotacao> getAllAnnotationByLeituraId(String leituraId);

    int countAllAnnotationByUserId(String userId);
}
