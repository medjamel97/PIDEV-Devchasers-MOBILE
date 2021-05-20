package com.devchasers.khedemti.gui.front_end.offre_de_travail;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class AfficherToutOffreDeTravail extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    public AfficherToutOffreDeTravail() {
        super();
        addGUIs();
        addActions();

        getToolbar().hideToolbar();
    }

    private void addGUIs() {
        this.add(new Label("offres"));
        Button b = new Button("hi");
        b.addActionListener(l -> {
            
        });
        this.add(b);
    }

    private void addActions() {

    }
}
