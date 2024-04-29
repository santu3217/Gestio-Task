<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GestioTask Dashboard</title>
    
    <style>
        /* Basic styling for the navbar */
         body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }
        .header {
            background-color: #333;
            overflow: hidden;
            display: flex; /* Use flexbox layout */
            justify-content: space-between; /* Space items evenly */
            align-items: center; /* Center vertically */
            padding: 0px;
            position: fixed; /* Set the header to be fixed */
            top: 0; /* Place the header at the top of the viewport */
            width: 100%; /* Ensure the header spans the entire viewport width */
            z-index: 9999;
        }

        .header-brand {
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 18px;
            margin-right: auto; /* Push the brand to the left */
        }

        .header-links {
            display: flex; /* Use flexbox layout */
        }

        .link {
            color: white;
            text-decoration: none;
            padding: 14px 16px;
        }

        .link:hover {
            background-color: #ddd;
            color: black;
            text-decoration: none;
        }


    </style>
</head>
<body>
<c:set var="userData" value="${sessionScope.user}" />
<div class="header">
    <div class="header-brand">
        GestioTask Dashboard
    </div>
    <div class="header-links">
        <a href="${pageContext.request.contextPath}/dashboard/org-${userData.getOrg().getId()}/t/${userData.getTeam().getId()}/tasks" class="link">Home</a>
        <a href="${pageContext.request.contextPath}/dashboard/org-${userData.getOrg().getId()}/t/${userData.getTeam().getId()}/my-tasks" class="link">My Tasks</a>
        <a href="${pageContext.request.contextPath}/org-${userData.getOrg().getId()}/teams" class="link">Teams</a>
        <a href="${pageContext.request.contextPath}/dashboard/org-${userData.getOrg().getId()}/t/${userData.getTeam().getId()}/tasks/create" class="link">Create Task</a>
        <a href="${pageContext.request.contextPath}/user/logout" class="link">Logout</a>
    </div>
    <div style="clear:both;"></div> <!-- Clear floats -->
</div>

<!-- Your page content goes here -->

</body>
</html>
