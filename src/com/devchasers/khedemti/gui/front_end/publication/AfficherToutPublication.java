package com.devchasers.khedemti.gui.front_end.publication;

import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class AfficherToutPublication extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    public AfficherToutPublication(Form previous) {
        super("Accueil");
        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

    }

    private void addActions() {

    }
}
