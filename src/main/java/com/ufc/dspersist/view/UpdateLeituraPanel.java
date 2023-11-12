package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.AutorController;
import com.ufc.dspersist.controller.LeituraController;
import com.ufc.dspersist.enumeration.BookStatus;
import com.ufc.dspersist.enumeration.BookType;
import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.model.Leitura;
import com.ufc.dspersist.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class UpdateLeituraPanel extends JPanel {

    private final Usuario usuario;
    private final LeituraController leituraController;
    private final AutorController autorController;

    private JComboBox<Object> titleBox;
    private JTextField pagesQtdField;
    private JComboBox<Object> autorBox;
    private JComboBox<Object> typeBox;
    private JComboBox<Object> statusBox;

    JPanel leituraCard;

    public UpdateLeituraPanel(Usuario usuario, LeituraController leituraController, AutorController autorController) {
        this.usuario = usuario;
        this.leituraController = leituraController;
        this.autorController = autorController;

        initComponents();
    }

    private void initComponents() {
        setLayout(new CardLayout());

        leituraCard = createLeituraCard();
        add(leituraCard, "leituraCard");
    }

    private JPanel createLeituraCard() {

        GridBagConstraints gbc = new GridBagConstraints();

        leituraCard = new JPanel();
        leituraCard.setLayout(new GridBagLayout());

        pagesQtdField = new JTextField("0");

        var authorsList = autorController.getAllAuthors();
        var leiturasList = leituraController.getAllLeiturasById(usuario);

        Object[] bookTypes = Arrays.stream(BookType.values())
                .map(BookType::getType).toArray();

        Object[] bookStatus = Arrays.stream(BookStatus.values())
                .map(BookStatus::getStatus).toArray();

        titleBox = new JComboBox<>(leiturasList.stream().map(Leitura::getTitle).toArray());
        autorBox = new JComboBox<>(authorsList.stream().map(Autor::getAuthorName).toArray());
        typeBox = new JComboBox<>(bookTypes);
        statusBox = new JComboBox<>(bookStatus);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        leituraCard.add(new JLabel("Título"), gbc);
        gbc.gridx++;
        leituraCard.add(titleBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        leituraCard.add(new JLabel("Nome do Autor:"), gbc);
        gbc.gridx++;
        leituraCard.add(autorBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        leituraCard.add(new JLabel("Quantidade de Páginas:"), gbc);
        gbc.gridx++;
        leituraCard.add(pagesQtdField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        leituraCard.add(new JLabel("Tipo:"), gbc);
        gbc.gridx++;
        leituraCard.add(typeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        leituraCard.add(new JLabel("Status"), gbc);
        gbc.gridx++;
        leituraCard.add(statusBox, gbc);

        JButton updateButton = new JButton("Atualizar");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        leituraCard.add(updateButton, gbc);

        updateButton.addActionListener(e -> {
            Autor autor = null;
            for (var a : authorsList)
                if (a.getAuthorName() == autorBox.getSelectedItem()) autor = a;
            Leitura leitura = null;
            for (var a : leiturasList)
                if (a.getTitle() == titleBox.getSelectedItem()) leitura = a;

        });

        return leituraCard;
    }

}
