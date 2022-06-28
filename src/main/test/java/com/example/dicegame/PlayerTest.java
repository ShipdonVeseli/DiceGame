package com.example.dicegame;

import com.example.dicegame.game.Dice;
import com.example.dicegame.game.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testEquals() {
        Player player = new Player("seef");
        player.addResource(new Resource());

        assertTrue(player.equals(player));

    }

    @Test
    void testEquals2() {
        String name = "test";

        Player player = new Player(name);
        Player player2 = new Player(name);

        Resource resource = new Resource();
        Resource resource1 = new Resource();

        player.addResource(resource);
        player2.addResource(resource);

        assertFalse(player.equals(player2));

    }//+tests


    @Test
    void test1to6with2Dices() {
        Player player = new Player("seef");
        Dice dice1 = new Dice();
        Dice dice2 = new Dice();

        player.getDices().add(dice1);
        player.getDices().add(dice2);

        boolean result = player.is1to6();
        assertFalse(result);
    }


    @Test
    void test1to6() {
        Player player = new Player("seef");
        Dice dice1 = player.getDice(0);
        dice1.setRange(1, 6);

        boolean result = player.is1to6();
        assertTrue(result);
    }

    @Test
    void test2to6() {
        Player player = new Player("seef");
        Dice dice1 = player.getDice(0);
        dice1.setRange(2, 6);


        boolean result = player.is1to6();
        assertFalse(result);
    }

    @Test
    void convertToJSONTest() {

        Player player = new Player("seef");
        player.addResource(new Resource());


        System.out.println(player.convertToJSON());

    }


    @Test
    void setAiTest() {
        //ToDo
        //timertest!!
    }

    @Test
    void removeDiceTest() {
        //ToDo
    }

    @Test
    void getDiceTest() {
        //ToDo
    }

    @Test
    void rollAllDiceTest() {
        //ToDO
    }

    @Test
    void getSummOfDiceValuesTest() {
        String playerName = "test";
        Player player = new Player(playerName);

        Dice dice1 = player.getDices().get(0);
        Dice dice2 = new Dice();
        player.getDices().add(dice2);

        int expectedResult = 8;
        int actualResult;

        dice1.setValueForTests(4);
        dice2.setValueForTests(4);

        actualResult=player.getSummOfDiceValues();

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void getSummOfDiceValuesTest2() {
        String playerName = "test";
        Player player = new Player(playerName);

        player.getDices().remove(0);

        int expectedResult =0 ;
        int actualResult;

        actualResult=player.getSummOfDiceValues();

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void getDiceValuesTest() {
        String playerName = "test";
        Player player = new Player(playerName);

        Dice dice1 = player.getDices().get(0);
        Dice dice2 = new Dice();
        player.getDices().add(dice2);

        String expectedResult = "[4,4]";
        String actualResult;

        dice1.setValueForTests(4);
        dice2.setValueForTests(4);

        actualResult=player.getDiceValues();

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void getDiceValuesTest2() {
        String playerName = "test";
        Player player = new Player(playerName);

        player.getDices().remove(0);

        String expectedResult = "[]";
        String actualResult;

        actualResult=player.getDiceValues();

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void getResourcesTest() {
        String playerName = "test";
        Player player = new Player(playerName);

        int amound=2;

        Resource resource1=new Resource(false);
        Resource resource2=new Resource(false);
        Resource resource3=new Resource(false);
        Resource resource4=new Resource(false);

        ArrayList<Resource> expectedResult=new ArrayList<>();
        ArrayList<Resource> actualResult=new ArrayList<>();


        player.getResources().add(resource1);
        player.getResources().add(resource2);
        player.getResources().add(resource3);
        player.getResources().add(resource4);

        expectedResult.add(resource1);
        expectedResult.add(resource2);

        actualResult=player.getResources(amound);

        assertIterableEquals(expectedResult,actualResult);
    }

    @Test
    void getResourcesTest2() {
        String playerName = "test";
        Player player = new Player(playerName);
        int amound=2;

        ArrayList<Resource> expectedResult=new ArrayList<>();
        ArrayList<Resource> actualResult=new ArrayList<>();

        actualResult=player.getResources(amound);

        assertIterableEquals(expectedResult,actualResult);
    }

    @Test
    void getBlueResourcesTest() {
        String playerName = "test";
        Player player = new Player(playerName);

        boolean isBlue=true;

        Resource resource1=new Resource(isBlue);
        Resource resource2=new Resource(isBlue);
        Resource resource3=new Resource(isBlue);
        Resource resource4=new Resource(isBlue);

        int expectedResult=4;
        int actualResult=0;

        player.getResources().add(resource1);
        player.getResources().add(resource2);
        player.getResources().add(resource3);
        player.getResources().add(resource4);

        actualResult=player.getBlueResources();

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void getBlueResourcesTest2() {
        String playerName = "test";
        Player player = new Player(playerName);

        int expectedResult=0;
        int actualResult=0;

        actualResult=player.getBlueResources();

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void getNormalResources() {

        String playerName = "test";
        Player player = new Player(playerName);

        boolean isBlue=false;

        Resource resource1=new Resource(isBlue);
        Resource resource2=new Resource(isBlue);
        Resource resource3=new Resource(isBlue);
        Resource resource4=new Resource(isBlue);

        int expectedResult=4;
        int actualResult=0;

        player.getResources().add(resource1);
        player.getResources().add(resource2);
        player.getResources().add(resource3);
        player.getResources().add(resource4);

        actualResult=player.getNormalResources();

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void getNormalResources2() {
        String playerName = "test";
        Player player = new Player(playerName);

        int expectedResult=0;
        int actualResult=0;

        actualResult=player.getNormalResources();

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void checkSizeTest() {
        //ToDo
    }

    @Test
    void removeResourcesTest() {
        //ToDo
    }

    @Test
    void resetTest() {
        //ToDo
    }

    @Test
    void setDiceRangesTest() {
        //ToDo
    }

    @Test
    void resetTimerTest() {
        //ToDo
    }

    @Test
    void startTimerTest() {
        //ToDo
    }

    @Test
    void createDateTest() {
        //ToDo
    }

    @Test
    void timerTaskTest() {
        //ToDo
    }

    @Test
    void voteTest() {
        //ToDo
    }

    @Test
    void resetVotingHistoryTest() {
        //ToDo
    }
}
