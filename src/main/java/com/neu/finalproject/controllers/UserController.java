/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.controllers;

import com.neu.finalproject.dao.OrgDAO;
import com.neu.finalproject.dao.UserDAO;
import com.neu.finalproject.pojo.Org;
import com.neu.finalproject.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author santo
 */

@Controller
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/login")
    public String showLoginPage(){
        return "user-login-page";
    }
    
    @GetMapping("/register")
    public String showRegistrationPage(Model model, OrgDAO orgDAO){
        List<Org> orgs = orgDAO.getOrgs();
        model.addAttribute("register-user", new User());
        model.addAttribute("orgs", orgs);
        return "user-registration-page";
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("register-user") User user, Model model, HttpServletRequest request, UserDAO userDAO, OrgDAO orgDAO){
        if (userDAO.getUser(user.getEmail()) != null){
            model.addAttribute("error", "User already exists");
            return "user-registration-page";
        }
        
        Org org = orgDAO.getOrg(user.getEmail().split("@")[1]);
        if(org != null) {
//            user.setOrgId(org.getId);
            user.setOrg(org);
            userDAO.saveUser(user);
            return "redirect:/user/login";
        } else {
            model.addAttribute("error", "Organization does not exist!");
            return "user-registration-page";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // Invalidate the session if it exists
        if (session != null) {
            session.invalidate();
        }
        
        return "redirect:/user/login";
    }
    
    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }
}
