package com.ufc.dspersist.controller;

import com.ufc.dspersist.model.Anotacao;
import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.service.IAnotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AnotacaoController {

    private final IAnotacaoService IAnotacaoService;

    @Autowired
    public AnotacaoController(IAnotacaoService IAnotacaoService) {
        this.IAnotacaoService = IAnotacaoService;
    }

    public void saveAnotacao(Anotacao anotacao, Leitura leitura, String anottation) {
        if (anotacao.getId() == null) {
            anotacao = new Anotacao();
        }

        if (anottation == null || anottation.isEmpty()) {
            throw new NullPointerException("Anotação não pode ser nula ou vazia");
        }

        anotacao.setAnnotation(anottation);
        anotacao.setDate(LocalDateTime.now());
        anotacao.setLeitura(leitura);
        IAnotacaoService.saveAnotacao(anotacao);
    }

    public List<Anotacao> getAllAnottation(Leitura leitura) {
        return IAnotacaoService.getAllAnnotationByLeituraId(leitura.getId());
    }

    public void deleteAnotacao(Anotacao anotacao) {
        IAnotacaoService.deleteAnotacao(anotacao);
    }
}
