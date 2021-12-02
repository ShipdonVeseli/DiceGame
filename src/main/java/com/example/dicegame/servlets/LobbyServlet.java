package com.example.dicegame.servlets;

import com.example.dicegame.GameServer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        String mode = request.getParameter("mode");

        String username = request.getParameter("username");
        switch (mode) {
            case "join":
                UUID lobbyID = UUID.fromString(request.getParameter("lobbyID"));
                try {
                    gameServer.getLobbymanager().addUserToLobby(username, lobbyID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "create":
                try {
                    String id=gameServer.getLobbymanager().createLobby(username).toString();
                    Cookie cookie =new Cookie("lobbyID",id);

                    response.addCookie(cookie);
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
        }
    }


    public void destroy() {
    }

}