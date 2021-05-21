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
import com.devchasers.khedemti.entities.Interview;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Grim
 */
public class InterviewService {

    public static InterviewService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    private ArrayList<Interview> listInterviews;
    private ArrayList<Object> listSocieteOffre;

    private InterviewService() {
        cr = new ConnectionRequest();
    }

    public static InterviewService getInstance() {
        if (instance == null) {
            instance = new InterviewService();
        }
        return instance;
    }

    public ArrayList<Interview> recupererInterviews() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_interviews");
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listInterviews = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        Interview interview = new Interview(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("idCandidatureOffre").toString()),
                                (int) Float.parseFloat(obj.get("difficulte").toString()),
                                (String) obj.get("nomCandidat"),
                                (String) obj.get("prenomCandidat"),
                                (String) obj.get("idPhotoCandidat"),
                                (String) obj.get("nomSociete"),
                                (String) obj.get("objet"),
                                (String) obj.get("description"),
                                (String) obj.get("dateCreation")
                        );

                        interview.setIdCandidat((int) Float.parseFloat(obj.get("idCandidat").toString()));

                        listInterviews.add(interview);
                    }

                } catch (IOException ex) {
                    System.out.println("Interview vide");
                }

                cr.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return listInterviews;
    }

    public ArrayList<Object> recupererSocieteOffrePourInterview() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_societe_offre_pour_interview");
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listSocieteOffre = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        listSocieteOffre.add(obj);
                    }

                } catch (IOException ex) {
                    System.out.println("Interview vide");
                }

                cr.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return listSocieteOffre;
    }

    public int ajouterInterview(Interview interview) {
        cr.setUrl(Statics.BASE_URL + "/mobile/manipuler_interview");
        cr.addArgument("idCandidatureOffre", String.valueOf(interview.getIdCandidatureOffre()));
        cr.addArgument("difficulte", String.valueOf(interview.getDifficulte()));
        cr.addArgument("objet", interview.getObjet());
        cr.addArgument("description", interview.getDescription());
        cr.addArgument("dateCreation", interview.getDateCreation());
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

    public int modifierInterview(Interview interview) {
        cr.setUrl(Statics.BASE_URL + "/mobile/manipuler_interview");
        cr.addArgument("id", String.valueOf(interview.getId()));
        cr.addArgument("difficulte", String.valueOf(interview.getDifficulte()));
        cr.addArgument("objet", interview.getObjet());
        cr.addArgument("description", interview.getDescription());
        cr.addArgument("dateCreation", interview.getDateCreation());
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

    public void supprimerInterview(int interviewId) {
        cr.setUrl(Statics.BASE_URL + "/mobile/supprimer_interview");
        cr.addArgument("id", String.valueOf(interviewId));
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
    }
}
