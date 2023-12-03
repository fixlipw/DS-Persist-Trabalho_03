package com.ufc.dspersist.repository;

import com.ufc.dspersist.model.Autor;

import java.util.List;

public interface AutorDAO {

    List<Autor> findAll();

    Autor save(Autor autor);

    void delete(Autor autor);
}
