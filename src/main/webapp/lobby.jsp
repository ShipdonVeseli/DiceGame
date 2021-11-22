<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dicegame.Player" %>
<%@ page import="com.example.dicegame.GameServer" %>
<%@ page import="com.example.dicegame.Lobby" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.UUID" %>
<!DOCTYPE html>
<html>
<%
    String username;
    try {
         username = session.getAttribute("u_name").toString();
    }catch (Exception e){
        username="error";
        response.sendRedirect("index.html");//test
    }
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
    if ((!gameServer.hasPlayerCreatedLobby(username)) && (!gameServer.isPlayerinLobby(username))) {
        UUID lobbyID=gameServer.createLobby(username);
        session.setAttribute("lobby_id",lobbyID);
    }
}
    if (request.getParameter("join") != null) {
        if (!gameServer.isPlayerinLobby(username)) {
            gameServer.addUserToLobby(username, request.getParameter("join").substring(11));

            int lobbyID=Integer.parseInt(request.getParameter("join").substring(11));
            session.setAttribute("lobby_id",lobbyID);


        }
    }

    if (request.getParameter("remove") != null) {
        if (gameServer.getLobby(UUID.fromString(request.getParameter("remove").substring(13))).getOwner().getPlayername().equals(username)) {
            gameServer.removeLobby(UUID.fromString(request.getParameter("remove").substring(13)));
        }
    }


%>

<form action="lobby.jsp" method="POST">
    <h1>
        <%
            for (Lobby lo : gameServer.getLobbies()) {
                if (!gameServer.isPlayerinLobby(username) || lo.isPlayerInThatLobby(username)) {
        %>
        <ul>
            <li>Lobby Number <%=lo.getId()%>
                <%if(!gameServer.isPlayerinLobby(username)){ %> <input type="submit" name="join" value='Join Lobby <%=lo.getId()%>'/> <% } %>

                <%if(lo.getOwner().getPlayername().equals(username)) { %><input type="submit" name="remove" value='Remove Lobby <%=lo.getId()%>'/> <% } %>
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
        <%
                }
            }
        %>
    </h1>

    <% if(!gameServer.isPlayerinLobby(username) ){ %>
    <input type="submit" name="create" value="Create Lobby"/>
    <% } %>
</form>
</body>
</html>