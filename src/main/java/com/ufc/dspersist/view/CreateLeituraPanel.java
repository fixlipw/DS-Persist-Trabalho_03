package com.ufc.dspersist.view;

import com.ufc.dspersist.controller.AutorController;
import com.ufc.dspersist.controller.LeituraController;
import com.ufc.dspersist.enumeration.BookStatus;
import com.ufc.dspersist.enumeration.BookType;
import com.ufc.dspersist.model.Autor;
import com.ufc.dspersist.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class CreateLeituraPanel extends JPanel {

    private final Usuario usuario;
    private final LeituraController leituraController;
    private final AutorController autorController;

    private JTextField titleField;
    private JTextField pagesQtdField;
    private JComboBox<Object> autorBox;
    private JComboBox<Object> typeBox;
    private JComboBox<Object> statusBox;

    JPanel leituraCard;

    public CreateLeituraPanel(Usuario usuario, LeituraController leituraController, AutorController autorController) {
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

        titleField = new JTextField();
        pagesQtdField = new JTextField("0");

        var authorsList = autorController.getAllAuthors();

        Object[] bookTypes = Arrays.stream(BookType.values())
                .map(BookType::getType).toArray();

        Object[] bookStatus = Arrays.stream(BookStatus.values())
                .map(BookStatus::getStatus).toArray();

        autorBox = new JComboBox<>(authorsList.stream().map(Autor::getAuthorName).toArray());
        typeBox = new JComboBox<>(bookTypes);
        statusBox = new JComboBox<>(bookStatus);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        leituraCard.add(new JLabel("Título"), gbc);
        gbc.gridx++;
        leituraCard.add(titleField, gbc);

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

        JButton addButton = new JButton("Adicionar");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        leituraCard.add(addButton, gbc);

        addButton.addActionListener(e -> {
            Autor autor = null;
            for (var a : authorsList)
                if (a.getAuthorName() == autorBox.getSelectedItem()) autor = a;
            leituraController.saveLeitura(
                    usuario,
                    titleField.getText(),
                    autor,
                    pagesQtdField.getText(),
                    BookType.getByType((String) typeBox.getSelectedItem()),
                    BookStatus.getByStatus((String) statusBox.getSelectedItem()));
        });

        return leituraCard;
    }

}
