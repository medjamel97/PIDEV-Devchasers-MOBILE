/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.interview;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
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
import com.devchasers.khedemti.entities.Interview;
import static com.devchasers.khedemti.gui.front_end.interview.AfficherToutInterview.interviewActuelle;
import com.devchasers.khedemti.services.InterviewService;

/**
 *
 * @author Grim
 */
public class ManipulerInterview extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    Label labelDifficulte, labelObjet, labelDescription;
    TextField tfObjet;
    TextArea tfDescription;
    ComboBox<String> cbDifficulte;
    String[] difficulte = {"trés facile", "facile", "moyen", "difficile", "trés difficile"};
    Button btnManipuler;

    Form previous;

    public ManipulerInterview(Form previous) {
        super(
                AfficherToutInterview.interviewActuelle == null ? "Nouvelle interview" : "Modifier ma interview",
                new BoxLayout(BoxLayout.Y_AXIS)
        );
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        labelDifficulte = new Label("Difficulte : ");
        labelDifficulte.setUIID("defaultLabel");

        cbDifficulte = new ComboBox<>();
        cbDifficulte.addItem(difficulte[0]);
        cbDifficulte.addItem(difficulte[1]);
        cbDifficulte.addItem(difficulte[2]);
        cbDifficulte.addItem(difficulte[3]);
        cbDifficulte.addItem(difficulte[4]);

        labelObjet = new Label("Objet :");
        labelObjet.setUIID("defaultLabel");
        tfObjet = new TextField();

        labelDescription = new Label("Description : ");
        labelDescription.setUIID("defaultLabel");
        tfDescription = new TextArea();

        if (AfficherToutInterview.interviewActuelle == null) {
            btnManipuler = new Button("Ajouter");
        } else {
            tfObjet.setText((String) AfficherToutInterview.interviewActuelle.getObjet());
            tfDescription.setText((String) AfficherToutInterview.interviewActuelle.getDescription());

            btnManipuler = new Button("Modifier");

        }
        btnManipuler.setUIID("buttonSuccess");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("interviewContainer");
        container.addAll(cbDifficulte, labelObjet, tfObjet, labelDescription, tfDescription, btnManipuler);

        this.addAll(container);
    }

    private void addActions() {
        if (AfficherToutInterview.interviewActuelle == null) {
            btnManipuler.addActionListener(action -> {
                if (controleDeSaisie()) {

                    if (interviewActuelle.getDifficulte() == 0) {
                        cbDifficulte.setSelectedIndex(0);
                    } else if (interviewActuelle.getDifficulte() == 1) {
                        cbDifficulte.setSelectedIndex(1);
                    } else if (interviewActuelle.getDifficulte() == 2) {
                        cbDifficulte.setSelectedIndex(2);
                    } else if (interviewActuelle.getDifficulte() == 3) {
                        cbDifficulte.setSelectedIndex(3);
                    } else if (interviewActuelle.getDifficulte() == 4) {
                        cbDifficulte.setSelectedIndex(4);
                    }

                    int responseCode = InterviewService.getInstance().ajouterInterview(
                            new Interview(
                                    AfficherToutInterview.candidatureOffreActuelle.getId(),
                                    cbDifficulte.getSelectedIndex(),
                                    tfObjet.getText(),
                                    tfDescription.getText(),
                                    null
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Interview ajouté avec succes", new Command("Ok"));
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de interview. Code d'erreur : " + responseCode, new Command("Ok"));
                    }

                    ((AfficherToutInterview) previous).refresh();
                    previous.showBack();
                }
            });
        } else {
            btnManipuler.addActionListener(action -> {
                if (controleDeSaisie()) {
                    InterviewService.getInstance().modifierInterview(
                            new Interview(
                                    interviewActuelle.getId(),
                                    AfficherToutInterview.candidatureOffreActuelle.getId(),
                                    cbDifficulte.getSelectedIndex(),
                                    tfObjet.getText(),
                                    tfDescription.getText(),
                                    null
                            )
                    );

                    Dialog.show("Succés", "Interview modifié avec succes", new Command("Ok"));

                    ((AfficherToutInterview) previous).refresh();
                    previous.showBack();
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
        if (cbDifficulte.getSelectedIndex() == 0) {
            Dialog.show("Saisir une difficulte", "", new Command("Ok"));
            return false;
        }
        return true;
    }
}
