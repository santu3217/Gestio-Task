/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.pojo;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author santo
 */

@Entity
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String status;
    
    @Column(name="created_on", updatable=false)
    @CreationTimestamp
    private LocalDateTime createdOn;
    
    @Column(name="updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    
    private LocalDateTime deadline;
    
    @ManyToOne
    @JoinColumn(name = "assigned_to", referencedColumnName = "id")
    private User assignTo;
    
    @Column(name="team_id")
    private Long teamId;
    
    @ManyToOne
    @JoinColumn(name = "assigned_by", referencedColumnName = "id")
    private User assignBy;
    
    @Transient
    private String assignToEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public User getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(User assignTo) {
        this.assignTo = assignTo;
    }

    public User getAssignBy() {
        return assignBy;
    }

    public void setAssignBy(User assignBy) {
        this.assignBy = assignBy;
    }

    public String getAssignToEmail() {
        return assignToEmail;
    }

    public void setAssignToEmail(String assignToEmail) {
        this.assignToEmail = assignToEmail;
    }
    
}
