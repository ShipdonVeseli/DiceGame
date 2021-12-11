package com.example.dicegame.servlets;

import com.example.dicegame.GameServer;
import com.example.dicegame.Lobby;
import com.example.dicegame.Player;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet(name = "LobbyServlet", value = "/Lobby-servlet")
public class LobbyServlet extends HttpServlet {
    private GameServer gameServer = GameServer.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        lobbyFunctions(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        lobbyFunctions(request, response);
    }

    private void lobbyFunctions(HttpServletRequest request, HttpServletResponse response) {
        try {
            String mode = request.getParameter("mode");

            String username = request.getParameter("username");

            switch (mode) {
                case "join":
                    UUID lobbyID = UUID.fromString(request.getParameter("lobbyID"));
                    try {
                        if (!gameServer.getLobbymanager().isPlayerinLobby(username)) {
                            gameServer.getLobbymanager().addUserToLobby(username, lobbyID);
                        } else {
                            response.sendError(0);//ToDo correct Error handling
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "create":
                    try {
                        if (!gameServer.getLobbymanager().isPlayerinLobby(username)) {
                            String id = gameServer.getLobbymanager().createLobby(username).toString();
                            Cookie cookie = new Cookie("lobbyID", id);
                            response.addCookie(cookie);
                        } else {
                            response.sendError(0);//ToDo correct Error handling
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "leave":
                    try {
                        String IDofLobby = (request.getParameter("lobbyID"));
                        gameServer.getLobbymanager().removePlayerFromLobby(username, IDofLobby);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;


                case "get-Lobbies":
                    //TODO
                   System.out.println(gameServer.getLobbymanager().converToJSON());
                    response.setHeader("lobbies", gameServer.getLobbymanager().converToJSON());
                    break;
                default:

                    //TODO:Return Erorr
                    break;
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLobbies(HttpServletResponse response) {

        response.setContentType("text/html");

        // Hello
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println("<html><body>");
            for(Lobby lobby: gameServer.getLobbymanager().getLobbies()){
                out.println("<ul>");
                out.println("<li>"+lobby.getOwner()+" Lobby </li>");
                    out.println("<ul>");
                    for(Player player: lobby.getPlayers()){
                        out.println("<li>"+player.getPlayerName()+"</li>");
                    }
                    out.println("</ul>");
                out.println("</ul>");
            }
            out.println("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void destroy() {
    }

}
