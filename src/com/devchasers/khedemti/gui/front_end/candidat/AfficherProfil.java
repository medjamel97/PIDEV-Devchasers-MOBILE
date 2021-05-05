package com.devchasers.khedemti.gui.front_end.candidat;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class AfficherProfil extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    public AfficherProfil() {
        super();
        addGUIs();
        addActions();

        getToolbar().hideToolbar();
    }

    private void addGUIs() {
        this.add(new Label("profil"));
    }

    private void addActions() {

    }
}
