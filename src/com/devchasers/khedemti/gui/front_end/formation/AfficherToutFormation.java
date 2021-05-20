package com.devchasers.khedemti.gui.front_end.formation;

import com.codename1.components.MultiButton;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.devchasers.khedemti.entities.Event;
import com.devchasers.khedemti.entities.Formation;
import com.devchasers.khedemti.gui.front_end.evenement.DetailEventForm;
import com.devchasers.khedemti.services.ServiceEvent;
import com.devchasers.khedemti.services.ServiceFormation;

public class AfficherToutFormation extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    public AfficherToutFormation(Form previous) {
        super("Formations");
       Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
     for(Formation f :ServiceFormation.getInstance().getAllFormations()){
         MultiButton mb = new MultiButton(f.getNom());
         mb.setTextLine2(f.getDebut()+" à "+f.getFin());
         mb.setTextLine3("Voire plus");
              mb.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent evt) {
             DetailFormationForm DEF =new DetailFormationForm(f,previous);
             DEF.show();
             }
         });   

         cnt.add(mb);
     }
     add(cnt);
     getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
}
