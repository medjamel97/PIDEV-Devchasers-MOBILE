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
import com.devchasers.khedemti.entities.ExperienceDeTravail;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Faten
 */
public class ExperienceDeTravailService {

    public static ExperienceDeTravailService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    private ArrayList<ExperienceDeTravail> listExperienceDeTravails;

    private ExperienceDeTravailService() {
        cr = new ConnectionRequest();
    }

    public static ExperienceDeTravailService getInstance() {
        if (instance == null) {
            instance = new ExperienceDeTravailService();
        }
        return instance;
    }

    public ArrayList<ExperienceDeTravail> recupererExperienceDeTravail() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_experience_de_travail");
        cr.addArgument("candidatId", String.valueOf(MainApp.getSession().getCandidatId()));
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listExperienceDeTravails = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        ExperienceDeTravail experience_de_travail = new ExperienceDeTravail(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("idCandidat").toString()),
                                (String) obj.get("description"),
                                (String) obj.get("titreEmploi"),
                                (String) obj.get("nomEntreprise"),
                                (String) obj.get("ville"),
                                (String) obj.get("duree")
                        );

                        listExperienceDeTravails.add(experience_de_travail);
                    }

                } catch (IOException ex) {
                    System.out.println("ExperienceDeTravail vide");
                }

                cr.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return listExperienceDeTravails;
    }

    public int ajouterExperienceDeTravail(ExperienceDeTravail experience_de_travail) {
        cr.setUrl(Statics.BASE_URL + "/mobile/ajouter_experience_de_travail");
        cr.addArgument("candidatId", String.valueOf(MainApp.getSession().getCandidatId()));
        cr.addArgument("description", experience_de_travail.getDescription());
        cr.addArgument("titreEmploi", experience_de_travail.getTitreEmploi());
        cr.addArgument("nomEntreprise", experience_de_travail.getNomEntreprise());
        cr.addArgument("ville", experience_de_travail.getVille());
        cr.addArgument("duree", experience_de_travail.getDuree());

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

    public int modifierExperienceDeTravail(ExperienceDeTravail experience_de_travail) {
        cr.setUrl(Statics.BASE_URL + "/mobile/modifier_experience_de_travail");
        cr.addArgument("id", String.valueOf(experience_de_travail.getId()));
        cr.addArgument("description", experience_de_travail.getDescription());
        cr.addArgument("titreEmploi", experience_de_travail.getTitreEmploi());
        cr.addArgument("nomEntreprise", experience_de_travail.getNomEntreprise());
        cr.addArgument("ville", experience_de_travail.getVille());
        cr.addArgument("duree", experience_de_travail.getDuree());
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

    public int supprimerExperienceDeTravail(ExperienceDeTravail experience_de_travail) {
        cr.setUrl(Statics.BASE_URL + "/mobile/supprimer_experience_de_travail");
        cr.addArgument("id", String.valueOf(experience_de_travail.getId()));
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
