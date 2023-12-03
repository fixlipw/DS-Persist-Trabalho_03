package com.ufc.dspersist.repository;

import com.ufc.dspersist.model.Anotacao;
import com.ufc.dspersist.model.Autor;

import java.util.List;

public interface AnotacaoDAO {

    List<Anotacao> findAllByLeituraId(String userid);

    int countAnotacaosByUsuario(String id);

    Anotacao save(Anotacao anotacao);

    void delete(Anotacao anotacao);
}
