package com.example.dicegame;

public class Player {
    private String playerName;

    public Player(String username){
        playerName = username;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return playerName +" ";
    }


}
