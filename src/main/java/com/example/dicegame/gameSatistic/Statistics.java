package com.example.dicegame.gameSatistic;

import java.util.ArrayList;

public class Statistics implements StatisticObserver{
    private GameData gameData=new GameData();
    private ArrayList <PlayerData> playerDataArrayList=new ArrayList<>();
    private ArrayList<RessourceData> ressourceDataArrayList =new ArrayList<>();


    public String getActivity(){
        //TODO
        return "";
    }

    public String getThroughput(){
        //TODO
        return "";
    }

    public String getNumberInSystem(){
        //TODO
        return "";
    }

    public String getTimeInSystem(){
        //TODO
        return "";
    }

    public String getYourPerformance(){
        //TODO
        return "";
    }


    @Override
    public void saveMovedRessource(String playerName, int NumberOfMovedResources) {

    }

    @Override
    public void saveDiceRolled(String playerName, int value) {

    }

    @Override
    public void saveRessourceAddedToStorrage(int OrderOfArival, int timeInSystem) {

    }

    @Override
    public void saveNmberOFResourcesInGame(int number) {

    }

    @Override
    public void saveNumberOFResourcesInStorrage(int number) {

    }
}
