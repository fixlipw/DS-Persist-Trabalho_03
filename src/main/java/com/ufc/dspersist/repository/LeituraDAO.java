package com.ufc.dspersist.repository;

import com.ufc.dspersist.enumeration.BookType;
import com.ufc.dspersist.model.Leitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeituraDAO extends JpaRepository<Leitura, Integer> {

    int countLeituraById(int id);

    List<Leitura> getLeiturasByUsuarioId(int id);

    int countLeituraByUsuarioId(int id);

}
