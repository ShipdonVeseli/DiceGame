package com.example.dicegame.gameSatistic;

public interface StatisticObserver {

    void saveMovedRessource(String playerName, int NumberOfMovedResources);

    void saveDiceRolled(String playerName, int value);

    void saveRessourceAddedToStorrage(int OrderOfArival,int timeInSystem);

    void saveNmberOFResourcesInGame(int number);

    void saveNumberOFResourcesInStorrage(int number);
}
