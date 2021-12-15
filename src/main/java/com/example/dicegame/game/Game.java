package com.example.dicegame.game;

import com.example.dicegame.Lobby;
import com.example.dicegame.Player;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Game {
    private int gameMode;
    private Lobby lobby;
    private ArrayList<Resource> storage=new ArrayList<>();
    private int round=0;

    public Game(Lobby lobby) {
        this.lobby = lobby;
    }

    public Game(int gameMode, Lobby lobby) {
        this.gameMode = gameMode;
        this.lobby = lobby;
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
        for (Player playerInLobby:lobby.getPlayers()             ) {
            if(userName.equals(playerInLobby)){
                playerInLobby.rollAllDices();
                return;
            }
        }
        throw new NoSuchElementException("No Player with Username= "+userName);
    }

    public void rollAllDiceInGame(){
        lobby.getPlayers().forEach(e->e.rollAllDices());
    }

    //TODO test
    protected void addNewResources(){
        Player firstPlayer=lobby.getPlayer(0);
        int numberOFNewResource=firstPlayer.getSummOfDiceValues();

        for (int i=0;i<numberOFNewResource;i++){
            Resource resource =new Resource();
            resource.setBlueResource(true);

            firstPlayer.addResource(resource);
        }
    }

    //TODO test
    protected void moveResourcesToStorage(){
        Player lastPlayer=lobby.getPlayer(lobby.playerCount()-1);
        int amount=lastPlayer.getSummOfDiceValues();
        ArrayList<Resource> resourcesFromLastPlayer= lastPlayer.getResources(amount);

        storage.addAll(resourcesFromLastPlayer);

        lastPlayer.removeResources(amount);
    }

    //TODO test
    public void moveResources(){
        round++;
        moveResourcesToStorage();
        for (int i=lobby.playerCount()-1;i>=1;i--){
            Player playerReciver=lobby.getPlayer(i);
            Player playerSend=lobby.getPlayer(i--);

            int amount=playerSend.getSummOfDiceValues();

            ArrayList<Resource> resources=playerSend.getResources(amount);
            playerReciver.addResources(resources);

            playerSend.removeResources(amount);
        }
        addNewResources();
    }






}
