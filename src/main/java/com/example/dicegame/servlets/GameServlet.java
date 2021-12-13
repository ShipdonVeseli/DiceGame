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

@WebServlet(name = "GameServlet", value = "/Game-servlet")
public class GameServlet extends HttpServlet {
    private GameServer gameServer = GameServer.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        gameFunctions(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        gameFunctions(request, response);
    }

    private void gameFunctions(HttpServletRequest request, HttpServletResponse response){
       // String mode = request.getParameter("mode");

        try {

            Map<String, String[]> map = request.getParameterMap();

            ServletFunctions.printNames(map);

            String mode =   ServletFunctions.getParameterValue(map, "mode");

            String username =   ServletFunctions.getParameterValue(map, "username");

            UUID lobbyIdOfTheGame;
            Lobby lobbyOfTheGame;
            switch (mode) {
                case "make-move":

                    break;

                case "get-move":

                    break;

                case "start-game":
                    lobbyIdOfTheGame = UUID.fromString(  ServletFunctions.getParameterValue(map, "lobbyID"));
                    lobbyOfTheGame=gameServer.getLobbymanager().getLobby(lobbyIdOfTheGame);
                    lobbyOfTheGame.startGame();
                    response.setHeader("isStarted", "true");

                    break;

                case "has-Game-started":
                    lobbyIdOfTheGame = UUID.fromString(  ServletFunctions.getParameterValue(map, "lobbyID"));
                    lobbyOfTheGame=gameServer.getLobbymanager().getLobby(lobbyIdOfTheGame);
                    Boolean hasGameStared= lobbyOfTheGame.isHasGameStarted();
                    response.setHeader("isStarted", hasGameStared.toString());

                    break;


                default:

                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
