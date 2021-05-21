/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.candidat;

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
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.Education;
import com.devchasers.khedemti.gui.front_end.MainForm;
import com.devchasers.khedemti.services.EducationService;

/**
 *
 * @author Faten
 */
public class AjouterEducation extends Form {

    Label labelDescription, labelniveauEducation, labelEtablissement, labelFiliere, labelVille, labelDuree;
    TextField tfniveauEducation, tfEtablissement, tfFiliere, tfVille, tfDuree;
    TextArea tfDescription;
    Button btnAjouter;

    Form previous;

    public AjouterEducation(Form previous) {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        this.previous = previous;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> MainForm.accueilFrontForm.showBack());

    }

    private void addGUIs() {

        labelDescription = new Label("Description : ");
        labelDescription.setUIID("defaultLabel");
        tfDescription = new TextArea();

        labelniveauEducation = new Label("niveau d'Education:");
        labelniveauEducation.setUIID("defaultLabel");
        tfniveauEducation = new TextField();

        labelFiliere = new Label("Filiere:");
        labelFiliere.setUIID("defaultLabel");
        tfFiliere = new TextField();

        labelEtablissement = new Label("Etablissement:");
        labelEtablissement.setUIID("defaultLabel");
        tfEtablissement = new TextField();

        labelVille = new Label("Ville:");
        labelVille.setUIID("defaultLabel");
        tfVille = new TextField();

        labelDuree = new Label("Duree:");
        labelDuree.setUIID("defaultLabel");
        tfDuree = new TextField();

        btnAjouter = new Button("Ajouter");
        btnAjouter.setUIID("buttonSuccess");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("EducationContainer");
        container.addAll(
                labelDescription, tfDescription,
                labelniveauEducation, tfniveauEducation,
                labelFiliere, tfFiliere,
                labelEtablissement, tfEtablissement,
                labelVille, tfVille,
                labelDuree, tfDuree,
                btnAjouter
        );

        this.addAll(container);

    }

    private void addActions() {

        btnAjouter.addActionListener((action) -> {
            if (controleDeSaisie()) {
                EducationService.getInstance().ajouterEducation(new Education(
                        MainApp.getSession().getCandidatId(),
                        tfDescription.getText(),
                        tfniveauEducation.getText(),
                        tfFiliere.getText(),
                        tfEtablissement.getText(),
                        tfVille.getText(),
                        tfDuree.getText()
                ));
                Dialog.show("Succés", "education ajouté avec succes", new Command("Ok"));

                ((AfficherProfil) previous).refreshProfil();
                MainForm.accueilFrontForm.showBack();
            }
        });
    }

    private boolean controleDeSaisie() {
        if (tfniveauEducation.getText().equals("")) {
            Dialog.show("niveau education est vide", "", new Command("Ok"));
            return false;
        }
        if (tfFiliere.getText().equals("")) {
            Dialog.show("le champ Filiere est vide", "", new Command("Ok"));
            return false;
        }
        if (tfEtablissement.getText().equals("")) {
            Dialog.show("le champ Etablissement est vide", "", new Command("Ok"));
            return false;
        }
        if (tfVille.getText().equals("")) {
            Dialog.show("le champ ville est vide", "", new Command("Ok"));
            return false;
        }
        if (tfDuree.getText().equals("")) {
            Dialog.show("le champ duree est vide", "", new Command("Ok"));
            return false;
        }
        if (tfDescription.getText().equals("")) {
            Dialog.show("le champ description est vide", "", new Command("Ok"));
            return false;
        }

        return true;
    }

}
