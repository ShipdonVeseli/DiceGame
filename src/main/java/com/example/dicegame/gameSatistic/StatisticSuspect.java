package com.example.dicegame.gameSatistic;

import java.util.ArrayList;

public abstract class StatisticSuspect {

    ArrayList<StatisticObserver> observers = new ArrayList<>();

    public void addObserver(StatisticObserver observer) {
        observers.add(observer);
    }


    public ArrayList<StatisticObserver> getObservers() {
        return observers;
    }

    public void removeObserver(StatisticObserver observer) {
        observers.add(observer);
    }

    public void saveNumberOfMovedResources(String playerName, int amount) {
        observers.forEach(e -> {
            e.saveMovedRessource(playerName, amount);
        });
    }

    public void saveRolledDiceValue(String playerName, int value) {
        observers.forEach(e -> {
            e.saveDiceRolled(playerName, value);
        });
    }

    public void saveNumberInStorage(int amount) {
        observers.forEach(e -> {
            e.saveNumberOFResourcesInStorage(amount);
        });
    }

    public void saveNumberInGame(int amount) {
        observers.forEach(e -> {
            e.saveNumberOFResourcesInGame(amount);
        });
    }

    public void saveRessourceInStorage(int timeInSystem) {
        observers.forEach(e -> {
            e.saveRessourceAddedToStorage(timeInSystem);
        });
    }


}
