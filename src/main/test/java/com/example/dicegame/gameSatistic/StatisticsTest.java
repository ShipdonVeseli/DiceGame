package com.example.dicegame.gameSatistic;

import com.example.dicegame.Lobby;
import com.example.dicegame.Player;
import com.example.dicegame.game.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {


    @Test
    void getActivityTest() {
        Lobby lobby=new Lobby("test 1");
        lobby.addPlayer(new Player("test 2"));
        Game game=new Game(lobby);

        try {
            game.rollAllDiceInGame();
            game.move();

            game.rollAllDiceInGame();
            game.move();

            game.rollAllDiceInGame();
            game.move();

            String result=game.getStatistics().getActivity();
            System.out.println(result);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}