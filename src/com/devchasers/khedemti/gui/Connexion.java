/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.User;
import com.devchasers.khedemti.services.UserService;
import java.util.List;

/**
 *
 * @author Grim
 */
public class Connexion extends Form {

    public Connexion() {
        super("Authentification", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        addActions();
    }

    TextField tfEmail;
    TextField tfPassword;
    Button btnConnexion, btnInscription;

    ComboBox<Object> cbUser;
    Button btnConnexionDebug;

    private void addGUIs() {

        tfEmail = new TextField("", "Entrez votre email");
        tfPassword = new TextField("", "PASSWORD", 20, TextField.PASSWORD);
        btnConnexion = new Button("Connexion");
        btnInscription = new Button("Inscription");

        cbUser = new ComboBox<>();
        for (User user : UserService.getInstance().recupererUsers()) {
            cbUser.addItem(user);
        }

        btnConnexionDebug = new Button("Connexion debug");

        this.addAll(tfEmail, tfPassword, btnConnexion, btnInscription, cbUser, btnConnexionDebug);
    }

    private void addActions() {
        btnConnexion.addActionListener(action -> {
            if (UserService.getInstance().verifierMotDePasse(tfEmail.getText(), tfPassword.getText())) {
                connexion(UserService.getInstance().recupererUserParEmail(tfEmail.getText()));
            } else {
                Dialog.show("Erreur", "Identifiants invalides", new Command("Ok"));
            }
        });

        btnInscription.addActionListener(action -> {
            new Inscription(this).show();
        });

        btnConnexionDebug.addActionListener(action -> {
            connexion((User) cbUser.getSelectedItem());
        });
    }

    private void connexion(User user) {
        List<String> roles = user.getRoles();

        MainApp.setSession(user);

        for (int i = 0; i < roles.size(); i++) {
            switch (roles.get(i)) {
                case "ROLE_ADMIN":
                    new com.devchasers.khedemti.gui.back_end.MainForm(this, "Back admin").show();
                    break;
                case "ROLE_SOCIETE":
                    new com.devchasers.khedemti.gui.back_end.MainForm(this, "Back societe").show();
                    break;
                case "ROLE_CANDIDAT":
                    new com.devchasers.khedemti.gui.front_end.MainForm(this).show();
                    break;
            }
        }
    }
}
