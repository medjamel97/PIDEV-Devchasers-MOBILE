/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Grim
 */
public class RevueAfficherTout extends Form {

    public static Map<String, Object> revueActuelleMap = null;
    Resources theme = UIManager.initFirstTheme("/theme");

    public RevueAfficherTout() {
        super("Revues", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
    }

    private void addGUIs() {
        try {
            Container topBarContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Button btnRetour = new Button("<- Accueil");
            btnRetour.addActionListener(action -> {
                new MainContainer().show();
            });
            topBarContainer.add(btnRetour);

            ConnectionRequest cr = new ConnectionRequest();
            cr.setUrl("http://127.0.0.1:8000/mobile/recuperer_revues");
            NetworkManager.getInstance().addToQueueAndWait(cr);
            Map<String, Object> jsonRoot = new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(cr.getResponseData()), "UTF-8")
            );
            ArrayList revues = (ArrayList) jsonRoot.get("root");

            Button btnAjouter = new Button("Nouvelle revue");
            btnAjouter.setUIID("newButton");
            btnAjouter.addActionListener(action -> {
                revueActuelleMap = null;
                new RevueManipuler().show();
            });

            this.addAll(topBarContainer, btnAjouter);

            for (int i = 0; i < revues.size(); i++) {
                this.add(creerRevue((Map<String, Object>) revues.get(i)));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Component creerRevue(Map<String, Object> revue) {
        Container revueModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        revueModel.setUIID("revueContainer");

        Container userContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        ImageViewer photoCandidat = new ImageViewer(theme.getImage("default.jpg"));
        if (revue.get("idPhotoCandidat") != null) {
            String url = "http://localhost/" + revue.get("idPhotoCandidat");
            photoCandidat = new ImageViewer(
                    URLImage.createToStorage(
                            EncodedImage.createFromImage(theme.getImage("default.jpg"), false),
                            url,
                            url,
                            URLImage.RESIZE_SCALE
                    )
            );
        }
        photoCandidat.setFocusable(false);
        photoCandidat.setUIID("candidatImage");
        photoCandidat.setPreferredSize(new Dimension(200, 200));
        Label nomPrenomCandidat = new Label(revue.get("prenomCandidat") + " " + revue.get("nomCandidat"));
        userContainer.addAll(photoCandidat, nomPrenomCandidat);

        Container nbEtoilesContainer = new Container();
        for (int indexEtoile = 0; indexEtoile < 5; indexEtoile++) {
            if (indexEtoile < (double) revue.get("nbEtoiles")) {
                nbEtoilesContainer.add(new ImageViewer(theme.getImage("star.png")));
            } else {
                nbEtoilesContainer.add(new ImageViewer(theme.getImage("star-outline.png")));
            }
        }
        Label labelObjet = new Label((String) revue.get("objet"));
        labelObjet.setUIID("defaultLabel");
        SpanLabel spanLabelDescription = new SpanLabel((String) revue.get("description"));
        spanLabelDescription.setTextUIID("description");
        Label labelDateCreation = new Label((String) revue.get("dateCreation"));
        labelDateCreation.setUIID("dateLabel");

        Container btnsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        btnsContainer.setUIID("buttonsContainer");

        Button btnModifier = new Button("Modifier");
        Button btnSupprimer = new Button("Supprimer");

        btnModifier.addActionListener(action -> {
            revueActuelleMap = revue;
            new RevueManipuler().show();
        });
        btnSupprimer.addActionListener(action -> {
            supprimerRevue(Math.round((double) revue.get("id")));
            revueActuelleMap = null;
        });

        btnsContainer.addAll(btnModifier, btnSupprimer);
        revueModel.addAll(labelDateCreation, userContainer, nbEtoilesContainer, labelObjet, spanLabelDescription, btnsContainer);

        return revueModel;
    }

    private void supprimerRevue(long idRevue) {
        try {
            ConnectionRequest cr = new ConnectionRequest();
            cr.addArgument("idRevue", String.valueOf(idRevue));
            cr.setUrl("http://127.0.0.1:8000/mobile/supprimer_revue");
            NetworkManager.getInstance().addToQueueAndWait(cr);

            char[] state = new char[1];
            new InputStreamReader(new ByteArrayInputStream(cr.getResponseData()), "UTF-8").read(state);

            switch (Integer.valueOf(String.valueOf(state))) {
                case 0:
                    Dialog.show("Revue supprimé", "", new Command("Ok"));
                    break;
                case -1:
                    Dialog.show("Erreur de suppression", "", new Command("Ok"));
                    RevueAfficherTout.revueActuelleMap = null;
                    new RevueAfficherTout().show();
                    break;
                default:
                    Dialog.show("Unknown error", "", new Command("Ok"));
                    break;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
