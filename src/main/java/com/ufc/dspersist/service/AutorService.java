package com.ufc.dspersist.service;

import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.repository.AutorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private AutorDAO autorRepository;

    @Autowired
    public void setAutorDAO(AutorDAO autorDao) {
        this.autorRepository = autorDao;
    }

    public List<Autor> getAllAuthors() {
        return autorRepository.findAll();
    }

    public void saveAutor(Autor autor) {
        autorRepository.save(autor);
    }

    public void deleteAutor(Autor autor) {
        autorRepository.delete(autor);
    }

}
