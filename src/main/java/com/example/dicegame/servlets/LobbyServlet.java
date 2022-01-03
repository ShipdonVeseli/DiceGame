package com.example.dicegame.servlets;

import com.example.dicegame.GameServer;
import com.example.dicegame.Lobby;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
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
            Map<String, String[]> map = request.getParameterMap();

            ServletFunctions.printNames(map);

            String mode = ServletFunctions.getParameterValue(map, "mode");
            String username = ServletFunctions.getParameterValue(map, "username");

            switch (mode) {
                case "join":
                    join(response, map, username);
                    break;

                case "create":
                    create(response, username);
                    break;

                case "leave":
                    leave(map, username);
                    break;

                case "get-Lobbies":
                    getLobbies(response);
                    break;

                case "get-lobby-id":
                    getLobbyId(response, username);
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void getLobbyId(HttpServletResponse response, String username) {
        Lobby lobby = gameServer.getLobbymanager().getLobbyByUsername(username);
        response.setHeader("lobbyid", lobby.getId().toString());
    }

    private void getLobbies(HttpServletResponse response) {
        System.out.println(gameServer.getLobbymanager().convertToJSON());
        response.setHeader("lobbies", gameServer.getLobbymanager().convertToJSON());
    }

    private void leave(Map<String, String[]> map, String username) {
        try {
            String IDofLobby = (ServletFunctions.getParameterValue(map, "lobbyID"));//request.getParameter("lobbyID"));
            Lobby lobby = gameServer.getLobbymanager().removePlayerFromLobby2(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void create(HttpServletResponse response, String username) {
        try {
            if (!gameServer.getLobbymanager().isPlayerinLobby(username)) {
                String id = gameServer.getLobbymanager().createLobby(username).toString();


                response.setHeader("lobbyID", id);
            } else {
                gameServer.getLobbymanager().removePlayerFromLobby2(username);

                String id = gameServer.getLobbymanager().createLobby(username).toString();
                response.setHeader("lobbyID", id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void join(HttpServletResponse response, Map<String, String[]> map, String username) {
        UUID lobbyID = UUID.fromString(ServletFunctions.getParameterValue(map, "lobbyID"));
        try {
            if (!gameServer.getLobbymanager().isPlayerinLobby(username)) {
                gameServer.getLobbymanager().addUserToLobby(username, lobbyID);
            } else {
                gameServer.getLobbymanager().removePlayerFromLobby2(username);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }

}
