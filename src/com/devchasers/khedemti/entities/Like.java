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
public class Like {

    private int id;
    private int publicationId;
    private byte typeLike;
    private int idUser;

    public Like(int id, int publicationId, byte typeLike, int idUser) {
        this.id = id;
        this.publicationId = publicationId;
        this.typeLike = typeLike;
        this.idUser = idUser;
    }

    public Like(int publicationId, byte typeLike, int idUser) {
        this.publicationId = publicationId;
        this.typeLike = typeLike;
        this.idUser = idUser;
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

    public byte getTypeLike() {
        return typeLike;
    }

    public void setTypeLike(byte typeLike) {
        this.typeLike = typeLike;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

}
