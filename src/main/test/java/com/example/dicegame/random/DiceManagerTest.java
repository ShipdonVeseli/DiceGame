package com.example.dicegame.random;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceManagerTest {


    @RepeatedTest(100)
    public void testGetDice(){
        try {
            DiceManager diceManager=new DiceManager();

            int result=diceManager.getDice();

            System.out.println("result= "+result);
            assertTrue(result>=1&&result<=6);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }
}