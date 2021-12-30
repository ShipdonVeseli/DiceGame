package com.example.dicegame.random;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Queue;

public class RandomOrgRandom implements RandomStrategy{
    private final String websiteURL ="https://www.random.org/integers/";
    private final String httpMethod ="GET";

    @Override
    public Queue<Integer> fillQueue(Queue queue) throws Exception {
        System.out.println("Try to get values from random.org");
        getRandomValuesFromRandomOrg(queue);
        return queue;
    }

    public void getRandomValuesFromRandomOrg(Queue<Integer> queue)throws Exception{
        try {
            HttpSend httpSend=new HttpSend(websiteURL);
            httpSend.setHttpMethode(httpMethod);

            httpSend.addParameter("num",String.valueOf(DiceManager.size));
            httpSend.addParameter("min","1");
            httpSend.addParameter("max","6");
            httpSend.addParameter("col","1");
            httpSend.addParameter("base","10");
            httpSend.addParameter("format","plain");
            httpSend.addParameter("rnd","new");

            httpSend.init();
            System.out.println(httpSend.getUrl());

            int responseCode= httpSend.getStatus();
            System.out.println( "response code=  "+ responseCode);
            if(responseCode!=200){
                throw new Exception("Random.org can not be reached");
            }
            InputStream response =httpSend.start();

            write(response,queue);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    private void write(InputStream inputStream,Queue<Integer> queue){
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e-> {
                   System.out.println(e);
                    queue.add(Integer.valueOf(e.toString()));
                }

        );
    }

}
