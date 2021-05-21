/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.back_end.offre_de_travail;

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
import com.devchasers.khedemti.entities.OffreDeTravail;
import com.devchasers.khedemti.gui.front_end.MainForm;
import com.devchasers.khedemti.services.OffreDeTravailService;

/**
 *
 * @author Anis
 */
public class AjouterOffre extends Form {

    Label labelNom, labelDescription;
    TextField tfNom;
    TextArea tfDescription;
    Button btnAjouter;

    Form previous;

    public AjouterOffre(Form previous) {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        this.previous = previous;

        addGUIs();
        addActions();
        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> MainForm.accueilFrontForm.showBack());

    }

    private void addGUIs() {

        labelNom = new Label("Objet :");
        labelNom.setUIID("defaultLabel");
        tfNom = new TextField();

        labelDescription = new Label("Description : ");
        labelDescription.setUIID("defaultLabel");
        tfDescription = new TextArea();

        btnAjouter = new Button("Ajouter");

        btnAjouter.setUIID("buttonSuccess");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("offreContainer");
        container.addAll(labelNom, tfNom, labelDescription, tfDescription, btnAjouter);

        this.addAll(container);

    }

    private void addActions() {

        btnAjouter.addActionListener((action) -> {
            if (controleDeSaisie()) {
                OffreDeTravailService.getInstance().ajouterOffre(new OffreDeTravail(
                        1,//categorie
                        MainApp.getSession().getSocieteId(),
                        tfNom.getText(),
                        tfDescription.getText()
                ));
                Dialog.show("Succés", "offre ajouté avec succes", new Command("Ok"));
                ((AfficherMesOffresDeTravail) previous).refresh();

                previous.showBack();
            }
        });
    }

    private boolean controleDeSaisie() {
        if (tfNom.getText().equals("")) {
            Dialog.show("Objet vide", "", new Command("Ok"));
            return false;
        }
        if (tfDescription.getText().equals("")) {
            Dialog.show("Description vide", "", new Command("Ok"));
            return false;
        }

        return true;
    }

}
