package com.example.dicegame.gameSatistic;

public class RessourceData implements StatisticData {
    private int OrderOfArrival;
    private int timeInSystem;

    public int getOrderOfArrival() {
        return OrderOfArrival;
    }

    public int getTimeInSystem() {
        return timeInSystem;
    }

    public void setOrderOfArrival(int orderOfArrival) {
        OrderOfArrival = orderOfArrival;
    }

    public void setTimeInSystem(int timeInSystem) {
        this.timeInSystem = timeInSystem;
    }

    @Override
    public String convertToJSON() {
        String result = "{";
        result += "\"OrderOfArrival\": " + OrderOfArrival + ",";
        result += "\"timeInSystem\": " + timeInSystem;
        result += "}";
        return result;
    }
}
