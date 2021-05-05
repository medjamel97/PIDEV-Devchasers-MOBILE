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

    public CandidatureOffre(int id, int offreDeTravailId, int candidatId, String etat) {
        this.id = id;
        this.offreDeTravailId = offreDeTravailId;
        this.candidatId = candidatId;
        this.etat = etat;
    }

    public CandidatureOffre(int offreDeTravailId, int candidatId, String etat) {
        this.offreDeTravailId = offreDeTravailId;
        this.candidatId = candidatId;
        this.etat = etat;
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

}
