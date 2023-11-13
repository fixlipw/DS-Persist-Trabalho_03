package com.ufc.dspersist.repository;

import com.ufc.dspersist.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorDAO extends JpaRepository<Autor, Integer> {


}
