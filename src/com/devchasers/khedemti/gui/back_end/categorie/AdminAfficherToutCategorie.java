package com.devchasers.khedemti.gui.back_end.categorie;

import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class AdminAfficherToutCategorie extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    public AdminAfficherToutCategorie(Form previous) {
        super("Categories");
        addGUIs();
        addActions();
        
        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

    }

    private void addActions() {

    }
}
