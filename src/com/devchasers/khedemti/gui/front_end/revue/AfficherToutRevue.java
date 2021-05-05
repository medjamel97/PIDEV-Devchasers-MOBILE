/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.revue;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.CandidatureOffre;
import com.devchasers.khedemti.entities.Revue;
import com.devchasers.khedemti.services.RevueService;
import java.util.ArrayList;

/**
 *
 * @author Grim
 */
public class AfficherToutRevue extends Form {

    public static Revue revueActuelle = null;
    public static CandidatureOffre candidatureOffreActuelle = null;
    public static 
    Resources theme = UIManager.initFirstTheme("/theme");

    Container nbEtoilesContainer, revueModel, userContainer, btnsContainer;
    Label nomPrenomCandidat, labelObjet, labelDateCreation;
    SpanLabel spanLabelDescription;
    ImageViewer photoCandidat;
    Button btnRetour, btnAjouter, btnModifier, btnSupprimer;

    public AfficherToutRevue(Form previous) {
        super("Revues", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
    
    private void addGUIs() {
        btnAjouter = new Button("Nouvelle revue");
        btnAjouter.setUIID("newButton");

        this.add(btnAjouter);

        ArrayList<Revue> listRevues = RevueService.getInstance().recupererRevues();
        for (int i = 0; i < listRevues.size(); i++) {
            this.add(creerRevue(listRevues.get(i)));
        }
    }

    private void addActions() {
        btnAjouter.addActionListener(action -> {
            if (candidatureOffreActuelle != null) {
                revueActuelle = null;
                new ManipulerRevue(this).show();
            } else {
                Dialog.show("Erreur", "Vous devez candidater pour cette offre pour ajouter une revue", new Command("Ok"));
            }
        });
    }

    private Component creerRevue(Revue revue) {
        revueModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        revueModel.setUIID("revueContainer");

        userContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        photoCandidat = new ImageViewer(theme.getImage("default.jpg"));
        if (revue.getIdPhotoCandidat() != null) {
            String url = "http://localhost/" + revue.getIdPhotoCandidat();
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
        nomPrenomCandidat = new Label(revue.getPrenomCandidat() + " " + revue.getNomCandidat());
        userContainer.addAll(photoCandidat, nomPrenomCandidat);

        nbEtoilesContainer = new Container();
        for (int indexEtoile = 0; indexEtoile < 5; indexEtoile++) {
            if (indexEtoile < revue.getNbEtoiles()) {
                nbEtoilesContainer.add(new ImageViewer(theme.getImage("star.png")));
            } else {
                nbEtoilesContainer.add(new ImageViewer(theme.getImage("star-outline.png")));
            }
        }
        labelObjet = new Label((String) revue.getObjet());
        labelObjet.setUIID("defaultLabel");
        spanLabelDescription = new SpanLabel(revue.getDescription());
        spanLabelDescription.setTextUIID("description");
        labelDateCreation = new Label(revue.getDateCreation());
        labelDateCreation.setUIID("dateLabel");

        btnsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        btnsContainer.setUIID("buttonsContainer");
        btnsContainer.setPreferredH(200);

        btnModifier = new Button("Modifier");
        btnModifier.setUIID("actionButton");
        btnSupprimer = new Button("Supprimer");
        btnSupprimer.setUIID("actionButton");

        btnModifier.addActionListener(action -> {
            revueActuelle = revue;
            new ManipulerRevue(this).show();
        });
        btnSupprimer.addActionListener(action -> {

            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer votre revue ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                RevueService.getInstance().supprimerRevue(revue);
                revueActuelle = null;
                dlg.dispose();
                addGUIs();
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            //Dimension pre = dlg.getContentPane().getPreferredSize();
            //dlg.show(0, 0, Display.getInstance().getDisplayWidth() - (pre.getWidth() + pre.getWidth() / 6), 0);
            dlg.show(900, 900, 150, 150);

        });

        if (revue.getIdCandidatureOffre() == MainApp.getSession().getId()) {
            btnsContainer.addAll(btnModifier, btnSupprimer);
        }
        revueModel.addAll(labelDateCreation, userContainer, nbEtoilesContainer, labelObjet, spanLabelDescription, btnsContainer);

        return revueModel;
    }
}
