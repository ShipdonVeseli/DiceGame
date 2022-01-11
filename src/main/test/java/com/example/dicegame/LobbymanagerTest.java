package com.example.dicegame;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LobbymanagerTest {

    @Test
    void isPlayerInLobbytest(){
        String userName="Test";
        Player player=new Player(userName);
        Lobbymanager lobbymanager=new Lobbymanager();
        Lobby lobby=new Lobby(player);

        lobbymanager.getLobbies().add(lobby);

        boolean result=lobbymanager.isPlayerInLobby(userName);

        assertTrue(result);
    }



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