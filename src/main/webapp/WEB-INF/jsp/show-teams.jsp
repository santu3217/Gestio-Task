<%-- 
    Document   : show-teams
    Created on : 18-Apr-2024, 3:28:00 pm
    Author     : santo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Select Team</title>
</head>
<body>

<h2>Join your Team</h2>

<form>
  <label for="team">Select a team:</label>
  <select id="teamId" name="teamId">
      <c:forEach var="team" items="${teams}">
          <option value=${team.id}>${team.name}</option>
      </c:forEach>
  </select>
  <br><br>
  <button type="button" onclick="saveTeamAndRedirect()">Join</button>
</form>

<script>
    function saveTeamAndRedirect() {
        var teamId = document.getElementById("teamId").value;

        // Create a new XMLHttpRequest object
        var xhr = new XMLHttpRequest();
        
        var url = "/FinalProject/user/team/join";
        // Configure the request
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        // Set up a callback function to handle the response
        xhr.onreadystatechange = function() {            
            if (xhr.readyState === XMLHttpRequest.DONE) {
                console.log(xhr.status);
                if (xhr.status === 200) {
                    // Redirect to the home page upon successful saving
                    window.location.href = "/FinalProject/dashboard";
                } else {
                    // Handle errors
                    console.error("Error: " + xhr.status);
                }
            }
        };

        // Send the request with the teamId as a parameter
        xhr.send("teamId=" + teamId);
    }
</script>

</body>
</html>

