<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dicegame.Player" %>
<%@ page import="com.example.dicegame.GameServer" %>
<%@ page import="com.example.dicegame.Lobby" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<%
    String username = session.getAttribute("u_name").toString();
    response.setIntHeader("Refresh", 5);
    GameServer gameServer = GameServer.getInstance();
%>
<head>
    <title>Index</title>
</head>
<body>


<h1><%="Welcome "%><%= session.getAttribute("u_name")%>
</h1>

<% if ("Create Lobby".equals(request.getParameter("create"))) {
    if((!gameServer.hasOwnerCreatedLobby(username)) && (!gameServer.isPlayerinLobby(username))) {
        gameServer.createLobby(session.getAttribute("u_name").toString());
    }
}
    if (request.getParameter("join") != null) {
        if(!gameServer.isPlayerinLobby(username)) {
            gameServer.addUserToLobby(username, request.getParameter("join").substring(11));
        }
    }

%>

<form action="lobby.jsp" method="POST">
    <h1>
        <% ArrayList<Lobby> lobbies = gameServer.getLobbies();%>
        <%
            for (Lobby lo : lobbies) {
        %>
        <ul>
            <li><input type="submit" name="join" value='Join Lobby <%=lo.getId()%>'/>
                <ul>
                    <li>
                        <%
                            String players = "";
                            for (Player pl : lo.getPlayers()) {
                                players += pl.toString() + " ";
                            }

                        %>
                        <%=players%>
                    </li>
                </ul>
            </li>
        </ul>
        <%}%>
    </h1>


    <input type="submit" name="create" value="Create Lobby"/>

</form>
</body>
</html>