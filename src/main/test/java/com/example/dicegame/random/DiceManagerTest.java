package com.example.dicegame.random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceManagerTest {

    DiceManager diceManager=DiceManager.getInstanz();
    @BeforeEach
    void setUp() {

    }

    @RepeatedTest(100)
    public void testGetDice(){
        try {

            int result=diceManager.getDice();

            System.out.println("result= "+result);
            assertTrue(result>=1&&result<=6);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }
}