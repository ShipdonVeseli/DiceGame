package com.example.dicegame;

import java.util.ArrayList;
import java.util.Random;

public class Lobby {
    private Integer id ;
    private ArrayList<Player> players = new ArrayList<>();
    private Player owner;

    public Lobby(String username){
        owner = new Player(username);
        players.add(owner);
        id= new Random().nextInt(59);
    }

    public Lobby(Integer id, Player owner) {
        this.id = id;
        this.owner = owner;
    }

    public void addPlayer(Player player){players.add(player);}

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Player getOwner(){return owner;}

    public Integer getId(){
        return id;
    }

}
