/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.entities;

/**
 *
 * @author Faten
 */
public class ExperienceDeTravail {

    private int id;
    private int candidatId;
    private String description;
    private String titreEmploi;
    private String nomEntreprise;
    private String ville;
    private String duree;

    public ExperienceDeTravail(int id, int candidatId, String description, String titreEmploi, String nomEntreprise, String ville, String duree) {
        this.id = id;
        this.candidatId = candidatId;
        this.description = description;
        this.titreEmploi = titreEmploi;
        this.nomEntreprise = nomEntreprise;
        this.ville = ville;
        this.duree = duree;
    }

    public ExperienceDeTravail(int candidatId, String description, String titreEmploi, String nomEntreprise, String ville, String duree) {
        this.candidatId = candidatId;
        this.description = description;
        this.titreEmploi = titreEmploi;
        this.nomEntreprise = nomEntreprise;
        this.ville = ville;
        this.duree = duree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(int candidatId) {
        this.candidatId = candidatId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitreEmploi() {
        return titreEmploi;
    }

    public void setTitreEmploi(String titreEmploi) {
        this.titreEmploi = titreEmploi;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

}
