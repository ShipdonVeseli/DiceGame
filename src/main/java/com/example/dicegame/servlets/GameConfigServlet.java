package com.example.dicegame.servlets;

import com.example.dicegame.GameServer;
import com.example.dicegame.Lobby;
import com.example.dicegame.game.Game;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class GameConfigServlet {

    private GameServer gameServer = GameServer.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        gameFunctions(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        gameFunctions(request, response);
    }

    private void gameFunctions(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> map = request.getParameterMap();

            ServletFunctions.printNames(map);

            String mode = ServletFunctions.getParameterValue(map, "mode");
            String username = ServletFunctions.getParameterValue(map, "username");

            UUID lobbyIdOfTheGame = UUID.fromString(ServletFunctions.getParameterValue(map, "lobbyID"));
            Lobby lobbyOfTheGame = gameServer.getLobbymanager().getLobby(lobbyIdOfTheGame);
            Game game = lobbyOfTheGame.getGame();
            switch (mode) {
                case "set_Number_of_Players":
                    setNumberOfPlayers(map, game);
                    break;

                case "get_Number_of_Players":
                    getNumberOfPlayers(response, game);
                    break;





                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getNumberOfPlayers(HttpServletResponse response, Game game) {
        int numberOfPlayers= game.getNumberOfPlayers();
        response.setHeader("Number_of_Players", String.valueOf(numberOfPlayers));
    }

    private void setNumberOfPlayers(Map<String, String[]> map, Game game) {
        int numberOfPlayers = Integer.parseInt(ServletFunctions.getParameterValue(map, "Number_of_Players"));
        game.setNumberOfPlayers(numberOfPlayers);
    }


}
