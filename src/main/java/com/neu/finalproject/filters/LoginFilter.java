/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.filters;

/**
 *
 * @author santo
 */

import com.neu.finalproject.dao.UserDAO;
import com.neu.finalproject.pojo.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"*"})
public class LoginFilter implements Filter {
    
   
    private UserDAO userDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic, if needed
        userDAO = new UserDAO();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false); // false means don't create new session if it doesn't exist
        
        User user = null;
        
        // Check if the user is trying to login
        if (request.getRequestURI().endsWith("/login")) {
            String username = request.getParameter("email");
            String password = request.getParameter("password");
            
            
            user = userDAO.login(username, password);
            
            // Replace this with your actual authentication logic
            if (user != null) {
                // User logged in, create or get the session
                session= request.getSession(true); // true means create new session if it doesn't exist
                session.setAttribute("user", user); // Example: set user attribute in session
                if(user.getTeam() != null) {
                    String redirectUrl = String.format("%s/dashboard/org-%s/t/%s/tasks", request.getContextPath(), user.getOrg().getId(), user.getTeam().getId());
                    response.sendRedirect(redirectUrl);
                    return; // Stop filter chain
                }
            }
        }
        
        // Check if the user is accessing a path under /user
        if (request.getRequestURI().startsWith(request.getContextPath() + "/user/") || request.getRequestURI().startsWith(request.getContextPath() + "/org/")) {
            
            if(session != null && session.getAttribute("user") !=  null && request.getRequestURI().endsWith("/user/login")) {
                User userDetails = (User) session.getAttribute("user");
                if (userDetails.getTeam() == null) {
                    String redirectUrl = String.format("%s/dashboard/org-%s/teams", request.getContextPath(), userDetails.getOrg().getId());
                    response.sendRedirect(redirectUrl);
                    return;
                } 
                String redirectUrl = String.format("%s/dashboard/org-%s/t/%s/tasks", request.getContextPath(), userDetails.getOrg().getId(), userDetails.getTeam().getId());
                response.sendRedirect(redirectUrl);
                return;
            }
            
            // Pass through filter chain for paths under /user
            filterChain.doFilter(request, response);
            return; // Stop filter chain
        }

        // Check if the user is already logged in
        if (session != null && session.getAttribute("user") != null) {
            // User is logged in, continue with the request
            filterChain.doFilter(request, response);
        } else {
            // User is not logged in, redirect to login page for any other URL except the login page
            if (!request.getRequestURI().endsWith("/user/login")) {
                response.sendRedirect(request.getContextPath() + "/user/login");
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

}

