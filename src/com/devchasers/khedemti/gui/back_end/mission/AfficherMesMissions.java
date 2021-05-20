package com.devchasers.khedemti.gui.back_end.mission;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.BrowserComponent;
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
import com.devchasers.khedemti.entities.Mission;
import static com.devchasers.khedemti.gui.front_end.revue.AfficherToutRevue.revueActuelle;
import com.devchasers.khedemti.services.MissionService;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class AfficherMesMissions extends Form {

    public static Mission currentMission=null;
    Container missionsContainer;
    Resources theme = UIManager.initFirstTheme("/theme");
    Form current;

    static Form previous1;

    public AfficherMesMissions(Form previous) {
        super("Mes missions");
        previous1 = previous;
        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refreshUI() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        current = this;
        ArrayList<Mission> listMission = MissionService.getInstance().recupererMissions();
        missionsContainer = new Container(new GridLayout(3, 3));
        for (Mission missionOffreMap : listMission) {
            missionsContainer.add(creerMission(missionOffreMap));
        }
        Button btnAddMission = new Button("Add Mission");
        btnAddMission.addActionListener(e -> new AddMissionForm(current).show());
        this.add(btnAddMission);
        this.add(missionsContainer);

    }

    public Container creerMission(Mission missionMap) {

//        Map<String, Object> missionOffre = (Map<String, Object>) missionOffreMap;
        Container missionContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
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

        image.setUIID("imagemission");
        image.setFocusable(false);
        image.setPreferredH(360);
        image.setPreferredW(360);

        Label labelTel = new Label(((String) missionMap.getDescription()).replace('T', Character.MIN_VALUE));
        labelTel.setUIID("centerLabel");
        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("actionButton");
        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.setUIID("actionButton");
        btnSupprimer.setPreferredH(60);
        btnModifier.addActionListener(action -> {
            currentMission = missionMap;
            new ManipulerMission(this).show();
        });
        btnSupprimer.addActionListener(action -> {

            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer votre Mission?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                MissionService.getInstance().supprimerMission(missionMap);
                revueActuelle = null;
                dlg.dispose();
                missionContainer.remove();
                this.refreshTheme();
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            //Dimension pre = dlg.getContentPane().getPreferredSize();
            //dlg.show(0, 0, Display.getInstance().getDisplayWidth() - (pre.getWidth() + pre.getWidth() / 6), 0);
            dlg.show(900, 900, 150, 150);

        });
        missionContainer.setUIID("missionContainer");
        int dw = Display.getInstance().getDisplayWidth();
        missionContainer.setPreferredW(dw );
        missionContainer.setPreferredH((dw*3) / 4);
        missionContainer.setHeight(dw);
        Container btn1Container = new Container(new GridLayout(1,2));
         Container imageContainer = new Container();
         imageContainer.add(image);
        btn1Container.addAll(btnModifier, btnSupprimer);
        missionContainer.addAll(labelNom, imageContainer, labelTel,btn1Container);

        Button missionBtn = new Button();
        missionBtn.addActionListener(l -> {
//            if (missionOffre.get("offres") != null) {
//                creerOffre((List<Map<String, Object>>) missionOffre.get("offres"), (String) missionOffre.get("nommission"));
//            } else {
//                Dialog.show("Information", "Aucune offre de travail pour cette mission", new Command("Ok"));
//            }
              currentMission=missionMap;
//            Dialog.show("Information", "Aucune offre de travail pour cette mission", new Command("Ok"));
            Form hi = new Form("Browser", new BorderLayout());
            BrowserComponent browser = new BrowserComponent();
            browser.setURL("http://127.0.0.1:8000/map/" + currentMission.getLatitude() + "/" + currentMission.getLongitude());
            hi.add(BorderLayout.CENTER, browser);
            Display.getInstance().setProperty("android.webContentsDebuggingEnabled", "true");
            hi.show();
        });
        imageContainer.setLeadComponent(missionBtn);
        return missionContainer;
    }

    private void addActions() {

    }
}
