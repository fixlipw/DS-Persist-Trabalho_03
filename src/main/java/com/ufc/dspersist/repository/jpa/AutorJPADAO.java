package com.ufc.dspersist.repository.jpa;

import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.repository.AutorDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorJPADAO extends JpaRepository<Autor, String>, AutorDAO {

}