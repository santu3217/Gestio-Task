<%-- 
    Document   : user-registration-page
    Created on : 29-Mar-2024, 9:32:20 pm
    Author     : santo
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        
        .links {
            margin-top: 30px;
        }
    </style>
</head>
<body>

<div class="container-fluid d-flex justify-content-center align-items-center" style="height: 75vh;">
<div class="container-md">
    <h2 class="h2 my-3">Login</h2>
    <form>
        <div class="form-group col-4 mb-3">
          <label for="email">Email address</label>
          <input type="email" name="email" class="form-control" id="userEmail" placeholder="your organization email" required>
        </div>
        <div class="form-group col-4 mb-3">
          <label for="password">Password</label>
          <input type="password" name="password" class="form-control" id="password" placeholder="Password" required>
        </div>
        <button type="submit" class="justify-content-center btn btn-primary col-4">Login</button>
        
        <%-- Display error message if it exists --%>
        <c:if test="${not empty error}">
            <div style="color:red">${error}</div>
        </c:if>
            
        <div class="links">
            <p>Don't have an account?<a href="./register" class="link"> Create Account</a></p>
            <p>New to GestioTask?<a href="${pageContext.request.contextPath}/org/register" class="link"> Register your Organization</a></p>
        </div>   
        
    </form>
</div>
</div>
</body>
</html>
