package com.ufc.dspersist.controller;

import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.repository.AutorDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutorController {

    private final AutorDAO autorRepository;

    public AutorController(AutorDAO autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> getAllAuthors() {
        return autorRepository.findAll();
    }

    public void saveAutor(String autorName, String brief) {
        if (autorName.isEmpty() || brief.isEmpty()) {
            throw new IllegalArgumentException("Nome ou descrição do autor não podem ser nulos ou vazios");
        }

        Autor autor = new Autor();
        autor.setAuthorName(autorName);
        autor.setBrief(brief);
        autorRepository.save(autor);

    }

    public void deleteAutor(Autor autor) {
        autorRepository.delete(autor);
    }

    public void updateAutor(Autor autor, String newBrief) {
        if (newBrief.isEmpty()) {
            throw new IllegalArgumentException("Nome ou descrição do autor não podem sevazios");
        }
        autor.setBrief(newBrief);
        autorRepository.save(autor);
    }
}
