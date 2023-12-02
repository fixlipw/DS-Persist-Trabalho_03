package com.ufc.dspersist.service;

import com.ufc.dspersist.model.Autor;

import java.util.List;

public interface IAutorService {

    List<Autor> getAllAuthors();

    void saveAutor(Autor autor);

    void deleteAutor(Autor autor);

}
