package com.example.dicegame.random;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DiceManager {
    private static DiceManager diceManager;

    public final static int size =1000;

    private RandomContext randomContext=new RandomContext();

    private Queue<Integer> dices=new ConcurrentLinkedQueue<>();

    public static DiceManager getInstanz(){
        if(diceManager==null){
            diceManager=new DiceManager();
        }
        return diceManager;
    }

    private DiceManager() {
    }

    public int getDice(){
        if(dices.size()==0){
            fillQueue(0);
        }
        return dices.poll();
    }

    private void fillQueue(int count){
        try {
            randomContext.SetStrategy(new RandomOrgRandom());
            randomContext.fillQueue(dices);
        }catch (Exception e){
            //if Random.org can not be reached
            e.printStackTrace();

            if(count>5) {
                //use java Random Instead
                try {
                    randomContext.SetStrategy(new JavaRandom());
                    randomContext.fillQueue(dices);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else {
                fillQueue(count++);
            }
        }

    }








}
