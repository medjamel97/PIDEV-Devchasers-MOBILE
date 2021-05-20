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
import com.devchasers.khedemti.entities.Contact;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Grim
 */
public class ContactsService {

    public static ContactsService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    private ArrayList<Contact> listContacts;

    private ContactsService() {
        cr = new ConnectionRequest();
    }

    public static ContactsService getInstance() {
        if (instance == null) {
            instance = new ContactsService();
        }
        return instance;
    }

    public ArrayList<Contact> recupererContacts() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_contacts");
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listContacts = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        Contact contact = new Contact(
                                (int) Float.parseFloat(obj.get("userId").toString()),
                                (String) obj.get("role"),
                                (String) obj.get("nomComplet"),
                                (String) obj.get("idPhoto")
                        );

                        listContacts.add(contact);
                    }

                } catch (IOException ex) {
                    System.out.println("Contact vide");
                }

                cr.removeResponseListener(this);
            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
        return listContacts;
    }
}
