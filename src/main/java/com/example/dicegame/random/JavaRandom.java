package com.example.dicegame.random;

import java.util.Queue;

public class JavaRandom implements RandomStrategy{
    private int min=1;
    private int max=6;

    @Override
    public Queue<Integer> fillQueue(Queue queue) throws Exception {
        for (int i=0;i<DiceManager.size;i++){
            int randomDice= min + (int) (Math.random() * ((max - min) + 1)) ;
            queue.add(randomDice);
        }
        return queue;
    }

    @Override
    public void setRange(int min, int max) {
        this.min=min;
        this.max=max;
    }
}
