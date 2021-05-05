/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.messagerie;

import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.Layout;

/**
 *
 * @author Grim
 */
public class AfficherMessagerie extends Form {

    public AfficherMessagerie(Form previous) {
        super("Messagerie");

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

}
