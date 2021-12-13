package com.example.dicegame;

import com.example.dicegame.game.Dice;
import com.example.dicegame.game.Resources;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Player {
    private String playerName;
    private ArrayList<Dice> dices = new ArrayList<>();
    private ArrayList<Resources> resources=new ArrayList<>();


    public Player(String username) {
        playerName = username;
    }

    public void addResources(ArrayList<Resources>additionalResources){
        resources.addAll(additionalResources);
    }

    public ArrayList<Resources> getResources() {
        return resources;
    }

    public void emptyResources(){
        resources=new ArrayList<>();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void addDice(Dice dice) {
        dices.add(dice);
    }

    public void removeDice(Dice dice) {

    }

    public Dice getDice(Dice dice) throws NoSuchElementException {
        for (Dice diceInPlayer : dices) {
            if (dice.getID().equals(diceInPlayer.getID())) {
                return diceInPlayer;
            }
        }
        throw new NoSuchElementException();
    }

    public void rollAllDices() {
        dices.forEach(e -> e.roll());
    }


    @Override
    public String toString() {
        return playerName + " ";
    }


}
