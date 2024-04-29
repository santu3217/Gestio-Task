/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.dao;

import com.neu.finalproject.pojo.Task;
import java.util.List;
import org.hibernate.query.Query;

/**
 *
 * @author santo
 */
public class TaskDAO extends DAO{
    
    public void saveTask(Task task) {
        beginTransaction();
        getSession().persist(task);
        commitTransaction();
        closeSession();
    }
    
    public Task getTask(Long id) {
        String hql = "FROM Task WHERE id = :taskId";
        Task task = null;
        try {
            beginTransaction();
            Query<Task> query = getSession().createQuery(hql, Task.class);
            query.setParameter("taskId", id);
            task = query.uniqueResult();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while fetching task: " + e);
        } finally {
            closeSession();
        }
        
        return task;      
    }
    
    public List<Task> getTasksByUserId(Long id) {
        beginTransaction();
        List<Task> tasks = null;
        
        try {
            Query query = getSession().createQuery("FROM Task WHERE assignTo.id= :assignTo");
            query.setParameter("assignTo", id);
            tasks = query.list();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while fetching tasks: " + e);
        } finally {
            closeSession();
        }
        
        return tasks;
    }
    
    public List<Task> getTasksByUserId(String status, Long id, Long teamId) {
        beginTransaction();
        List<Task> tasks = null;
        
        try {
            Query query = getSession().createQuery("FROM Task WHERE assignTo.id= :assignTo AND status= :status AND teamId= : teamId");
            query.setParameter("assignTo", id);
            query.setParameter("status", status);
            query.setParameter("teamId", teamId);
            tasks = query.list();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while fetching tasks: " + e);
        } finally {
            closeSession();
        }
        
        return tasks;
    }
    
    
    public void updateTask(Task updatedTask) {
        beginTransaction();
        try {
            getSession().merge(updatedTask);
            commitTransaction();
        } catch (Exception e) {
            System.out.println("Exception occured while updating task: " + e);
        } finally {
            closeSession();
        }
    }
    
    
    public void updateTaskStatus(Long id, String status) {
        String hql = "UPDATE Task SET status = :status WHERE id = :taskId";
        beginTransaction();
         try {
             // Create HQL query to update task status
             Query query = getSession().createQuery(hql);
             query.setParameter("status", status);
             query.setParameter("taskId", id);
             query.executeUpdate();
             commitTransaction();
         } catch (Exception e) {
             System.out.println("Exception occured while updating task Status: " + e);
         } finally {
             closeSession();
         }
     }
    
    public void deleteTask(Long id) {
        String hql = "DELETE FROM Task WHERE id = :taskId";
        beginTransaction();
        try {
            Query query = getSession().createQuery(hql);
            query.setParameter("taskId", id);
            query.executeUpdate();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while deleting task: " + e);
        } finally {
            closeSession();
        }
    }
    
    public List<Task> getTasksByTeamId(Long id) {
        beginTransaction();
        List<Task> tasks = null;
        
        try {
            Query query = getSession().createQuery("FROM Task WHERE teamId= :teamId");
            query.setParameter("teamId", id);
            tasks = query.list();
            commitTransaction();
        } catch(Exception e) {
            System.out.println("Exception occured while fetching tasks: " + e);
        } finally {
            closeSession();
        }
        
        return tasks;
    }
    
    public List<Task> getAllTasksByStatus(String status) {
        beginTransaction();
        List<Task> tasks = null;
        
        try {
            Query query = getSession().createQuery("FROM Task WHERE status = :taskStatus");
            query.setParameter("taskStatus", status);
            tasks = query.list();
            commitTransaction();
        } catch(Exception e) {
            System.out.println(String.format("Exception occured while fetching tasks for %s: %s", status, e));
        } finally {
            closeSession();
        }
        
        return tasks;
    }
    
    
    public List<Task> getAllTasksByStatus(String status, Long teamId) {
        beginTransaction();
        List<Task> tasks = null;
        
        try {
            Query query = getSession().createQuery("FROM Task WHERE status= :taskStatus AND teamId=: teamID");
            query.setParameter("taskStatus", status);
            query.setParameter("teamID", teamId);
            tasks = query.list();
            commitTransaction();
        } catch(Exception e) {
            System.out.println(String.format("Exception occured while fetching tasks for %s: %s", status, e));
        } finally {
            closeSession();
        }
        
        return tasks;
    }

}
