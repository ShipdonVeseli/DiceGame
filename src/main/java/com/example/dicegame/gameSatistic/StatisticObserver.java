package com.example.dicegame.gameSatistic;

public interface StatisticObserver {

    void saveMovedRessource(String playerName, int NumberOfMovedResources);

    void saveDiceRolled(String playerName, int value);

    void saveRessourceAddedToStorage(int OrderOfArival, int timeInSystem);

    void saveNumberOFResourcesInGame(int number);

    void saveNumberOFResourcesInStorage(int number);
}
