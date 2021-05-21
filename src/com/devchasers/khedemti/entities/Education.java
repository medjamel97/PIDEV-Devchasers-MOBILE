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
public class Education {

    private int id;
    private int candidatId;
    private String description;
    private String niveauEducation;
    private String filiere;
    private String etablissement;
    private String ville;
    private String duree;

    // affichage / modif
    public Education(int id, int candidatId, String description, String niveauEducation, String filiere, String etablissement, String ville, String duree) {
        this.id = id;
        this.candidatId = candidatId;
        this.description = description;
        this.niveauEducation = niveauEducation;
        this.filiere = filiere;
        this.etablissement = etablissement;
        this.ville = ville;
        this.duree = duree;
    }

    // ajout
    public Education(int candidatId, String description, String niveauEducation, String filiere, String etablissement, String ville, String duree) {
        this.candidatId = candidatId;
        this.description = description;
        this.niveauEducation = niveauEducation;
        this.filiere = filiere;
        this.etablissement = etablissement;
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

    public String getNiveauEducation() {
        return niveauEducation;
    }

    public void setNiveauEducation(String niveauEducation) {
        this.niveauEducation = niveauEducation;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
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
