package com.devchasers.khedemti.gui.back_end.offre_de_travail;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.OffreDeTravail;
import com.devchasers.khedemti.services.OffreDeTravailService;
import java.util.ArrayList;

public class AfficherMesOffresDeTravail extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    public static OffreDeTravail offreActuelle;

    Container offreModel, btnsContainer;
    Label labelNom, labelDescription;
    Button btnRetour, btnAjouter, btnModifier, btnSupprimer;

    public AfficherMesOffresDeTravail(Form previous) {
        super("Mes offres", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public AfficherMesOffresDeTravail() {
    }

    private void addGUIs() {
        btnAjouter = new Button("Nouvelle Offre");
        btnAjouter.setUIID("newButton");

        this.add(btnAjouter);

        ArrayList<OffreDeTravail> listeOffre = OffreDeTravailService.getInstance().recupererOffreParSociete(MainApp.getSession().getSocieteId());
        for (int i = 0; i < listeOffre.size(); i++) {
           this.add(creerOffre(listeOffre.get(i)));
        }

        
    }

    private void addActions() {
        btnAjouter.addActionListener(action -> {
            new AjouterOffre(this).show();
        });
    }

    private Component creerOffre(OffreDeTravail offre) {

        offreModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        offreModel.setUIID("revueContainer");

        labelNom = new Label((String) offre.getNom());
        labelNom.setUIID("defaultLabel");
        labelDescription = new Label((String) offre.getDescription());
        labelDescription.setUIID("defaultLabel");

        btnsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        btnsContainer.setUIID("buttonsContainer");
        btnsContainer.setPreferredH(200);

        btnModifier = new Button("Modifier");
        btnModifier.setUIID("actionButton");
        btnSupprimer = new Button("Supprimer");
        btnSupprimer.setUIID("actionButton");

        btnModifier.addActionListener(action -> {
            offreActuelle = offre;
            new ModifierOffre(this).show();
        });
        btnSupprimer.addActionListener(action -> {

            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer votre offre ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((o) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                OffreDeTravailService.getInstance().supprimerOffre(offre);
                offreActuelle = null;
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

        if (offre.getSocieteId()== MainApp.getSession().getSocieteId()) {
            btnsContainer.addAll(btnModifier, btnSupprimer, btnRetour);
        }
        offreModel.addAll(labelNom, labelDescription, btnsContainer);

        return offreModel;
    }
}
