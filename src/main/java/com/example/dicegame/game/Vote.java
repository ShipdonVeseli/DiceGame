package com.example.dicegame.game;

import java.util.Objects;

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

    public String convertToJSON() {
        return "{" + "\"indexOfWeakestPlayer\": " + indexOfWeakestPlayer + "," +
                "\"gameRound\": " + gameRound + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return indexOfWeakestPlayer == vote.indexOfWeakestPlayer && gameRound == vote.gameRound;
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexOfWeakestPlayer, gameRound);
    }
}
