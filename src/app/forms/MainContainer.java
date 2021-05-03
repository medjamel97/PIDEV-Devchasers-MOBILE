/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author Grim
 */
public class MainContainer extends Form {

    public MainContainer() {
        super("Accueil", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
    }

    private void addGUIs() {
        Button btnRevues = new Button("revues");
        Button btnInterviews = new Button("Interviews");

        btnRevues.addActionListener(action -> {
            new RevueAfficherTout().show();
        });

        btnInterviews.addActionListener(action -> {
            new InterviewAfficherTout().show();
        });
        
        this.addAll(btnRevues,btnInterviews);
    }
}
