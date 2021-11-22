package com.example.dicegame;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Lobby {
    private static int lobbyCount;

    private UUID id =UUID.randomUUID();
    private ArrayList<Player> players = new ArrayList<>();
    private Player owner;

    public Lobby(String username){
        players.add(new Player(username));
        lobbyCount++;
    }

    public Lobby( Player owner) {
        players.add(owner);
        lobbyCount++;
        this.owner = owner;
    }

    public boolean  isPlayerInThatLobby(String username){
        for (Player player: players) {
            if(player.getPlayername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public void addPlayer(Player player){players.add(player);}

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Player getOwner(){return owner;}

    public UUID getId(){
        return id;
    }

}
