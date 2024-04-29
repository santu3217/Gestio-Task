/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.controllers;

import com.neu.finalproject.dao.OrgDAO;
import com.neu.finalproject.dao.TeamDAO;
import com.neu.finalproject.dao.UserDAO;
import com.neu.finalproject.pojo.Org;
import com.neu.finalproject.pojo.Team;
import com.neu.finalproject.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author santo
 */

@Controller
@RequestMapping("/org-{orgId}/teams")
public class TeamController {
    
    @GetMapping(value = {"", "/"})
    public String showTeams(@PathVariable("orgId") Long orgId, Model model, TeamDAO teamDAO) {        
        List<Team> teams = teamDAO.getTeams(orgId);
        
        model.addAttribute("teams", teams);        
        return "teams";
        
    }
    
    @GetMapping("/create")
    public String createTeam(@PathVariable("orgId") Long orgId, Model model, UserDAO userDAO) {
        List<User> users = userDAO.getUsers(orgId);
        
        model.addAttribute("users", users);
        model.addAttribute("create-team", new Team());
        return "create-team";
    }
    
    @PostMapping("/create")
    public String createTeam(@PathVariable("orgId") Long orgId, @ModelAttribute("create-team") Team team, TeamDAO teamDAO, OrgDAO orgDAO, UserDAO userDAO) {
        Org org = orgDAO.getOrg(orgId);
        User user = userDAO.getUser(team.getTeamLeadEmail());
        
        team.setOrg(org);
        team.setTeamLead(user);
        teamDAO.saveTeam(team);
        
        String redirectUrl = String.format("redirect:/org-%s/teams", team.getOrg().getId());
        
        return redirectUrl;
    }
    
    @GetMapping("/{teamId}")
    public String showTeam(@PathVariable("teamId") Long id, Model model, TeamDAO teamDAO) {
        Team team = teamDAO.getTeam(id);
        model.addAttribute("team", team);
        return "show-team-details";
    }
    
    @GetMapping("/update/{teamId}")
    public String showUpdateTeam(@PathVariable("orgId") Long orgId, @PathVariable("teamId") Long teamId, Model model, TeamDAO teamDAO, UserDAO userDAO) {        
        Team team = teamDAO.getTeam(teamId);
        List<User> users = userDAO.getUsers(orgId);

        model.addAttribute("users", users);
        model.addAttribute("team", team);
        return "update-team";
    }
    
    @PostMapping("/update/{teamId}")
    public String updateTeam(@PathVariable("teamId") Long teamId, @ModelAttribute("team") Team updatedTeam, Model model, TeamDAO teamDAO, UserDAO userDAO) {
        Team team = teamDAO.getTeam(teamId);
        
        team.setName(updatedTeam.getName());
        team.setDescription(updatedTeam.getDescription());
        
        if(updatedTeam.getTeamLeadEmail() != null) {
            User user = userDAO.getUser(updatedTeam.getTeamLeadEmail());
            if (user == null) {
                model.addAttribute("error", "USER NOT FOUND");
                return "update-team";
            }
            team.setTeamLead(user);
        }
        
        teamDAO.updateTeam(team);
        
        String redirectUrl = String.format("redirect:/org-%s/teams", team.getOrg().getId());
        
        return redirectUrl;
    }
    
    @GetMapping(value = {"/join/{teamId}", "/join/{teamId}/"})
    @ResponseBody
    public ResponseEntity<String> updateUserTeam(@PathVariable("teamId") Long teamId, HttpServletRequest request, UserDAO userDAO, TeamDAO teamDAO) {
        
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            Team team = teamDAO.getTeam(teamId);

            user.setTeam(team);
            userDAO.updateUser(user);
            session.setAttribute("user", user);
        } catch(Exception e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .body("Team not assigned!");
        }  
        
        return ResponseEntity.ok("Team assigned successfully");
    }
}
