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
public class Revue {

    private int id, idCandidatureOffre, nbEtoiles;
    private String nomCandidat, prenomCandidat, idPhotoCandidat, nomSociete, objet, description;
    private String dateCreation;

    // pour affichage
    public Revue(int idCandidatureOffre, int nbEtoiles, String nomCandidat, String prenomCandidat, String idPhotoCandidat, String nomSociete, String objet, String description, String dateCreation) {
        this.idCandidatureOffre = idCandidatureOffre;
        this.nbEtoiles = nbEtoiles;
        this.nomCandidat = nomCandidat;
        this.prenomCandidat = prenomCandidat;
        this.idPhotoCandidat = idPhotoCandidat;
        this.nomSociete = nomSociete;
        this.objet = objet;
        this.description = description;
        this.dateCreation = dateCreation;
    }
    

    // pour ajout
    public Revue(int idCandidatureOffre, int nbEtoiles, String objet, String description, String dateCreation) {
        this.idCandidatureOffre = idCandidatureOffre;
        this.nbEtoiles = nbEtoiles;
        this.objet = objet;
        this.description = description;
        this.dateCreation = dateCreation;
    }

    // pour modification
    public Revue(int id, int idCandidatureOffre, int nbEtoiles, String objet, String description, String dateCreation) {
        this.id = id;
        this.idCandidatureOffre = idCandidatureOffre;
        this.nbEtoiles = nbEtoiles;
        this.objet = objet;
        this.description = description;
        this.dateCreation = dateCreation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCandidatureOffre() {
        return idCandidatureOffre;
    }

    public void setIdCandidatureOffre(int idCandidatureOffre) {
        this.idCandidatureOffre = idCandidatureOffre;
    }

    public int getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(int nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    public String getNomCandidat() {
        return nomCandidat;
    }

    public void setNomCandidat(String nomCandidat) {
        this.nomCandidat = nomCandidat;
    }

    public String getPrenomCandidat() {
        return prenomCandidat;
    }

    public void setPrenomCandidat(String prenomCandidat) {
        this.prenomCandidat = prenomCandidat;
    }

    public String getIdPhotoCandidat() {
        return idPhotoCandidat;
    }

    public void setIdPhotoCandidat(String idPhotoCandidat) {
        this.idPhotoCandidat = idPhotoCandidat;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

}
