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
public class Interview {

    private int id, idCandidat, idCandidatureOffre, difficulte;
    private String nomCandidat, prenomCandidat, idPhotoCandidat, nomSociete, objet, description;
    private String dateCreation;

    // pour affichage
    public Interview(int id, int idCandidatureOffre, int difficulte, String nomCandidat, String prenomCandidat, String idPhotoCandidat, String nomSociete, String objet, String description, String dateCreation) {
        this.id = id;
        this.idCandidatureOffre = idCandidatureOffre;
        this.difficulte = difficulte;
        this.nomCandidat = nomCandidat;
        this.prenomCandidat = prenomCandidat;
        this.idPhotoCandidat = idPhotoCandidat;
        this.nomSociete = nomSociete;
        this.objet = objet;
        this.description = description;
        this.dateCreation = dateCreation;
    }

    // pour ajout
    public Interview(int idCandidatureOffre, int difficulte, String objet, String description, String dateCreation) {
        this.idCandidatureOffre = idCandidatureOffre;
        this.difficulte = difficulte;
        this.objet = objet;
        this.description = description;
        this.dateCreation = dateCreation;
    }

    // pour modification
    public Interview(int id, int idCandidatureOffre, int difficulte, String objet, String description, String dateCreation) {
        this.id = id;
        this.idCandidatureOffre = idCandidatureOffre;
        this.difficulte = difficulte;
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

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
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

    public int getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(int idCandidat) {
        this.idCandidat = idCandidat;
    }

}