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
import com.devchasers.khedemti.entities.ExperienceDeTravail;
import com.devchasers.khedemti.gui.front_end.MainForm;
import com.devchasers.khedemti.services.ExperienceDeTravailService;

/**
 *
 * @author Faten
 */
public class ModifierExperienceDeTravail extends Form {

    Label labelDescription, labeltitreEmploi, labelnomEntreprise, labelVille, labelDuree;
    TextField tfVille, tfDuree, tftitreEmploi, tfnomEntreprise;
    TextArea tfDescription;
    Button btnModifier;

    Form previous;

    public ModifierExperienceDeTravail(Form previous) {
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

        labeltitreEmploi = new Label("Titre Emploi:");
        labeltitreEmploi.setUIID("defaultLabel");
        tftitreEmploi = new TextField();

        labelnomEntreprise = new Label("Nom Entreprise");
        labelnomEntreprise.setUIID("defaultLabel");
        tfnomEntreprise = new TextField();

        labelVille = new Label("Ville:");
        labelVille.setUIID("defaultLabel");
        tfVille = new TextField();

        labelDuree = new Label("Duree:");
        labelDuree.setUIID("defaultLabel");
        tfDuree = new TextField();

        btnModifier = new Button("Modifier");
        btnModifier.setUIID("buttonSuccess");

        tfDescription.setText(AfficherProfil.experienceDeTravailActuelle.getDescription());
        tftitreEmploi.setText(AfficherProfil.experienceDeTravailActuelle.getTitreEmploi());
        tfnomEntreprise.setText(AfficherProfil.experienceDeTravailActuelle.getNomEntreprise());
        tfVille.setText(AfficherProfil.experienceDeTravailActuelle.getVille());
        tfDuree.setText(AfficherProfil.experienceDeTravailActuelle.getDuree());

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("ExperienceDeTravailContainer");
        container.addAll(
                labelDescription, tfDescription,
                labeltitreEmploi, tftitreEmploi,
                labelnomEntreprise, tfnomEntreprise,
                labelVille, tfVille,
                labelDuree, tfDuree,
                btnModifier
        );

        this.addAll(container);
    }

    private void addActions() {

        btnModifier.addActionListener((action) -> {
            if (controleDeSaisie()) {
                ExperienceDeTravailService.getInstance().modifierExperienceDeTravail(new ExperienceDeTravail(
                        AfficherProfil.experienceDeTravailActuelle.getId(),
                        MainApp.getSession().getCandidatId(),
                        tfDescription.getText(),
                        tftitreEmploi.getText(),
                        tfnomEntreprise.getText(),
                        tfVille.getText(),
                        tfDuree.getText()
                ));

                Dialog.show("Succés", "experienceDeTravail modifié avec succes", new Command("Ok"));

                ((AfficherProfil) previous).refreshProfil();
                MainForm.accueilFrontForm.showBack();
            }
        });
    }

    private boolean controleDeSaisie() {
        if (tfnomEntreprise.getText().equals("")) {
            Dialog.show("niveau Nom Entreprise est vide", "", new Command("Ok"));
            return false;
        }
        if (tftitreEmploi.getText().equals("")) {
            Dialog.show("le champ Titre emploi est vide", "", new Command("Ok"));
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
