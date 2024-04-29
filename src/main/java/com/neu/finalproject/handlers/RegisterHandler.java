    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
package com.neu.finalproject.handlers;

import com.neu.finalproject.dao.OrgDAO;
import com.neu.finalproject.dao.TeamDAO;
import com.neu.finalproject.dao.UserDAO;
import com.neu.finalproject.models.OrgRegisterModel;
import com.neu.finalproject.pojo.Org;
import com.neu.finalproject.pojo.Team;
import com.neu.finalproject.pojo.User;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

    /**
     *
     * @author santo
     */

@Component
@Scope("request")
public class RegisterHandler{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrgDAO orgDAO;
    
    @Autowired
    private TeamDAO teamDAO;

    public OrgRegisterModel init() {
        return new OrgRegisterModel();
    }

    public void addUser(OrgRegisterModel model, User user) {
        model.setUser(user);
    }

    public void addOrg(OrgRegisterModel model, Org org) {
        model.setOrg(org);
    }
    
    public void addTeam(OrgRegisterModel model, Team team) {
        model.setTeam(team);
    } 

    public String saveAll(OrgRegisterModel model) {
        String transitionValue = "success";

        try {
            
            Org org = model.getOrg();
            Team team = model.getTeam();
            User user = model.getUser();
            // persist the org details
            orgDAO.saveOrg(org);
            
            // get the orgId and assign it to team and user
//            team.setOrgId(org.getId());
//            user.setOrgId(org.getId());
            
            team.setOrg(org);
            user.setOrg(org);
            // persist the team details
            teamDAO.saveTeam(team);
            
            // set the teamId of user
//            user.setTeamId(team.getId());
            user.setTeam(team);
            
            // persist the user
            userDAO.saveUser(user);
            
        } catch(Exception e) {
            transitionValue = "error";   
        }

        return transitionValue;
    }
}
