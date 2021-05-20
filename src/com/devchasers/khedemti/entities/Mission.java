/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.entities;



/**
 *
 * @author Akram
 */
public class Mission {
      private int id;
    private int societeId;
    private String nom;
    private String description;
    private String date;
    private int nombreHeures;
    private float prixHeure;
    private String ville;
    private String longitude;
    private String latitude;

    public Mission(int id, int societeId, String nom, String description,String date, int nombreHeures, float prixHeure, String ville, String longitude, String latitude) {
        this.id = id;
        this.societeId = societeId;
        this.nom = nom;
        this.description = description;
        this.date=date;
        this.nombreHeures = nombreHeures;
        this.prixHeure = prixHeure;
        this.ville = ville;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Mission(int societeId, String nom, String description, String date, int nombreHeures, float prixHeure, String ville, String longitude, String latitude) {
        this.societeId = societeId;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.nombreHeures = nombreHeures;
        this.prixHeure = prixHeure;
        this.ville = ville;
        this.longitude = longitude;
        this.latitude = latitude;
    }



    public Mission() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSocieteId() {
        return societeId;
    }

    public void setSocieteId(int societeId) {
        this.societeId = societeId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNombreHeures() {
        return nombreHeures;
    }

    public void setNombreHeures(int nombreHeures) {
        this.nombreHeures = nombreHeures;
    }

    public float getPrixHeure() {
        return prixHeure;
    }

    public void setPrixHeure(float prixHeure) {
        this.prixHeure = prixHeure;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    
}
