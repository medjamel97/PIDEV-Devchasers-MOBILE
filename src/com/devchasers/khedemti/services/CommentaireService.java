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
import com.devchasers.khedemti.entities.Commentaire;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Grim
 */
public class CommentaireService {

    public static CommentaireService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    private ArrayList<Commentaire> listCommentaires;
    private ArrayList<Object> listSocieteOffre;

    private CommentaireService() {
        cr = new ConnectionRequest();
    }

    public static CommentaireService getInstance() {
        if (instance == null) {
            instance = new CommentaireService();
        }
        return instance;
    }

    public ArrayList<Commentaire> recupererCommentaires(int idPub) {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_commentaires_by_pub");
        cr.addArgument("id", String.valueOf(idPub));
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listCommentaires = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        Commentaire commentaire = new Commentaire(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("publicationId").toString()),
                                (int) Float.parseFloat(obj.get("userId").toString()),
                                (String) obj.get("description")
                        );

                        listCommentaires.add(commentaire);
                    }

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                cr.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cr);
        return listCommentaires;
    }
    

    public int ajouterCommentaire(Commentaire commentaire) {
        cr.setUrl(Statics.BASE_URL + "/mobile/ajouter_commentaire");
       cr.addArgument("idUser", String.valueOf(MainApp.getSession().getId()));
           cr.addArgument("idPub", String.valueOf(commentaire.getPublicationId()));
        cr.addArgument("description", commentaire.getDescription());
      
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

    public int modifierCommentaire(Commentaire commentaire) {
     cr.setUrl(Statics.BASE_URL + "/mobile/manipuler_commentaire");
       cr.addArgument("idUser", String.valueOf(MainApp.getSession().getId()));
           cr.addArgument("idPub", String.valueOf(commentaire.getPublicationId()));
               cr.addArgument("id", String.valueOf(commentaire.getId()));
        cr.addArgument("description", commentaire.getDescription());
      
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

    public int supprimerCommentaire(Commentaire commentaire) {
        cr.setUrl(Statics.BASE_URL + "/mobile/supprimer_commentaire");
        cr.addArgument("id", String.valueOf(commentaire.getId()));
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
