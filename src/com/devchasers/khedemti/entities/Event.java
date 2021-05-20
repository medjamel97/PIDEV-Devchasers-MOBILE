/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.entities;

/**
 *
 * @author Louay
 */
public class Event {
    
     int id; 
    String titre;
    String description;
    String debut;
    String fin;
    boolean allDay;
    int societe_id;

    public Event() {
    }

    
    public Event(String titre, String description, String debut, String fin, boolean allDay, int societe_id) {
        this.titre = titre;
        this.description = description;
        this.debut = debut;
        this.fin = fin;
        this.allDay = allDay;
        this.societe_id = societe_id;
    }

    
    public Event(int id, String titre, String description, String debut, String fin, boolean allDay, int societe) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.debut = debut;
        this.fin = fin;
        this.allDay = allDay;
        this.societe_id = societe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

 

    public int getSociete_id() {
        return societe_id;
    }

    public void setSociete_id(int societe_id) {
        this.societe_id = societe_id;
    }


    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", debut=" + debut + ", fin=" + fin + ", AllDay=" + allDay + ", societe=" + societe_id + '}';
    }
    
    
}
