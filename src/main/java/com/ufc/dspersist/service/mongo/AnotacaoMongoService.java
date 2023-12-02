package com.ufc.dspersist.service.mongo;

import com.ufc.dspersist.model.Anotacao;
import com.ufc.dspersist.repository.mongo.AnotacaoMongoDAO;
import com.ufc.dspersist.service.IAnotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile({"!pg", "!sqlite"})
public class AnotacaoMongoService implements IAnotacaoService {

    private AnotacaoMongoDAO anotacaoRepository;

    @Autowired
    public void setAnotacaoRepository(AnotacaoMongoDAO anotacaoRepository) {
        this.anotacaoRepository = anotacaoRepository;
    }

    @Override
    public void saveAnotacao(Anotacao anotacao) {
        anotacaoRepository.save(anotacao);
    }

    @Override
    public void deleteAnotacao(Anotacao anotacao) {
        anotacaoRepository.delete(anotacao);
    }

    @Override
    public List<Anotacao> getAllAnnotationByLeituraId(String leituraId) {
        return anotacaoRepository.findAllByLeituraId(leituraId);
    }

    @Override
    public int countAllAnnotationByUserId(String userId) {
        return anotacaoRepository.countAnotacaosByUsuario(userId);
    }

}
