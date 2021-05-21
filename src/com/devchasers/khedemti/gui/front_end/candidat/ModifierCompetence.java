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
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.Competence;
import com.devchasers.khedemti.gui.front_end.MainForm;
import com.devchasers.khedemti.services.CompetenceService;

/**
 *
 * @author Faten
 */
public class ModifierCompetence extends Form {

    Label labelLevel, labelName;
    TextField tfName;
    TextField tfLevel;
    Button btnModifier;

    Form previous;

    public ModifierCompetence(Form previous) {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        this.previous = previous;

        addGUIs();
        addActions();
        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> MainForm.accueilFrontForm.showBack());

    }

    private void addGUIs() {

        labelName = new Label("Nom competence :");
        labelName.setUIID("defaultLabel");
        tfName = new TextField();

        labelLevel = new Label("level : ");
        labelLevel.setUIID("defaultLabel");
        tfLevel = new TextField();

        btnModifier = new Button("Modifier");

        btnModifier.setUIID("buttonSuccess");

        tfName.setText((String) AfficherProfil.competenceActuelle.getName());
        tfLevel.setText(String.valueOf(AfficherProfil.competenceActuelle.getLevel()));

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("CompetenceContainer");
        container.addAll(labelName, tfName, labelLevel, tfLevel, btnModifier);

        this.addAll(container);
    }

    private void addActions() {

        btnModifier.addActionListener((action) -> {
            if (controleDeSaisie()) {
                CompetenceService.getInstance().modifierCompetence(new Competence(
                        AfficherProfil.competenceActuelle.getId(),
                        MainApp.getSession().getCandidatId(),
                        tfName.getText(),
                        Integer.parseInt(tfLevel.getText())
                ));

                Dialog.show("Succés", "competence modifié avec succes", new Command("Ok"));

                ((AfficherProfil) previous).refreshProfil();
                MainForm.accueilFrontForm.showBack();
            }
        });
    }

    private boolean controleDeSaisie() {
        if (tfName.getText().equals("")) {
            Dialog.show("Nom competence vide", "", new Command("Ok"));
            return false;
        }
        if (tfLevel.getText().length() == 0) {
            Dialog.show("niveau d'expertise vide", "", new Command("Ok"));
            return false;
        }

        return true;
    }
}
