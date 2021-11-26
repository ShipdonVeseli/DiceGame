package com.example.dicegame;

public class Player {
    private String playername;

    public Player(String username){
        playername = username;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    @Override
    public String toString() {
        return playername+" ";
    }


}
