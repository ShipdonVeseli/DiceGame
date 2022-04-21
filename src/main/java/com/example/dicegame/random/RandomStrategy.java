package com.example.dicegame.random;

import java.util.Queue;

public interface RandomStrategy {
    Queue<Integer> fillQueue(Queue queue) throws Exception;

    void setRange(int min, int max);
}
