/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.front_end.evenement;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.devchasers.khedemti.entities.Event;
import com.sendgrid.*;
import java.io.IOException;


/**
 *
 * @author Louay
 */
public class DetailEventForm  extends Form{

     public DetailEventForm(Event ev,Form previous) {
         Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
         Label Titre = new Label("Titre : "+ev.getTitre());
Label description = new Label("description : "+ev.getDescription());
SpanLabel  societe = new SpanLabel ("societe_id : "+ev.getSociete_id());
Label debut = new Label("Date debut : "+ev.getDebut());
Label fin = new Label("Date fin : "+ev.getFin());

Label allDay = new Label("allDay : "+ev.isAllDay());

 Button btnValider= new Button("interrésé ");
 btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               Email from = new Email("meriembader8@gmail.com");
    String subject = "Event response";
    Email to = new Email("meriembader8@gmail.com");
    Content content = new Content("text/plain", "merci pour votre intéret");   
    Mail mail = new Mail(from, subject, to, content);

    SendGrid sg = new SendGrid("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");
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");   
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
    } catch (IOException ex) {
    System.out.println("message non envoyé");
    }
            }
        });
    
 
cnt.add(Titre);
cnt.add(description);
cnt.add(societe);
cnt.add(debut);
cnt.add(fin);
cnt.add(allDay);
cnt.add(btnValider);

    
     add(cnt);
     getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    /*    DetailEventForm(Event evt, Form previous) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

}

