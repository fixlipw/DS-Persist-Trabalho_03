package com.ufc.dspersist.service;

import com.ufc.dspersist.model.Anotacao;
import com.ufc.dspersist.repository.AnotacaoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnotacaoService {

    private AnotacaoDAO anotacaoRepository;

    @Autowired
    private void setAnotacaoRepository(AnotacaoDAO anotacaoRepository) {
        this.anotacaoRepository = anotacaoRepository;
    }

    public void saveAnotacao(Anotacao anotacao) {
        anotacaoRepository.save(anotacao);
    }

    public void deleteAnotacao(Anotacao anotacao) {
        anotacaoRepository.delete(anotacao);
    }

    public List<Anotacao> getAllAnnotationByLeituraId(int leituraId) {
        return anotacaoRepository.findAllByLeituraId(leituraId);
    }

    public int countAllAnnotationByUserId(int userId) {
        return anotacaoRepository.countAnotacaosByUsuario(userId);
    }

}
