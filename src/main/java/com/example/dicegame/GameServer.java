package com.example.dicegame;

import java.util.ArrayList;

public  class GameServer {

    private GameServer(){
    }

    private static GameServer gameServer;

    public static GameServer getInstance(){
        if(gameServer==null){
            gameServer=new GameServer();
        }
        return gameServer;
    }

    private   ArrayList<Lobby> lobbies = new ArrayList<>();

    public  ArrayList<Lobby> getLobbies(){
        return lobbies;
    }

    public  void createLobby(String username){
      //  lobbies.add(new Lobby(username));
        Player player=new Player(username);
        lobbies.add(new Lobby(player));




    }

    public  void setLobbies(Lobby lb){
        lobbies.add(lb);
    }






}
