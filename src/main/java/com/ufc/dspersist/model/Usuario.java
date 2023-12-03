package com.ufc.dspersist.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
@Document("usuarios")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(mappedBy = "usuario")
    @DBRef
    private List<Leitura> leituras;

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", username='" + username + ", password='" + password + '}';
    }
}
