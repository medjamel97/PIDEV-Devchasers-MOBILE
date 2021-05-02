/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.forms;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
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
public class RevueManipuler extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    Button[] stars = {new Button(), new Button(), new Button(), new Button(), new Button()};

    long nbEtoiles = 0;
    TextField tfObjet;
    TextArea tfDescription;

    public RevueManipuler() {

        super(
                RevueAfficherTout.revueActuelleMap == null ? "Nouvelle revue" : "Modifier ma revue",
                new BoxLayout(BoxLayout.Y_AXIS)
        );
        addGUIs();
    }

    private void addGUIs() {

        Label labelNbEtoiles = new Label("Note : ");
        labelNbEtoiles.setUIID("defaultLabel");
        Container starsContainer = new Container();
        starsContainer.add(labelNbEtoiles);
        for (int indexEtoile = 0; indexEtoile < 5; indexEtoile++) {
            int i = indexEtoile;
            stars[i].addActionListener(action -> {
                setStars(i + 1);
            });
            stars[i].setUIID("starButton");
            stars[i].setFocusable(false);
            starsContainer.add(stars[i]);
        }

        Label labelObjet = new Label("Objet :");
        labelObjet.setUIID("defaultLabel");
        tfObjet = new TextField();

        Label labelDescription = new Label("Description : ");
        labelDescription.setUIID("defaultLabel");
        tfDescription = new TextArea();

        Button btnManipuler;

        if (RevueAfficherTout.revueActuelleMap == null) {
            setStars(0);
            btnManipuler = new Button("Ajouter");
            btnManipuler.addActionListener(action -> {
                manipulerRevue(0);
            });
        } else {
            setStars(Math.round((double) RevueAfficherTout.revueActuelleMap.get("nbEtoiles")));
            tfObjet.setText((String) RevueAfficherTout.revueActuelleMap.get("objet"));
            tfDescription.setText((String) RevueAfficherTout.revueActuelleMap.get("description"));

            btnManipuler = new Button("Modifier");
            btnManipuler.addActionListener(action -> {
                manipulerRevue(Math.round((double) RevueAfficherTout.revueActuelleMap.get("id")));
            });
        }
        btnManipuler.setUIID("buttonSuccess");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("revueContainer");
        container.addAll(starsContainer, labelObjet, tfObjet, labelDescription, tfDescription, btnManipuler);
        
        this.add(container);
    }

    private void manipulerRevue(long idRevue) {
        try {
            String manipulation1 = "ajouté";
            String manipulation2 = "d'ajout";

            ConnectionRequest cr = new ConnectionRequest();
            if (idRevue != 0) {
                cr.addArgument("idRevue", String.valueOf(idRevue));
                manipulation1 = "modifié";
                manipulation2 = "de modification";
            }
            cr.addArgument("candidatureOffre", "2");
            cr.addArgument("nbEtoiles", String.valueOf(nbEtoiles));
            cr.addArgument("objet", tfObjet.getText());
            cr.addArgument("description", tfDescription.getText());
            cr.setUrl("http://127.0.0.1:8000/mobile/manipuler_revue");
            NetworkManager.getInstance().addToQueueAndWait(cr);

            char[] state = new char[1];
            new InputStreamReader(new ByteArrayInputStream(cr.getResponseData()), "UTF-8").read(state);

            switch (Integer.valueOf(String.valueOf(state))) {
                case -1:
                    Dialog.show("Veuillez remplir les champs", "", new Command("Ok"));
                    break;
                case 0:
                    Dialog.show("Revue " + manipulation1, "", new Command("Ok"));
                    RevueAfficherTout.revueActuelleMap = null;
                    new RevueAfficherTout().show();
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

    private void setStars(long nb) {
        for (int i = 0; i < 5; i++) {
            if (i < nb) {
                stars[i].setIcon(theme.getImage("star.png"));
            } else {
                stars[i].setIcon(theme.getImage("star-outline.png"));
            }
        }
        nbEtoiles = nb;
    }
}
