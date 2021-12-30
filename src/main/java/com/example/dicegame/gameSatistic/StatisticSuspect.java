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



}
