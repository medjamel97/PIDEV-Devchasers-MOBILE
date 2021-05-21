/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.interview;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.CandidatureOffre;
import com.devchasers.khedemti.entities.Interview;
import com.devchasers.khedemti.services.InterviewService;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author Grim
 */
public class AfficherToutInterview extends Form {

    public static Interview interviewActuelle = null;
    public static CandidatureOffre candidatureOffreActuelle = null;
    public static Resources theme = UIManager.initFirstTheme("/theme");
    public static String nomOffreActuelle, nomSocieteActuelle;

    Button btnAjouter;

    public AfficherToutInterview(Form previous) {
        super("Interviews sur " + nomOffreActuelle + " de la societe " + nomSocieteActuelle, new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        btnAjouter = new Button("Nouvelle interview");
        btnAjouter.setUIID("newButton");

        this.add(btnAjouter);

        Tabs tabs = new Tabs();

        ArrayList<Interview> listInterviews = InterviewService.getInstance().recupererInterviews();
        for (int i = 0; i < listInterviews.size(); i++) {
            this.add(creerInterview(listInterviews.get(i)));
        }

        this.add(tabs);
    }

    private void addActions() {
        btnAjouter.addActionListener(action -> {
            if (candidatureOffreActuelle != null) {
                interviewActuelle = null;
                new ManipulerInterview(this).show();
            } else {
                Dialog.show("Erreur", "Vous devez avoir candidaté à cette offre pour pouvoir ajouter une interview", new Command("Ok"));
            }
        });
    }

    private Component creerInterview(Interview interview) {
        Container interviewModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        interviewModel.setUIID("interviewContainer");

        Container userContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        ImageViewer photoCandidat = new ImageViewer(theme.getImage("default.jpg"));
        if (interview.getIdPhotoCandidat() != null) {
            String url = "http://localhost/" + interview.getIdPhotoCandidat();
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
        Label nomPrenomCandidat = new Label(interview.getPrenomCandidat() + " " + interview.getNomCandidat());
        userContainer.addAll(photoCandidat, nomPrenomCandidat);

        Label labelDifficulte = new Label("Difficulte");
        labelDifficulte.setUIID("defaultLabel");

        if (interview.getDifficulte() == 0) {
            labelDifficulte.setText("Trés facile");
            labelDifficulte.setUIID("difficulte0");
        } else if (interview.getDifficulte() == 1) {
            labelDifficulte.setText("Facile");
            labelDifficulte.setUIID("difficulte1");
        } else if (interview.getDifficulte() == 2) {
            labelDifficulte.setText("Moyenne");
            labelDifficulte.setUIID("difficulte2");
        } else if (interview.getDifficulte() == 3) {
            labelDifficulte.setText("Difficile");
            labelDifficulte.setUIID("difficulte3");
        } else if (interview.getDifficulte() == 4) {
            labelDifficulte.setText("Trés difficile");
            labelDifficulte.setUIID("difficulte4");
        }

        Label labelObjet = new Label((String) interview.getObjet());
        labelObjet.setUIID("defaultLabel");
        SpanLabel spanLabelDescription = new SpanLabel(interview.getDescription());
        spanLabelDescription.setTextUIID("description");
        Label labelDateCreation = new Label(interview.getDateCreation());
        labelDateCreation.setUIID("dateLabel");

        Container btnsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        btnsContainer.setUIID("buttonsContainer");
        btnsContainer.setPreferredH(200);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("actionButton");
        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setUIID("actionButton");

        btnModifier.addActionListener(action -> {
            interviewActuelle = interview;
            new ManipulerInterview(this).show();
        });
        btnSupprimer.addActionListener(action -> {

            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer votre interview ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                InterviewService.getInstance().supprimerInterview(interview.getId());
                interviewActuelle = null;
                dlg.dispose();

                interviewModel.remove();
                this.refreshTheme();
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            //Dimension pre = dlg.getContentPane().getPreferredSize();
            //dlg.show(0, 0, Display.getInstance().getDisplayWidth() - (pre.getWidth() + pre.getWidth() / 6), 0);
            dlg.show(900, 900, 150, 150);

        });

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
        btnPartager.setTextToShare("vien voir cette interview");
        screenShotForm.addAll(screenshotViewer, btnPartager);

        Button btnAfficherScreenshot = new Button("Partager");
        btnAfficherScreenshot.addActionListener(listener -> {
            screenShotForm.show();
        });

        if (interview.getIdCandidat() == MainApp.getSession().getCandidatId()) {
            btnsContainer.addAll(btnModifier, btnSupprimer);
        }
        btnsContainer.add(btnAfficherScreenshot);

        interviewModel.addAll(labelDateCreation, userContainer, labelDifficulte, labelObjet, spanLabelDescription, btnsContainer);

        return interviewModel;
    }
}
