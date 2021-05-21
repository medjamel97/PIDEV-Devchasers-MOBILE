/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.publication;

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
import com.devchasers.khedemti.entities.Publication;
import com.devchasers.khedemti.gui.front_end.MainForm;
import com.devchasers.khedemti.services.PublicationService;

/**
 *
 * @author Maher
 */
public class AjouterPublication extends Form {

    Label labelTitre, labelDescription;
    TextField tfTitre;
    TextArea tfDescription;
    Button btnAjouter;

    Form previous;
    
    public AjouterPublication(Form previous) {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        
        this.previous = previous;
        
        addGUIs();
        addActions();
      
        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> MainForm.accueilFrontForm.showBack());
    }

    private void addGUIs() {

        labelTitre = new Label("Titre de la publication");
        labelTitre.setUIID("defaultLabel");
        tfTitre = new TextField();

        labelDescription = new Label("Description : ");
        labelDescription.setUIID("defaultLabel");
        tfDescription = new TextArea();

        btnAjouter = new Button("Ajouter");

        btnAjouter.setUIID("buttonSuccess");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        container.setUIID("PublicationContainer");
        container.addAll(labelTitre, tfTitre, labelDescription, tfDescription, btnAjouter);

        this.addAll(container);
    }

    private boolean controleDeSaisie() {
        if (tfTitre.getText().equals("")) {
            Dialog.show("Objet vide", "", new Command("Ok"));
            return false;
        }
        if (tfDescription.getText().equals("")) {
            Dialog.show("Description vide", "", new Command("Ok"));
            return false;
        }

        return true;
    }

    private void addActions() {

        btnAjouter.addActionListener((action) -> {
            if (controleDeSaisie()) {
                System.out.println("11");

                int responseCode = PublicationService.getInstance().ajouterPublication(new Publication(MainApp.getSession().getCandidatId(), tfTitre.getText(), tfDescription.getText()));
                System.out.println("22");
                String numTelephone = "+21623292574";
                sms s = new sms();
                //  s.send("votre publication a ètè ajouté", numTelephone);
                if (responseCode == 200) {
                    Dialog.show("Succés", "Publication ajouté avec succes", new Command("Ok"));
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de Publication. Code d'erreur : " + responseCode, new Command("Ok"));
                }
                
                ((AfficherToutPublication) previous).refresh();
                MainForm.accueilFrontForm.showBack();
            }
        });

    }
}
