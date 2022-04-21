package com.example.dicegame.random;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Queue;

public class RandomOrgRandom implements RandomStrategy {
    private int min = 1;
    private int max = 6;

    private final String websiteURL = "https://www.random.org/integers/";
    private final String httpMethod = "GET";

    @Override
    public Queue<Integer> fillQueue(Queue queue) throws Exception {
        System.out.println("Try to get values from random.org");
        getRandomValuesFromRandomOrg(queue);
        return queue;
    }

    @Override
    public void setRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public void getRandomValuesFromRandomOrg(Queue<Integer> queue) throws Exception {
        try {
            HttpSend httpSend = new HttpSend(websiteURL);
            httpSend.setHttpMethode(httpMethod);

            httpSend.addParameter("num", String.valueOf(DiceManager.size));
            httpSend.addParameter("min", String.valueOf(min));
            httpSend.addParameter("max", String.valueOf(max));
            httpSend.addParameter("col", "1");
            httpSend.addParameter("base", "10");
            httpSend.addParameter("format", "plain");
            httpSend.addParameter("rnd", "new");

            httpSend.init();
            System.out.println(httpSend.getUrl());

            int responseCode = httpSend.getStatus();
            System.out.println("response code=  " + responseCode);
            if (responseCode != 200) {
                throw new Exception("Random.org can not be reached");
            }
            InputStream response = httpSend.start();

            write(response, queue);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void write(InputStream inputStream, Queue<Integer> queue) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> {
                    //System.out.println(e);
                    queue.add(Integer.valueOf(e.toString()));
                }

        );
    }

}
