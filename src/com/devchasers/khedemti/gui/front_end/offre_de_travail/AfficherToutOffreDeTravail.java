package com.devchasers.khedemti.gui.front_end.offre_de_travail;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.CandidatureOffre;
import com.devchasers.khedemti.entities.OffreDeTravail;
import com.devchasers.khedemti.services.CandidatureOffreService;
import com.devchasers.khedemti.services.OffreDeTravailService;
import java.util.ArrayList;

public class AfficherToutOffreDeTravail extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    public static OffreDeTravail offreActuelle;

    public static CandidatureOffre candidatureOffre;

    Container offreModel;
    Label labelNom, labelDescription;

    public AfficherToutOffreDeTravail(Form previous) {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        addActions();

        getToolbar().hideToolbar();
    }

    private void addGUIs() {
        Label titre = new Label("Toutes les offres");
        titre.setUIID("titreCentre");
        this.add(titre);
        ArrayList<OffreDeTravail> listeOffre = OffreDeTravailService.getInstance().recupererOffres();
        for (int i = 0; i < listeOffre.size(); i++) {
            this.add(creerOffre(listeOffre.get(i)));
        }
    }

    private void addActions() {

    }

    private Component creerOffre(OffreDeTravail offre) {

        offreModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        offreModel.setUIID("revueContainer");

        labelNom = new Label((String) offre.getNom());
        labelNom.setUIID("defaultLabel");
        labelDescription = new Label((String) offre.getDescription());
        labelDescription.setUIID("descriptionLabel");

        Button btnPostuler = new Button("Postuler");
        btnPostuler.addActionListener((l) -> {
            candidatureOffre = new CandidatureOffre(
                    offre.getId(),
                    MainApp.getSession().getCandidatId(),
                    "non traité"
            );

            CandidatureOffreService.getInstance().ajouterCandidature(candidatureOffre);

            btnPostuler.remove();
            offreModel.add(new Label("merci"));
            this.refreshTheme();

        });
        offreModel.addAll(labelNom, labelDescription, btnPostuler);

        return offreModel;
    }
}
