package com.ufc.dspersist.repository;

import com.ufc.dspersist.model.Anotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnotacaoDAO extends JpaRepository<Anotacao, Integer> {}
