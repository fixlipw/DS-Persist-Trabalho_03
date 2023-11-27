package com.ufc.dspersist.service;

import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.repository.LeituraDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeituraService {

    private final LeituraDAO leituraRepository;

    @Autowired
    public LeituraService(LeituraDAO leituraRepository) {
        this.leituraRepository = leituraRepository;
    }

    public int getLeiturasQtd(int id) {
        return leituraRepository.countLeituraByUsuarioId(id);
    }

    public void saveLeitura(Leitura leitura) {
        leituraRepository.save(leitura);
    }

    public void deleteLeitura(Leitura leitura) {
        leituraRepository.delete(leitura);
    }

    public List<Leitura> getAllLeiturasById(int userId) {
        return leituraRepository.findLeiturasByUsuarioId(userId);
    }

    public List<Leitura> getLeiturasNaoLidasById(int userId) {
        return leituraRepository.findLeiturasNaoLidasById(userId);
    }

    public List<Leitura> getLeiturasAbandonadasById(int userId) {
        return leituraRepository.findLeiturasAbandonadasById(userId);
    }

    public List<Leitura> getLeiturasEmAndamentoById(int userId) {
        return leituraRepository.findLeiturasEmAndamentoById(userId);
    }

    public List<Leitura> getLeiturasConcluidasById(int userId) {
        return leituraRepository.findLeiturasConcluidasById(userId);
    }

}
