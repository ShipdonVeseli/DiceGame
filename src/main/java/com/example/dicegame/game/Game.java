package com.example.dicegame.game;

import com.example.dicegame.Lobby;
import com.example.dicegame.Player;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Game {
    private int gameMode;
    private Lobby lobby;
    private ArrayList<Resource> storage=new ArrayList<>();

    public Game(Lobby lobby) {
        this.lobby = lobby;
    }

    public Game(int gameMode, Lobby lobby) {
        this.gameMode = gameMode;
        this.lobby = lobby;
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

    private void addNewResources(){
        Player firstPlayer=lobby.getPlayer(0);
        int numberOFNewResource=firstPlayer.getSummOfDiceValues();

        for (int i=0;i<numberOFNewResource;i++){
            Resource resource =new Resource();
            resource.setBlueResource(true);

            firstPlayer.addResource(resource);
        }
    }

    private void moveResourcesToStorrage(){
        Player lastPlayer=lobby.getPlayer(lobby.playerCount()-1);
        ArrayList<Resource> resourcesFromLastPlayer= lastPlayer.getResources();

        storage.addAll(resourcesFromLastPlayer);

        lastPlayer.emptyResources();
    }

    public void moveResources(){



    }






}
