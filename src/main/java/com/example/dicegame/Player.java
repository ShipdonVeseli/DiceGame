package com.example.dicegame;

public class Player {
    private String playername;

    private Lobby lobby;

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

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
}
