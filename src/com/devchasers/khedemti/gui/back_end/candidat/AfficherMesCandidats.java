package com.devchasers.khedemti.gui.back_end.candidat;

import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class AfficherMesCandidats extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    public AfficherMesCandidats(Form previous) {
        super("Mes candidats");
        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

    }

    private void addActions() {

    }
}
