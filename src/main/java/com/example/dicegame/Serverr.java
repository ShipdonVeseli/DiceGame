package com.example.dicegame;

import java.util.ArrayList;

public final class Serverr {
    private Serverr(){
    }

    public static ArrayList<Lobby> lobbies = new ArrayList<>();

    public static ArrayList<Lobby> getLobbies(){
        return lobbies;
    }

    public static void createLobby(String username){
        lobbies.add(new Lobby(username));
    }
    public static void setLobbies(Lobby lb){
        lobbies.add(lb);
    }
}
