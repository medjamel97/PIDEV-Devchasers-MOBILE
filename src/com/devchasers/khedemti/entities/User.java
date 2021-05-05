/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devchasers.khedemti.entities;

import java.util.List;

/**
 *
 * @author Faten
 */
public class User {

    private int id;
    private int candidatId;
    private int societeId;
    private String email;
    private List<String> roles;
    private String password;
    private boolean isVerified;
    private boolean isSetUp;

    public User(int id, int candidatId, int societeId, String email, List<String> roles, String password, boolean isVerified, boolean isSetUp) {
        this.id = id;
        this.candidatId = candidatId;
        this.societeId = societeId;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.isVerified = isVerified;
        this.isSetUp = isSetUp;
    }

    public User(int candidatId, int societeId, String email, List<String> roles, String password, boolean isVerified, boolean isSetUp) {
        this.candidatId = candidatId;
        this.societeId = societeId;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.isVerified = isVerified;
        this.isSetUp = isSetUp;
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

    public int getSocieteId() {
        return societeId;
    }

    public void setSocieteId(int societeId) {
        this.societeId = societeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean getIsSetUp() {
        return isSetUp;
    }

    public void setIsSetUp(boolean isSetUp) {
        this.isSetUp = isSetUp;
    }

    @Override
    public String toString() {
        return email;
    }
}
