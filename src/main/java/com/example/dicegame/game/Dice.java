package com.example.dicegame.game;

import com.example.dicegame.random.DiceManager;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dice dice = (Dice) o;
        return value == dice.value && Objects.equals(uuid, dice.uuid) && Objects.equals(manager, dice.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, value, manager);
    }
}
