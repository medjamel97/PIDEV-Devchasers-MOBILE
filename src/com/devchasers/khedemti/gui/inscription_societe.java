/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.services.UserService;

/**
 *
 * @author Grim
 */
public class inscription_societe extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    Form previous;

    public inscription_societe(Form previous) {
        super("Authentification", new BoxLayout(BoxLayout.Y_AXIS));
        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.show());

        this.previous = previous;

        addGUIs();
        addActions();
    }
    TextField tfEmail, tfPassword, tfNom, tfTel, tfCreationDate, tfIdPhoto;
    Button btnInscription;

    private void addGUIs() {

        tfEmail = new TextField("", "Entrez votre email");
        tfPassword = new TextField("", "PASSWORD", 20, TextField.PASSWORD);
        tfNom = new TextField("", "Entrez votre nom");
        tfTel = new TextField("", "Entrez votre telephone");
        tfCreationDate = new TextField("", "Entrez votre prenom");
        tfIdPhoto = new TextField("", "Entrez votre idphoto");

        btnInscription = new Button("Inscription");

        this.addAll(tfEmail, tfPassword, tfNom, tfTel, tfCreationDate, tfIdPhoto, btnInscription);
    }

    private void addActions() {
        /*btnInscription.addActionListener(action -> {
            UserService.getInstance().inscriptionSociete(
                    tfEmail.getText(),
                    tfPassword.getText(),
                    tfNom.getText(),
                    tfTel.getText(),
                    tfCreationDate.getText()
            );
            Dialog.show("Succes", "Inscription effectué", new Command("Ok"));
            previous.showBack();
        });*/
    }
}
