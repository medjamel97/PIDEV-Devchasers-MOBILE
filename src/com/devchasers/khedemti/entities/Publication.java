/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.entities;


/**
 *
 * @author Maher
 */
public class Publication {

    private int id;
    private int candidatId;
    private String titre;
    private String description;
    private String date;
    private float pourcentageLike;

    public Publication(int id, int candidatId, String titre, String description, String date, float pourcentageLike) {
        this.id = id;
        this.candidatId = candidatId;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.pourcentageLike = pourcentageLike;
    }

    public Publication(int candidatId, String titre, String description, String date, float pourcentageLike) {
        this.candidatId = candidatId;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.pourcentageLike = pourcentageLike;
    }
      public Publication(int candidatId, String titre, String description, float pourcentageLike) {
        this.candidatId = candidatId;
        this.titre = titre;
        this.description = description;
  
        this.pourcentageLike = pourcentageLike;
    }

         public Publication(int candidatId, String titre, String description) {
        this.candidatId = candidatId;
        this.titre = titre;
        this.description = description;
  
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPourcentageLike() {
        return pourcentageLike;
    }

    public void setPourcentageLike(float pourcentageLike) {
        this.pourcentageLike = pourcentageLike;
    }

    @Override
    public String toString() {
        return "Publication{" + "id=" + id + ", candidatId=" + candidatId + ", titre=" + titre + ", description=" + description + ", date=" + date + ", pourcentageLike=" + pourcentageLike + '}';
    }

    
}
