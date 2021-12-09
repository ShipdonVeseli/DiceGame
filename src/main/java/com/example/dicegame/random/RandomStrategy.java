package com.example.dicegame.random;

import java.util.Queue;

public interface RandomStrategy {
    public Queue<Integer> fillQueue(Queue queue) throws Exception;
}
