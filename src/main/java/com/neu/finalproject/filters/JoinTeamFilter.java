/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.filters;

/**
 *
 * @author santo
 */

import com.neu.finalproject.pojo.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/dashboard/*") 
public class JoinTeamFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        if (user != null && user.getTeam() == null) {
            // Redirect user to join team page if they haven't joined a team
            String redirectUrl = String.format("%s/org-%s/teams", request.getContextPath(), user.getOrg().getId());
            response.sendRedirect(redirectUrl);
            return; // Stop further filter chain execution
        }

        // Continue with the filter chain if the user has joined a team
        filterChain.doFilter(request, response);
    }

}
