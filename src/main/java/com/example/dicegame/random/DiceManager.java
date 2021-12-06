package com.example.dicegame.random;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DiceManager {
    private RandomContext randomContext=new RandomContext();

    private Queue<Integer> dices=new ConcurrentLinkedQueue<>();

    public int getDice(){
        if(dices.size()==0){
            fillQueue();
        }
        return dices.poll();
    }

    private void fillQueue(){
        try {
            randomContext.SetStrategy(new RandomOrgRandom());
            randomContext.fillQueue(dices);
        }catch (Exception e){
            //if Random.org can not be reached
            e.printStackTrace();
            //use java Random Instead
            try {
                randomContext.SetStrategy(new JavaRandom());
                randomContext.fillQueue(dices);
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }

    }








}
