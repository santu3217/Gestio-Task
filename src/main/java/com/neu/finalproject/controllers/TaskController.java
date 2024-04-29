/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.controllers;

import com.neu.finalproject.dao.TaskDAO;
import com.neu.finalproject.dao.UserDAO;
import com.neu.finalproject.pojo.Task;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author santo
 */

@Controller
@RequestMapping("/dashboard/org-{orgId}/t/{teamId}/tasks")
public class TaskController {
    
    @GetMapping("/create")
    public String createTask(@PathVariable("orgId") Long orgId, Model model, UserDAO userDAO) {
        List<User> users = userDAO.getUsers(orgId);
        
        model.addAttribute("users", users);
        model.addAttribute("create-task", new Task());

        return "task-creation-form";
    }
    
    
    @PostMapping("/create")
    public String createTask(@PathVariable("teamId") Long teamId, @ModelAttribute("create-task") Task task, HttpServletRequest request, TaskDAO taskDAO, UserDAO userDAO) {        

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        User assignToUser = userDAO.getUser(task.getAssignToEmail());

        // Set the retrieved User object in the Task entity
        task.setAssignTo(assignToUser);
        task.setAssignBy(user);
        task.setTeamId(teamId);
        taskDAO.saveTask(task);
        
        String redirectUrl = String.format("redirect:/dashboard/org-%s/t/%s/tasks", user.getOrg().getId(), user.getTeam().getId());
        return redirectUrl;
    }
    
    @GetMapping("/{id}")
    public String fetchTask(@PathVariable("id") Long id, Model model, TaskDAO taskDAO) {
        Task task = taskDAO.getTask(id);
        if (task == null) {
            return "error";
        }
        
        model.addAttribute("display-task", task);
        return "display-task-details";
    }
    
    @GetMapping("/update/{id}")
    public String showUpdateTask(@PathVariable("orgId") Long orgId, @PathVariable("id") Long TaskId, Model model, TaskDAO taskDAO, UserDAO userDAO) {
        Task existingTask = taskDAO.getTask(TaskId);
        List<User> users = userDAO.getUsers(orgId);
        
        model.addAttribute("users", users);
        model.addAttribute("task", existingTask);
        return "update-task";
    }
    
    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable("id") Long id, @ModelAttribute("task") Task updatedTask, TaskDAO taskDAO, UserDAO userDAO, HttpServletRequest request) {
        Task existingTask = taskDAO.getTask(id);
        
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        if (existingTask != null) {
            
            User assignedUser = userDAO.getUser(updatedTask.getAssignToEmail());
        
            existingTask.setName(updatedTask.getName());
            if(assignedUser != null) {
                existingTask.setAssignTo(assignedUser);
            }
            existingTask.setDeadline(updatedTask.getDeadline());
            existingTask.setStatus(updatedTask.getStatus());

            taskDAO.updateTask(existingTask);
        }
        
        String redirectUrl = String.format("redirect:/dashboard/org-%s/t/%s/tasks", user.getOrg().getId(), user.getTeam().getId());
        
        return redirectUrl;
    }
    
    @PutMapping("/update/status/{id}")
    @ResponseBody
    public ResponseEntity<String> updateTaskStatus(@PathVariable("id") Long id, @RequestParam("status") String status, TaskDAO taskDAO) {
        Task existingTask = taskDAO.getTask(id);
        
        if(existingTask != null) {
            taskDAO.updateTaskStatus(id, status);
            return ResponseEntity.ok("Task status updated successfully");
        }
        
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .body("Task not found with ID: " + id);
    }
    
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteTask(@PathVariable("id") Long id, TaskDAO taskDAO, HttpServletRequest request) {
        taskDAO.deleteTask(id);
    }
}
