<%-- 
    Document   : user-registration-page
    Created on : 29-Mar-2024, 9:32:20?pm
    Author     : santo
--%>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>New Task</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
    </style>
</head>

<body>
<%@ include file="header.jsp" %>

<div class="container-fluid d-flex justify-content-center align-items-center" style="height: 75vh;">
<div class="container-md">
    <h2 class="h2 my-3">Create New Task</h2>
    <form action="./create" method="POST">
        <div class="form-group col-4 mb-3">
          <label for="name">Name:</label>
          <input type="text" name="name" class="form-control" id="name" placeholder="Task Name" required>
        </div>  
        <div class="form-group col-4 mb-3">
            <label for="status">Task Status</label>
            <select name="status" class="form-select" aria-label="Task Status">
                <option value="Pending">Pending</option>
                <option value="InProgress">InProgress</option>
                <option value="Completed">Completed</option>
            </select>
        </div>
        <div class="form-group col-4 mb-3">
           <label for="user">Assign to</label>
           <select id="assignToEmail" name="assignToEmail" class="form-select">
               <c:forEach var="user" items="${users}">
                   <option value=${user.email}>${user.email}</option>
               </c:forEach>
           </select>
       </div>

        <div class="form-group col-4 mb-3">
            <label for="deadline">Deadline</label>
            <input type="datetime-local" name="deadline" class="form-control" id="deadline" required>
        </div>
        <button type="submit" class="justify-content-center btn btn-primary col-4">Create Task</button>
    </form>
</div>
</div>
</body>
</html>
