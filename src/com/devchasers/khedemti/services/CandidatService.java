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
import com.devchasers.khedemti.entities.Candidat;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Faten
 */
public class CandidatService {

    public static CandidatService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    private ArrayList<Candidat> listCandidats;
    private ArrayList<Object> listSocieteOffre;

    private CandidatService() {
        cr = new ConnectionRequest();
    }

    public static CandidatService getInstance() {
        if (instance == null) {
            instance = new CandidatService();
        }
        return instance;
    }

    public ArrayList<Candidat> recupererMesCandidats() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_mes_candidats");
        cr.addArgument("societeId", String.valueOf(MainApp.getSession().getSocieteId()));
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listCandidats = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        Candidat candidat = new Candidat(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (String) obj.get("nom"),
                                (String) obj.get("prenom"),
                                (String) obj.get("dateNaissance"),
                                (String) obj.get("sexe"),
                                (String) obj.get("tel"),
                                (String) obj.get("idPhoto")
                        );

                        listCandidats.add(candidat);
                    }

                } catch (IOException ex) {
                    System.out.println("Candidat vide");
                }

                cr.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return listCandidats;
    }

    public ArrayList<Candidat> recupererCandidats() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_candidats");
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listCandidats = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        Candidat candidat = new Candidat(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (String) obj.get("nom"),
                                (String) obj.get("prenom"),
                                (String) obj.get("dateNaissance"),
                                (String) obj.get("sexe"),
                                (String) obj.get("tel"),
                                (String) obj.get("idPhoto")
                        );

                        listCandidats.add(candidat);
                    }

                } catch (IOException ex) {
                    System.out.println("Candidat vide");
                }

                cr.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return listCandidats;
    }

    public void supprimerCandidat(int candidatId) {
        cr.setUrl(Statics.BASE_URL + "/mobile/supprimer_candidat");
        cr.addArgument("candidatId", String.valueOf(candidatId));

        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
    }
}
