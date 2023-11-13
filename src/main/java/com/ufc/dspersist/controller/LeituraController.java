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

    LeituraDAO leituraRepository;

    @Autowired
    private void setLeituraService(LeituraDAO leituraRepository) {
        this.leituraRepository = leituraRepository;
    }

    public int getLeiturasQtd(Usuario usuario) {

        try {
            return usuario.getLeituras().size();
        } catch (Exception ignored) {
        }

        return leituraRepository.countLeituraByUsuarioId(usuario.getId());

    }

    public void saveLeitura(Usuario usuario, String title, Autor author, String pages, Object type, Object status) {

        int pagesQtd;
        try {
            pagesQtd = Integer.parseInt(pages);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Número de páginas inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BookType bookType = (BookType) type;
        BookStatus bookStatus = (BookStatus) status;

        Leitura leitura = new Leitura();
        leitura.setTitle(title);
        leitura.setAuthorname(author.getAuthorName());
        leitura.setPagesQtd(pagesQtd);
        leitura.setType(bookType);
        leitura.setStatus(bookStatus);
        leitura.setUsuario(usuario);

        leituraRepository.save(leitura);

    }

    public List<Leitura> getAllLeiturasById(Usuario user) {
        return leituraRepository.findLeiturasByUsuarioId(user.getId());
    }

    public void saveLeitura(Leitura leitura) {
        leituraRepository.save(leitura);
    }

    public void deleteLeitura(Leitura leitura) {
        leituraRepository.delete(leitura);
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
