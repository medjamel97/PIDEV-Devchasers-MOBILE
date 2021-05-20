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
import com.devchasers.khedemti.entities.Publication;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Grim
 */
public class PublicationService {

    public static PublicationService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    private ArrayList<Publication> listPublications;
    private ArrayList<Object> listSocieteOffre;

    private PublicationService() {
        cr = new ConnectionRequest();
    }

    public static PublicationService getInstance() {
        if (instance == null) {
            instance = new PublicationService();
        }
        return instance;
    }

    public ArrayList<Publication> recupererPublications() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_publications");
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listPublications = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        Publication publication = new Publication(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("candidat_id").toString()),
                                (String) obj.get("titre"),
                                (String) obj.get("description"),
                                (String) obj.get("date"),
                                0
                              //  Float.parseFloat(obj.get("pourcentage_like").toString())
                        );
                        
                        System.out.println(publication);

                        listPublications.add(publication);
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
        return listPublications;
    }
/*
    public ArrayList<Object> recupererSocieteOffrePourPublication() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_societe_publication_pour_publication");
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
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return listSocieteOffre;
    }

    public int ajouterPublication(Publication publication) {
        cr.setUrl(Statics.BASE_URL + "/mobile/manipuler_interview");
        cr.addArgument("nbEtoiles", String.valueOf(publication.getNbEtoiles()));
        cr.addArgument("objet", publication.getObjet());
        cr.addArgument("description", publication.getDescription());
        cr.addArgument("dateCreation", publication.getDateCreation());
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
*/
    public int modifierPublication(Publication publication) {
        cr.setUrl(Statics.BASE_URL + "/mobile/modifierpub");
        cr.addArgument("id", String.valueOf(publication.getId()));
        cr.addArgument("candidat_id", String.valueOf(publication.getCandidatId()));
         cr.addArgument("titre", publication.getTitre());
        cr.addArgument("description", publication.getDescription());
         cr.addArgument("dateCreation", publication.getDate());
          
       
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

    public int supprimerPublication(Publication publication) {
        cr.setUrl(Statics.BASE_URL + "/mobile/supprimer_publication");
        cr.addArgument("id", String.valueOf(publication.getId()));
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
    
    
     public int ajouterPublication(Publication publication) {
        cr.setUrl(Statics.BASE_URL + "/mobile/majouterpub");
        cr.addArgument("candidatId", String.valueOf(MainApp.getSession().getCandidatId()));
        cr.addArgument("titre", publication.getTitre());
        cr.addArgument("description", publication.getDescription());
      
         System.out.println("1");
        
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                   System.out.println("2");
        
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
                   System.out.println("3");
        

            
     }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return resultCode;
    }
     
     
     
     
}
