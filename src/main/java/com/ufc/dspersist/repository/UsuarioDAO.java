package com.ufc.dspersist.repository;

import com.ufc.dspersist.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

    // JPQL
    @Query("select u from Usuario u where u.username = :username")
    Usuario findUsuarioByUsername(String username);

    // NamedQuery
    Usuario findByIdNamedQuery(int id);

}
