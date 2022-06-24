package com.example.dicegame.random;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class DiceManager {

    private static DiceManager diceManager;

    public final static int size = 1000;

    private ArrayList<RandomSet> randomSets = new ArrayList<>();

    public static DiceManager getInstanz() {
        if (diceManager == null) {
            diceManager = new DiceManager();
        }
        return diceManager;
    }

    private DiceManager() {

    }

    public ArrayList<RandomSet> getRandomSets() {
        return randomSets;
    }

    private RandomSet getSet(int min, int max) throws NoSuchElementException {
        for (RandomSet rand : randomSets) {
            if (rand.getMin() == min && rand.getMax() == max) {
                return rand;
            }
        }
        throw new NoSuchElementException();
    }

    public int getDice(int min, int max) {
        RandomSet random;
        try {
            random = getSet(min, max);
        } catch (NoSuchElementException e) {
            random = new RandomSet(min, max);
            randomSets.add(random);
        }
        return random.getDice();
    }

    public void clearRandomSets(){
        randomSets=new ArrayList<>();
    }

}
