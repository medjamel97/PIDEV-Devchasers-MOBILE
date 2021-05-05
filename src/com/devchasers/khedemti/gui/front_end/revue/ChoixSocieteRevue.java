/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.revue;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.CandidatureOffre;
import com.devchasers.khedemti.services.CandidatureOffreService;
import com.devchasers.khedemti.services.RevueService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Grim
 */
public class ChoixSocieteRevue extends Form {

    Container societesContainer;
    Resources theme = UIManager.initFirstTheme("/theme");

    public ChoixSocieteRevue(Form previous) {
        super("Choisir une societe");
        addGUIs();

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        ArrayList<Object> listSocieteOffre = RevueService.getInstance().recupererSocieteOffrePourRevue();
        societesContainer = new Container(new GridLayout(3));
        for (Object societeOffreMap : listSocieteOffre) {
            societesContainer.add(creerSociete(societeOffreMap));
        }
        this.add(societesContainer);
    }

    private Container creerSociete(Object societeOffreMap) {
        Map<String, Object> societeOffre = (Map<String, Object>) societeOffreMap;
        Container societeContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label labelId = new Label(String.valueOf(Float.parseFloat(societeOffre.get("id").toString())));
        labelId.setUIID("centerLabel");

        ImageViewer image;
        if (societeOffre.get("idPhoto") != null) {
            String url = "http://localhost/" + societeOffre.get("idPhoto");
            image = new ImageViewer(
                    URLImage.createToStorage(
                            EncodedImage.createFromImage(theme.getImage("default.jpg"), false),
                            url,
                            url,
                            URLImage.RESIZE_SCALE
                    )
            );
        } else {
            image = new ImageViewer();
        }
        image.setUIID("imageSociete");
        image.setFocusable(false);
        image.setPreferredH(360);
        image.setPreferredW(360);

        Label labelTel = new Label(String.valueOf(Float.parseFloat(societeOffre.get("tel").toString())));
        labelTel.setUIID("centerLabel");

        societeContainer.setUIID("societeContainer");
        int dw = Display.getInstance().getDisplayWidth();
        societeContainer.setPreferredW(dw / 3);
        societeContainer.setPreferredH(dw + dw / 6);
        societeContainer.addAll(labelId, image, labelTel);

        Button societeBtn = new Button();
        societeBtn.addActionListener(l -> {
            if (societeOffre.get("offres") != null) {
                creerOffre((List<Map<String, Object>>) societeOffre.get("offres"));
            } else {
                Dialog.show("Information", "Aucune offre de travail pour cette societe", new Command("Ok"));
            }
        });
        societeContainer.setLeadComponent(societeBtn);
        return societeContainer;
    }

    private void creerOffre(List<Map<String, Object>> listOffres) {
        InteractionDialog dlg = new InteractionDialog("Choisir une offre");
        dlg.setLayout(new BorderLayout());
        Container offresContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        for (Map<String, Object> offre : listOffres) {
            Button btnOffre = new Button((String) offre.get("nom"));
            btnOffre.addActionListener(actionConf -> {
                CandidatureOffre candidatureOffre = CandidatureOffreService.getInstance().recupererCandidatureOffreParOffreCandidat(
                        (int) (Float.parseFloat(offre.get("id").toString())),
                        MainApp.getSession().getCandidatId()
                );
                AfficherToutRevue.revueActuelle = null;
                AfficherToutRevue.candidatureOffreActuelle = candidatureOffre;
                dlg.dispose();
                new AfficherToutRevue(this).show();
            });
            btnOffre.setUIID("offreButton");
            offresContainer.add(btnOffre);
        }
        dlg.addComponent(BorderLayout.CENTER, offresContainer);
        Button btnClose = new Button("Annuler");
        btnClose.setUIID("dangerButton");
        btnClose.addActionListener((ee) -> dlg.dispose());
        dlg.addComponent(BorderLayout.SOUTH, btnClose);
        dlg.show(900, 900, 150, 150);
    }
}
