package com.ufc.dspersist.controller;

import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.service.IAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutorController {

    private IAutorService IAutorService;

    @Autowired
    public void setAutorService(IAutorService IAutorService) {
        this.IAutorService = IAutorService;
    }

    public List<Autor> getAllAuthors() {
        return IAutorService.getAllAuthors();
    }

    public void saveAutor(String autorName, String brief) {
        if (autorName.isEmpty() || brief.isEmpty()) {
            throw new IllegalArgumentException("Nome ou descrição do autor não podem ser nulos ou vazios");
        }

        Autor autor = new Autor();
        autor.setAuthorName(autorName);
        autor.setBrief(brief);
        IAutorService.saveAutor(autor);

    }

    public void deleteAutor(Autor autor) {
        IAutorService.deleteAutor(autor);
    }

    public void updateAutor(Autor autor, String newBrief) {
        if (newBrief.isEmpty()) {
            throw new IllegalArgumentException("Nome ou descrição do autor não podem sevazios");
        }
        autor.setBrief(newBrief);
        IAutorService.saveAutor(autor);
    }
}
