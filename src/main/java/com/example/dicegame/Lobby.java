package com.example.dicegame;

import com.example.dicegame.game.Dice;
import com.example.dicegame.game.Game;
import com.example.dicegame.game.Resource;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

public class Lobby {
    private UUID id = UUID.randomUUID();
    private ArrayList<Player> players = new ArrayList<>();
    private Player owner;
    private Game game;
    private boolean hasGameStarted = false;

    public Lobby(String username) {
        this.owner = new Player(username);
        players.add(owner);
        game = new Game(this);
        owner.setGame(game);
    }

    public Lobby(Player owner) {
        players.add(owner);
        this.owner = owner;
        game = new Game(this);
        owner.setGame(game);
    }

    public void startGame() {
        hasGameStarted = true;
        players.forEach(Player::resetTimer);
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
        player.setGame(game);
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

    public int getNumberOfAllResourcesFromAllPlayers() {
        final int[] result = {0};
        players.forEach(e -> result[0] += e.getResources().size());
        return result[0];
    }

    public void increaseTimeInSystemInOllPlayerResources() {
        players.forEach(e -> e.getResources().forEach(Resource::increaseTimeInSystem));
    }

    public String convertToJSON() {
        String result = "[{";
        result += "\"id\": " + "\"" + id + "\"" + ",";

        result += "\"hasGameStarted\": " + hasGameStarted + ",";

        result += "\"players\": [";

        for (int i = 0; i < players.size(); i++) {
            result += players.get(i).convertToJSON();
            if (i < players.size() - 1) {
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
