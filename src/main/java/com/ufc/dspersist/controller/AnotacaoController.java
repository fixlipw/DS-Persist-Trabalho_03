package com.ufc.dspersist.controller;

import com.ufc.dspersist.model.Anotacao;
import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.repository.AnotacaoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AnotacaoController {

    private final AnotacaoDAO anotacaoRepository;

    @Autowired
    public AnotacaoController(AnotacaoDAO anotacaoRepository) {
        this.anotacaoRepository = anotacaoRepository;
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
        anotacaoRepository.save(anotacao);
    }

    public List<Anotacao> getAllAnottation(Leitura leitura) {
        return anotacaoRepository.findAllByLeituraId(leitura.getId());
    }

    public void deleteAnotacao(Anotacao anotacao) {
        anotacaoRepository.delete(anotacao);
    }
}
