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
import com.devchasers.khedemti.entities.Message;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Grim
 */
public class MessageService {

    public static MessageService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    private ArrayList<Message> listMessages;

    private MessageService() {
        cr = new ConnectionRequest();
    }

    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }

    public ArrayList<Message> recupererMessagesParConversation(int conversationId) {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_messages");
        System.out.println(conversationId);
        cr.addArgument("id", String.valueOf(conversationId));
        cr.addArgument("page", "1");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    resultCode = cr.getResponseCode();

                    if (resultCode != 500) {
                        listMessages = new ArrayList<>();
                        Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                                new String(cr.getResponseData()).toCharArray()
                        ));
                        List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                        for (Map<String, Object> obj : list) {
                            listMessages.add(new Message(
                                    (int) Float.parseFloat(obj.get("id").toString()),
                                    (int) conversationId,
                                    (String) obj.get("contenu"),
                                    (String) obj.get("dateCreation"),
                                    (boolean) Boolean.parseBoolean((String) obj.get("estProprietaire")),
                                    (boolean) Boolean.parseBoolean((String) obj.get("estVu"))
                            ));
                        }
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
        return listMessages;
    }

    public int ajouterMessage(Message message) {
        cr.setUrl(Statics.BASE_URL + "/mobile/nouveau_message");
        cr.addArgument("id", String.valueOf(message.getConversationId()));
        cr.addArgument("contenu", message.getContenu());

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
