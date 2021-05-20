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
import com.devchasers.khedemti.entities.Event;
import com.devchasers.khedemti.entities.Formation;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Louay
 */
public class ServiceFormation {
    
      public ArrayList<Formation> formation;
    
    public static ServiceFormation instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceFormation() {
         req = new ConnectionRequest();
    }

    public static ServiceFormation getInstance() {
        if (instance == null) {
            instance = new ServiceFormation();
        }
        return instance;
    }
    
     public ArrayList<Formation> parseFormation(String jsonText){
        try {
            formation =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Formation f = new Formation();
                float id = Float.parseFloat(obj.get("id").toString());
                f.setId((int)id);
                f.setNom(((String)(obj.get("nom").toString())));
                 f.setFiliere(((String)(obj.get("filiere").toString())));
                   float societe_id = Float.parseFloat(obj.get("societe_id").toString());
                f.setSociete_id((int)societe_id);
                
                
             
               f.setDebut(((String)(obj.get("debut").toString())).substring(0, 16));
               f.setFin(((String)(obj.get("fin").toString())).substring(0, 16));
                System.out.println(f);

               formation.add(f);
            }
            
            
        } catch (IOException ex) {
            
        }
        return formation;
    }
       public ArrayList<Formation> getAllFormations(){
        String url = Statics.BASE_URL+"/espritApi/allFormation";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                formation = parseFormation(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return formation;
    }
}
