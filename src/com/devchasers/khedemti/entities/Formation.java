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
public class Formation {
    int id;
    String nom;
    String filiere;
    String debut; 
    String fin;
     int societe_id;

 

    public Formation() {
    }

    public Formation(String nom, String filiere, String debut, String fin, int societe_id) {
        this.nom = nom;
        this.filiere = filiere;
        this.debut = debut;
        this.fin = fin;
        this.societe_id = societe_id;
    }

    public Formation(int id, String nom, String filiere, String debut, String fin, int societe_id) {
        this.id = id;
        this.nom = nom;
        this.filiere = filiere;
        this.debut = debut;
        this.fin = fin;
        this.societe_id = societe_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
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
   public int getSociete_id() {
        return societe_id;
    }

    public void setSociete_id(int societe_id) {
        this.societe_id = societe_id;
    }
 
    @Override
    public String toString() {
        return "Formation{" + "id=" + id + ", nom=" + nom + ", filiere=" + filiere + ", debut=" + debut + ", fin=" + fin + ", societe_id=" + societe_id + '}';
    }
    
    
    
           
    
}
