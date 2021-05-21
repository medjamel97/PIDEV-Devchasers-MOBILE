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
public class Competence {

    private int id;
    private int candidatId;
    private String name;
    private int level;

    public Competence(int id, int candidatId, String name, int level) {
        this.id = id;
        this.candidatId = candidatId;
        this.name = name;
        this.level = level;
    }

    public Competence(int candidatId, String name, int level) {
        this.candidatId = candidatId;
        this.name = name;
        this.level = level;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
