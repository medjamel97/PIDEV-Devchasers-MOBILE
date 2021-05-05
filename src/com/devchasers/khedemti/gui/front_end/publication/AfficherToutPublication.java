package com.devchasers.khedemti.gui.front_end.publication;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class AfficherToutPublication extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    public AfficherToutPublication() {
        super();
        addGUIs();
        addActions();

        getToolbar().hideToolbar();
    }

    private void addGUIs() {
        this.add(new Label("accueil"));
    }

    private void addActions() {

    }
}
