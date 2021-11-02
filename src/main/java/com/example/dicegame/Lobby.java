package com.example.dicegame;

import java.util.ArrayList;
import java.util.Random;

public class Lobby {
    private Integer id = new Random().nextInt(59);
    private ArrayList<Player> players = new ArrayList<>();
    private Player owner;

    public Lobby(String username){
        owner = new Player(username);
        players.add(owner);
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
