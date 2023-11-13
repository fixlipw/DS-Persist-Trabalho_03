package com.ufc.dspersist.controller;

import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.repository.AutorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutorController {

    private AutorDAO autorRepository;

    @Autowired
    public void setAutorDAO(AutorDAO autorDao) {
        this.autorRepository = autorDao;
    }

    public List<Autor> getAllAuthors() {
        return autorRepository.findAll();
    }

    public void saveAutor(String autorName, String brief) {
        Autor autor = new Autor();
        autor.setAuthorName(autorName);
        autor.setBrief(brief);
        autorRepository.save(autor);

    }

    public void deleteAutor(Autor autor) {
        autorRepository.delete(autor);
    }

    public void updateAutor(Autor autor, String newBrief) {
        autor.setBrief(newBrief);
        autorRepository.save(autor);
    }
}
