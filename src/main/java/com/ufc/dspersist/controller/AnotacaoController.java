package com.ufc.dspersist.controller;

import com.ufc.dspersist.model.Anotacao;
import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.service.AnotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AnotacaoController {

    private final AnotacaoService anotacaoService;

    @Autowired
    public AnotacaoController(AnotacaoService anotacaoService) {
        this.anotacaoService = anotacaoService;
    }

    public void saveAnotacao(Anotacao anotacao, Leitura leitura, String anottation) {
        if (anotacao.getId() == null) {
            anotacao = new Anotacao();
        }
        anotacao.setAnnotation(anottation);
        anotacao.setDate(LocalDateTime.now());
        anotacao.setLeitura(leitura);
        anotacaoService.saveAnotacao(anotacao);
    }

    public List<Anotacao> getAllAnottation(Leitura leitura) {
        return anotacaoService.getAllAnnotationByLeituraId(leitura.getId());
    }

    public void deleteAnotacao(Anotacao anotacao) {
        anotacaoService.deleteAnotacao(anotacao);
    }
}
