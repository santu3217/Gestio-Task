<%-- 
    Document   : user-registration-page
    Created on : 29-Mar-2024, 9:32:20?pm
    Author     : santo
--%>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Team</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
    </style>
</head>
<body>
    <div class="container-fluid d-flex justify-content-center align-items-center" style="height: 75vh;">
        <div class="container-md">
            <h2 class="h2 my-3">Edit Team</h2>
            <form action="./${team.getId()}" method="POST">
                <div class="form-group col-4 mb-3">
                  <label for="team-name">Team Name</label>
                  <input type="text" name="name" class="form-control" id="name" placeholder="Team name" value="${team.getName()}" required>
                </div>
                <div class="form-group col-4 mb-3">
                  <label for="org-description">About</label>
                  <input type="text" name="description" class="form-control" id="description" placeholder="Describe about your organization" value="${team.getDescription()}" required>
                </div>      

                <div class="form-group col-4 mb-3">
                   <label for="user">Team Lead</label>
                   <select id="teamLeadEmail" name="teamLeadEmail" class="form-select" value="${team.getTeamLead().getEmail()}">
                       <c:forEach var="user" items="${users}">
                           <option value=${user.email}>${user.email}</option>
                       </c:forEach>
                   </select>
                </div>
                <button type="submit" class="justify-content-center btn btn-primary col-4">Update Team</button>

                <%-- Display error message if it exists --%>
                <c:if test="${not empty error}">
                    <div style="color:red">${error}</div>
                </c:if>
            </form>
        </div>
    </div>
    <script>
        var teamLeadEmail = "${team.getTeamLead().getEmail()}";
        var selectElement = document.getElementById("teamLeadEmail");

        // Loop through options to find the matching email and set it as selected
        for (var i = 0; i < selectElement.options.length; i++) {
            if (selectElement.options[i].value === teamLeadEmail) {
                selectElement.options[i].selected = true;
                break;
            }
        }
    </script>
   
</body>
</html>
