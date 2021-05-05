/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Form;
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

    ComboBox<Object> cbUser;
    Button btnConnexion;

    private void addGUIs() {
        List<User> listUsers = UserService.getInstance().recupererUsers();
        cbUser = new ComboBox<>();
        for (User user : listUsers) {
            cbUser.addItem(user);
        }
        
        btnConnexion = new Button("Connexion");

        this.addAll(cbUser, btnConnexion);
    }

    private void addActions() {
        btnConnexion.addActionListener(action -> {
            MainApp.setSession((User) cbUser.getSelectedItem());
            List<String> roles = ((User) cbUser.getSelectedItem()).getRoles();
            for (int i = 0; i < roles.size(); i++) {
                switch (roles.get(i)) {
                    case "ROLE_ADMIN":
                        new com.devchasers.khedemti.gui.back_end.MainForm(this,"Back admin").show();
                        break;
                    case "ROLE_SOCIETE":
                        new com.devchasers.khedemti.gui.back_end.MainForm(this,"Back societe").show();
                        break;
                    case "ROLE_CANDIDAT":
                        new com.devchasers.khedemti.gui.front_end.MainForm(this).show();
                        break;
                }
            }
        });
    }
}
