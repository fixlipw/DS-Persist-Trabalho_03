package com.ufc.dspersist.controller;

import com.ufc.dspersist.enumeration.BookStatus;
import com.ufc.dspersist.enumeration.BookType;
import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.model.Usuario;
import com.ufc.dspersist.service.ILeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;

@Component
public class LeituraController {

    ILeituraService ILeituraService;

    @Autowired
    private void setLeituraService(ILeituraService ILeituraService) {
        this.ILeituraService = ILeituraService;
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

        ILeituraService.saveLeitura(leitura);

    }

    public void saveLeitura(Leitura leitura) {
        ILeituraService.saveLeitura(leitura);
    }

    public void deleteLeitura(Leitura leitura) {
        ILeituraService.deleteLeitura(leitura);
    }

    public List<Leitura> getAllLeiturasById(Usuario usuario) {
        return ILeituraService.getAllLeiturasById(usuario.getId());
    }

    public List<Leitura> getLeiturasNaoLidasById(Usuario usuario) {
        return ILeituraService.getLeiturasNaoLidasById(usuario.getId());
    }

    public List<Leitura> getLeiturasAbandonadasById(Usuario usuario) {
        return ILeituraService.getLeiturasAbandonadasById(usuario.getId());
    }

    public List<Leitura> getLeiturasEmAndamentoById(Usuario usuario) {
        return ILeituraService.getLeiturasEmAndamentoById(usuario.getId());
    }

    public List<Leitura> getLeiturasConcluidasById(Usuario usuario) {
        return ILeituraService.getLeiturasConcluidasById(usuario.getId());
    }
}
