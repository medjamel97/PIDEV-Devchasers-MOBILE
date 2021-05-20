package com.devchasers.khedemti.gui.front_end.mission;

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
import com.devchasers.khedemti.entities.Mission;
import com.devchasers.khedemti.services.MissionService;
import com.devchasers.khedemti.services.RevueService;
import java.util.ArrayList;
import java.util.Map;

public class AfficherToutMission extends Form {
   Container missionsContainer;
    Resources theme = UIManager.initFirstTheme("/theme");

    public AfficherToutMission(Form previous) {
        super("Missions");
        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
   ArrayList<Mission> listMission = MissionService.getInstance().recupererMissions();
        missionsContainer = new Container(new GridLayout(3));
        for (Mission societeOffreMap : listMission) {
            missionsContainer.add(creerMission(societeOffreMap));
        }
        this.add(missionsContainer);
    }
    
      private Container creerMission(Mission missionMap) {
//        Map<String, Object> mission = (Map<String, Object>) missionMap;
        Container societeContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label labelNom = new Label((String) missionMap.getNom());
        labelNom.setUIID("centerLabel");

        ImageViewer image;
        
            String url = "http://localhost/" + missionMap.getNom();
            image = new ImageViewer(
                    URLImage.createToStorage(
                            EncodedImage.createFromImage(theme.getImage("default.jpg"), false),
                            url,
                            url,
                            URLImage.RESIZE_SCALE
                    )
            );
       
        image.setUIID("imageSociete");
        image.setFocusable(false);
        image.setPreferredH(360);
        image.setPreferredW(360);

        Label labelTel = new Label(((String) missionMap.getDescription()).replace('T', Character.MIN_VALUE));
        labelTel.setUIID("centerLabel");

        societeContainer.setUIID("societeContainer");
        int dw = Display.getInstance().getDisplayWidth();
        societeContainer.setPreferredW(dw / 3);
        societeContainer.setPreferredH(dw + dw / 10);
        societeContainer.addAll(labelNom, image, labelTel);

        Button societeBtn = new Button();
        societeBtn.addActionListener(l -> {
//            if (societeOffre.get("offres") != null) {
//                creerOffre((List<Map<String, Object>>) societeOffre.get("offres"), (String) societeOffre.get("nomSociete"));
//            } else {
//                Dialog.show("Information", "Aucune offre de travail pour cette societe", new Command("Ok"));
//            }
 Dialog.show("Information", "Aucune offre de travail pour cette societe", new Command("Ok"));
        });
        societeContainer.setLeadComponent(societeBtn);
        return societeContainer;
    }

    private void addActions() {

    }
}
