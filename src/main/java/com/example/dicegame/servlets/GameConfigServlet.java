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

@WebServlet(name = "GameConfigServlet", value = "/Game-Config-servlet")
public class GameConfigServlet extends HttpServlet {

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

            //GameServlet.resetPlayer(lobbyOfTheGame,username);
            switch (mode) {
                case "set_Number_of_Players":
                    setNumberOfPlayers(map, game, response);
                    break;

                case "get_Number_of_Players":
                    getNumberOfPlayers(response, game);
                    break;

                case "set-Game-Mode":
                    setGameMode(map, game);
                    break;

                case "get-Game-mode":
                    getGameMode(response, game);
                    break;

                case "setDice":
                    setDice(map, username, response, game);
                    break;

                case "set-game-length":
                    setGameLength(map, game);
                    break;

                case "get-game-length":
                    getGameLength(response, game);
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getGameLength(HttpServletResponse response, Game game) {
        response.setHeader("get-game-length", String.valueOf(game.getGameLength()));
    }

    private void setGameLength(Map<String, String[]> map, Game game) {
        int gameLength = Integer.parseInt(ServletFunctions.getParameterValue(map, "gameLength"));
        game.setGameLength(gameLength);
    }

    private void getNumberOfPlayers(HttpServletResponse response, Game game) {
        int numberOfPlayers = game.getNumberOfPlayers();
        response.setHeader("Number_of_Players", String.valueOf(numberOfPlayers));
    }

    private void setNumberOfPlayers(Map<String, String[]> map, Game game, HttpServletResponse response) {
        if (!game.getLobby().isHasGameStarted()) {
            int numberOfPlayers = Integer.parseInt(ServletFunctions.getParameterValue(map, "Number_of_Players"));
            game.setNumberOfPlayers(numberOfPlayers);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void getGameMode(HttpServletResponse response, Game game) {
        int gameMode = game.getGameMode();
        response.setHeader("gameMode", String.valueOf(gameMode));
    }

    private void setGameMode(Map<String, String[]> map, Game game) {
        int gameMode = Integer.parseInt(ServletFunctions.getParameterValue(map, "game-mode"));
        game.setGameMode(gameMode);
    }

    private void setDice(Map<String, String[]> map, String username, HttpServletResponse response, Game game) {
        try {
            int min = Integer.parseInt(ServletFunctions.getParameterValue(map, "min"));
            int max = Integer.parseInt(ServletFunctions.getParameterValue(map, "max"));

            game.setDiceRangeFromPlayer(username, min, max);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
