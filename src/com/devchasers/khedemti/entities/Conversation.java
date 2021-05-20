/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.entities;

/**
 *
 * @author Grim
 */
public class Conversation {

    private int id, userExpediteurId, userDestinataireId;
    private String nom, dernierMessage;

    // additionnal values
    private String idPhoto;
    private boolean dernierMessageEstVu;
    private int nombreNotifications;

    // Pour affichage
    public Conversation(int id, String nom, String dernierMessage, String idPhoto, boolean dernierMessageEstVu, int nombreNotifications) {
        this.id = id;
        this.nom = nom;
        this.dernierMessage = dernierMessage;
        this.idPhoto = idPhoto;
        this.dernierMessageEstVu = dernierMessageEstVu;
        this.nombreNotifications = nombreNotifications;
    }

    // Pour ajout
    public Conversation(int userExpediteurId, int userDestinataireId, String nom, String dernierMessage) {
        this.userExpediteurId = userExpediteurId;
        this.userDestinataireId = userDestinataireId;
        this.nom = nom;
        this.dernierMessage = dernierMessage;
    }

    // Pour modification
    public Conversation(int id, String nom, String dernierMessage) {
        this.id = id;
        this.nom = nom;
        this.dernierMessage = dernierMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserExpediteurId() {
        return userExpediteurId;
    }

    public void setUserExpediteurId(int userExpediteurId) {
        this.userExpediteurId = userExpediteurId;
    }

    public int getUserDestinataireId() {
        return userDestinataireId;
    }

    public void setUserDestinataireId(int userDestinataireId) {
        this.userDestinataireId = userDestinataireId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDernierMessage() {
        return dernierMessage;
    }

    public void setDernierMessage(String dernierMessage) {
        this.dernierMessage = dernierMessage;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public boolean isDernierMessageEstVu() {
        return dernierMessageEstVu;
    }

    public void setDernierMessageEstVu(boolean dernierMessageEstVu) {
        this.dernierMessageEstVu = dernierMessageEstVu;
    }

    public int getNombreNotifications() {
        return nombreNotifications;
    }

    public void setNombreNotifications(int nombreNotifications) {
        this.nombreNotifications = nombreNotifications;
    }

}
