/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.controllers;

import com.neu.finalproject.dao.OrgDAO;
import com.neu.finalproject.pojo.Org;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/org")
public class OrgController {
    
    @GetMapping("/registers")
    public String showRegistrationPage(Model model){
        model.addAttribute("register-user", new Org());
        return "org-registration-page";
    }
    
    @PostMapping("/registers")
    public String registerUser(@ModelAttribute("register-user") Org org, Model model, HttpServletRequest request, OrgDAO orgDAO){
        if (orgDAO.getOrg(org.getId()) != null){
            model.addAttribute("error", "Org already exists");
            return "org-registration-page";
        }
        
        orgDAO.saveOrg(org);
        return "redirect:/user/register";
    }
}