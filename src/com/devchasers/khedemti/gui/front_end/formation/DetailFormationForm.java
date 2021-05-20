/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.formation;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.devchasers.khedemti.entities.Event;
import com.devchasers.khedemti.entities.Formation;

/**
 *
 * @author Louay
 */
public class DetailFormationForm extends Form {
    
      public DetailFormationForm(Formation f,Form previous) {
         Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
         Label Nom = new Label("Nom : "+f.getNom());
         Label filiere = new Label("filiere : "+f.getFiliere());
         SpanLabel  societe_id = new SpanLabel ("societe_id : "+f.getSociete_id());
         Label debut = new Label("Date debut : "+f.getDebut());
         Label fin = new Label("Date fin : "+f.getFin());

 cnt.add(Nom);
cnt.add(filiere);
cnt.add(societe_id);
cnt.add(debut);
 
     add(cnt);
     getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
 

}

