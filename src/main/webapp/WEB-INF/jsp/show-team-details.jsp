<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.neu.finalproject.pojo.Team" %>
<%@ include file="header.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Team Details</title>
    <!-- Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
        }
    </style>
</head>
<body>
    <div class="container" style="margin-top: 50px;">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <div class="card mt-5">
                    <div class="card-body">
                        <h2 class="card-title text-center mb-4">${team.name}</h2>
                        <hr>
                        <h4>About:</h4>
                        <p class="card-text">${team.description}</p>
                        <hr>
                        <h4>Team Lead: </h4>
                        <p class="card-text">${team.teamLead.email}</p>
                        <hr>
                        <h4>Details:</h4>
                        <p class="card-text">Created On: ${team.createdOn}</p>
                        <p class="card-text">Updated On: ${team.updatedOn}</p>
                        <hr>                       
                        <button id="joinTeamBtn" type="button" onclick="joinTeam()" class="btn btn-primary btn-block">Join Team</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        function joinTeam() {
            // Make AJAX request here
            var teamId = ${team.id};
            var url = "./join/" + teamId;
            var xhr = new XMLHttpRequest();
            xhr.open("GET", url, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                   window.location.href = "./";
                } 
            };
            xhr.send();
        }
    </script>
</body>
</html>
