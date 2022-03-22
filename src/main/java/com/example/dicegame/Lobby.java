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

    public void autoTerminate() {
        if (checkIfAllPlayerAreAI()) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n Terminate");
            //   terminate();
        }
    }

    public void terminate() {
        GameServer.getInstance().getLobbymanager().removeLobby(this.getId());
    }

    public boolean checkIfAllPlayerAreAI() {
        for (Player player : players) {
            if (!player.isAI()) {
                return false;
            }
        }
        return true;
    }

    public void startGame() {
        fillLobby();

        hasGameStarted = true;
        players.forEach(Player::resetTimer);
        game.init();

        if(game.getGameMode()==4){
            game.initGameMode4();
        }
    }

    private void fillLobby() {
        int numberOfPlayers = game.getNumberOfPlayers();
        int playerCount = playerCount();

        for (int i = 0; i < (numberOfPlayers - playerCount); i++) {
            Player player = new Player("AINumber" + (i + 1));
            player.setAI(true);
            addPlayer(player);
        }

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

    public Player getWeakestPlayer() {
        final Player[] weakest = {players.get(0)};
        players.forEach(e -> {
            Dice eDice = e.getDice(0);
            Dice weakestDice = weakest[0].getDice(0);

            if (eDice.getExpectedValue() < weakestDice.getExpectedValue()) {
                weakest[0] = e;
            }
        });
        return weakest[0];
    }

    public int getIndexFromPlayer(Player player)throws NoSuchElementException {
        for (int i = 0; i <players.size() ; i++) {
            if(players.equals(player)){
                return i;
            }
        }
        throw new NoSuchElementException();
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
        return "\nLobby{" +
                "id=" + id +
                ", players=" + players +
                ", owner=" + owner +
                ", game=" + game +
                ", hasGameStarted=" + hasGameStarted +
                '}';
    }

    public String getVotesInJson(){
        String result = "[{";

        result += "\"players\": [";

        for (int i = 0; i < players.size(); i++) {
            result += players.get(i).getVotesInJson();
            if (i < players.size() - 1) {
                result += ",";
            }
        }
        result += "],";
        result += "}]";
        return result;
    }
}
