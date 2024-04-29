/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.dao;

import com.neu.finalproject.pojo.User;
import java.util.List;
import org.hibernate.query.Query;

/**
 *
 * @author santo
 */
public class UserDAO extends DAO{
    
    public void saveUser(User user) {
        beginTransaction();
        getSession().persist(user);
        commitTransaction();
        closeSession();
    }
    
    public void updateUser(User updatedUser) {
        beginTransaction();
        try {
            getSession().merge(updatedUser);
            commitTransaction();
        } catch (Exception e) {
            System.out.println("Exception occured while updating user: " + e);
        } finally {
            closeSession();
        }
    }
    
    public User getUser(String userEmail) {
        String hql = "FROM User WHERE email = :userEmail";
        User user = null;
        try {
            beginTransaction();
            Query<User> query = getSession().createQuery(hql, User.class);
            query.setParameter("userEmail", userEmail);
            user = query.uniqueResult();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while fetching user: " + e);
        } finally {
            closeSession();
        }
        
        return user;      
    }
    
    public User getUser(Long id) {
        String hql = "FROM User WHERE id = :UserId";
        User user = null;
        try {
            beginTransaction();
            Query<User> query = getSession().createQuery(hql, User.class);
            query.setParameter("UserId", id);
            user = query.uniqueResult();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while fetching user: " + e);
        } finally {
            closeSession();
        }
        
        return user;      
    }
    
    public List<User> getUsers(Long OrgId) {
        String hql = "FROM User WHERE org.id = :OrgId";
        List<User> users = null;
        try {
            beginTransaction();
            Query<User> query = getSession().createQuery(hql, User.class);
            query.setParameter("OrgId", OrgId);
            users = query.list();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while fetching users: " + e);
        } finally {
            closeSession();
        }
        
        return users;      
    }
    
    
    public User login(String email, String password) {
        String hql = "FROM User WHERE email = :email AND password = :password";
        User user = null;
        try {
            beginTransaction();
            Query<User> query = getSession().createQuery(hql, User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            user = query.uniqueResult();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while verifying user: " + e);
        } finally {
            closeSession();
        }
        
        return user;
    }
}
