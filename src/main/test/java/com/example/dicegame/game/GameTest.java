package com.example.dicegame.game;

import com.example.dicegame.Lobby;
import com.example.dicegame.Player;
import com.example.dicegame.random.DiceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @BeforeEach
    void setUp() {
        DiceManager diceManager=DiceManager.getInstanz();
    }

//
//    @Test
//    void test(){
//        Game game=new Game();
//
//
//    }

    @Test
    void addNewResourcesTest(){
        Player p1=new Player("Test1");
        Player p2 =new Player("Test2");
        Lobby lobby=new Lobby(p1);
        lobby.addPlayer(p2);
        Game game=new Game(lobby);

        int expected=p1.getSummOfDiceValues();
        System.out.println("anz= "+expected);

        game.addNewResources();

        int result=p1.getResources().size();

        assertEquals(result,expected);
    }

}