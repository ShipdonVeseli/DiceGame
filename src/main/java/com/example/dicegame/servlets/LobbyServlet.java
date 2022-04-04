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
import java.io.OutputStream;
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

           // ServletFunctions.printNames(map);

            String mode = ServletFunctions.getParameterValue(map, "mode");
            String username = ServletFunctions.getParameterValue(map, "username");

            switch (mode) {
                case "login":
                    login(response,username);
                    break;

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

                case "set_Token":
                    setToken(response, map, username);
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void setToken(HttpServletResponse response, Map<String, String[]> map, String username) {
        try {
            String token=ServletFunctions.getParameterValue(map, "lobbyID");

            Player player = gameServer.getLobbymanager().getLobbyByUsername(username).getPlayer(username);

            player.setToken(token);

        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void login(HttpServletResponse response, String username) {
        boolean checkIfExist=gameServer.getLobbymanager().checkUsername(username);
        String isUsernameAlreadyTaken = "isUsernameAlreadyTaken";
        response.setHeader(isUsernameAlreadyTaken, String.valueOf(checkIfExist));
    }


    private void getLobbyId(HttpServletResponse response, String username) {
        Lobby lobby = gameServer.getLobbymanager().getLobbyByUsername(username);
        response.setHeader("lobbyid", lobby.getId().toString());
    }

    private void getLobbies(HttpServletResponse response) {
        try {
            String out=gameServer.getLobbymanager().toString();
            System.out.println(out);
        }catch (Exception e) {
            e.printStackTrace();
        }
        response.setHeader("lobbies", gameServer.getLobbymanager().convertToJSON());
    }

    private void leave(Map<String, String[]> map, String username) {
        try {
            gameServer.getLobbymanager().removePlayer(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void create(HttpServletResponse response, String username) {
        try {
            if (!gameServer.getLobbymanager().isPlayerInLobby(username)) {
                String id = gameServer.getLobbymanager().createLobby(username).toString();

                response.setHeader("lobbyID", id);
            } else {
//                gameServer.getLobbymanager().removePlayerFromLobby2(username);
//
//                String id = gameServer.getLobbymanager().createLobby(username).toString();
//                response.setHeader("lobbyID", id);

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void join(HttpServletResponse response, Map<String, String[]> map, String username) {
        UUID lobbyID = UUID.fromString(ServletFunctions.getParameterValue(map, "lobbyID"));
        try {
            Lobby lobby=gameServer.getLobbymanager().getLobby(lobbyID);
            Game game=lobby.getGame();
            int numberOfPlayers=lobby.getPlayers().size();
            int maxPlayerSize=game.getNumberOfPlayers();

            if (!gameServer.getLobbymanager().isPlayerInLobby(username)) {
                if (numberOfPlayers<maxPlayerSize) {
                    gameServer.getLobbymanager().addUserToLobby(username, lobbyID);
                }else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }

}
