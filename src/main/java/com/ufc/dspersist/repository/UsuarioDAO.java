package com.ufc.dspersist.repository;

import com.ufc.dspersist.model.Usuario;

public interface UsuarioDAO {
    Usuario findUsuarioByUsername(String username);

    Usuario findByIdNamedQuery(String id);

    Usuario save(Usuario user);
}
