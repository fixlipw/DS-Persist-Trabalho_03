package com.ufc.dspersist.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NamedQuery(name = "Usuario.findByIdNamedQuery", query = "select u from Usuario u where u.id = :id")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(mappedBy = "usuario")
    private List<Leitura> leituras;

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", username='" + username + ", password='" + password + '}';
    }
}
