package com.ufc.dspersist.controller;

import com.ufc.dspersist.enumeration.BookStatus;
import com.ufc.dspersist.enumeration.BookType;
import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.service.LeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;

@Component
public class LeituraController {

    LeituraService leituraService;

    @Autowired
    private void setLeituraService(LeituraService leituraService) {
        this.leituraService = leituraService;
    }

    public int getLeiturasQtd(Usuario usuario) {

        try {
            return usuario.getLeituras().size();
        } catch (Exception ignored) {
        }

        return leituraService.getLeiturasQtd(usuario.getId());

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

        leituraService.saveLeitura(leitura);

    }

    public void saveLeitura(Leitura leitura) {
        leituraService.saveLeitura(leitura);
    }

    public void deleteLeitura(Leitura leitura) {
        leituraService.deleteLeitura(leitura);
    }

    public List<Leitura> getAllLeiturasById(Usuario user) {
        return leituraService.getAllLeiturasById(user.getId());
    }

    public List<Leitura> getLeiturasNaoLidasById(Usuario usuario) {
        return leituraService.getLeiturasNaoLidasById(usuario.getId());
    }

    public List<Leitura> getLeiturasAbandonadasById(Usuario usuario) {
        return leituraService.getLeiturasAbandonadasById(usuario.getId());
    }

    public List<Leitura> getLeiturasEmAndamentoById(Usuario usuario) {
        return leituraService.getLeiturasEmAndamentoById(usuario.getId());
    }

    public List<Leitura> getLeiturasConcluidasById(Usuario usuario) {
        return leituraService.getLeiturasConcluidasById(usuario.getId());
    }
}
