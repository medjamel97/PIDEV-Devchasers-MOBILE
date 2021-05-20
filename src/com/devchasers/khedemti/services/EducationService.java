/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.devchasers.khedemti.MainApp;
import com.devchasers.khedemti.entities.Education;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Faten
 */
public class EducationService {

    public static EducationService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    private ArrayList<Education> listEducations;

    private EducationService() {
        cr = new ConnectionRequest();
    }

    public static EducationService getInstance() {
        if (instance == null) {
            instance = new EducationService();
        }
        return instance;
    }

    public ArrayList<Education> recupererEducation() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_education");
        cr.addArgument("candidatId", String.valueOf(MainApp.getSession().getCandidatId()));
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listEducations = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        Education education = new Education(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("idCandidat").toString()),
                                (String) obj.get("description"),
                                (String) obj.get("niveauEducation"),
                                (String) obj.get("filiere"),
                                (String) obj.get("etablissement"),
                                (String) obj.get("ville"),
                                (String) obj.get("duree")
                        );

                        listEducations.add(education);
                    }

                } catch (IOException ex) {
                     System.out.println("Education vide");
                }

                cr.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (NullPointerException e) {
            System.out.println("Vide");
        }
        return listEducations;
    }

    public int ajouterEducation(Education education) {
        cr.setUrl(Statics.BASE_URL + "/mobile/ajouter_education");
        cr.addArgument("candidatId", String.valueOf(MainApp.getSession().getCandidatId()));
        cr.addArgument("description", education.getDescription());
        cr.addArgument("niveauEducation", education.getNiveauEducation());
        cr.addArgument("filiere", education.getFiliere());
        cr.addArgument("etablissement", education.getEtablissement());
        cr.addArgument("ville", education.getVille());
        cr.addArgument("duree", education.getDuree());

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);

            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return resultCode;
    }

    public int modifierEducation(Education education) {
        cr.setUrl(Statics.BASE_URL + "/mobile/modifier_education");
        cr.addArgument("id", String.valueOf(education.getId()));
        cr.addArgument("description", education.getDescription());
        cr.addArgument("niveauEducation", education.getNiveauEducation());
        cr.addArgument("filiere", education.getFiliere());
        cr.addArgument("etablissement", education.getEtablissement());
        cr.addArgument("ville", education.getVille());
        cr.addArgument("duree", education.getDuree());
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);

            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return resultCode;
    }

    public int supprimerEducation(Education education) {
        cr.setUrl(Statics.BASE_URL + "/mobile/supprimer_education");
        cr.addArgument("id", String.valueOf(education.getId()));
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);

            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return resultCode;
    }

}
