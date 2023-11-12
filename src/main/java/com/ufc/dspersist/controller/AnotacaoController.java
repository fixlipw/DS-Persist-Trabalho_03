package com.ufc.dspersist.controller;

import com.ufc.dspersist.model.Anotacao;
import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.repository.AnotacaoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AnotacaoController {

    private AnotacaoDAO anotacaoRepository;

    @Autowired
    private void setAnotacaoRepository(AnotacaoDAO anotacaoRepository) {
        this.anotacaoRepository = anotacaoRepository;
    }

    public void saveAnotacao(Leitura leitura, String anottation) {
        Anotacao anotacao = new Anotacao();
        anotacao.setAnnotation(anottation);
        anotacao.setDate(LocalDateTime.now());
        anotacao.setLeitura(leitura);
        anotacaoRepository.save(anotacao);
    }
}
