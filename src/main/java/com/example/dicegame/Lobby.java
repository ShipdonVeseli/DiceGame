package com.example.dicegame;

import com.example.dicegame.game.Dice;
import com.example.dicegame.game.Game;
import com.example.dicegame.game.Resource;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

public class Lobby {
    private static int lobbyCount;

    private UUID id = UUID.randomUUID();
    private ArrayList<Player> players = new ArrayList<>();
    private Player owner;
    private Game game;
    private boolean hasGameStarted = false;

    public Lobby(String username) {
        this.owner = new Player(username);
        players.add(owner);
        lobbyCount++;
    }

    public Lobby(Player owner) {
        players.add(owner);
        lobbyCount++;
        this.owner = owner;
    }

    public void startGame() {
        hasGameStarted = true;
        game = new Game(this);
    }

    public boolean isHasGameStarted() {
        return hasGameStarted;
    }

    public boolean isPlayerInThatLobby(String username) {
        for (Player player : players) {
            if (player.getPlayerName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getOwner() {
        return owner;
    }

    public UUID getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public Player getPlayer(String playerName) throws NoSuchElementException {
        if (isPlayerInThatLobby(playerName)) {
            for (Player player : players) {
                if (player.getPlayerName().equals(playerName)) {
                    return player;
                }
            }
        }
        throw new NoSuchElementException("No Player with Name= " + playerName);
    }

    public int playerCount() {
        return players.size();
    }

    public String convertToJSON() {
        String result = "[{";
        result += "\"id\": " +"\""+ id + "\""+",";
        //result += "\"owner\": " + owner.convertToJSON() + ",";
        result += "\"hasGameStarted\": " + hasGameStarted + ",";

        result += "\"players\": [";
        for (Player player : players) {
            result += player.convertToJSON();
            if (players.size() > 1) {
                result += ",";
            }
        }
        result += "]";

        result += "}]";
        return result;
    }

    @Override
    public String toString() {
        return "Lobby{" +
                "id=" + id +
                ", players=" + players +
                ", owner=" + owner +
                ", game=" + game +
                ", hasGameStarted=" + hasGameStarted +
                '}';
    }
}
