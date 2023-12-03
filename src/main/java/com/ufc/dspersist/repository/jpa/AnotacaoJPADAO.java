package com.ufc.dspersist.repository.jpa;

import com.ufc.dspersist.model.Anotacao;
import com.ufc.dspersist.repository.AnotacaoDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnotacaoJPADAO extends JpaRepository<Anotacao, String>, AnotacaoDAO {

    // Spring
    @Override
    List<Anotacao> findAllByLeituraId(String userid);

    //JPQL
    @Override
    @Query("select count (a) from Anotacao a join a.leitura l join l.usuario u where u.id = :id")
    int countAnotacaosByLeitura_Usuario_Id(String id);

}
