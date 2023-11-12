package com.ufc.dspersist.repository;

import com.ufc.dspersist.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

    Usuario findUsuarioByUsernameIs(String username);

    Usuario findById(int id);


}
