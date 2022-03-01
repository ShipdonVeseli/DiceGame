package com.example.dicegame;

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
    void convertToJSONTest() {

    }
}