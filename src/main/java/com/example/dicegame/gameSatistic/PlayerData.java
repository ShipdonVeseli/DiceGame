package com.example.dicegame.gameSatistic;

import java.util.ArrayList;

public class PlayerData {
    private String PlayerName;
    private ArrayList<Integer> diceValues=new ArrayList<>();
    private ArrayList<Integer> movedResources=new ArrayList<>();

    public PlayerData(String playerName) {
        PlayerName = playerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public ArrayList<Integer> getDiceValues() {
        return diceValues;
    }

    public ArrayList<Integer> getMovedResources() {
        return movedResources;
    }

    public void addDiceValue(int value){
        diceValues.add(value);
    }

    public void addMovedResource(int numberOfMovedResources){
        movedResources.add(numberOfMovedResources);
    }

    public int getDiceValue(int round){
     return diceValues.get(round);
    }

    public int getMovedResourcesInRound(int round){
        return movedResources.get(round);
    }
}
