package com.example.dicegame.servlets;

import com.example.dicegame.GameServer;
import com.example.dicegame.Lobby;
import com.example.dicegame.game.Game;

import javax.servlet.ServletException;
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

//    public void init() throws ServletException
//    {
//
//        ServletFunctions.initFunction();
//    }

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
                    rollAll(response, username, game);
                    break;

                case "status":
                    status(response, game);
                    break;

                case "make-move":
                    makeMove(response, username, game);
                    break;

                case "get-Active-Player":
                    getActivePlayers(response, lobbyOfTheGame, game);
                    break;

                case "get-Round":
                    getRound(response, game);
                    break;

                case "start-game":
                    startGame(response, lobbyOfTheGame);
                    break;

                case "has-Game-started":
                    hasGameStarted(response, lobbyOfTheGame);
                    break;

                case "get-Activity":
                    getActivity(response, game);
                    break;

                case "get-Throughput":
                    getThroughput(response, game);
                    break;

                case "get-Number-in-System":
                    getNumberInSystem(response, game);
                    break;

                case "get-Time-in-System":
                    getTimeInSystem(response, game);
                    break;

                case "set-game-length":
                    setGameLength(map, game);
                    break;

                case "get-game-length":
                    getGameLength(response, game);
                    break;

                case "reset":
                    reset(game);
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reset(Game game) {
        game.reset();
    }

    private void getGameLength(HttpServletResponse response, Game game) {
        response.setHeader("get-game-length", String.valueOf(game.getGameLength()));
    }

    private void setGameLength(Map<String, String[]> map, Game game) {
        int gameLength = Integer.parseInt(ServletFunctions.getParameterValue(map, "gameLength"));
        game.setGameLength(gameLength);
    }

    private void getTimeInSystem(HttpServletResponse response, Game game) {
        response.setHeader("Time-in-System", game.getStatistics().getTimeInSystem());
    }

    private void getNumberInSystem(HttpServletResponse response, Game game) {
        response.setHeader("Number-in-System", game.getStatistics().getNumberInSystem());
    }

    private void getThroughput(HttpServletResponse response, Game game) {
        response.setHeader("Throughput", game.getStatistics().getThroughput());
    }

    private void getActivity(HttpServletResponse response, Game game) {
        System.out.println(game.getStatistics().getActivity2());
        response.setHeader("Activity", game.getStatistics().getActivity2());
    }

    private void hasGameStarted(HttpServletResponse response, Lobby lobbyOfTheGame) {
        Boolean hasGameStared = lobbyOfTheGame.isHasGameStarted();
        response.setHeader("isStarted", hasGameStared.toString());
    }

    private void startGame(HttpServletResponse response, Lobby lobbyOfTheGame) {
        lobbyOfTheGame.startGame();
        response.setHeader("isStarted", "true");
    }

    private void getRound(HttpServletResponse response, Game game) {
        int round = game.getRound();
        response.setHeader("getRound", String.valueOf(round));
    }

    private void getActivePlayers(HttpServletResponse response, Lobby lobbyOfTheGame, Game game) {
        try {
            int activePlayerIndex = game.getActivePlayerIndex();
            String activePlayerName = lobbyOfTheGame.getPlayer(activePlayerIndex).getPlayerName();
            response.setHeader("ActivePlayer", activePlayerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void status(HttpServletResponse response, Game game) {
        response.setHeader("gameStatus", game.convertToJSON2());
    }

    private void makeMove(HttpServletResponse response, String username, Game game) {
        if (game.checkIfPlayerIsActivePlayer(username)) {
            if(game.checkIfGameHasNotEnded()) {
                game.move();
                return;
            }
        }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void rollAll(HttpServletResponse response, String username, Game game) {
        if (game.checkIfPlayerIsActivePlayer(username)) {
            game.rollAllDiceInGame();
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
