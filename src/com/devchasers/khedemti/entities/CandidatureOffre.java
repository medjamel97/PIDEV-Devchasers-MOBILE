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
public class CandidatureOffre {

    private int id;
    private int offreDeTravailId;
    private int candidatId;
    private String etat;
    
    private String nomOffre,nomPrenomCandidat;

    // pour affichage
    public CandidatureOffre(int id, String etat, String nomOffre, String nomPrenomCandidat) {
        this.id = id;
        this.etat = etat;
        this.nomOffre = nomOffre;
        this.nomPrenomCandidat = nomPrenomCandidat;
    }
    
    public CandidatureOffre(int id, int offreDeTravailId, int candidatId, String etat) {
        this.id = id;
        this.offreDeTravailId = offreDeTravailId;
        this.candidatId = candidatId;
        this.etat = etat;
    }

    public CandidatureOffre(String etat) {
        this.etat = etat;
    }

    public CandidatureOffre(int offreDeTravailId, int candidatId, String etat) {
        this.offreDeTravailId = offreDeTravailId;
        this.candidatId = candidatId;
        this.etat = etat;
    }

    public CandidatureOffre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOffreDeTravailId() {
        return offreDeTravailId;
    }

    public void setOffreDeTravailId(int offreDeTravailId) {
        this.offreDeTravailId = offreDeTravailId;
    }

    public int getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(int candidatId) {
        this.candidatId = candidatId;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getNomOffre() {
        return nomOffre;
    }

    public void setNomOffre(String nomOffre) {
        this.nomOffre = nomOffre;
    }

    public String getNomPrenomCandidat() {
        return nomPrenomCandidat;
    }

    public void setNomPrenomCandidat(String nomPrenomCandidat) {
        this.nomPrenomCandidat = nomPrenomCandidat;
    }

    @Override
    public String toString() {
        return "CandidatureOffre{" + "id=" + id + ", etat=" + etat + ", nomOffre=" + nomOffre + ", nomPrenomCandidat=" + nomPrenomCandidat + '}';
    }
    
    
}
