package com.example.dicegame.game;

import com.example.dicegame.random.DiceManager;

import java.util.UUID;

public class Dice {

    private UUID uuid=UUID.randomUUID();
    private int value;
    private DiceManager manager=DiceManager.getInstanz();

    public void setValueForTests(int value){
        this.value=value;
    }

    public Dice() {
        roll();
    }

    public int roll(){
        value= manager.getDice();
        return value;
    }

    public UUID getID() {
        return uuid;
    }

    public int getValue() {
        return value;
    }

    public String convertToJSON(){
        return "{"
                +"\"uuid\": "
                +uuid.toString()+","
                +"\"value\": "
                +value
                +"}";
    }

    @Override
    public String toString() {
        return "Dice{" +
                "uuid=" + uuid +
                ", value=" + value +
                ", manager=" + manager +
                '}';
    }
}
