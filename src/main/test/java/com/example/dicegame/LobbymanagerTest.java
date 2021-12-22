package com.example.dicegame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LobbymanagerTest {

    @Test
    void convertToJSONTest() {
        try {
            Lobbymanager lobbymanager = new Lobbymanager();
            lobbymanager.createLobby("test 1");
            lobbymanager.createLobby("test 2");

            lobbymanager.convertToJSON();
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}