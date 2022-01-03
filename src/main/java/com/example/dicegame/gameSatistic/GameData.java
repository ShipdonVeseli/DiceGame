package com.example.dicegame.gameSatistic;

import java.util.ArrayList;

public class GameData implements StatisticData{
    private ArrayList<Integer> numberOFResourcesInGame=new ArrayList<>();
    private ArrayList<Integer> numberOFResourcesInStorage =new ArrayList<>();

    public ArrayList<Integer> getNumberOFResourcesInGame() {
        return numberOFResourcesInGame;
    }

    public ArrayList<Integer> getNumberOFResourcesInStorage() {
        return numberOFResourcesInStorage;
    }

    public void addNumberOFResourcesInGameInGameRound(int number){
        numberOFResourcesInGame.add(number);
    }

    public void addNumberOFResourcesInStorageInGameRound(int number){
        getNumberOFResourcesInStorage().add(number);
    }

    public int getNumberOFResourcesInGameInRound(int round){
        return numberOFResourcesInGame.get(round);
    }

    public int getNumberOFResourcesInStorageInRound(int round){
        return numberOFResourcesInStorage.get(round);
    }

    @Override
    public String convertToJSON() {
        return "";
    }
}
