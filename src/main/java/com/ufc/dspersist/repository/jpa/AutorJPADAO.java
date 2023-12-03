package com.ufc.dspersist.repository.jpa;

import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.repository.AutorDAO;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface AutorJPADAO extends JpaRepository<Autor, String>, AutorDAO {

}