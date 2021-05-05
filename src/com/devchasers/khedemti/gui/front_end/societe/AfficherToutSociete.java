package com.devchasers.khedemti.gui.front_end.societe;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class AfficherToutSociete extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    public AfficherToutSociete() {
        super();
        addGUIs();
        addActions();

        getToolbar().hideToolbar();
    }

    private void addGUIs() {
        this.add(new Label("societes"));
    }

    private void addActions() {

    }
}
