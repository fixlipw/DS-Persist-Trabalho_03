package com.ufc.dspersist.enumeration;

import lombok.Getter;

@Getter
public enum BookType {

    LIVRO("Livro"), EBOOK("Ebook"), PDF("PDF"), ARTIGO("Artigo"), REVISTA("Revista"), JORNAL("Jornal"), AUDIOBOOK("Audiobook"), QUADRINHO("Quadrinho"), BLOG("Blog"), RELATORIO("Relatório"), TCC("TCC"), DIARIO("Diário");

    private final String type;

    BookType(String type) {
        this.type = type;
    }

    public static BookType getByType(String type) {
        for (BookType value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}