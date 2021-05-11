/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.entities;

/**
 *
 * @author Anis
 */
public class OffreDeTravail {

    private int id;
    private int categorieId;
    private int societeId;
    private String nom;
    private String description;

    private String nomSociete;
    private String nomCategorie;

    // pour affichage
    public OffreDeTravail(int id, String nom, String description, String nomCategorie) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.nomCategorie = nomCategorie;
    }

    // pour modification
    public OffreDeTravail(int id, int categorieId, int societeId, String nom, String description) {
        this.id = id;
        this.categorieId = categorieId;
        this.societeId = societeId;
        this.nom = nom;
        this.description = description;
    }

    // pour ajout
    public OffreDeTravail(int categorieId, int societeId, String nom, String description) {
        this.categorieId = categorieId;
        this.societeId = societeId;
        this.nom = nom;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
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

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

}
