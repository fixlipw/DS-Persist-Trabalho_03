package com.ufc.dspersist.service.mongo;

import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.repository.mongo.LeituraMongoDao;
import com.ufc.dspersist.service.ILeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile({"!pg", "!sqlite"})
public class LeituraMongoService implements ILeituraService {

    private final LeituraMongoDao leituraRepository;

    @Autowired
    public LeituraMongoService(LeituraMongoDao leituraRepository) {
        this.leituraRepository = leituraRepository;
    }

    @Override
    public int getLeiturasQtd(String id) {
        return leituraRepository.countLeituraByUsuarioIdMongo(id);
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
        return leituraRepository.findLeiturasByUsuarioIdMongo(userId);
    }

    @Override
    public List<Leitura> getLeiturasNaoLidasById(String userId) {
        return leituraRepository.findLeiturasNaoLidasByIdMongo(userId);
    }

    @Override
    public List<Leitura> getLeiturasAbandonadasById(String userId) {
        return leituraRepository.findLeiturasAbandonadasByIdMongo(userId);
    }

    @Override
    public List<Leitura> getLeiturasEmAndamentoById(String userId) {
        return leituraRepository.findLeiturasEmAndamentoByIdMongo(userId);
    }

    @Override
    public List<Leitura> getLeiturasConcluidasById(String userId) {
        return leituraRepository.findLeiturasConcluidasByIdMongo(userId);
    }

}
