package com.devchasers.khedemti.gui.back_end.candidat;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
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
import com.devchasers.khedemti.entities.Candidat;
import com.devchasers.khedemti.services.CandidatService;

public class AdminAfficherToutCandidat extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    Form previous;

    public AdminAfficherToutCandidat(Form previous) {
        super("Candidats");
        addGUIs();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        for (Candidat candidat : CandidatService.getInstance().recupererCandidats()) {
            this.add(creerCandidat(candidat));
        }
    }

    private Component creerCandidat(Candidat candidat) {
        Container candidatModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        candidatModel.setUIID("candidatContainer");

        Label labelNom = new Label((String) candidat.getNom());
        labelNom.setUIID("defaultLabel");
        Label labelPrenom = new Label((String) candidat.getPrenom());
        labelPrenom.setUIID("defaultLabel");
        Label labelDateNaissance = new Label((String) candidat.getDateNaissance());
        labelDateNaissance.setUIID("defaultLabel");
        Label labelTel = new Label((String) candidat.getTel());
        labelTel.setUIID("defaultLabel");
        Label labelSexe = new Label((String) candidat.getSexe());
        labelSexe.setUIID("defaultLabel");

        ImageViewer photoCandidat = new ImageViewer(theme.getImage("default.jpg"));
        if (candidat.getIdPhoto() != null) {
            String url = "http://localhost/" + candidat.getIdPhoto();
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

        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setUIID("actionButton");

        btnSupprimer.addActionListener(action -> {

            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer votre candidat ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                CandidatService.getInstance().supprimerCandidat(candidat.getId());
                dlg.dispose();

                candidatModel.remove();
                this.refreshTheme();
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            //Dimension pre = dlg.getContentPane().getPreferredSize();
            //dlg.show(0, 0, Display.getInstance().getDisplayWidth() - (pre.getWidth() + pre.getWidth() / 6), 0);
            dlg.show(900, 900, 150, 150);

        });

        candidatModel.addAll(labelPrenom, labelNom, labelDateNaissance, labelTel, labelSexe, photoCandidat, btnSupprimer);

        return candidatModel;
    }
}
