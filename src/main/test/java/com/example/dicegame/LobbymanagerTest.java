package com.example.dicegame;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LobbymanagerTest {


    @Test
    void toStringTest(){
        try {
            Lobbymanager lobbymanager=new Lobbymanager();

            String userName="Test";
            Player player=new Player(userName);
            String userName2="Test2";
            Player player2=new Player(userName2);
            String userName3="Test3";
            Player player3=new Player(userName3);

            Lobby lobby=new Lobby(player);
            lobby.getPlayers().add(player2);
            lobby.getPlayers().add(player3);

            lobbymanager.getLobbies().add(lobby);

            String result=lobbymanager.toString();
            System.out.println(result);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

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