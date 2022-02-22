package com.example.dicegame.random;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RandomSet {


    public final static int size =1000;

    private RandomContext randomContext=new RandomContext();
    private Queue<Integer> dices=new ConcurrentLinkedQueue<>();
    private RandomOrgRandom randomOrgRandom=new RandomOrgRandom();
    private JavaRandom javaRandom=new JavaRandom();
    private int min=1;
    private int max=6;

    public RandomSet() {
    }

    public RandomSet(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getDice(){
        if(dices.size()==0){
            fillQueue(0);
        }
        return dices.poll();
    }

    private void fillQueue(int count){
        try {
            randomContext.SetStrategy(randomOrgRandom);
            randomContext.setRange(min,max);
            randomContext.fillQueue(dices);
        }catch (Exception e){
            //if Random.org can not be reached
            e.printStackTrace();

            //use java Random Instead
            try {
                randomContext.SetStrategy(javaRandom);
                randomContext.setRange(min,max);
                randomContext.fillQueue(dices);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
