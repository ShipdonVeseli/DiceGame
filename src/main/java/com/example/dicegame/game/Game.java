package com.example.dicegame.game;

import com.example.dicegame.Lobby;
import com.example.dicegame.Player;
import com.example.dicegame.gameSatistic.StatisticSuspect;
import com.example.dicegame.gameSatistic.Statistics;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Game extends StatisticSuspect {
    private int gameMode = 1;
    private int round = 0;
    private int activePlayerIndex = 0;
    private int gameLength = 19;
    private boolean dicesAlreadyRolled = false;
    private int numberOfPlayers = 10;

    private Lobby lobby;

    private Statistics statistics = new Statistics();

    private ArrayList<Resource> storage = new ArrayList<>();

    public Game(Lobby lobby) {
        this.lobby = lobby;
        init();
    }

    public Game(int gameMode, Lobby lobby) {
        this.gameMode = gameMode;
        this.lobby = lobby;
        //init();
    }


    public void init() {
        addObserver(statistics);
        lobby.getPlayers().forEach(e -> {
            e.addObserver(statistics);
        });
        addStartResources();
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getGameLength() {
        return gameLength;
    }

    public void setGameLength(int gameLength) {
        this.gameLength = gameLength;
    }

    public boolean checkIfGameHasNotEnded() {
        return round <= gameLength;
    }

    private void addStartResources() {
        for (int i = 0; i < lobby.getPlayers().size() - 1; i++) {
            Player player = lobby.getPlayer(i);
            player.addResource(new Resource(false));
            player.addResource(new Resource(false));
            player.addResource(new Resource(false));
            player.addResource(new Resource(false));
        }
    }

    public void reset() {
        round = 0;
        activePlayerIndex = 0;
        dicesAlreadyRolled = false;
        storage = new ArrayList<>();

        statistics.reset();
        lobby.getPlayers().forEach(e -> {
            e.reset();
            e.setHasRolledDices(false);
        });

        addStartResources();
    }

    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public boolean checkIfPlayerIsActivePlayer(String playerName) {
        try {
            Player player = lobby.getPlayer(playerName);
            return lobby.getPlayer(activePlayerIndex).equals(player);
        } catch (Exception e) {
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

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public int getRound() {
        return round;
    }

    public ArrayList<Resource> getStorage() {
        return storage;
    }

    public void rollDicesFromOnePlayer(String userName) throws NoSuchElementException {
        Player player = lobby.getPlayer(userName);
        if (!dicesAlreadyRolled && !player.isHasRolledDices()) {

            player.rollAllDices();
            dicesAlreadyRolled = true;
        }
    }

    public void rollAllDiceInGame() {
        if (!dicesAlreadyRolled) {
            lobby.getPlayers().forEach(e -> {
                if (!e.isHasRolledDices()) {
                    e.rollAllDices();
                }
                dicesAlreadyRolled = true;
            });
        }
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

    public void giveDiceToOtherPlayer(String playerNameSender, String playerNameReceiver) throws
            IllegalStateException {
        if (gameMode == 2) {
            Player sender = lobby.getPlayer(playerNameSender);
            Player receiver = lobby.getPlayer(playerNameReceiver);

            Dice dice = sender.getDice(0);

            receiver.addDice(dice);
            sender.removeDice(dice);
        } else {
            throw new IllegalStateException("this Operation is not allowed in Game mode" + gameMode);
        }
    }

    public void setDiceRangeFromPlayer(String username, int min, int max) throws IllegalStateException {
        if (gameMode == 3 || gameMode == 6 || gameMode == 5) {
            Player user = lobby.getPlayer(username);
            user.setDiceRanges(min, max);
        } else {
            throw new IllegalStateException("this Operation is not allowed in Game mode" + gameMode);
        }
    }

    protected void moveResourcesToStorage() {
        Player lastPlayer = lobby.getPlayer(lobby.playerCount() - 1);
        int amount = lastPlayer.getResources().size();
        ArrayList<Resource> resourcesFromLastPlayer = lastPlayer.getResources(amount);

        storage.addAll(resourcesFromLastPlayer);

        lastPlayer.removeResources(amount);
    }

    protected void moveResources() {
        for (int i = lobby.playerCount() - 1; i >= 1; i--) {
            Player playerReceiver = lobby.getPlayer(i);
            Player playerSend = lobby.getPlayer(i - 1);

            int amount = playerReceiver.getSummOfDiceValues();

            ArrayList<Resource> resources = playerSend.getResources(amount);
            playerReceiver.addResources(resources);

            playerSend.removeResources(amount);

            playerSend.addedResources(amount);
        }
    }

    public void move() {
        if (round <= gameLength) {
            round++;

            moveResources();
            moveResourcesToStorage();
            addNewResources();

            changeActivePlayer();
            resetPlayerForTheNextRound();
            statisticValuesSaving();
        }
    }

    private void resetPlayerForTheNextRound(){
        lobby.getPlayers().forEach(e->{
            e.setHasRolledDices(false);
        });
    }

    private void changeActivePlayer() {
        if (activePlayerIndex < lobby.playerCount() - 1) {
            activePlayerIndex++;
        } else {

            activePlayerIndex = 0;
        }
        dicesAlreadyRolled = false;
    }

    private void statisticValuesSaving() {
        lobby.increaseTimeInSystemInOllPlayerResources();
        saveNumberInStorage(storage.size());
        saveNumberInGame(lobby.getNumberOfAllResourcesFromAllPlayers());
        lobby.getPlayers().forEach(e -> e.saveMovedResources());

        storage.forEach(e -> {
            if (!e.isAlreadySaved()) {
                e.setAlreadySaved(true);
                saveRessourceInStorage(e.getTimeInSystem());
            }

        });
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

    public String convertToJSON2() {
        String result = "[";
        result += "{\"gameMode\": " + gameMode + ",";
        result += "\"lobbyid\": \"" + lobby.getId() + "\",";
        result += "\"round\": " + round + ",";
        result += "\"activePlayerIndex\": " + activePlayerIndex + ",";
        result += "\"storage\": " + storage.size() + "},";
        for (int i = 0; i < lobby.playerCount(); i++) {
            result += "{\"playername\": \"" + lobby.getPlayer(i).getPlayerName() + "\",";
            result += "\"dicevalue\": " + lobby.getPlayer(i).getSummOfDiceValues() + ",";
            if (i != lobby.playerCount() - 1) {
                result += "\"blueresources\": " + lobby.getPlayer(i).getBlueResources() + ",";
                result += "\"normalresources\": " + lobby.getPlayer(i).getNormalResources() + "}";
                result += ",";
            } else {
                int blueresource = 0;
                for (Resource st : storage) {
                    if (st.isBlueResource()) blueresource++;
                }
                int normalresource = storage.size() - blueresource;
                result += "\"blueresources\": " + blueresource + ",";
                result += "\"normalresources\": " + normalresource + "}";
            }
        }
        result += "]";
        return result;
    }

    public static String printResourcesJson(String result, ArrayList<Resource> storage) {
        for (int i = 0; i < storage.size(); i++) {
            result += storage.get(i).convertToJSON();
            if (i < storage.size() - 1) {
                result += ",";
            }
        }
        result += "]";

        return result;
    }

}
