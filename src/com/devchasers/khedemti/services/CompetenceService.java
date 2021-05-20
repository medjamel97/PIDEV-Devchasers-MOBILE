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
import com.devchasers.khedemti.entities.Competence;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Faten
 */
public class CompetenceService {

    public static CompetenceService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    private ArrayList<Competence> listCompetences;

    private CompetenceService() {
        cr = new ConnectionRequest();
    }

    public static CompetenceService getInstance() {
        if (instance == null) {
            instance = new CompetenceService();
        }
        return instance;
    }

    public ArrayList<Competence> recupererCompetence() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_competence");
        cr.addArgument("candidatId", String.valueOf(MainApp.getSession().getCandidatId()));
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listCompetences = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
                    
                    for (Map<String, Object> obj : list) {
                        Competence competence = new Competence(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("idCandidat").toString()),
                                (String) obj.get("name"),
                                (int) Float.parseFloat(obj.get("level").toString())
                        );

                        listCompetences.add(competence);
                    }

                } catch (IOException ex) {
                      System.out.println("Competence vide");
                }

                cr.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return listCompetences;
    }

    public int ajouterCompetence(Competence competence) {
        cr.setUrl(Statics.BASE_URL + "/mobile/ajouter_competence");
        cr.addArgument("candidatId", String.valueOf(MainApp.getSession().getCandidatId()));
        cr.addArgument("level", String.valueOf(competence.getLevel()));
        cr.addArgument("name", competence.getName());

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

    public int modifierCompetence(Competence competence) {
        cr.setUrl(Statics.BASE_URL + "/mobile/modifier_competence");
        cr.addArgument("id", String.valueOf(competence.getId()));
        cr.addArgument("level", String.valueOf(competence.getLevel()));
        cr.addArgument("name", competence.getName());
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

    public int supprimerCompetence(Competence competence) {
        cr.setUrl(Statics.BASE_URL + "/mobile/supprimer_competence");
        cr.addArgument("id", String.valueOf(competence.getId()));
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
