package com.ufc.dspersist.repository.jpa;

import com.ufc.dspersist.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioJPADAO extends JpaRepository<Usuario, String> {

    @Query("select u from Usuario u where u.username = :username")
    Usuario findUsuarioByUsername(String username);

    Usuario findByIdNamedQuery(String id);

}
