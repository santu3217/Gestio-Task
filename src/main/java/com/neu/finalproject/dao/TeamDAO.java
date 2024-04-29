/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.dao;


import com.neu.finalproject.pojo.Team;
import java.util.List;
import org.hibernate.query.Query;

/**
 *
 * @author santo
 */
public class TeamDAO extends DAO{
    
    public void saveTeam(Team team) {
        beginTransaction();
        getSession().persist(team);
        commitTransaction();
        closeSession();
    }
    
    
    public Team getTeam(Long teamId) {
        String hql = "FROM Team WHERE id = :teamId";
        Team team = null;
        try {
            beginTransaction();
            Query<Team> query = getSession().createQuery(hql, Team.class);
            query.setParameter("teamId", teamId);
            team = query.uniqueResult();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while fetching team: " + e);
        } finally {
            closeSession();
        }
        
        return team;      
    }
    
    public List<Team> getTeams(Long orgId) {
        beginTransaction();
        List<Team> teams = null;
        
        try {
            Query query = getSession().createQuery("FROM Team WHERE org.id= :orgId");
            query.setParameter("orgId", orgId);
            teams = query.list();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while fetching teams: " + e);
        } finally {
            closeSession();
        }
        
        return teams;
    }
    
    public void updateTeam(Team updatedTeam) {
        beginTransaction();
        try {
            getSession().merge(updatedTeam);
            commitTransaction();
        } catch (Exception e) {
            System.out.println("Exception occured while updating Team: " + e);
        } finally {
            closeSession();
        }
    }
}
