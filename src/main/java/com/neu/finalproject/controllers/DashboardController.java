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
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author santo
 */

@Controller
@RequestMapping("/dashboard/org-{orgId}/t/{teamId}")
public class DashboardController {
    
    @GetMapping("/tasks")
//    @ResponseBody
    public String allTasks(@PathVariable("teamId") Long id, Model model, UserDAO userDAO, TaskDAO taskDAO){ 
        
        List<Task> createdTasks = taskDAO.getAllTasksByStatus("Pending", id);
        List<Task> inProgressTasks = taskDAO.getAllTasksByStatus("InProgress", id);
        List<Task> completedTasks = taskDAO.getAllTasksByStatus("Completed", id);
        model.addAttribute("createdTasks", createdTasks);
        model.addAttribute("inProgressTasks", inProgressTasks);
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("dashboardName", "All Tasks");
        
        return "display-tasks";
    }
    
    @GetMapping("/my-tasks")
    public String  myTasks(@PathVariable("teamId") Long id, Model model, HttpServletRequest request, UserDAO userDAO, TaskDAO taskDAO) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");    
        
        List<Task> createdTasks = taskDAO.getTasksByUserId("Pending", user.getId(), id);
        List<Task> inProgressTasks = taskDAO.getTasksByUserId("InProgress", user.getId(), id);
        List<Task> completedTasks = taskDAO.getTasksByUserId("Completed", user.getId(), id);
        model.addAttribute("createdTasks", createdTasks);
        model.addAttribute("inProgressTasks", inProgressTasks);
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("dashboardName", "My Tasks");
        
        return "display-tasks";
    }
}