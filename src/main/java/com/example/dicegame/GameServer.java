package com.example.dicegame;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

public class GameServer {

    private GameServer() {
    }

    private static GameServer gameServer;

    public static GameServer getInstance() {
        if (gameServer == null) {
            gameServer = new GameServer();
        }
        return gameServer;
    }

    private ArrayList<Lobby> lobbies = new ArrayList<>();

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public UUID createLobby(String username) {
        //  lobbies.add(new Lobby(username));
        Player player = new Player(username);
        Lobby lobby=new Lobby(player);
        lobbies.add(lobby);
        return lobby.getId();
    }

    public Lobby getLobby(UUID id) throws NoSuchElementException {
        for (Lobby lobby : lobbies) {
            if (lobby.getId().equals( id)) {
                return lobby;
            }
        }
        throw new NoSuchElementException("No lobby with id = " + id);
    }

    public void addPlayerToLobby(Player player, UUID lobbyid) {
        try {
            Lobby lobby = getLobby(lobbyid);
            lobby.addPlayer(player);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void addUserToLobby(String user, String lobbyid) {
        try {

            Lobby lobby = getLobby(UUID.fromString(lobbyid));
            lobby.addPlayer(new Player(user));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void exitUserFromLobby(String user, String lobbyid) {

        Lobby lobby = getLobby(UUID.fromString(lobbyid));
        Player player = new Player(user);
        lobby.removePlayer(player);
    }

    public void removeLobby(UUID id) {
        for (Lobby lobby : lobbies) {
            if (lobby.getId().equals(id)) {
                lobbies.remove(lobby);
                return;
            }
        }
        throw new NoSuchElementException("No lobby with id = " + id);
    }



    public void setLobbies(Lobby lb) {
        lobbies.add(lb);
    }

    public boolean isPlayerinLobby(String username) {
        for (Lobby lobby : lobbies) {
            for (Player player : lobby.getPlayers()) {
                if (player.getPlayername().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasPlayerCreatedLobby(String username) {
        for(Lobby lobby : lobbies) {
            if(lobby.getOwner().getPlayername().equals(username)) {
                return true;
            }
        }
        return false;
    }

}
