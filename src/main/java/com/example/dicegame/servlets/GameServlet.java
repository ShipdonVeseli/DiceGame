package com.example.dicegame.servlets;

import com.example.dicegame.GameServer;
import com.example.dicegame.Lobby;
import com.example.dicegame.Player;
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

    public static void resetPlayer(Lobby lobby, String playerName) {
        Player player = lobby.getPlayer(playerName);
        player.setAI(false);
        player.resetTimer();

        if(lobby.getTerminationTimer()!=null){
            lobby.resetTerminationTimer();
        }
    }

    private void gameFunctions(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> map = request.getParameterMap();

         //   ServletFunctions.printNames(map);

            String mode = ServletFunctions.getParameterValue(map, "mode");
            String username = ServletFunctions.getParameterValue(map, "username");
            UUID lobbyIdOfTheGame = UUID.fromString(ServletFunctions.getParameterValue(map, "lobbyID"));
            Lobby lobbyOfTheGame = gameServer.getLobbymanager().getLobby(lobbyIdOfTheGame);
            Game game = lobbyOfTheGame.getGame();


            switch (mode) {
                case "get-weakest-Link":
                    getWeackestLink(response, lobbyOfTheGame, game);
                    break;
                case "vote":
                    vote(response, map, username, game);
                    GameServlet.resetPlayer(lobbyOfTheGame, username);
                    break;

                case "get-Voting-History":
                    getVotingHistory(response, lobbyOfTheGame);
                    break;

                case "roll-me":
                    rollMe(username, game);
                    GameServlet.resetPlayer(lobbyOfTheGame, username);
                    break;

                case "roll-all": //rolls all dices from all Players
                    rollAll(response, username, game);

                    GameServlet.resetPlayer(lobbyOfTheGame, username);
                    break;

                case "status":
                    status(response, game);
                    break;

                case "make-move":
                    makeMove(response, username, game);

                    GameServlet.resetPlayer(lobbyOfTheGame, username);
                    break;

                case "get-Active-Player":
                    getActivePlayers(response, lobbyOfTheGame, game);
                    break;

                case "get-Round":
                    getRound(response, game);
                    break;

                case "give-dice":
                    giveDice(map, username, game, response);

                    //   GameServlet.resetPlayer(lobbyOfTheGame,username);
                    break;

                case "start-game":
                    startGame(response, lobbyOfTheGame);
                    break;

                case "leave-game":
                    leaveGame(username, lobbyOfTheGame);
                    break;

                case "has-Game-started":
                    hasGameStarted(response, lobbyOfTheGame);
                    break;

                case "get-Activity":
                    getActivity(response, game);

                    // GameServlet.resetPlayer(lobbyOfTheGame,username);
                    break;

                case "get-Throughput":
                    getThroughput(response, game);

                    //  GameServlet.resetPlayer(lobbyOfTheGame,username);
                    break;

                case "get-Number-in-System":
                    getNumberInSystem(response, game);

                    // GameServlet.resetPlayer(lobbyOfTheGame,username);
                    break;

                case "get-Time-in-System":
                    getTimeInSystem(response, game);

                    // GameServlet.resetPlayer(lobbyOfTheGame,username);
                    break;

                case "reset":
                    reset(game);

                    GameServlet.resetPlayer(lobbyOfTheGame, username);
                    break;

                case "request-Dice-form-Ai":
                    requestDiceFormAi(game,response,map,username,lobbyOfTheGame);

                    GameServlet.resetPlayer(lobbyOfTheGame, username);
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void requestDiceFormAi(Game game, HttpServletResponse response, Map<String, String[]> map, String username, Lobby lobby) {
        try {
            if (game.getGameMode() == 2) {
                String aiName = ServletFunctions.getParameterValue(map, "ai_Player_Name");
                Player ai = lobby.getPlayer(aiName);

                if(ai.isAI()){
                    game.giveDiceToOtherPlayer(aiName,username);

                }else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void getVotingHistory(HttpServletResponse response, Lobby lobbyOfTheGame) {
        response.setHeader("getVotingHistory", lobbyOfTheGame.getVotesInJson());
    }

    private void getWeackestLink(HttpServletResponse response, Lobby lobbyOfTheGame, Game game) {
        try {
            Player weakest = game.getWeakestLink();

            response.setHeader("weakest-Link", weakest.getPlayerName());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void vote(HttpServletResponse response, Map<String, String[]> map, String username, Game game) {
        try {
            String NameOfWeakestLink = ServletFunctions.getParameterValue(map, "PlayerNameWeakestLink");
            int round = Integer.parseInt(ServletFunctions.getParameterValue(map, "round"));
            game.voteForPlayer(username, NameOfWeakestLink, round);
        } catch (IllegalStateException illegalStateException) {
            illegalStateException.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void leaveGame(String username, Lobby lobbyOfTheGame) {
        Player player = lobbyOfTheGame.getPlayer(username);
        player.setPlayerName(player.getPlayerName() + "_KI");
        player.setAI(true);
    }

    private void rollMe(String username, Game game) {
        game.rollDicesFromOnePlayer(username);
    }

    private void giveDice(Map<String, String[]> map, String username, Game game, HttpServletResponse response) {
        String playerNameReceiver = ServletFunctions.getParameterValue(map, "playerNameReceiver");
        try {
            game.giveDiceToOtherPlayer(username, playerNameReceiver);
        } catch (IllegalStateException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void reset(Game game) {
        game.reset();
    }

    private void getTimeInSystem(HttpServletResponse response, Game game) {
        response.setHeader("Time-in-System", game.getStatistics().getTimeInSystem2());
    }

    private void getNumberInSystem(HttpServletResponse response, Game game) {
        response.setHeader("Number-in-System", game.getStatistics().getNumberInSystem2());
    }

    private void getThroughput(HttpServletResponse response, Game game) {
        response.setHeader("Throughput", game.getStatistics().getThroughput2());
    }

    private void getActivity(HttpServletResponse response, Game game) {
        // System.out.println(game.getStatistics().getActivity2());
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
        try {
            response.setHeader("gameStatus", game.convertToJSON2());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_GONE);
        }
    }

    private void makeMove(HttpServletResponse response, String username, Game game) {
        if (game.checkIfPlayerIsActivePlayer(username)) {
            if (game.checkIfGameHasNotEnded()) {
                game.move();
                return;
            }
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void rollAll(HttpServletResponse response, String username, Game game) {
        if (game.checkIfPlayerIsActivePlayer(username)) {
            if (game.checkIfGameHasNotEnded()) {
                game.rollAllDiceInGame();
                return;
            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

