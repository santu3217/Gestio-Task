/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.models;

import com.neu.finalproject.pojo.Org;
import com.neu.finalproject.pojo.Team;
import com.neu.finalproject.pojo.User;
import java.io.Serializable;

/**
 *
 * @author santo
 */
public class OrgRegisterModel implements Serializable {
    
    private User user;
    private Org org;
    private Team team;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    
}
