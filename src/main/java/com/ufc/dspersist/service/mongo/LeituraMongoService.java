package com.ufc.dspersist.service.mongo;

import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.repository.mongo.LeituraMongoDAO;
import com.ufc.dspersist.service.ILeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile({"!pg", "!sqlite"})
public class LeituraMongoService implements ILeituraService {

    private final LeituraMongoDAO leituraRepository;

    @Autowired
    public LeituraMongoService(LeituraMongoDAO leituraRepository) {
        this.leituraRepository = leituraRepository;
    }

    @Override
    public int getLeiturasQtd(String id) {
        return leituraRepository.countLeituraByUsuarioId(id);
    }

    @Override
    public void saveLeitura(Leitura leitura) {
        leituraRepository.save(leitura);
    }

    @Override
    public void deleteLeitura(Leitura leitura) {
        leituraRepository.delete(leitura);
    }

    @Override
    public List<Leitura> getAllLeiturasById(String userId) {
        return leituraRepository.findLeiturasByUsuarioId(userId);
    }

    @Override
    public List<Leitura> getLeiturasNaoLidasById(String userId) {
        return leituraRepository.findLeiturasNaoLidasById(userId);
    }

    @Override
    public List<Leitura> getLeiturasAbandonadasById(String userId) {
        return leituraRepository.findLeiturasAbandonadasById(userId);
    }

    @Override
    public List<Leitura> getLeiturasEmAndamentoById(String userId) {
        return leituraRepository.findLeiturasEmAndamentoById(userId);
    }

    @Override
    public List<Leitura> getLeiturasConcluidasById(String userId) {
        return leituraRepository.findLeiturasConcluidasById(userId);
    }

}
