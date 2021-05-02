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
import com.codename1.ui.Form;
import com.codename1.ui.Label;
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
            ConnectionRequest cr = new ConnectionRequest();
            cr.setUrl("http://127.0.0.1:8000/mobile/recuperer_revues");
            NetworkManager.getInstance().addToQueueAndWait(cr);
            Map<String, Object> jsonRoot = new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(cr.getResponseData()), "UTF-8")
            );
            ArrayList revues = (ArrayList) jsonRoot.get("root");

            Button btnAjouter = new Button("Nouvelle revue");
            btnAjouter.addActionListener(action -> {
                revueActuelleMap = null;
                new RevueManipuler().show();
            });

            this.add(btnAjouter);

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

        Container starsContainer = new Container();
        for (int indexEtoile = 0; indexEtoile < 5; indexEtoile++) {
            if (indexEtoile < (double) revue.get("nbEtoiles")) {
                starsContainer.add(new ImageViewer(theme.getImage("star.png")));
            } else {
                starsContainer.add(new ImageViewer(theme.getImage("star-outline.png")));
            }
        }
        Label labelObjet = new Label((String) revue.get("objet"));
        labelObjet.setUIID("defaultLabel");
        SpanLabel spanLabelDescription = new SpanLabel((String) revue.get("description"));
        spanLabelDescription.setTextUIID("description");

        Container btnsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));

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

        revueModel.addAll(starsContainer, labelObjet, spanLabelDescription, btnsContainer);

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
                    Dialog.show("Unknown error", "Ok", "Ok1", "Ok2");
                    break;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
