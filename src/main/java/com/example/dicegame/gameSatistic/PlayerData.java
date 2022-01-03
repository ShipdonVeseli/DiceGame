package com.example.dicegame.gameSatistic;

import java.util.ArrayList;

public class PlayerData implements StatisticData {
    private String playerName;
    private ArrayList<Integer> diceValues=new ArrayList<>();
    private ArrayList<Integer> movedResources=new ArrayList<>();

    public PlayerData(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
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

    @Override
    public String convertToJSON(){

        String result="{";
        result+="\"PlayerName\": \""+playerName+"\",";
        result+="\"Data\": "+"[";
        if(diceValues.size()==movedResources.size())  {
            for (int i = 0; i < diceValues.size(); i++) {
                result+="{";
                result+="\"round\": "+i+",";
                result+="\"diceValue\": "+diceValues.get(i)+",";
                result+="\"movedResource\": "+movedResources.get(i);
                result+="}";
                if (i<diceValues.size()-1){
                    result+=",";
                }
            }
        }else {
            System.out.println("error");
            System.out.println(diceValues.size()+" "+movedResources.size() );
        }
        result += "]}";
        return result;
    }
}
