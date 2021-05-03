/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Grim
 */
public class InterviewAfficherTout extends Form {

    public static Map<String, Object> interviewActuelleMap = null;
    Resources theme = UIManager.initFirstTheme("/theme");

    public InterviewAfficherTout() {
        super("Interviews", new BoxLayout(BoxLayout.Y_AXIS));
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
            cr.setUrl("http://127.0.0.1:8000/mobile/recuperer_interviews");
            NetworkManager.getInstance().addToQueueAndWait(cr);
            Map<String, Object> jsonRoot = new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(cr.getResponseData()), "UTF-8")
            );
            ArrayList interviews = (ArrayList) jsonRoot.get("root");

            Button btnAjouter = new Button("Nouvelle interview");
            btnAjouter.setUIID("newButton");
            btnAjouter.addActionListener(action -> {
                interviewActuelleMap = null;
                new InterviewManipuler().show();
            });

            this.addAll(topBarContainer, btnAjouter);

            for (int i = 0; i < interviews.size(); i++) {
                this.add(creerInterview((Map<String, Object>) interviews.get(i)));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Component creerInterview(Map<String, Object> interview) {
        Container interviewModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        interviewModel.setUIID("revueContainer");

        Container userContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        ImageViewer photoCandidat = new ImageViewer(theme.getImage("default.jpg"));
        if (interview.get("idPhotoCandidat") != null) {
            String url = "http://localhost/" + interview.get("idPhotoCandidat");
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
        Label nomPrenomCandidat = new Label(interview.get("prenomCandidat") + " " + interview.get("nomCandidat"));
        userContainer.addAll(photoCandidat, nomPrenomCandidat);

        Label labelDifficulte = new Label((String) interview.get("Difficulte : "));
        labelDifficulte.setUIID("defaultLabel");
        Label difficulte = new Label();

        long diff = Math.round((double) interview.get("difficulte"));
        if (diff == 0) {
            difficulte.setText("Trés facile");
            difficulte.setUIID("difficulte0");
        } else if (diff == 1) {
            difficulte.setText("Facile");
            difficulte.setUIID("difficulte1");
        } else if (diff == 2) {
            difficulte.setText("Moyenne");
            difficulte.setUIID("difficulte2");
        } else if (diff == 3) {
            difficulte.setText("Difficile");
            difficulte.setUIID("difficulte3");
        } else if (diff == 4) {
            difficulte.setText("Trés difficile");
            difficulte.setUIID("difficulte4");
        }

        Label labelObjet = new Label((String) interview.get("objet"));
        labelObjet.setUIID("defaultLabel");
        SpanLabel spanLabelDescription = new SpanLabel((String) interview.get("description"));
        spanLabelDescription.setTextUIID("description");
        Label labelDateCreation = new Label((String) interview.get("dateCreation"));
        labelDateCreation.setUIID("dateLabel");

        Container btnsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        btnsContainer.setUIID("buttonsContainer");

        Button btnModifier = new Button("Modifier");
        Button btnSupprimer = new Button("Supprimer");
        Button btnAfficherScreenshot = new Button();
        btnAfficherScreenshot.setIcon(theme.getImage("share.png"));

        btnModifier.addActionListener(action -> {
            interviewActuelleMap = interview;
            new InterviewManipuler().show();
        });
        btnSupprimer.addActionListener(action -> {
            supprimerInterview(Math.round((double) interview.get("id")));
            interviewActuelleMap = null;
        });

        btnsContainer.addAll(btnModifier, btnSupprimer, btnAfficherScreenshot);
        interviewModel.addAll(labelDateCreation, userContainer, labelDifficulte, difficulte, labelObjet, spanLabelDescription, btnsContainer);

        // API PARTAGE
        Form form = new Form();
        form.add(new Label("Votre interview"));
        form.add(interviewModel);
        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
        Image screenshot = Image.createImage(form.getWidth(), 800);
        form.revalidate();
        form.setVisible(true);
        form.paintComponent(screenshot.getGraphics(), true);
        form.removeAll();
        try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
        } catch (IOException err) {
            Log.e(err);
        }
        Form screenShotForm = new Form("Partager l'interview", new BoxLayout(BoxLayout.Y_AXIS));
        ImageViewer screenshotViewer = new ImageViewer(screenshot);
        screenshotViewer.setFocusable(false);
        screenshotViewer.setUIID("screenshot");
        ShareButton btnPartager = new ShareButton();
        btnPartager.setText("Partager ");
        btnPartager.setTextPosition(LEFT);
        btnPartager.setImageToShare(imageFile, "image/png");
        btnPartager.setTextToShare(
                "vien voir cette interview sur l'offre "
                + interview.get("nomOffre") + " de la societe "
                + interview.get("nomSociete")
        );
        screenShotForm.addAll(screenshotViewer, btnPartager);
        btnAfficherScreenshot.addActionListener(listener -> {
            screenShotForm.show();
        });

        return interviewModel;
    }

    private void supprimerInterview(long idInterview) {
        try {
            ConnectionRequest cr = new ConnectionRequest();
            cr.addArgument("idInterview", String.valueOf(idInterview));
            cr.setUrl("http://127.0.0.1:8000/mobile/supprimer_interview");
            NetworkManager.getInstance().addToQueueAndWait(cr);

            char[] state = new char[1];
            new InputStreamReader(new ByteArrayInputStream(cr.getResponseData()), "UTF-8").read(state);

            switch (Integer.valueOf(String.valueOf(state))) {
                case 0:
                    Dialog.show("Interview supprimé", "", new Command("Ok"));
                    break;
                case -1:
                    Dialog.show("Erreur de suppression", "", new Command("Ok"));
                    InterviewAfficherTout.interviewActuelleMap = null;
                    new InterviewAfficherTout().show();
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
