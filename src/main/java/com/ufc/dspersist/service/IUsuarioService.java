package com.ufc.dspersist.service;

import com.ufc.dspersist.model.Usuario;

import java.util.Optional;

public interface IUsuarioService {

    Usuario createUser(String username, String hashpsw);

    void saveUser(Usuario user) ;

    Usuario findUsuarioByUsername(String username);

    Optional<Usuario> getUsuario(String id);

}
