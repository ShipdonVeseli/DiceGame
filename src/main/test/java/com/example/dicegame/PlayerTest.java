package com.example.dicegame;

import com.example.dicegame.game.Dice;
import com.example.dicegame.game.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testEquals(){
        Player player=new Player("seef");
        player.addResource(new Resource());

        assertTrue(player.equals(player));

    }

    @Test
    void testEquals2(){
        String name="test";

        Player player=new Player(name);
        Player player2=new Player(name);

        Resource resource=new Resource();
        Resource resource1=new Resource();

        player.addResource(resource);
        player2.addResource(resource);

        assertFalse(player.equals(player2));

    }//+tests






    @Test
    void test1to6with2Dices(){
        Player player=new Player("seef");
        Dice dice1=new Dice();
        Dice dice2=new Dice();

        player.getDices().add(dice1);
        player.getDices().add(dice2);

        boolean result=player.is1to6();
        assertFalse(result);
    }



    @Test
    void test1to6(){
        Player player=new Player("seef");
        Dice dice1= player.getDice(0);
        dice1.setRange(1,6);

        boolean result=player.is1to6();
        assertTrue(result);
    }

    @Test
    void test2to6(){
        Player player=new Player("seef");
        Dice dice1= player.getDice(0);
        dice1.setRange(2,6);


        boolean result=player.is1to6();
        assertFalse(result);
    }

    @Test
    void convertToJSONTest() {

        Player player=new Player("seef");
        player.addResource(new Resource());


        System.out.println(player.convertToJSON());

    }


    @Test
    void setAiTest(){
        //ToDo
        //timertest!!
    }

    @Test
    void removeDiceTest(){
        //ToDo
    }

    @Test
    void getDiceTest(){
        //ToDo
    }

    @Test
    void rollAllDiceTest(){
        //ToDO
    }

    @Test
    void getSummOfDiceValuesTest(){
        //ToDo
    }


    @Test
    void getDiceValuesTest(){
        //ToDo
    }

    @Test
    void getResourcesTest(){
        //ToDo
    }

    @Test
    void getBlueResourcesTest(){
        //ToDo
    }

    @Test
    void getNormalResources(){
        //ToDo
    }

    @Test
    void checkSizeTest(){
        //ToDo
    }

    @Test
    void removeResourcesTest(){
        //ToDo
    }

    @Test
    void resetTest(){
        //ToDo
    }

    @Test
    void setDiceRangesTest(){
        //ToDo
    }

    @Test
    void resetTimerTest(){
        //ToDo
    }

    @Test
    void startTimerTest(){
        //ToDo
    }

    @Test
    void createDateTest(){
        //ToDo
    }

    @Test
    void timerTaskTest(){
        //ToDo
    }

    @Test
    void voteTest(){
        //ToDo
    }

    @Test
    void resetVotingHistoryTest(){
        //ToDo
    }
}
