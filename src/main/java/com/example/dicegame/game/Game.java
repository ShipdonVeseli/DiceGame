package com.example.dicegame.game;

import com.example.dicegame.Lobby;

import java.util.ArrayList;

public class Game {
    private Lobby lobby;
    private ArrayList<Resources> storage=new ArrayList<>();

    public Game(Lobby lobby) {
        this.lobby = lobby;
    }

    public void rollAllDiceInGame(){
        lobby.getPlayers().forEach(e->e.rollAllDices());
    }

    public void moveResources(){






    }






}
