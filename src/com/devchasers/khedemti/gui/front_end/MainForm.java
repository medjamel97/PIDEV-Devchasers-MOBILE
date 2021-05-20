/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.gui.front_end.candidat.AfficherProfil;
import com.devchasers.khedemti.gui.front_end.categorie.AfficherToutCategorie;
import com.devchasers.khedemti.gui.front_end.evenement.AfficherToutEvenement;
import com.devchasers.khedemti.gui.front_end.formation.AfficherToutFormation;
import com.devchasers.khedemti.gui.front_end.interview.AfficherToutInterview;
import com.devchasers.khedemti.gui.front_end.messagerie.AfficherConversations;
import com.devchasers.khedemti.gui.front_end.mission.AfficherToutMission;
import com.devchasers.khedemti.gui.front_end.offre_de_travail.AfficherToutOffreDeTravail;
import com.devchasers.khedemti.gui.front_end.publication.AfficherToutPublication;
import com.devchasers.khedemti.gui.front_end.revue.ChoixSocieteRevue;
import com.devchasers.khedemti.gui.front_end.societe.AfficherToutSociete;

/**
 *
 * @author Grim
 */
public class MainForm extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Label label;
    Form connexion;
    
    public static Form accueilFrontForm;
            
    public MainForm(Form previous) {
        super(new BorderLayout());
        connexion = previous;
        accueilFrontForm = this;
        addGUIs();

        getToolbar().hideToolbar();
    }

    private void addGUIs() {
        Tabs tabs = new Tabs();
        tabs.addTab("Accueil", FontImage.MATERIAL_HOME, 5, new AfficherToutPublication(this));
        tabs.addTab("Societes", FontImage.MATERIAL_APARTMENT, 5, new AfficherToutSociete());
        tabs.addTab("Offres", FontImage.MATERIAL_WORK, 5, new AfficherToutOffreDeTravail(this));
        tabs.addTab("Profil", FontImage.MATERIAL_ACCOUNT_BOX, 5, new AfficherProfil());
        tabs.addTab("Plus", FontImage.MATERIAL_MENU, 5, moreGUI());
        this.add(BorderLayout.CENTER, tabs);
    }

    private Container moreGUI() {

        Label topLabel = new Label("Menu");
        topLabel.setUIID("links");

        ImageViewer userImage = new ImageViewer(theme.getImage("default.jpg"));
        userImage.setPreferredH(200);
        userImage.setPreferredW(200);
        userImage.setUIID("candidatImage");
        label = new Label(MainApp.getSession().getEmail());
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setUIID("btnDanger");
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_LOGOUT);
        btnDeconnexion.addActionListener(action -> {
            connexion.showBack();
        });

        Container userContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        userContainer.setUIID("userContainer");
        userContainer.addAll(userImage, label, btnDeconnexion);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(topLabel,
                userContainer,
                makeButton("    Messagerie", FontImage.MATERIAL_CHAT, new AfficherConversations(this)),
                makeButton("    Categories d'offres", FontImage.MATERIAL_CATEGORY, new AfficherToutCategorie(this)),
                makeButton("    Missions", FontImage.MATERIAL_TRACK_CHANGES, new AfficherToutMission(this)),
                makeButton("    Evenements", FontImage.MATERIAL_EVENT, new AfficherToutEvenement(this)),
                makeButton("    Formations", FontImage.MATERIAL_MENU_BOOK, new AfficherToutFormation(this)),
                makeButton("    Revues", FontImage.MATERIAL_RATE_REVIEW, new ChoixSocieteRevue(this)),
                makeButton("    Interviews", FontImage.MATERIAL_CONNECT_WITHOUT_CONTACT, new AfficherToutInterview(this))
        );

        return (menuContainer);
    }

    private Button makeButton(String nomBouton, char icon, Form localisation) {
        Button button = new Button(nomBouton);
        button.setUIID("links");
        button.setMaterialIcon(icon);
        button.addActionListener(action -> {
            localisation.show();
        });
        return button;
    }
}
