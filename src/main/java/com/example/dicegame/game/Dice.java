package com.example.dicegame.game;

import com.example.dicegame.random.DiceManager;

import java.util.Objects;
import java.util.UUID;

public class Dice {

    private UUID uuid = UUID.randomUUID();
    private int min = 1;
    private int max = 6;
    private int value;
    private DiceManager manager = DiceManager.getInstanz();

    public void setValueForTests(int value) {
        this.value = value;
    }

    public Dice() {
        roll();
    }

    public int roll() {
        value = manager.getDice(min, max);
        return value;
    }

    public UUID getID() {
        return uuid;
    }

    public int getValue() {
        return value;
    }

    public void setRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public String convertToJSON() {
        return "{"
                + "\"uuid\": "
                + "\"" + uuid.toString() + "\"" + ","
                + "\"value\": "
                + value
                + "}";
    }

    public double getExpectedValue() {
        double probably = 1 / (Double.valueOf(max) - (Double.valueOf(min) - 1));
        double result = 0;
        for (int i = min; i <= max; i++) {
            result += i * probably;
        }
        return result;
    }

    @Override
    public String toString() {
        return "Dice{" +
                "uuid=" + uuid.toString() +
                ", value=" + value +
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
