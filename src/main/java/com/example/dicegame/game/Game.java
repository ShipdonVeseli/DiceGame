package com.example.dicegame.game;

import com.example.dicegame.Lobby;
import com.example.dicegame.Player;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Game {
    private int gameMode = 1;
    private Lobby lobby;
    private ArrayList<Resource> storage = new ArrayList<>();
    private int round = 0;
    private int activePlayerIndex = 0;
    private Statistics statistics=new Statistics();

    public Game(Lobby lobby) {
        this.lobby = lobby;
    }

    public Game(int gameMode, Lobby lobby) {
        this.gameMode = gameMode;
        this.lobby = lobby;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public boolean checkIfPlayerIsActivePlayer(String playerName) {
        try {
            Player player = lobby.getPlayer(playerName);
            return lobby.getPlayer(activePlayerIndex).equals(player);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Lobby getLobby() {
        return lobby;
    }

    public int getGameMode() {
        return gameMode;
    }

    public int getRound() {
        return round;
    }

    public ArrayList<Resource> getStorage() {
        return storage;
    }

    public void rollDicesFromOnePlayer(String userName) throws NoSuchElementException {
        for (Player playerInLobby : lobby.getPlayers()) {
            if (userName.equals(playerInLobby)) {
                playerInLobby.rollAllDices();
                return;
            }
        }
        throw new NoSuchElementException("No Player with Username= " + userName);
    }

    public void rollAllDiceInGame() {
        lobby.getPlayers().forEach(e -> e.rollAllDices());
    }

    protected void addNewResources() {
        Player firstPlayer = lobby.getPlayer(0);
        int numberOFNewResource = firstPlayer.getSummOfDiceValues();

        for (int i = 0; i < numberOFNewResource; i++) {
            Resource resource = new Resource();
            resource.setBlueResource(true);

            firstPlayer.addResource(resource);
        }
    }

    protected void moveResourcesToStorage() {
        Player lastPlayer = lobby.getPlayer(lobby.playerCount() - 1);
        int amount = lastPlayer.getSummOfDiceValues();
        ArrayList<Resource> resourcesFromLastPlayer = lastPlayer.getResources(amount);

        storage.addAll(resourcesFromLastPlayer);

        lastPlayer.removeResources(amount);
    }

    public void move() {
        round++;
        moveResourcesToStorage();
        moveResources();
        addNewResources();

        if (activePlayerIndex < lobby.playerCount() - 1) {
            activePlayerIndex++;
        } else {
            activePlayerIndex = 0;
        }
    }

    protected void moveResources() {
        for (int i = lobby.playerCount() - 1; i >= 1; i--) {
            Player playerReceiver = lobby.getPlayer(i);
            Player playerSend = lobby.getPlayer(i - 1);

            int amount = playerSend.getSummOfDiceValues();

            ArrayList<Resource> resources = playerSend.getResources(amount);
            playerReceiver.addResources(resources);

            playerSend.removeResources(amount);
        }
    }


    @Override
    public String toString() {
        return "Game{" +
                "gameMode=" + gameMode +
                ", lobby=" + lobby +
                ", storage=" + storage +
                ", round=" + round +
                '}';
    }

    public String convertToJSON() {
        String result = "[{";
        result += "\"gameMode\": " + gameMode + ",";
        result += "\"lobby\": " + lobby.convertToJSON() + ",";
        result += "\"round\": " + round + ",";
        result += "\"activePlayerIndex\": " + activePlayerIndex + ",";
        result += "\"storage\": [";
        result = printResourcesJson(result, storage);
        result += "}]";
        return result;
    }

    public static String printResourcesJson(String result, ArrayList<Resource> storage) {
        for (Resource resource : storage) {
            result += resource.convertToJSON();
            if (storage.size() > 1) {
                result += ",";
            }
        }
        result += "]";

        return result;
    }
}
