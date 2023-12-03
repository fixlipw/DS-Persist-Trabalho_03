package com.ufc.dspersist.repository;

import com.ufc.dspersist.model.Usuario;

import java.util.Optional;

public interface UsuarioDAO {

    Usuario findUsuarioByUsername(String username);

    Optional<Usuario> findById(String id);

    Usuario save(Usuario user);
}
