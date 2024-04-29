/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.dao;

import com.neu.finalproject.pojo.Org;
import java.util.List;
import org.hibernate.query.Query;

/**
 *
 * @author santo
 */
public class OrgDAO extends DAO{
    
    public void saveOrg(Org org) {
        beginTransaction();
        getSession().persist(org);
        commitTransaction();
        closeSession();
    }
    
    public void updateOrg() {
        
    }
    
    public List<Org> getOrgs() {
        beginTransaction();
        List<Org> orgs = null;
        
        try {
            Query query = getSession().createQuery("FROM Org");
            orgs = query.list();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while fetching orgs: " + e);
        } finally {
            closeSession();
        }
        
        return orgs;
    }
    
    public Org getOrg(Long id) {
        String hql = "FROM Org WHERE id = :orgId";
        Org org = null;
        try {
            beginTransaction();
            Query<Org> query = getSession().createQuery(hql, Org.class);
            query.setParameter("orgId", id);
            org = query.uniqueResult();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while fetching Org using id: " + e);
        } finally {
            closeSession();
        }
        
        return org;    
    }
    
    public Org getOrg(String domain) {
        String hql = "FROM Org WHERE domain = :domain";
        Org org = null;
        try {
            beginTransaction();
            Query<Org> query = getSession().createQuery(hql, Org.class);
            query.setParameter("domain", domain);
            org = query.uniqueResult();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while fetching Org using domain: " + e);
        } finally {
            closeSession();
        }
        
        return org;    
    }
    
    
}
