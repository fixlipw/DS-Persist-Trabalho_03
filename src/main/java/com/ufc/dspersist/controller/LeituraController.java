package com.ufc.dspersist.controller;

import com.ufc.dspersist.enumeration.BookStatus;
import com.ufc.dspersist.enumeration.BookType;
import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.repository.LeituraDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;

@Component
public class LeituraController {

    private final LeituraDAO leituraRepository;

    @Autowired
    public LeituraController(LeituraDAO leituraRepository) {
        this.leituraRepository = leituraRepository;
    }

    public void saveLeitura(Usuario usuario, String title, Autor author, String pages, Object type, Object status) {

        int pagesQtd;
        try {
            pagesQtd = Integer.parseInt(pages);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Número de páginas inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BookType bookType = BookType.getByType((String) type);
        BookStatus bookStatus = BookStatus.getByStatus((String) status);

        Leitura leitura = new Leitura();
        leitura.setTitle(title);
        leitura.setAuthorname(author.getAuthorName());
        leitura.setAutor(author);
        leitura.setPagesQtd(pagesQtd);
        leitura.setType(bookType);
        leitura.setStatus(bookStatus);
        leitura.setUsuario(usuario);

        leituraRepository.save(leitura);

    }

    public void saveLeitura(Leitura leitura) {
        leituraRepository.save(leitura);
    }

    public void deleteLeitura(Leitura leitura) {
        leituraRepository.delete(leitura);
    }

    public Leitura getLeituraByTitle(String title) {
        return leituraRepository.getLeituraByTitle(title);
    }

    public List<Leitura> getAllLeiturasById(Usuario usuario) {
        return leituraRepository.findLeiturasByUsuarioId(usuario.getId());
    }

    public List<Leitura> getLeiturasNaoLidasById(Usuario usuario) {
        return leituraRepository.findLeiturasNaoLidasById(usuario.getId());
    }

    public List<Leitura> getLeiturasAbandonadasById(Usuario usuario) {
        return leituraRepository.findLeiturasAbandonadasById(usuario.getId());
    }

    public List<Leitura> getLeiturasEmAndamentoById(Usuario usuario) {
        return leituraRepository.findLeiturasEmAndamentoById(usuario.getId());
    }

    public List<Leitura> getLeiturasConcluidasById(Usuario usuario) {
        return leituraRepository.findLeiturasConcluidasById(usuario.getId());
    }
}
