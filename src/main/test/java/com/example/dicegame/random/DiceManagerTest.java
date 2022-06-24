package com.example.dicegame.random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceManagerTest {

    DiceManager diceManager;

    @BeforeEach
    void setUp() {
        diceManager = DiceManager.getInstanz();
    }

    @Test
    public void testGetDice() {
        try {

            int result = diceManager.getDice(1, 6);

            System.out.println("result= " + result);
            assertTrue(result >= 1 && result <= 6);
            diceManager.clearRandomSets();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }


    @Test
    public void testGetDice2() {
        try {

            int result = diceManager.getDice(1, 1);

            System.out.println("result= " + result);
            assertTrue(result >= 1 && result <= 6);
            diceManager.clearRandomSets();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }


    @Test
    public void testGetSize() {
        try {
            int expectedResult = 2;
            int size;

            diceManager.getDice(1, 6);
            diceManager.getDice(1, 6);
            diceManager.getDice(2, 8);
            diceManager.getDice(2, 8);

            size = diceManager.getRandomSets().size();

            assertEquals(expectedResult, size);
            diceManager.clearRandomSets();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void testGetSize2() {
        try {
            int size ;
            int expectedResult = 0;

            size=  diceManager.getRandomSets().size();
            assertEquals(expectedResult, size);
            diceManager.clearRandomSets();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetSize3() {
        try {

            int size ;
            int expectedResult = 1;

            diceManager.getDice(1, 6);

            size= diceManager.getRandomSets().size();

            assertEquals(expectedResult, size);
            diceManager.clearRandomSets();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}