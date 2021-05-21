package com.devchasers.khedemti.gui.front_end.evenement;

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
import com.devchasers.khedemti.services.ServiceEvent;

public class AfficherToutEvenement extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    public AfficherToutEvenement(Form previous) {
        super("Evenements");

        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        for (Event ev : ServiceEvent.getInstance().getAllEvents()) {
            MultiButton mb = new MultiButton(ev.getTitre());
            mb.setTextLine2(ev.getDebut() + " à " + ev.getFin());
            mb.setTextLine3("Voire plus");
            mb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    DetailEventForm DEF = new DetailEventForm(ev, previous);
                    DEF.show();
                }
            });

            cnt.add(mb);
        }
        add(cnt);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
}
