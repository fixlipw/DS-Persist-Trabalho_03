package com.ufc.dspersist.service.jpa;

import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.repository.jpa.AutorJPADAO;
import com.ufc.dspersist.service.IAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Profile({"sqlite", "pg"})
public class AutorJPAService implements IAutorService {

    private final AutorJPADAO autorRepository;

    @Autowired
    public AutorJPAService(AutorJPADAO autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    public List<Autor> getAllAuthors() {
        return autorRepository.findAll();
    }

    @Override
    public void saveAutor(Autor autor) {
        autorRepository.save(autor);
    }

    @Override
    public void deleteAutor(Autor autor) {
        autorRepository.delete(autor);
    }
}
