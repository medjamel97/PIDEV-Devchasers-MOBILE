/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.interview;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Grim
 */
public class ManipulerInterview extends Form {

   /* Resources theme = UIManager.initFirstTheme("/theme");

    TextField tfObjet;
    TextArea tfDescription;
    ComboBox<String> cbDefficulte;
    String[] difficulte = {"trés facile", "facile", "moyen", "difficile", "trés difficile"};

    public InterviewManipuler() {

        super(
                AfficherTout.interviewActuelleMap == null ? "Nouvelle interview" : "Modifier ma interview",
                new BoxLayout(BoxLayout.Y_AXIS)
        );
        addGUIs();
    }

    private void addGUIs() {
        Container topBarContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Button btnRetour = new Button("<- Retour");
        btnRetour.addActionListener(action -> {
            new AfficherTout().show();
        });
        topBarContainer.add(btnRetour);

        Label labelDifficulte = new Label("Difficulte : ");
        labelDifficulte.setUIID("defaultLabel");

        cbDefficulte = new ComboBox<>();
        cbDefficulte.addItem(difficulte[0]);
        cbDefficulte.addItem(difficulte[1]);
        cbDefficulte.addItem(difficulte[2]);
        cbDefficulte.addItem(difficulte[3]);
        cbDefficulte.addItem(difficulte[4]);

        Label labelObjet = new Label("Objet :");
        labelObjet.setUIID("defaultLabel");
        tfObjet = new TextField();

        Label labelDescription = new Label("Description : ");
        labelDescription.setUIID("defaultLabel");
        tfDescription = new TextArea();

        Button btnManipuler;

        if (AfficherTout.interviewActuelleMap == null) {
            cbDefficulte.setSelectedIndex(0);
            btnManipuler = new Button("Ajouter");
            btnManipuler.addActionListener(action -> {
                manipulerInterview(0);
            });
        } else {
            long i = Math.round((double) AfficherTout.interviewActuelleMap.get("difficulte"));

            if (i == 0) {
                cbDefficulte.setSelectedIndex(0);
            } else if (i == 1) {
                cbDefficulte.setSelectedIndex(1);
            } else if (i == 2) {
                cbDefficulte.setSelectedIndex(2);
            } else if (i == 3) {
                cbDefficulte.setSelectedIndex(3);
            } else if (i == 4) {
                cbDefficulte.setSelectedIndex(4);
            }

            tfObjet.setText((String) AfficherTout.interviewActuelleMap.get("objet"));
            tfDescription.setText((String) AfficherTout.interviewActuelleMap.get("description"));

            btnManipuler = new Button("Modifier");
            btnManipuler.addActionListener(action -> {
                manipulerInterview(Math.round((double) AfficherTout.interviewActuelleMap.get("id")));
            });
        }
        btnManipuler.setUIID("buttonSuccess");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("interviewContainer");
        container.addAll(cbDefficulte, labelObjet, tfObjet, labelDescription, tfDescription, btnManipuler);

        this.addAll(topBarContainer, container);
    }

    private void manipulerInterview(long idInterview) {
        if (controleDeSaisie()) {
            try {
                String manipulation1 = "ajouté";
                String manipulation2 = "d'ajout";

                ConnectionRequest cr = new ConnectionRequest();
                if (idInterview != 0) {
                    cr.addArgument("idInterview", String.valueOf(idInterview));
                    manipulation1 = "modifié";
                    manipulation2 = "de modification";
                }
                cr.addArgument("candidatureOffre", "2");
                String difficulteInt = "";
                switch (cbDefficulte.getSelectedItem()) {
                    case "trés facile":
                        difficulteInt = "0";
                        break;
                    case "facile":
                        difficulteInt = "1";
                        break;
                    case "moyen":
                        difficulteInt = "2";
                        break;
                    case "difficile":
                        difficulteInt = "3";
                        break;
                    case "trés difficile":
                        difficulteInt = "4";
                        break;
                }
                cr.addArgument("difficulte", difficulteInt);
                cr.addArgument("objet", tfObjet.getText());
                cr.addArgument("description", tfDescription.getText());
                cr.setUrl("http://127.0.0.1:8000/mobile/manipuler_interview");
                try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }

                char[] state = new char[1];
                new InputStreamReader(new ByteArrayInputStream(cr.getResponseData()), "UTF-8").read(state);

                switch (Integer.valueOf(String.valueOf(state))) {
                    case -1:
                        Dialog.show("Veuillez remplir les champs", "", new Command("Ok"));
                        break;
                    case 0:
                        Dialog.show("Interview " + manipulation1, "", new Command("Ok"));
                        AfficherTout.interviewActuelleMap = null;
                        new AfficherTout().show();
                        break;
                    case 1:
                        Dialog.show("Erreur " + manipulation2, "", new Command("Ok"));
                        break;
                    default:
                        Dialog.show("Unknown error", "", new Command("Ok"));
                        break;
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
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
        return true;
    }*/
}
