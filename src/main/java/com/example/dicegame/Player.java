package com.example.dicegame;

import com.example.dicegame.game.Dice;
import com.example.dicegame.game.Resource;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

public class Player {
    private String playerName;
    private ArrayList<Dice> dices = new ArrayList<>();
    private ArrayList<Resource> resources = new ArrayList<>();


    public Player(String username) {
        playerName = username;
        addDice(new Dice());
    }

    public void addResource(Resource resource){
        resources.add(resource);
    }

    public void addResources(ArrayList<Resource> additionalResources) {
        resources.addAll(additionalResources);
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void emptyResources() {
        resources = new ArrayList<>();
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

    public void removeDice(UUID diceID) throws  NoSuchElementException{
        for (Dice dice:dices             ) {
            if(dice.getID().equals(diceID)){
                dices.remove(dice);
                return;
            }
        }
        throw new NoSuchElementException("No Dice with ID= "+diceID);
    }

    public ArrayList<Dice> getDices() {
        return dices;
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

    public int getSummOfDiceValues() {
        int result = 0;
        for (Dice dice : dices) {
            result += dice.getValue();
        }
        return result;
    }
    public ArrayList<Resource> getResources(int amount){
        return (ArrayList<Resource>) resources.subList(0,amount-1);
    }

    public void removeResources(int amount){
        for (int i=0;i<=amount;i++){
            resources.remove(0);
        }
    }

    @Override
    public String toString() {
        return playerName + " ";
    }


}
