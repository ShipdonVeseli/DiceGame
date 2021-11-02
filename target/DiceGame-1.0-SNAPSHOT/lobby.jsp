<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dicegame.Player" %>
<%@ page import="com.example.dicegame.Server" %>
<%@ page import="com.example.dicegame.Lobby" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Index</title>
    </head>
    <body>
        <h1><%="Welcome "%><%= session.getAttribute("u_name")%></h1>
        <% if("Create Lobby".equals(request.getParameter("create"))){
               Server.createLobby(session.getAttribute("u_name").toString());
           }
           int i= 5;
        %>

        <p> <%="Hey"+ i%></p>
        <h1>
            <%!ArrayList<Lobby> lobbies =  Server.getLobbies();%>
            <%for(Lobby lo: lobbies){ %>
                <ul>
                    <li><%=lo.getId()%></li>
                </ul>
            <%}%>
        </h1>

        <form action="lobby.jsp" method="POST">
            <input type="submit" name="create" value="Create Lobby"/>
        </form>
    </body>
</html>