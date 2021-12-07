package com.example.dicegame.random;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomOrgRandomTest {

    @Test
    public void test(){
        try {
           RandomOrgRandom randomOrgRandom =new RandomOrgRandom();

           randomOrgRandom.getRandomValuesFromRandomOrg();

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

}