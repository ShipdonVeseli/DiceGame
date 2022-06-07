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
}