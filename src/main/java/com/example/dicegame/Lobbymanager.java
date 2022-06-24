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

    public boolean isPlayerInLobby(String username) {
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

    public Lobby removePlayer(String userName) {
        for (Lobby lobby : this.lobbies) {
            for (Player player : lobby.getPlayers()) {
                if (player.getPlayerName().equals(userName)) {
                    lobby.removePlayer(player);
                    if (lobby.getPlayers().size() == 0) removeLobby(lobby.getId());
                    return lobby;
                }
            }
        }
        throw new NoSuchElementException("No Player found with = " + userName);
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
        System.out.println("added Lobby");
        System.out.println(lobby);
        toString();
        return lobby.getId();
    }

    public Lobby getLobbyByUsername(String username) {
        String output = "";

        for (Lobby lobby : lobbies) {
            output += "\n\nLobby id=" + lobby.getId();
            for (Player player : lobby.getPlayers()) {
                output += "Player=" + player.getPlayerName();
                if (player.getPlayerName().equals(username))
                    return lobby;
            }
        }
        System.out.println("\n\n*******************\nError player not found\n" + "search=" + username + "\n" + output + "\n*****************\n\n");
        throw new NoSuchElementException("No Player with = " + username);
    }

    public Lobby getLobby(UUID id) throws NoSuchElementException {
        String output = "";
        for (Lobby lobby : lobbies) {
            output += "\n\nLobby id=" + lobby.getId();
            if (lobby.getId().equals(id)) {
                return lobby;
            }
        }
        System.out.println("\n\n*******************\nError id not found\n" + "search=" + id + "\n" + output + "\n*****************\n\n");
        throw new NoSuchElementException("No lobby with id = " + id);
    }

    @Override
    public String toString() {
        return "Lobbymanager{" +
                "lobbies=" + lobbies +
                '}';
    }

    public String convertToJSON() {
        String result = "[";
        for (int i = 0; i < getLobbies().size(); i++) {
            result += "{\"lobbyid\": \"" + lobbies.get(i).getId().toString() + "\", \"gamemode\": \"" + lobbies.get(i).getGame().getGameMode() + "\", \"gamelength\": \"" + lobbies.get(i).getGame().getGameLength() + "\", \"numberofplayers\": \"" + lobbies.get(i).getGame().getNumberOfPlayers() + "\", \"lobbyowner\": \"" + lobbies.get(i).getOwner().getPlayerName() + "\", \"players\": \"";
            for (int j = 0; j < lobbies.get(i).getPlayers().size(); j++) {
                result += lobbies.get(i).getPlayers().get(j).getPlayerName();
                if (j != lobbies.get(i).getPlayers().size() - 1) result += ",";
            }
            result += "\"}";
            if (i != getLobbies().size() - 1) result += ",";
        }
        result += "]";
        System.out.println(result);
        return result;
    }

    public boolean checkUsername(String username) {
        for (Lobby lobby : this.lobbies) {
            for (Player player : lobby.getPlayers()) {
                if (player.getPlayerName().equals(username)) {
                    lobby.removePlayer(player);
                    return true;
                }
            }
        }
        return false;
    }
}
