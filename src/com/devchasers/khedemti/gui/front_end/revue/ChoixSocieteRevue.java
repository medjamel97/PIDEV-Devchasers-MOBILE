/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.revue;

import com.codename1.components.ImageViewer;
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

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        ArrayList<Object> listSocieteOffre = RevueService.getInstance().recupererSocieteOffrePourRevue();
        societesContainer = new Container(new GridLayout(3, 3));
        for (Object societeOffreMap : listSocieteOffre) {
            societesContainer.add(creerSociete(societeOffreMap));
        }
        this.add(societesContainer);
    }

    private Container creerSociete(Object societeOffreMap) {
        Map<String, Object> societeOffre = (Map<String, Object>) societeOffreMap;
        Container societeContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label labelNom = new Label((String) societeOffre.get("nomSociete"));
        labelNom.setUIID("centerLabel");

        ImageViewer image;
        if (societeOffre.get("idPhotoSociete") != null) {
            String url = (String) societeOffre.get("idPhotoSociete");
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

        Label labelTel = new Label(((String) societeOffre.get("telSociete")).replace('T', Character.MIN_VALUE));
        labelTel.setUIID("centerLabel");

        societeContainer.setUIID("societeContainer");
        int dw = Display.getInstance().getDisplayWidth();
        societeContainer.setPreferredW(dw / 3);
        societeContainer.setPreferredH(dw / 2);
        societeContainer.addAll(labelNom, image, labelTel);

        Button societeBtn = new Button();
        societeBtn.addActionListener(l -> {
            if (societeOffre.get("offres") != null) {
                creerOffre((List<Map<String, Object>>) societeOffre.get("offres"), (String) societeOffre.get("nomSociete"));
            } else {
                Dialog.show("Information", "Aucune offre de travail pour cette societe", new Command("Ok"));
            }
        });
        societeContainer.setLeadComponent(societeBtn);
        return societeContainer;
    }

    private void creerOffre(List<Map<String, Object>> listOffres, String nomSociete) {
        Form choixOffre = new Form("Choisir une offre", new BoxLayout(BoxLayout.Y_AXIS));
        choixOffre.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> this.showBack());
        for (Map<String, Object> offre : listOffres) {
            Button btnOffre = new Button((String) offre.get("nomOffre"));
            btnOffre.addActionListener(actionConf -> {
                CandidatureOffre candidatureOffre = CandidatureOffreService.getInstance().recupererCandidatureOffreParOffreCandidat(
                        (int) (Float.parseFloat(offre.get("idOffre").toString())),
                        MainApp.getSession().getCandidatId()
                );

                AfficherToutRevue.revueActuelle = null;
                AfficherToutRevue.candidatureOffreActuelle = candidatureOffre;
                AfficherToutRevue.nomOffreActuelle = (String) offre.get("nomOffre");
                AfficherToutRevue.nomSocieteActuelle = nomSociete;

                try {
                    System.out.println("Etat candidature pour " + AfficherToutRevue.nomOffreActuelle + candidatureOffre.getEtat());
                } catch (NullPointerException e) {
                    System.out.println("Pas de candidature");
                }

                new AfficherToutRevue(this).show();
            });
            btnOffre.setUIID("offreButton");
            choixOffre.add(btnOffre);
        }
        choixOffre.show();
    }
}
