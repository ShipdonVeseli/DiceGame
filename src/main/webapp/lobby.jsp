<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dicegame.Player" %>
<%@ page import="com.example.dicegame.GameServer" %>
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
               GameServer.createLobby(session.getAttribute("u_name").toString());
           }
        %>

        <h1>
            <%!ArrayList<Lobby> lobbies =  GameServer.getLobbies();%>
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