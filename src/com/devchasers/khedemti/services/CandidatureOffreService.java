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
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Grim
 */
public class CandidatureOffreService {

    public static CandidatureOffreService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    public ArrayList<CandidatureOffre> listeCandOffre;
    public ArrayList<CandidatureOffre> listCandidatureOffres;
    CandidatureOffre candidatureOffre = null;

    private CandidatureOffreService() {
        cr = new ConnectionRequest();
    }

    public static CandidatureOffreService getInstance() {
        if (instance == null) {
            instance = new CandidatureOffreService();
        }
        return instance;
    }

    public CandidatureOffre recupererCandidatureOffreParOffreCandidat(int offreDeTravailId, int candidatId) {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_candidature_offre_par_offre_candidat");
        cr.addArgument("offreDeTravailId", String.valueOf(offreDeTravailId));
        cr.addArgument("candidatId", String.valueOf(candidatId));
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    listCandidatureOffres = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    try {
                        Map<String, Object> obj = (Map<String, Object>) ((List<Map<String, Object>>) tasksListJson.get("root")).get(0);

                        candidatureOffre = new CandidatureOffre(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("offreDeTravailId").toString()),
                                (int) Float.parseFloat(obj.get("candidatId").toString()),
                                (String) obj.get("etat")
                        );
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                cr.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }

        return candidatureOffre;

    }

    public ArrayList<CandidatureOffre> recupererMesCandidatureOffre(int societeId) {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_MesCandidatureOffre");
        cr.addArgument("societeId", String.valueOf(societeId));
        System.out.println("societe = " + societeId);
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listCandidatureOffres = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
                    for (Map<String, Object> obj : list) {
                        CandidatureOffre candidatureOffre = new CandidatureOffre(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (String) obj.get("etat"),
                                (String) obj.get("nomOffre"),
                                (String) obj.get("nomPrenomCandidat")
                        );
                        System.out.println(candidatureOffre);
                        listCandidatureOffres.add(candidatureOffre);
                    }

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                cr.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return listCandidatureOffres;
    }

    public void ajouterCandidature(CandidatureOffre cand) {
        cr.setUrl(Statics.BASE_URL + "/mobile/ajouter_Candidature");
        cr.addArgument("offreDeTravailId", String.valueOf(cand.getOffreDeTravailId()));
        cr.addArgument("candidatId", String.valueOf(cand.getCandidatId()));
        cr.addArgument("etat", cand.getEtat());

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

        System.out.println("ajouté avec succés");
    }

    public int modifierCandidatureOffre(CandidatureOffre cand) {
        cr.setUrl(Statics.BASE_URL + "/mobile/modifier_CandidatureOffre");
        cr.addArgument("candidatureOffreId", String.valueOf(cand.getId()));
        cr.addArgument("etat", cand.getEtat());

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
