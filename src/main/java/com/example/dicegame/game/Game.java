package com.example.dicegame.game;

import com.example.dicegame.Lobby;
import com.example.dicegame.Player;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Game {
    private int gameMode;
    private Lobby lobby;
    private ArrayList<Resources> storage=new ArrayList<>();

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

    public void moveResources(){

    }






}
