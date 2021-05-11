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
    private final ConnectionRequest cnx;
    public ArrayList<CandidatureOffre> listeCandOffre;
    public ArrayList<CandidatureOffre> listCandidatureOffres;
    CandidatureOffre candidatureOffre = null;

    private CandidatureOffreService() {
        cnx = new ConnectionRequest();
    }

    public static CandidatureOffreService getInstance() {
        if (instance == null) {
            instance = new CandidatureOffreService();
        }
        return instance;
    }

    public CandidatureOffre recupererCandidatureOffreParOffreCandidat(int offreDeTravailId, int candidatId) {
        cnx.setUrl(Statics.BASE_URL + "/mobile/recuperer_candidature_offre_par_offre_candidat");
        cnx.addArgument("offreDeTravailId", String.valueOf(offreDeTravailId));
        cnx.addArgument("candidatId", String.valueOf(candidatId));
        cnx.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    listCandidatureOffres = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cnx.getResponseData()).toCharArray()
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

                cnx.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cnx);

        if (resultCode == 404) {
            return null;
        }
        return candidatureOffre;

    }

    public ArrayList<CandidatureOffre> recupererMesCandidatureOffre(int societeId) {
        cnx.setUrl(Statics.BASE_URL + "/mobile/recuperer_MesCandidatureOffre");
        cnx.addArgument("societeId", String.valueOf(societeId));
        System.out.println("societe = " + societeId);
        cnx.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listCandidatureOffres = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cnx.getResponseData()).toCharArray()
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

                cnx.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cnx);
        return listCandidatureOffres;
    }

    public void ajouterCandidature(CandidatureOffre cand) {
        cnx.setUrl(Statics.BASE_URL + "/mobile/ajouter_Candidature");
        cnx.addArgument("offreDeTravailId", String.valueOf(cand.getOffreDeTravailId()));
        cnx.addArgument("candidatId", String.valueOf(cand.getCandidatId()));
        cnx.addArgument("etat", cand.getEtat());

        cnx.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cnx.getResponseCode();
                cnx.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cnx);

        System.out.println("ajouté avec succés");
    }

    public int modifierCandidatureOffre(CandidatureOffre cand) {
        cnx.setUrl(Statics.BASE_URL + "/mobile/modifier_CandidatureOffre");
        cnx.addArgument("candidatureOffreId", String.valueOf(cand.getId()));
        cnx.addArgument("etat", cand.getEtat());

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
