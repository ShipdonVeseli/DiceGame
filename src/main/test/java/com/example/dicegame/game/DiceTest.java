package com.example.dicegame.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//WARNING
//This test Class SHOULD NOT be automatically run
//You may be baned from Random.org
class DiceTest {

    @Test
    void testGetExpectedValue1() {
        try {
            Dice dice = new Dice();
            int min = 1;
            int max = 6;
            double expectedResult = 3.5;
            dice.setRange(min, max);

            double actualResult = dice.getExpectedValue();
            System.out.println("result = "+actualResult);
            assertEquals(expectedResult, actualResult);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}