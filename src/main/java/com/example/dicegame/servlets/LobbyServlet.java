package com.example.dicegame.servlets;

import com.example.dicegame.GameServer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class LobbyServlet extends HttpServlet {
    private  GameServer gameServer =GameServer.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      lobbyFunctions(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        lobbyFunctions(request,response);
    }

    private void lobbyFunctions(HttpServletRequest request, HttpServletResponse response){
        String mode= request.getParameter("mode");

        switch (mode){
            case "join":
                UUID lobbyID= UUID.fromString(request.getParameter("lobbyID"));
                String username= request.getParameter("username");
                try {
                    gameServer.getLobbymanager().addUserToLobby(username,lobbyID);
                }catch (Exception e){

                }
                break;

            case "create":

                break;

            case "leave":

                break;
        }


    }



    public void destroy() {
    }

}
