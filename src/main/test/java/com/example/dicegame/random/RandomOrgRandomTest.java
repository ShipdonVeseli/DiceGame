package com.example.dicegame.random;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.*;

class RandomOrgRandomTest {

    @Test
    public void test(){
        try {
           RandomOrgRandom randomOrgRandom =new RandomOrgRandom();

           randomOrgRandom.getRandomValuesFromRandomOrg(new ConcurrentLinkedQueue());

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

}