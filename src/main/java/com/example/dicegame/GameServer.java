package com.example.dicegame;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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

    public Lobby getLobby(int id) throws NoSuchElementException {
        for (Lobby lobby:lobbies) {
            if(lobby.getId()==id){
                return lobby;
            }
        }
        throw new NoSuchElementException("No lobby with id = " + id);
    }

    public void addPlayerToLobby(Player player,int lobbyid ){
        try {
            Lobby lobby =getLobby(lobbyid);
            lobby.addPlayer(player);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void removeLobby(int id){
        for (Lobby lobby:lobbies) {
            if(lobby.getId()==id){
                lobbies.remove(lobby);
                return;
            }
        }
        throw new NoSuchElementException("No lobby with id = " + id);
    }

    public  void setLobbies(Lobby lb){
        lobbies.add(lb);
    }






}
