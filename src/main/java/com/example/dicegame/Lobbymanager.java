package com.example.dicegame;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

public class Lobbymanager {
    private ArrayList<Lobby> lobbies = new ArrayList<>();

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public void setLobbies(ArrayList<Lobby> lobbies) {
        this.lobbies = lobbies;
    }

    public boolean isPlayerinLobby(String username) {
        for (Lobby lobby : lobbies) {
            for (Player player : lobby.getPlayers()) {
                if (player.getPlayerName().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasPlayerCreatedLobby(String username) {
        for (Lobby lobby : lobbies) {
            if (lobby.getOwner().getPlayerName().equals(username)) {
                return true;
            }
        }
        return false;
    }


    public void addPlayerToLobby(Player player, UUID lobbyId) {
        try {
            Lobby lobby = getLobby(lobbyId);
            lobby.addPlayer(player);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void addUserToLobby(String userName, UUID lobbyId) {
        addPlayerToLobby(new Player(userName), lobbyId);
    }

    public void removePlayerFromLobby(String userName, String lobbyId) {
        Lobby lobby = getLobby(UUID.fromString(lobbyId));
        for (Player pl : lobby.getPlayers()) {
            if (pl.equals(userName)) {
                lobby.removePlayer(pl);
                return;
            }
        }
        throw new NoSuchElementException("No lobby with id = " + lobbyId);
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

    public UUID createLobby(String username) {
        //  lobbies.add(new Lobby(username));
        Lobby lobby = new Lobby(new Player(username));
        lobbies.add(lobby);
        return lobby.getId();
    }

    public Lobby getLobby(UUID id) throws NoSuchElementException {
        for (Lobby lobby : lobbies) {
            if (lobby.getId().equals(id)) {
                return lobby;
            }
        }
        throw new NoSuchElementException("No lobby with id = " + id);
    }


}
