package com.ufc.dspersist.controller;

import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutorController {

    private AutorService autorService;

    @Autowired
    public void setAutorService(AutorService autorService) {
        this.autorService = autorService;
    }

    public List<Autor> getAllAuthors() {
        return autorService.getAllAuthors();
    }

    public void saveAutor(String autorName, String brief) {
        Autor autor = new Autor();
        autor.setAuthorName(autorName);
        autor.setBrief(brief);
        autorService.saveAutor(autor);

    }

    public void deleteAutor(Autor autor) {
        autorService.deleteAutor(autor);
    }

    public void updateAutor(Autor autor, String newBrief) {
        autor.setBrief(newBrief);
        autorService.saveAutor(autor);
    }
}
