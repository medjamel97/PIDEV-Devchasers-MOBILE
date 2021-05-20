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
public class Commentaire {

    private int id;
    private int publicationId;
    private int userId;
    private String description;

    public Commentaire(int id, int publicationId, int userId, String description) {
        this.id = id;
        this.publicationId = publicationId;
        this.userId = userId;
        this.description = description;
    }

    public Commentaire(int publicationId, int userId, String description) {
        this.publicationId = publicationId;
        this.userId = userId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
