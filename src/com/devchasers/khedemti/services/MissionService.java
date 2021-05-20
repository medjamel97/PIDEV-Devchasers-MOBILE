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
import com.devchasers.khedemti.entities.Mission;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Akram
 */
public class MissionService {
    public static MissionService instance = null;
    public int resultCode;
     private ArrayList<Mission> listMission;
    private final ConnectionRequest cr;
    
       private MissionService() {
        cr = new ConnectionRequest();
    }

    public static MissionService getInstance() {
        if (instance == null) {
            instance = new MissionService();
        }
        return instance;
    }
    
        public ArrayList<Mission> recupererMissions() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_mission");
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listMission = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        Mission mission = new Mission(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("idSociete").toString()),
                                (String) obj.get("nom"),
                                (String) obj.get("description"),
                                (String) obj.get("date").toString(),
                                (int) Float.parseFloat(obj.get("nombreHeures").toString()),
                                Float.parseFloat(obj.get("prixHeure").toString()),
                                (String) obj.get("ville"),
                                (String) obj.get("longitude"),
                                 (String) obj.get("latitude")
                        );

                        listMission.add(mission);
                    }

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                cr.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cr);
        return listMission;
    }
        
         public int addMission(Mission m) {
             cr.setUrl(Statics.BASE_URL + "/mobile/AddMission");
        cr.addArgument("nom", m.getNom());
        cr.addArgument("idSociete",String.valueOf(m.getSocieteId()));
        cr.addArgument("description", m.getDescription());
        cr.addArgument("date", m.getDate());
        cr.addArgument("nombreHeures",String.valueOf(m.getNombreHeures()));
        cr.addArgument("prixHeure",String.valueOf(m.getPrixHeure()));
        cr.addArgument("ville", m.getVille());
         cr.addArgument("longitude", m.getLongitude());
         cr.addArgument("latitude", m.getLatitude());
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
         
             public int supprimerMission(Mission mission) {
        cr.setUrl(Statics.BASE_URL + "/mobile/supprimer_mission");
        cr.addArgument("id", String.valueOf(mission.getId()));
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
             
               public int modifierRevue(Mission m) {
        cr.setUrl(Statics.BASE_URL + "/mobile/AddMission");
        cr.addArgument("id", String.valueOf(m.getId()));
        cr.addArgument("nom", m.getNom());
        cr.addArgument("idSociete",String.valueOf(m.getSocieteId()));
        cr.addArgument("description", m.getDescription());
        cr.addArgument("date", m.getDate());
        cr.addArgument("nombreHeures",String.valueOf(m.getNombreHeures()));
        cr.addArgument("prixHeure",String.valueOf(m.getPrixHeure()));
        cr.addArgument("ville", m.getVille());
         cr.addArgument("longitude", m.getLongitude());
         cr.addArgument("latitude", m.getLatitude());
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
