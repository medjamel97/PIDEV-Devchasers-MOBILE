/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end;

import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.gui.front_end.candidat.AfficherProfil;
import com.devchasers.khedemti.gui.front_end.categorie.AfficherToutCategorie;
import com.devchasers.khedemti.gui.front_end.evenement.AfficherToutEvenement;
import com.devchasers.khedemti.gui.front_end.formation.AfficherToutFormation;
import com.devchasers.khedemti.gui.front_end.interview.AfficherToutInterview;
import com.devchasers.khedemti.gui.front_end.mission.AfficherToutMission;
import com.devchasers.khedemti.gui.front_end.offre_de_travail.AfficherToutOffreDeTravail;
import com.devchasers.khedemti.gui.front_end.publication.AfficherToutPublication;
import com.devchasers.khedemti.gui.front_end.revue.AfficherToutRevue;
import com.devchasers.khedemti.gui.front_end.revue.ChoixSocieteRevue;
import com.devchasers.khedemti.gui.front_end.societe.AfficherToutSociete;

/**
 *
 * @author Grim
 */
public class MainForm extends Form {

    public MainForm(Form previous) {
        super("Front", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    Label label;

    private void addGUIs() {
        label = new Label("Connecté avec email : " + MainApp.getSession().getEmail());

        addButton("Accueil", new AfficherToutPublication(this));
        addButton("Profil", new AfficherProfil(this));
        addButton("Societes", new AfficherToutSociete(this));
        addButton("Categories", new AfficherToutCategorie(this));
        addButton("Offres de travail", new AfficherToutOffreDeTravail(this));
        addButton("Revues", new ChoixSocieteRevue(this));
        addButton("Interviews", new AfficherToutInterview(this));
        addButton("Missions", new AfficherToutMission(this));
        addButton("Evenements", new AfficherToutEvenement(this));
        addButton("Formations", new AfficherToutFormation(this));

    }

    private void addButton(String nomBouton, Form localisation) {
        Button button = new Button(nomBouton);
        button.addActionListener(action -> {
            localisation.show();
        });
        this.add(button);
    }
}
