package com.example.dicegame.gameSatistic;

import java.util.ArrayList;
import java.util.Arrays;

public class Statistics implements StatisticObserver {
    private GameData gameData = new GameData();
    private ArrayList<PlayerData> playerDataArrayList = new ArrayList<>();
    private ArrayList<RessourceData> resourceDataArrayList = new ArrayList<>();


    public String getActivity() {
        String result = "[{";
        result += "\"playerDataArrayList\": [";
        result += printDataToJson(playerDataArrayList.toArray(new StatisticData[playerDataArrayList.size()]));
        result += "]}]";
        return result;
    }

    public String getActivity2() {
        String result = "[";
        for (int i = 0; i < playerDataArrayList.size(); i++) {
            result += "{\"playername\": \"" + playerDataArrayList.get(i).getPlayerName() + "\",";
            result += "\"dicevalues\": " + playerDataArrayList.get(i).getDiceValues() + "}";
            result += ",";
        }
        result += "{\"playername\": \"averageplayer\",";
        result += "\"dicevalues\": " + getSumDiceValues() + "}";
        result += "]";
        return result;
    }

    public ArrayList<Double> getSumDiceValues() {
        ArrayList<Double> sumDiceValues = new ArrayList<>();
        for (int p = 0; p < 10; p++) {
            sumDiceValues.add(0.00);
        }
        for (int i = 0; i < playerDataArrayList.size()-1; i++) {
            for (int j = 0; j < playerDataArrayList.get(i).getDiceValues().size()-1; j++) {
                sumDiceValues.set(j, sumDiceValues.get(j) + playerDataArrayList.get(i).getDiceValues().get(j).doubleValue());
            }
        }
        for (int k = 0; k < sumDiceValues.size()-1; k++) {
            sumDiceValues.set(k, sumDiceValues.get(k) / playerDataArrayList.size());
        }
        return sumDiceValues;
    }

    public String getThroughput() {

        String result = "[{";
        result += "\"Throughput\": [";

        for (int i = 0; i < gameData.getNumberOFResourcesInStorage().size(); i++) {
            result += "{";
            result += "\"round\": " + i;
            result += ",";
            result += "\"Number of Resources In Storage\": " + gameData.getNumberOFResourcesInStorage().get(i);
            result += "}";

            if (i < gameData.getNumberOFResourcesInStorage().size() - 1) {
                result += ",";
            }
        }

        result += "]}]";
        return result;
    }

    public String getThroughput2() {
        String result = "[{";
        result += "\"Throughput\": [";

        for (int i = 0; i < gameData.getNumberOFResourcesInStorage().size(); i++) {
            result += gameData.getNumberOFResourcesInStorage().get(i);
            if (i < gameData.getNumberOFResourcesInStorage().size() - 1) {
                result += ",";
            }
        }

        result += "]}]";
        return result;
    }

    public String getNumberInSystem() {
        String result = "[{";
        result += "\"Number In System\": [";

        for (int i = 0; i < gameData.getNumberOFResourcesInGame().size(); i++) {
            result += "{";
            result += "\"round\": " + i;
            result += ",";
            result += "\"Number of Resources In Game\": " + gameData.getNumberOFResourcesInGame().get(i);
            result += "}";

            if (i < gameData.getNumberOFResourcesInGame().size() - 1) {
                result += ",";
            }
        }

        result += "]}]";
        return result;
    }

    public String getNumberInSystem2() {
        String result = "[{";
        result += "\"NumberInSystem\": [";

        for (int i = 0; i < gameData.getNumberOFResourcesInGame().size(); i++) {
            result += gameData.getNumberOFResourcesInGame().get(i);
            if (i < gameData.getNumberOFResourcesInGame().size() - 1) {
                result += ",";
            }
        }
        result += "]}]";
        return result;
    }

    public String getTimeInSystem2() {
        String result = "[{";
        result += "\"resourceDataArrayList\": [";

        for (int i = 0; i < resourceDataArrayList.size(); i++) {
            if (resourceDataArrayList.get(i).getTimeInSystem() != 0) {
                result += resourceDataArrayList.get(i).getTimeInSystem();
                if (i != resourceDataArrayList.size() - 1) result += ",";
            }

        }
        result += "]}]";
        return result;
    }

    public String getTimeInSystem() {
        String result = "[{";
        result += "\"resourceDataArrayList\": [";

        result += printDataToJson(resourceDataArrayList.toArray(new StatisticData[resourceDataArrayList.size()]));

        result += "]}]";
        return result;
    }

    private String printDataToJson(StatisticData[] data) {
        String result = "";

        for (int i = 0; i < data.length; i++) {
            result += data[i].convertToJSON();
            if (i < data.length - 1) {
                result += ",";
            }
        }
        return result;
    }

    public void reset() {
        gameData = new GameData();
        playerDataArrayList = new ArrayList<>();
        resourceDataArrayList = new ArrayList<>();
    }

    @Override
    public void saveMovedRessource(String playerName, int numberOfMovedResources) {

        for (PlayerData player : playerDataArrayList) {
            if (player.getPlayerName().equals(playerName)) {
                player.addMovedResource(numberOfMovedResources);
                return;
            }
        }
        PlayerData playerData = new PlayerData(playerName);
        playerData.addMovedResource(numberOfMovedResources);
        playerDataArrayList.add(playerData);
    }

    @Override
    public void saveDiceRolled(String playerName, int value) {
        for (PlayerData player : playerDataArrayList) {
            if (player.getPlayerName().equals(playerName)) {
                player.addDiceValue(value);
                return;
            }
        }
        PlayerData playerData = new PlayerData(playerName);
        playerData.addDiceValue(value);
        playerDataArrayList.add(playerData);
    }

    @Override
    public void saveRessourceAddedToStorage(int timeInSystem) {
        int orderOfArrival = resourceDataArrayList.size();
        RessourceData ressourceData = new RessourceData();
        ressourceData.setOrderOfArrival(orderOfArrival);
        ressourceData.setTimeInSystem(timeInSystem);
        resourceDataArrayList.add(ressourceData);
    }

    @Override
    public void saveNumberOFResourcesInGame(int number) {
        gameData.addNumberOFResourcesInGameInGameRound(number);
    }

    @Override
    public void saveNumberOFResourcesInStorage(int number) {
        gameData.addNumberOFResourcesInStorageInGameRound(number);
    }
}
