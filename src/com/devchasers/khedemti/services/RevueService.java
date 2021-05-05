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
import com.devchasers.khedemti.entities.Revue;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Grim
 */
public class RevueService {

    public static RevueService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    private ArrayList<Revue> listRevues;
    private ArrayList<Object> listSocieteOffre;

    private RevueService() {
        cr = new ConnectionRequest();
    }

    public static RevueService getInstance() {
        if (instance == null) {
            instance = new RevueService();
        }
        return instance;
    }

    public ArrayList<Revue> recupererRevues() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_revues");
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listRevues = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        Revue revue = new Revue(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("idCandidatureOffre").toString()),
                                (String) obj.get("nomCandidat"),
                                (String) obj.get("prenomCandidat"),
                                (String) obj.get("idPhotoCandidat"),
                                (String) obj.get("nomSociete"),
                                (String) obj.get("objet"),
                                (String) obj.get("description"),
                                (String) obj.get("dateCreation")
                        );

                        listRevues.add(revue);
                    }

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                cr.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cr);
        return listRevues;
    }

    public ArrayList<Object> recupererSocieteOffrePourRevue() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_societe_offre_pour_revue");
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
                    System.out.println(ex.getMessage());
                }

                cr.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cr);
        return listSocieteOffre;
    }

    public int ajouterRevue(Revue revue) {
        cr.setUrl(Statics.BASE_URL + "/mobile/manipuler_interview");
        cr.addArgument("nbEtoiles", String.valueOf(revue.getNbEtoiles()));
        cr.addArgument("objet", revue.getObjet());
        cr.addArgument("description", revue.getDescription());
        cr.addArgument("dateCreation", revue.getDateCreation());
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cr);
        return resultCode;
    }

    public int modifierRevue(Revue revue) {
        cr.setUrl(Statics.BASE_URL + "/mobile/manipuler_interview");
        cr.addArgument("id", String.valueOf(revue.getId()));
        cr.addArgument("nbEtoiles", String.valueOf(revue.getNbEtoiles()));
        cr.addArgument("objet", revue.getObjet());
        cr.addArgument("description", revue.getDescription());
        cr.addArgument("dateCreation", revue.getDateCreation());
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cr);
        return resultCode;
    }

    public int supprimerRevue(Revue revue) {
        cr.setUrl(Statics.BASE_URL + "/mobile/supprimer_revue");
        cr.addArgument("id", String.valueOf(revue.getId()));
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cr);
        return resultCode;
    }
}
