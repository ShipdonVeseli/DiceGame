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

            int result=diceManager.getDice(1,6);

            System.out.println("result= "+result);
            assertTrue(result>=1&&result<=6);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void testgetDiceMinMac(){
        try {

            int result1=diceManager.getDice(1,6);
            int result2=diceManager.getDice(2,8);
            int result3=diceManager.getDice(2,8);

            System.out.println("result= "+result1);
            System.out.println("result2= "+result2);
            System.out.println("result3= "+result3);

            int size=diceManager.getRandomSets().size();

            assertTrue(result1>=1&&result1<=6);
            assertTrue(result2>=2&&result2<=8);
            assertTrue(result3>=1&&result3<=8);
            assertEquals(2,size);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

}