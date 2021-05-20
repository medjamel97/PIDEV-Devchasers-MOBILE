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
import com.devchasers.khedemti.entities.Conversation;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Grim
 */
public class ConversationService {

    public static ConversationService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    private ArrayList<Conversation> listConversations;
    private Conversation conversation;

    private ConversationService() {
        cr = new ConnectionRequest();
    }

    public static ConversationService getInstance() {
        if (instance == null) {
            instance = new ConversationService();
        }
        return instance;
    }

    public ArrayList<Conversation> recupererConversations() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_conversations");
        cr.addArgument("userId", String.valueOf(MainApp.getSession().getId()));
        cr.addArgument("recherche", "");
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    resultCode = cr.getResponseCode();

                    if (resultCode != 500) {
                        listConversations = new ArrayList<>();
                        Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                                new String(cr.getResponseData()).toCharArray()
                        ));
                        List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                        for (Map<String, Object> obj : list) {

                            Conversation conversation = new Conversation(
                                    (int) Float.parseFloat(obj.get("id").toString()),
                                    (String) obj.get("nom"),
                                    (String) obj.get("dernierMessage"),
                                    (String) obj.get("idPhoto"),
                                    (boolean) ((int) Float.parseFloat(obj.get("dernierMessageEstVu").toString()) == 1),
                                    (int) Float.parseFloat(obj.get("nombreNotifications").toString())
                            );

                            listConversations.add(conversation);
                        }
                    }

                } catch (IOException ex) {
                    System.out.println("Conversation vide");
                }
                cr.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            System.out.println("test");
        }

        return listConversations;
    }

    public Conversation ajouterConversation(int idUserDestinataire) {
        cr.setUrl(Statics.BASE_URL + "/mobile/ajouter_conversation");
        cr.addArgument("idUserExpediteur", String.valueOf(MainApp.getSession().getId()));
        cr.addArgument("idUserDestinataire", String.valueOf(idUserDestinataire));
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    resultCode = cr.getResponseCode();
                    System.out.println(resultCode);

                    if (resultCode != 500) {
                        listConversations = new ArrayList<>();
                        Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                                new String(cr.getResponseData()).toCharArray()
                        ));
                        List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                        for (Map<String, Object> obj : list) {

                            conversation = new Conversation(
                                    (int) Float.parseFloat(obj.get("id").toString()),
                                    (String) obj.get("nom"),
                                    (String) obj.get("dernierMessage"),
                                    (String) obj.get("idPhoto"),
                                    true,
                                    0
                            );

                        }
                    }

                } catch (IOException ex) {
                    System.out.println("Conversation vide");
                }

                cr.removeResponseListener(this);

            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }

        return conversation;
    }

    public int supprimerConversation(int conversationId) {
        cr.setUrl(Statics.BASE_URL + "/mobile/supprimer_conversation");
        cr.addArgument("id", String.valueOf(conversationId));
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
