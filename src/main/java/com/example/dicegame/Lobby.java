package com.example.dicegame;

import java.util.ArrayList;
import java.util.Random;

public class Lobby {
    private static int lobbyCount;

    private Integer id ;
    private ArrayList<Player> players = new ArrayList<>();
    private Player owner;

    public Lobby(String username){
        players.add(new Player(username));
        this.id = Lobby.lobbyCount;
        lobbyCount++;
    }

    public Lobby( Player owner) {
        this.id = Lobby.lobbyCount;
        players.add(owner);
        lobbyCount++;
        this.owner = owner;
    }

    public void addPlayer(Player player){players.add(player);}

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Player getOwner(){return owner;}

    public Integer getId(){
        return id;
    }

}
