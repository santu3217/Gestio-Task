<%-- 
    Document   : user-registration-page
    Created on : 29-Mar-2024, 9:32:20?pm
    Author     : santo
--%>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<html>
<head>
    <title>Register</title>
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
    <h2 class="h2 my-3">Create Team</h2>
    <form action="./create" method="POST">
        <div class="form-group col-4 mb-3">
          <label for="team-name">Team Name</label>
          <input type="text" name="name" class="form-control" id="name" placeholder="Team name" required>
        </div>
        <div class="form-group col-4 mb-3">
          <label for="org-description">About</label>
          <input type="text" name="description" class="form-control" id="description" placeholder="Describe about your organization" required>
        </div>
        <div class="form-group col-4 mb-3">
            <label for="user">Team Lead</label>
            <select id="teamLeadEmail" name="teamLeadEmail" class="form-select">
                <c:forEach var="user" items="${users}">
                    <option value=${user.email}>${user.email}</option>
                </c:forEach>
            </select>
         </div>
        <button type="submit" class="justify-content-center btn btn-primary col-4">Register</button>
        
        <%-- Display error message if it exists --%>
        <c:if test="${not empty error}">
            <div style="color:red">${error}</div>
        </c:if>
    </form>
</div>
</div>
</body>
</html>
