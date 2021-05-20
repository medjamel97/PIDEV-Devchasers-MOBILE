/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.gui.back_end.mission;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.DateSpinner;
import com.devchasers.khedemti.entities.Mission;
import com.devchasers.khedemti.services.MissionService;

/**
 *
 * @author Akram
 */
public class AddMissionForm extends Form {

    public AddMissionForm(Form previous) {
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
         */
        setTitle("Add a new Mission");
        setLayout(BoxLayout.y());

        TextField tfName = new TextField("", "Mission Name");
        TextField tfStatus = new TextField("", "description");
        DateSpinner date = new DateSpinner();
        TextField tfnbHeure = new TextField("", "nombre d'heure");
        TextField tfPrixHeure = new TextField("", "Prix Heure");
        TextField tfVille = new TextField("", "ville");
        TextField tfLong = new TextField("", "longitude");
        TextField tfLat = new TextField("", "latitude");
        Button btnValider = new Button("Add Mission");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (tfStatus.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        String day = String.valueOf(date.getCurrentDay());
                        String month = String.valueOf(date.getCurrentMonth());
                        String year = String.valueOf(date.getCurrentYear());
                        int responseCode = MissionService.getInstance().addMission(new Mission(
                                1,
                                tfName.getText(),
                                tfStatus.getText(),
                                day + "/" + month + "/" + year,
                                Integer.parseInt(tfnbHeure.getText()),
                                Float.parseFloat(tfPrixHeure.getText()),
                                tfVille.getText(),
                                tfLong.getText(),
                                tfLat.getText()
                        )
                        );
                        ToastBar.getInstance().setPosition(BOTTOM);
                        ToastBar.Status status = ToastBar.getInstance().createStatus();
                        status.setShowProgressIndicator(true);
                        //status.setIcon(res.getImage("done.png").scaledSmallerRatio(Display.getInstance().getDisplayWidth()/10, Display.getInstance().getDisplayWidth()/15));
                        status.setMessage("  Mission ajouté avec succès");
                        status.setExpires(10000);
                        status.show();

                        if (responseCode == 200) {
                            Dialog.show("Success", "Ajouté avec succes", new Command("OK"));

                            ((AfficherMesMissions)previous).refreshUI();
                            
                            previous.showBack();
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }

            }
        });

        addAll(tfName, tfStatus, date, tfnbHeure, tfPrixHeure, tfVille, tfLong, tfLat, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                e -> previous.showBack()); // Revenir vers l'interface précédente

    }

}
