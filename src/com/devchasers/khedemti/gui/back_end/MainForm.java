/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.back_end;

import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.gui.back_end.candidat.AdminAfficherToutCandidat;
import com.devchasers.khedemti.gui.back_end.candidat.AfficherMesCandidats;
import com.devchasers.khedemti.gui.back_end.categorie.AdminAfficherToutCategorie;
import com.devchasers.khedemti.gui.back_end.mission.AfficherMesMissions;
import com.devchasers.khedemti.gui.back_end.offre_de_travail.AfficherMesOffresDeTravail;
import java.util.List;

/**
 *
 * @author Grim
 */
public class MainForm extends Form {

    public MainForm(Form previous, String title) {
        super(title, new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    Label label;

    private void addGUIs() {
        label = new Label("Connecté avec email : " + MainApp.getSession().getEmail());

        List<String> roles = MainApp.getSession().getRoles();
        boolean isAdmin = false;

        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }

        if (isAdmin) {
            addButton("Candidats", new AdminAfficherToutCandidat(this));
            addButton("Categories", new AdminAfficherToutCategorie(this));
        } else {
            addButton("Mes candidats", new AfficherMesCandidats(this));
            addButton("Mes offres de travail", new AfficherMesOffresDeTravail(this));
            addButton("Mes Missions", new AfficherMesMissions(this));
        }
    }

    private void addButton(String nomBouton, Form localisation) {
        Button button = new Button(nomBouton);
        button.addActionListener(action -> {
            localisation.show();
        });
        this.add(button);
    }
}
