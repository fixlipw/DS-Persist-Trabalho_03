package com.ufc.dspersist.model;

import com.ufc.dspersist.enumeration.BookStatus;
import com.ufc.dspersist.enumeration.BookType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "leituras")
@Document(collection = "leituras")
@Entity
public class Leitura {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String title;

    private String authorname;

    @Column(name = "pages_qtd")
    @Field("pages_qtd")
    private Integer pagesQtd;

    @Enumerated(EnumType.STRING)
    private BookType type;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @DBRef
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @DBRef
    private Autor autor;

    @OneToMany(mappedBy = "leitura", cascade = CascadeType.ALL, orphanRemoval = true)
    @DBRef
    private List<Anotacao> anotacoes;

    public Leitura(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Leitura{" + "id=" + id + ", title='" + title + ", authorName='" + authorname + ", pagesQtd=" + pagesQtd + ", type=" + type + ", status=" + status + '}';
    }
}
