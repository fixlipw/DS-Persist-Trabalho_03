package com.ufc.dspersist.enumeration;

import lombok.Getter;

@Getter
public enum BookStatus {

    NAO_LIDO("Não Lido"),
    LENDO("Lendo"),
    LIDO("Lido"),
    ABANDONADO("Abandonado");

    private final String status;

    BookStatus(String status) {
        this.status = status;
    }

    public static BookStatus getByStatus(String status) {
        for (BookStatus value : values()) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }

}
