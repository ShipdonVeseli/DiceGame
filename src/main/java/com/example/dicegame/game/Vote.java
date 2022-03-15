package com.example.dicegame.game;

public class Vote {
    private int indexOfWeakestPlayer;
    private int gameRound;

    public int getIndexOfWeakestPlayer() {
        return indexOfWeakestPlayer;
    }

    public void setIndexOfWeakestPlayer(int indexOfWeakestPlayer) {
        this.indexOfWeakestPlayer = indexOfWeakestPlayer;
    }

    public int getGameRound() {
        return gameRound;
    }

    public void setGameRound(int gameRound) {
        this.gameRound = gameRound;
    }

    public Vote(int indexOfWeakestPlayer, int gameRound) {
        this.indexOfWeakestPlayer = indexOfWeakestPlayer;
        this.gameRound = gameRound;
    }

    public String convertToJSON(){
        return "{"+"\"indexOfWeakestPlayer\": "+indexOfWeakestPlayer+"," +
                "\"gameRound\": "+gameRound+"}";
    }
}
