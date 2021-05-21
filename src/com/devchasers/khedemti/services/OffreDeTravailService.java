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
    private final ConnectionRequest cr;
    private ArrayList<OffreDeTravail> listOffres;
    private ArrayList<OffreDeTravail> listOffreParSociete;
    
    private OffreDeTravailService() {
        cr = new ConnectionRequest();
    }
    
    public static OffreDeTravailService getInstance() {
        if (instance == null) {
            instance = new OffreDeTravailService();
        }
        return instance;
    }
    
    public ArrayList<OffreDeTravail> recupererOffres() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_offres");
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                try {
                    listOffres = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
                    
                    for (Map<String, Object> obj : list) {
                        OffreDeTravail offreDeTravail = new OffreDeTravail(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("categorieId").toString()),
                                (int) Float.parseFloat(obj.get("societeId").toString()),
                                (String) obj.get("nom"),
                                (String) obj.get("description"),
                                (String) obj.get("nomSociete"),
                                (String) obj.get("nomCategorie")
                        );
                        listOffres.add(offreDeTravail);
                    }
                    
                } catch (IOException ex) {
                    System.out.println("OffreDeTravail vide");
                }
                
                cr.removeResponseListener(this);
            }
        });
        
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            
        }
        return listOffres;
    }
    
    public ArrayList<OffreDeTravail> recupererOffreParSociete(int societeId) {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_offre_par_societe");
        cr.addArgument("societeId", String.valueOf(societeId));
        System.out.println("societe = " + societeId);
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                try {
                    listOffreParSociete = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
                    for (Map<String, Object> obj : list) {
                        OffreDeTravail offreDeTravail = new OffreDeTravail(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("categorieId").toString()),
                                (int) Float.parseFloat(obj.get("societeId").toString()),
                                (String) obj.get("nom"),
                                (String) obj.get("description"),
                                (String) obj.get("nomSociete"),
                                (String) obj.get("nomCategorie")
                        );
                        listOffreParSociete.add(offreDeTravail);
                    }
                    
                } catch (IOException ex) {
                    System.out.println("OffreDeTravail vide");
                }
                
                cr.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            
        }
        return listOffreParSociete;
    }
    
    public int ajouterOffre(OffreDeTravail offre) {
        cr.setUrl(Statics.BASE_URL + "/mobile/ajouter_offre");
        cr.addArgument("societeId", String.valueOf(MainApp.getSession().getSocieteId()));
        cr.addArgument("description", offre.getDescription());
        cr.addArgument("nom", offre.getNom());
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
    
    public int modifierOffre(OffreDeTravail offre) {
        cr.setUrl(Statics.BASE_URL + "/mobile/modifier_offre");
        cr.addArgument("id", String.valueOf(offre.getId()));
        cr.addArgument("nom", offre.getNom());
        cr.addArgument("description", offre.getDescription());
        
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
    
    public int supprimerOffre(OffreDeTravail offre) {
        cr.setUrl(Statics.BASE_URL + "/mobile/supprimer_offre");
        cr.addArgument("id", String.valueOf(offre.getId()));
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
