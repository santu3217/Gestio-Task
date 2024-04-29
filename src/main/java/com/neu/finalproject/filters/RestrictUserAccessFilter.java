/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.neu.finalproject.filters;

import com.neu.finalproject.pojo.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author santo
 */

@WebFilter(urlPatterns = {"/dashboard/*", "/org-*/*"})
public class RestrictUserAccessFilter implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false); // false means don't create new session if it doesn't exist
        
        User user = (User) session.getAttribute("user");
        
        String currentURL = request.getRequestURL().toString();
    
        // Extract orgId and teamId using regular expressions
        String orgId = null;
        String teamId = null;
        Pattern pattern = Pattern.compile("/org-(\\d+)/?(?:t|teams)/(\\d+)");
        Matcher matcher = pattern.matcher(currentURL);
        if (matcher.find()) {
            orgId = matcher.group(1);
            teamId = matcher.group(2);
        }
        
        if ((orgId != null && user.getOrg().getId() == Integer.parseInt(orgId))) {
            
            if (teamId != null && user.getTeam() != null && user.getTeam().getId() != Integer.parseInt(teamId)) {
                response.sendRedirect("/FinalProject/user/unauthorized");
            }
            
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect("/FinalProject/user/unauthorized");
        }        
    }
    
}
