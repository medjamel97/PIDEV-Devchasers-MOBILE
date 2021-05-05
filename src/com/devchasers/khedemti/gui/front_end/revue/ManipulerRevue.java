/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.revue;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.Revue;
import com.devchasers.khedemti.services.RevueService;

/**
 *
 * @author Grim
 */
public class ManipulerRevue extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    int nbEtoiles = 0;

    Label labelNbEtoiles, labelObjet, labelDescription;
    Container starsContainer;
    TextField tfObjet;
    TextArea tfDescription;
    Button[] stars = {new Button(), new Button(), new Button(), new Button(), new Button()};
    Button btnManipuler;

    public ManipulerRevue(Form previous) {

        super(
                AfficherToutRevue.revueActuelle == null ? "Nouvelle revue" : "Modifier ma revue",
                new BoxLayout(BoxLayout.Y_AXIS)
        );
        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        labelNbEtoiles = new Label("Note : ");
        labelNbEtoiles.setUIID("defaultLabel");
        starsContainer = new Container();
        starsContainer.add(labelNbEtoiles);
        for (int indexEtoile = 0; indexEtoile < 5; indexEtoile++) {
            int i = indexEtoile;
            stars[i].addActionListener(action -> {
                setStars(i + 1);
            });
            stars[i].setUIID("starButton");
            stars[i].setFocusable(false);
            starsContainer.add(stars[i]);
        }

        labelObjet = new Label("Objet :");
        labelObjet.setUIID("defaultLabel");
        tfObjet = new TextField();

        labelDescription = new Label("Description : ");
        labelDescription.setUIID("defaultLabel");
        tfDescription = new TextArea();

        if (AfficherToutRevue.revueActuelle == null) {
            setStars(0);
            btnManipuler = new Button("Ajouter");
        } else {
            setStars(AfficherToutRevue.revueActuelle.getNbEtoiles());
            tfObjet.setText((String) AfficherToutRevue.revueActuelle.getObjet());
            tfDescription.setText((String) AfficherToutRevue.revueActuelle.getDescription());

            btnManipuler = new Button("Modifier");

        }
        btnManipuler.setUIID("buttonSuccess");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("revueContainer");
        container.addAll(starsContainer, labelObjet, tfObjet, labelDescription, tfDescription, btnManipuler);

        this.addAll(container);
    }

    private void addActions() {
        if (AfficherToutRevue.revueActuelle == null) {
            btnManipuler.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = RevueService.getInstance().ajouterRevue(
                            new Revue(
                                    MainApp.getSession().getCandidatId(),
                                    nbEtoiles,
                                    tfObjet.getText(),
                                    tfDescription.getText(),
                                    null
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Revue ajouté avec succes", new Command("Ok"));
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de revue. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            btnManipuler.addActionListener(action -> {
                RevueService.getInstance().modifierRevue(
                        new Revue(
                                AfficherToutRevue.candidatureOffreActuelle.getId(),
                                nbEtoiles,
                                tfObjet.getText(),
                                tfDescription.getText(),
                                null
                        )
                );
            });
        }
    }

    private void setStars(int nb) {
        for (int i = 0; i < 5; i++) {
            if (i < nb) {
                stars[i].setIcon(theme.getImage("star.png"));
            } else {
                stars[i].setIcon(theme.getImage("star-outline.png"));
            }
        }
        nbEtoiles = nb;
    }

    private boolean controleDeSaisie() {
        if (tfObjet.getText().equals("")) {
            Dialog.show("Objet vide", "", new Command("Ok"));
            return false;
        }
        if (tfDescription.getText().equals("")) {
            Dialog.show("Description vide", "", new Command("Ok"));
            return false;
        }
        if (nbEtoiles == 0) {
            Dialog.show("Saisir une note", "", new Command("Ok"));
            return false;
        }
        return true;
    }
}
