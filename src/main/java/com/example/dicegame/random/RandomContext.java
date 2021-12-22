package com.example.dicegame.random;

import java.util.Queue;

public class RandomContext {

    private RandomStrategy strategy;

    public void SetStrategy(RandomStrategy randomStrategy){
        strategy=randomStrategy;
    }

    public Queue<Integer> fillQueue(Queue queue)throws Exception{
        return strategy.fillQueue(queue);
    }

    public void setRange(int min,int max) {
        strategy.setRange(min,max);
    }

}
