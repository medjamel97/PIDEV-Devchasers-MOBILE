package com.devchasers.khedemti.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.devchasers.khedemti.entities.User;
import com.devchasers.khedemti.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserService {

    public static UserService instance = null;
    public int resultCode;
    private final ConnectionRequest cr;
    public ArrayList<User> listUsers;

    User user;
    boolean userVerif;

    private UserService() {
        cr = new ConnectionRequest();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public ArrayList<User> recupererUsers() {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_users");
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    listUsers = new ArrayList<>();
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        listUsers.add(new User(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("candidatId").toString()),
                                (int) Float.parseFloat(obj.get("societeId").toString()),
                                (String) obj.get("email"),
                                (List<String>) obj.get("roles"),
                                (String) obj.get("password"),
                                true,
                                true
                        ));
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
        return listUsers;
    }

    public User recupererUserParEmail(String email) {
        cr.setUrl(Statics.BASE_URL + "/mobile/recuperer_user_par_email");
        cr.addArgument("email", email);
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

                    for (Map<String, Object> obj : list) {
                        user = new User(
                                (int) Float.parseFloat(obj.get("id").toString()),
                                (int) Float.parseFloat(obj.get("candidatId").toString()),
                                (int) Float.parseFloat(obj.get("societeId").toString()),
                                (String) obj.get("email"),
                                (List<String>) obj.get("roles"),
                                (String) obj.get("password"),
                                true,
                                true
                        );
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
        return user;
    }

    public boolean verifierMotDePasse(String email, String password) {
        userVerif = false;
        cr.setUrl(Statics.BASE_URL + "/mobile/verication_mot_de_passe");
        cr.addArgument("email", email);
        cr.addArgument("password", password);
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    Map<String, Object> tasksListJson = new JSONParser().parseJSON(new CharArrayReader(
                            new String(cr.getResponseData()).toCharArray()
                    ));

                    userVerif = Boolean.valueOf((String) tasksListJson.get("isValid"));;

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
        return userVerif;
    }

    public void inscriptionCandidat(
            String email, String password, String day, String month, String year, String nom, String prenom, String tel, String sexe) {

        cr.setUrl(Statics.BASE_URL + "/mobile/inscription_candidat");
        cr.addArgument("email", email);
        cr.addArgument("password", password);
        cr.addArgument("day", day);
        cr.addArgument("month", month);
        cr.addArgument("year", year);
        cr.addArgument("nom", nom);
        cr.addArgument("prenom", prenom);
        cr.addArgument("tel", tel);
        cr.addArgument("sexe", sexe);

        try {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {

        }
    }

}
