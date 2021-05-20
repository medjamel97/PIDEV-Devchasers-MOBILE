package com.devchasers.khedemti.gui.back_end.candidatureOffre;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.CandidatureOffre;

import com.devchasers.khedemti.services.CandidatureOffreService;

import java.util.ArrayList;

public class AfficherMesCandidatures extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    public AfficherMesCandidatures(Form previous) {
        super("Mes Candidatures", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public AfficherMesCandidatures() {
    }

    private void addGUIs() {
        Label titre = new Label("Mes Candidatures");
        titre.setUIID("titreCentre");
        ArrayList<CandidatureOffre> listeCandOffre = CandidatureOffreService.getInstance().recupererMesCandidatureOffre(MainApp.getSession().getSocieteId());
        for (int i = 0; i < listeCandOffre.size(); i++) {
            this.add(creerCandidatureOffre(listeCandOffre.get(i)));
        }
    }

    private void addActions() {

    }

    private Component creerCandidatureOffre(CandidatureOffre candidatureOffre) {

        Container offreModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        offreModel.setUIID("revueContainer");

        Container btnsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        btnsContainer.setUIID("buttonsContainer");
        btnsContainer.setPreferredH(200);
        Button btnAccepter = new Button("Accepter");
        btnAccepter.setUIID("actionButton");
        Button btnRefsuer = new Button("Modifier");
        btnRefsuer.setUIID("actionButton");

        btnRefsuer.addActionListener(action -> {
            candidatureOffre.setEtat("refusé");
            CandidatureOffreService.getInstance().modifierCandidatureOffre(candidatureOffre);

            this.removeAll();
            this.addGUIs();
            this.refreshTheme();
        });
        btnAccepter.addActionListener(action -> {
            candidatureOffre.setEtat("accepté");
            CandidatureOffreService.getInstance().modifierCandidatureOffre(candidatureOffre);

            this.removeAll();
            this.addGUIs();
            this.refreshTheme();
        });

        if (candidatureOffre.getEtat().equals("non traité")) {
            btnsContainer.addAll(btnAccepter, btnRefsuer);
        } else {
            btnsContainer.add(new Label("Vous avez " + candidatureOffre.getEtat() + " cette candidature"));
        }

        Label labelNom = new Label("Offre : " + candidatureOffre.getNomOffre());
        labelNom.setUIID("defaultLabel");

        Label labelOffre = new Label("Candidat : " + candidatureOffre.getNomPrenomCandidat());
        labelOffre.setUIID("defaultLabel");

        Label labelDescription = new Label("Etat : " + candidatureOffre.getEtat());
        labelOffre.setUIID("descriptionLabel");

        offreModel.addAll(labelNom, labelOffre, labelDescription, btnsContainer);

        return offreModel;

    }

}
