package com.example.dicegame.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    @Test
    void testGetExpectedValue1() {
        try {
            Dice dice = new Dice();
            int min = 1;
            int max = 6;
            double expectedResult = 3.5;
            dice.setRange(min, max);

            double actuallResult = dice.getExpectedValue();
            System.out.println("result = "+actuallResult);
            assertEquals(expectedResult, actuallResult);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}