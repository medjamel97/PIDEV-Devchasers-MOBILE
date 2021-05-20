/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.back_end.mission;

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
import com.codename1.ui.spinner.DateSpinner;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.Mission;
import com.devchasers.khedemti.entities.Revue;
import com.devchasers.khedemti.gui.front_end.revue.AfficherToutRevue;
import com.devchasers.khedemti.services.MissionService;
import com.devchasers.khedemti.services.RevueService;

/**
 *
 * @author Akram
 */
public class ManipulerMission extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    int nbEtoiles = 0;

    Label labelMissionName, labelObjet, labelDescription;
    Container starsContainer;
    TextField tfObjet,tfName,tfStatus,tfnbHeure,tfPrixHeure,tfVille,tfLong,tfLat;
    TextArea tfDescription;
    Button[] stars = {new Button(), new Button(), new Button(), new Button(), new Button()};
    Button btnValider;
     DateSpinner date;

    public ManipulerMission(Form previous) {

        super(
                AfficherMesMissions.currentMission == null ? "Nouvelle mission" : "Modifier ma mission",
                new BoxLayout(BoxLayout.Y_AXIS)
        );
        addGUIs();
        addActions(previous);

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
//            labelMissionName = new Label("nom du mission ");
//        labelMissionName.setUIID("defaultLabel");
//        starsContainer = new Container();
//        starsContainer.add(labelMissionName);
//        for (int indexEtoile = 0; indexEtoile < 5; indexEtoile++) {
//            int i = indexEtoile;
//            stars[i].addActionListener(action -> {
//                setStars(i + 1);
//            });
//            stars[i].setUIID("starButton");
//            stars[i].setFocusable(false);
//            starsContainer.add(stars[i]);
//        }
//
//        labelObjet = new Label("Objet :");
//        labelObjet.setUIID("defaultLabel");
//        tfObjet = new TextField();
//
//        labelDescription = new Label("Description : ");
//        labelDescription.setUIID("defaultLabel");
//        tfDescription = new TextArea();
//
//        if (AfficherToutRevue.revueActuelle == null) {
//            setStars(0);
//            btnManipuler = new Button("Ajouter");
//        } else {
//            setStars(AfficherToutRevue.revueActuelle.getNbEtoiles());
//            tfObjet.setText((String) AfficherToutRevue.revueActuelle.getObjet());
//            tfDescription.setText((String) AfficherToutRevue.revueActuelle.getDescription());
//
//            btnManipuler = new Button("Modifier");
//
//        }
//        btnManipuler.setUIID("buttonSuccess");
//
        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("revueContainer");
         tfName = new TextField("", "Mission Name");
         tfStatus = new TextField("", "description");
         date = new DateSpinner();
         tfnbHeure = new TextField("", "nombre d'heure");
         tfPrixHeure = new TextField("", "Prix Heure");
         tfVille = new TextField("", "ville");
         tfLong = new TextField("", "longitude");
         tfLat = new TextField("", "latitude");
         btnValider = new Button("Add Mission");

        container.addAll(tfName, tfStatus, date, tfnbHeure, tfPrixHeure, tfVille, tfLong, tfLat, btnValider);

        this.addAll(container);
    }

    private void addActions(Form previous) {
  
        if ( AfficherMesMissions.currentMission == null) {
            btnValider.addActionListener(action -> {
                      String day = String.valueOf(date.getCurrentDay());
        String month = String.valueOf(date.getCurrentMonth());
        String year = String.valueOf(date.getCurrentYear());
                if (controleDeSaisie()) {
                    int responseCode = MissionService.getInstance().addMission(
                            new Mission( 1,
                                tfName.getText(),
                                tfStatus.getText(),
                                day + "/" + month + "/" + year,
                                Integer.parseInt(tfnbHeure.getText()),
                                Float.parseFloat(tfPrixHeure.getText()),
                                tfVille.getText(),
                                tfLong.getText(),
                                tfLat.getText()
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
            btnValider.addActionListener(action -> {
                      String day = String.valueOf(date.getCurrentDay());
        String month = String.valueOf(date.getCurrentMonth());
        String year = String.valueOf(date.getCurrentYear());
            int responseCode = MissionService.getInstance().modifierRevue(
                        new Mission( AfficherMesMissions.currentMission.getId(),
                                1,
                                tfName.getText(),
                                tfStatus.getText(),
                                day + "/" + month + "/" + year,
                                Integer.parseInt(tfnbHeure.getText()),
                                Float.parseFloat(tfPrixHeure.getText()),
                                tfVille.getText(),
                                tfLong.getText(),
                                tfLat.getText()
                        )
                );
             if (responseCode == 200) {
                        Dialog.show("Succés", "Revue ajouté avec succes", new Command("Ok"));
                         ((AfficherMesMissions)previous).refreshUI();  
                            previous.showBack();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de revue. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
            });
        }
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
