<%--
  Created by IntelliJ IDEA.
  User: shipdon
  Date: 02.11.21
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Session</title>
</head>
<body>
    <%
        if(session.getAttribute("u_name")==null)
        {
            session.setAttribute("u_name", request.getParameter("username"));
        }
        response.sendRedirect("lobby.jsp");

    %>
</body>
</html>
