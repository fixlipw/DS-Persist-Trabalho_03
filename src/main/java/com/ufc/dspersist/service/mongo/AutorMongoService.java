package com.ufc.dspersist.service.mongo;

import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.repository.mongo.AutorMongoDAO;
import com.ufc.dspersist.service.IAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile({"!pg", "!sqlite"})
public class AutorMongoService implements IAutorService {

    private final AutorMongoDAO autorRepository;

    @Autowired
    public AutorMongoService(AutorMongoDAO autorRepository) {
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
