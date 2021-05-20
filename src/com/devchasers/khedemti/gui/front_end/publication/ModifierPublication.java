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
import static com.devchasers.khedemti.gui.front_end.publication.AfficherToutPublication.publicationActuelle;
import com.devchasers.khedemti.services.PublicationService;
import java.util.ArrayList;

/**
 *
 * @author Maher
 */
public class ModifierPublication extends Form  {
    
      Label labelTitre, labelDescription;
    TextField tfTitre;
    TextArea tfDescription;
    Button btnModifier;
    
    
  private void addGUIs() {

        labelTitre = new Label("Titre de la publication");
        labelTitre.setUIID("defaultLabel");
        tfTitre = new TextField(publicationActuelle.getTitre());

        labelDescription = new Label("Description : ");
        labelDescription.setUIID("defaultLabel");
        tfDescription = new TextArea(publicationActuelle.getDescription());

        btnModifier = new Button("Modifier");

        btnModifier.setUIID("buttonSuccess");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    
  container.setUIID("PublicationContainer");
        container.addAll(labelTitre, tfTitre, labelDescription, tfDescription, btnModifier);

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
  
  
  
 private void addActions () {
     
 
  btnModifier.addActionListener((action) -> {
            if (controleDeSaisie()) {
                   System.out.println("11");
        Publication publication = new Publication (
                publicationActuelle.getId(),
                MainApp.getSession().getCandidatId(),
                tfTitre.getText(),
                tfDescription.getText(),
                null,
                0); 
                int responseCode = PublicationService.getInstance().modifierPublication(publication);
                   System.out.println("22");
                if (responseCode == 200) {
           
 Dialog.show("Succés", "Publication modifieré avec succes", new Command("Ok"));
                } else {
                    Dialog.show("Erreur", "Erreur d'modifier de Publication. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            }
        });
  
  }
    
    public ModifierPublication(Form previous) {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        addActions();
        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.show());

    }
    
    
    
    
    
}
