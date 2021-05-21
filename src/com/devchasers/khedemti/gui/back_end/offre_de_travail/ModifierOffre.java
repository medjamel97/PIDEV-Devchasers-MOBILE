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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.OffreDeTravail;
import com.devchasers.khedemti.services.OffreDeTravailService;

/**
 *
 * @author Anis
 */
public class ModifierOffre extends Form {

    Label labelNom, labelDescription;
    TextField tfNom;
    TextArea tfDescription;
    Button btnModifier;
    Form previous;

    public ModifierOffre(Form previous) {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;
        addGUIs();
        addActions();
        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.show());

    }

    private void addGUIs() {

        labelNom = new Label("Objet :");
        labelNom.setUIID("defaultLabel");
        tfNom = new TextField();

        labelDescription = new Label("Description : ");
        labelDescription.setUIID("defaultLabel");
        tfDescription = new TextArea();

        btnModifier = new Button("Modifer");

        btnModifier.setUIID("buttonSuccess");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("offreContainer");
        container.addAll(labelNom, tfNom, labelDescription, tfDescription, btnModifier);

        this.addAll(container);

    }

    private void addActions() {

        btnModifier.addActionListener((ActionEvent action) -> {
            OffreDeTravailService.getInstance().modifierOffre(new OffreDeTravail(AfficherMesOffresDeTravail.offreActuelle.getId(),
                    
                
                    tfNom.getText(),
                    tfDescription.getText()
            ));
              Dialog.show("Succés", "offre modifié avec succes", new Command("Ok"));
                ((AfficherMesOffresDeTravail) previous).refresh();

                previous.showBack();

        });
    }
}
