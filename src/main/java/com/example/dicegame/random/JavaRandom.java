package com.example.dicegame.random;

import java.util.Queue;

public class JavaRandom implements RandomStrategy{
    @Override
    public Queue<Integer> fillQueue(Queue queue) throws Exception {
        final int min=1;
        final int max=6;

        for (int i=0;i<DiceManager.size;i++){
            int randomDice= min+(int)(Math.random()*((max-min)+1)) ;
            queue.add(randomDice);
        }
        return queue;
    }
}
