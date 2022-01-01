package com.example.dicegame.gameSatistic;

import java.util.ArrayList;

public abstract class StatisticSuspect {

    ArrayList<StatisticObserver>observers=new ArrayList<>();

    public void addObserver(StatisticObserver observer){
        observers.add(observer);
    }

    public void removeObserver(StatisticObserver observer){
        observers.add(observer);
    }

    public void saveNumberOfMovedResources(String playerName,int amount){
        observers.forEach(e->{
            e.saveMovedRessource(playerName,amount);
        });
    }

    public void saveRolledDiceValue(String playerName,int value){
        observers.forEach(e->{
            e.saveDiceRolled(playerName,value);
        });
    }



}
