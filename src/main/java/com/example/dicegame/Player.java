package com.example.dicegame;

public class Player {
    private String playername;

    public Player(String username){
        playername = username;
    }

    public String getPlayername() {
        return playername;
    }

    @Override
    public String toString() {
        return playername+" ";
    }
}
