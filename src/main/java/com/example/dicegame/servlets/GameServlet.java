package com.example.dicegame.servlets;

import com.example.dicegame.GameServer;
import com.example.dicegame.Lobby;
import com.example.dicegame.game.Game;

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
                //rolls all dices from all Players
                case "roll-all":
                    if (game.checkIfPlayerIsActivePlayer(username)) {
                        game.rollAllDiceInGame();
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }
                    break;

                //rolls all dices from the calling Player
                case "roll-me":
                    if (game.checkIfPlayerIsActivePlayer(username)) {
                        game.rollDicesFromOnePlayer(username);
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }
                    break;

                case "status":
                    response.setHeader("gameStatus", game.convertToJSON());
                    break;

                case "make-move":
                    if (game.checkIfPlayerIsActivePlayer(username)) {
                        game.move();
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }
                    break;

                case "start-game":
                    lobbyOfTheGame.startGame();
                    response.setHeader("isStarted", "true");
                    break;

                case "has-Game-started":
                    Boolean hasGameStared = lobbyOfTheGame.isHasGameStarted();
                    response.setHeader("isStarted", hasGameStared.toString());
                    break;

                case "get-Activity":
                    //TODO
                    break;

                case "get-Throughput":
                    //TODO
                    break;

                case "get-Number-in-System":
                    //TODO
                    break;

                case "get-Time-in-System":
                    //TODO
                    break;

                case "get-Your-performance":
                    //TODO
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
