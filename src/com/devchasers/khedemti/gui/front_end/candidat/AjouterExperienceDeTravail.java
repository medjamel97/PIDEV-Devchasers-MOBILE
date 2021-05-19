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
import com.devchasers.khedemti.services.ExperienceDeTravailService;

/**
 *
 * @author Faten
 */
public class AjouterExperienceDeTravail extends Form{
    Label labelDescription,labeltitreEmploi ,labelnomEntreprise, labelVille, labelDuree  ;
    TextField  tfVille, tfDuree, tftitreEmploi ,tfnomEntreprise;
    TextArea tfDescription;
    Button btnAjouter;
    public AjouterExperienceDeTravail(Form previous) {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        addActions();
        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.show());

    }
    private void addGUIs() {

        labeltitreEmploi = new Label("Titre Emploi:");
        labeltitreEmploi.setUIID("defaultLabel");
        tftitreEmploi= new TextField();
        
        labelnomEntreprise = new Label("Nom Entreprise");
        labelnomEntreprise.setUIID("defaultLabel");
        tfnomEntreprise = new TextField();
        
        labelVille = new Label("Ville:");
        labelVille.setUIID("defaultLabel");
        tfVille = new TextField();
                
        labelDuree = new Label("Duree:");
        labelDuree.setUIID("defaultLabel");
        tfDuree = new TextField();
        
        labelDescription = new Label("Description : ");
        labelDescription.setUIID("defaultLabel");
        tfDescription = new TextArea();
                
        
        
        btnAjouter = new Button("Ajouter");

        btnAjouter.setUIID("buttonSuccess");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("ExperienceDeTravailContainer");
        container.addAll(labeltitreEmploi,tftitreEmploi ,labelnomEntreprise,tfnomEntreprise, labelVille, tfVille, labelDuree,
                tfDuree, labelDescription, tfDescription, btnAjouter);

        this.addAll(container);

    }

    private void addActions() {

        btnAjouter.addActionListener((action) -> {
            if (controleDeSaisie()) {
                int responseCode = ExperienceDeTravailService.getInstance().ajouterExperienceDeTravail(new ExperienceDeTravail(
                        MainApp.getSession().getCandidatId(),
                        tftitreEmploi.getText(),
                        tfnomEntreprise.getText(),
                        tfVille.getText(),
                        tfDuree.getText(),
                        tfDescription.getText()
                        
                )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "education ajouté avec succes", new Command("Ok"));
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de education. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
    }

    private boolean controleDeSaisie() {
        if (tfnomEntreprise.getText().equals("")) {
            Dialog.show("niveau Nom Entreprise est vide", "", new Command("Ok"));
            return false;
        }
        if ( tftitreEmploi.getText().equals("")) {
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








 