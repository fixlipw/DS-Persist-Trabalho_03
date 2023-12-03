package com.ufc.dspersist.repository;

import com.ufc.dspersist.model.Anotacao;

import java.util.List;

public interface AnotacaoDAO {

    List<Anotacao> findAllByLeituraId(String userid);

    int countAnotacaosByLeitura_Usuario_Id(String id);

    Anotacao save(Anotacao anotacao);

    void delete(Anotacao anotacao);
}
