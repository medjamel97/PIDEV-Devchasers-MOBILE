/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui;

import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.services.UserService;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Grim
 */
public class Inscription extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    
    Form previous;

    public Inscription(Form previous) {
        super("Authentification", new BoxLayout(BoxLayout.Y_AXIS));
        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.show());

        this.previous = previous;
        
        addGUIs();
        addActions();
    }

    TextField tfEmail, tfPassword, tfDay, tfMonth, tfYear, tfNom, tfPrenom, tfTel, tfSexe, tfIdPhoto;
    Button btnInscription;

    private void addGUIs() {

        tfEmail = new TextField("", "Entrez votre email");
        tfPassword = new TextField("", "PASSWORD", 20, TextField.PASSWORD);

        tfDay = new TextField("", "Entrez votre jour de naissance");
        tfMonth = new TextField("", "Entrez votre mois de naissance");
        tfYear = new TextField("", "Entrez votre année de naissance");
        tfNom = new TextField("", "Entrez votre nom");
        tfPrenom = new TextField("", "Entrez votre prenom");
        tfTel = new TextField("", "Entrez votre telephone");
        tfSexe = new TextField("", "Entrez votre sexe");
        tfIdPhoto = new TextField("", "Entrez votre idphoto");

        btnInscription = new Button("Inscription");

        this.addAll(tfEmail, tfPassword, tfDay, tfMonth, tfYear, tfNom, tfPrenom, tfTel, tfSexe, btnInscription);
    }

    private void addActions() {
        btnInscription.addActionListener(action -> {
            UserService.getInstance().inscriptionCandidat(
                    tfEmail.getText(),
                    tfPassword.getText(),
                    tfDay.getText(),
                    tfMonth.getText(),
                    tfYear.getText(),
                    tfNom.getText(),
                    tfPrenom.getText(),
                    tfTel.getText(),
                    tfSexe.getText()
            );
            Dialog.show("Succes", "Inscription effectué", new Command("Ok"));
            previous.showBack();
        });
    }
}
