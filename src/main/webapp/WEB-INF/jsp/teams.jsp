<%@ include file="header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teams</title>
    <!-- Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            margin-top: 100px;
        }
        
        .heading {
            margin-bottom: 20px;
        }
        
        .create-team {
            margin-bottom: 30px;
        }
        
        .card:hover {
            background-color: #333;
            color: #FFFFFF;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="heading row justify-content-center">
            <div class="col-md-6">
                <h1 class="text-center">Teams</h1>
            </div>
        </div>
        <div class="row">
            <!-- Iterate over teams and display each as a box -->
            <c:forEach var="team" items="${teams}">
                <div class="col-md-6">
                    <a href="${pageContext.request.contextPath}/org-${sessionScope.user.getOrg().getId()}/teams/${team.id}" class="text-decoration-none text-reset">
                        <div class="card mb-3">
                            <div class="card-body">
                                <h2 class="card-title">${team.name}</h2>
                                <p>${team.description}</p>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
        <div class="create-team row justify-content-center">
            <button id="createTeamBtn" type="button" name="create-team" class="btn btn-primary col-4">Create New Team</button>
        </div>
    </div>
    
    <script>
        // Function to handle button click event
        document.getElementById("createTeamBtn").addEventListener("click", function() {
            // Navigate to the desired endpoint
            window.location.href = "${pageContext.request.contextPath}/org-${sessionScope.user.getOrg().getId()}/teams/create";
        });
    </script>
    
</body>
</html>
