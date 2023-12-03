package com.ufc.dspersist.repository.jpa;

import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.repository.UsuarioDAO;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface UsuarioJPADAO extends JpaRepository<Usuario, String>, UsuarioDAO {

    //JPQL
    @Override
    @Query("select u from Usuario u where u.username = :username")
    Usuario findUsuarioByUsername(String username);

    //Named Query
    @Override
    Usuario findByIdNamedQuery(String id);

}
