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
import com.devchasers.khedemti.entities.CandidatureOffre;
import com.devchasers.khedemti.entities.OffreDeTravail;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Anis
 */
public class OffreDeTravailService {

    public static OffreDeTravailService instance = null;
    public int resultCode;
    private final ConnectionRequest cnx;
    private ArrayList<OffreDeTravail> listOffres;
    private ArrayList<OffreDeTravail> listOffreParSociete;

    private OffreDeTravailService() {
        cnx = new ConnectionRequest();
    }

    public static OffreDeTravailService getInstance() {
        if (instance == null) {
            instance = new OffreDeTravailService();
        }
        return instance;
    }

    public ArrayList<OffreDeTravail> recupererOffres() {
        cnx.setUrl(Statics.BASE_URL + "/mobile/recuperer_offres");
        cnx.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listOffres = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cnx.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        OffreDeTravail offreDeTravail = new OffreDeTravail(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (String) obj.get("nom"),
                                (String) obj.get("description"),
                                (String) obj.get("nomCategorie")
                        );
                        listOffres.add(offreDeTravail);
                    }

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                cnx.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cnx);
        return listOffres;
    }

    public ArrayList<OffreDeTravail> recupererOffreParSociete(int societeId) {
        cnx.setUrl(Statics.BASE_URL + "/mobile/recuperer_offre_par_societe");
        cnx.addArgument("societeId", String.valueOf(societeId));
        System.out.println("societe = " + societeId);
        cnx.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listOffreParSociete = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cnx.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
                    for (Map<String, Object> obj : list) {
                        OffreDeTravail offreDeTravail = new OffreDeTravail(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (String) obj.get("nom"),
                                (String) obj.get("description"),
                                (String) obj.get("nomCategorie")
                        );
                        listOffreParSociete.add(offreDeTravail);
                    }

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                cnx.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cnx);
        return listOffreParSociete;
    }

    public int ajouterOffre(OffreDeTravail offre) {
        cnx.setUrl(Statics.BASE_URL + "/mobile/ajouter_offre");
        cnx.addArgument("description", offre.getDescription());
        cnx.addArgument("dateCreation", offre.getNom());
        cnx.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cnx.getResponseCode();
                cnx.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cnx);
        return resultCode;
    }

    public int modifierOffre(OffreDeTravail offre) {
        cnx.setUrl(Statics.BASE_URL + "/mobile/modifier_offre");
        cnx.addArgument("id", String.valueOf(offre.getId()));
        cnx.addArgument("nom", offre.getNom());
        cnx.addArgument("description", offre.getDescription());

        cnx.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cnx.getResponseCode();
                cnx.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cnx);
        return resultCode;
    }

    public int supprimerOffre(OffreDeTravail offre) {
        cnx.setUrl(Statics.BASE_URL + "/mobile/supprimer_offre");
        cnx.addArgument("id", String.valueOf(offre.getId()));
        cnx.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cnx.getResponseCode();
                cnx.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cnx);
        return resultCode;
    }
    
    
}
