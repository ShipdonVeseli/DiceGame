package com.example.dicegame.random;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DiceManager {

    private static DiceManager diceManager;

    public final static int size = 1000;


    private ArrayList<RandomSet> randomSets = new ArrayList<>();

//    private RandomContext randomContext = new RandomContext();
//
//    private Queue<Integer> dices = new ConcurrentLinkedQueue<>();
//
//    private RandomOrgRandom randomOrgRandom = new RandomOrgRandom();
//    private JavaRandom javaRandom = new JavaRandom();

    public static DiceManager getInstanz() {
        if (diceManager == null) {
            diceManager = new DiceManager();
        }
        return diceManager;
    }

    private DiceManager() {
    }

//    public int getDice() {
//        if (dices.size() == 0) {
//            fillQueue(0);
//        }
//        return dices.poll();
//    }
//
//    private void fillQueue(int count) {
//        try {
//            randomContext.SetStrategy(randomOrgRandom);
//            randomContext.fillQueue(dices);
//        } catch (Exception e) {
//            //if Random.org can not be reached
//            e.printStackTrace();
//
//            //use java Random Instead
//            try {
//                randomContext.SetStrategy(javaRandom);
//                randomContext.fillQueue(dices);
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//        }
//    }



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

}
