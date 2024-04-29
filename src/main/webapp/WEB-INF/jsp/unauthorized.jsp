<%-- 
    Document   : unauthorized
    Created on : 25-Apr-2024, 4:49:25 am
    Author     : santo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Unauthorized</title>
        <style>
            .error-msg {
                text-align: center;
                position: absolute; /* Position it absolutely */
                top: 50%; /* Move it 50% from the top */
                left: 50%; /* Move it 50% from the left */
                transform: translate(-50%, -50%); /* Center it both horizontally and vertically */
            }
        </style>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.user}" />
        <div class="error-msg">
            <h1>You are not Authorized (401)</h1>
            <a href="${pageContext.request.contextPath}/dashboard/org-${user.getOrg().getId()}/t/${user.getTeam().getId()}/tasks">Go to Home page</a>
        </div>
    </body>
</html>
