package com.example.dicegame;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LobbymanagerTest {


    @Test
    void toStringTest() {
        try {
            Lobbymanager lobbymanager = new Lobbymanager();

            String userName = "Test";
            Player player = new Player(userName);
            String userName2 = "Test2";
            Player player2 = new Player(userName2);
            String userName3 = "Test3";
            Player player3 = new Player(userName3);

            Lobby lobby = new Lobby(player);
            lobby.getPlayers().add(player2);
            lobby.getPlayers().add(player3);

            lobbymanager.getLobbies().add(lobby);

            String result = lobbymanager.toString();
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void isPlayerInLobbytest() {
        String userName = "Test";
        Player player = new Player(userName);
        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);

        lobbymanager.getLobbies().add(lobby);

        boolean result = lobbymanager.isPlayerInLobby(userName);

        assertTrue(result);
    }

    @Test
    void convertToJSONTest() {
        try {
            Lobbymanager lobbymanager = new Lobbymanager();
            lobbymanager.createLobby("test 1");
            lobbymanager.createLobby("test 2");

            lobbymanager.convertToJSON();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    void hasPlayerCreatedLobbyTest() {
        String userName = "Test";
        Player player = new Player(userName);
        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);

        lobbymanager.getLobbies().add(lobby);

        boolean result = lobbymanager.hasPlayerCreatedLobby(userName);

        assertTrue(result);
    }

    @Test
    void hasPlayerCreatedLobbyTest2() {
        String userName = "Test";
        String userName2 = "Test2";
        Player player = new Player(userName);
        Player player2 = new Player(userName2);
        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);
        lobby.getPlayers().add(player2);

        lobbymanager.getLobbies().add(lobby);

        boolean result = lobbymanager.hasPlayerCreatedLobby(userName2);

        assertFalse(result);
    }

    @Test
    void hasPlayerCreatedLobbyTest3() {
        String userName = "Test";
        String userName2 = "Test2";
        Player player = new Player(userName);
        Player player2 = new Player(userName2);
        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);
        lobby.getPlayers().add(player2);

        lobbymanager.getLobbies().add(lobby);

        boolean result = lobbymanager.hasPlayerCreatedLobby("userName2");

        assertFalse(result);
    }

    @Test
    void addPlayerToLobbyTest() {
        String userName = "Test";
        String userName2 = "Test2";
        Player player = new Player(userName);
        Player player2 = new Player(userName2);
        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);

        ArrayList<Player> expectedResult = new ArrayList<>();
        ArrayList<Player> actualResult;

        expectedResult.add(player);
        expectedResult.add(player2);

        lobbymanager.getLobbies().add(lobby);

        lobbymanager.addPlayerToLobby(player2, lobby.getId());

        actualResult = lobby.getPlayers();

        assertIterableEquals(expectedResult, actualResult);
    }

    @Test
    void addUserToLobbyTest() {
        String userName = "Test";
        String userName2 = "Test2";
        Player player = new Player(userName);

        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);


        lobbymanager.getLobbies().add(lobby);

        lobbymanager.addUserToLobby(userName2, lobby.getId());

        String result = lobby.getPlayers().get(1).getPlayerName();

        int actualSize = lobby.getPlayers().size();
        int expectedSize = lobby.getPlayers().size();

        assertEquals(userName2, result);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void removePlayerTest() {
        String userName = "Test";
        String userName2 = "Test2";
        Player player = new Player(userName);
        Player player2 = new Player(userName2);
        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);
        lobby.getPlayers().add(player2);

        ArrayList<Player> expectedResult = new ArrayList<>();
        ArrayList<Player> actualResult;

        expectedResult.add(player);


        lobbymanager.getLobbies().add(lobby);

        lobbymanager.removePlayer(userName2);

        actualResult = lobby.getPlayers();

        assertIterableEquals(expectedResult, actualResult);
    }


    @Test
    void removeLobbyTest() {
        String userName = "Test";
        String userName2 = "Test2";
        Player player = new Player(userName);
        Player player2 = new Player(userName2);
        Player player3 = new Player(userName2);

        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);
        lobby.getPlayers().add(player2);

        Lobby lobby2 = new Lobby(player3);

        ArrayList<Lobby> expectedResult = new ArrayList<>();
        ArrayList<Lobby> actualResult;

        expectedResult.add(lobby);

        lobbymanager.getLobbies().add(lobby);
        lobbymanager.getLobbies().add(lobby2);

        lobbymanager.removeLobby(lobby2.getId());

        actualResult = lobbymanager.getLobbies();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void removeLobbyTest2() {
        String userName = "Test";
        String userName2 = "Test2";
        Player player = new Player(userName);
        Player player2 = new Player(userName2);
        Player player3 = new Player(userName2);

        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);
        lobby.getPlayers().add(player2);

        Lobby lobby2 = new Lobby(player3);

        lobbymanager.getLobbies().add(lobby);
        lobbymanager.getLobbies().add(lobby2);

        assertThrows(NoSuchElementException.class, () -> {
            lobbymanager.removeLobby(UUID.randomUUID());
        });
    }

    @Test
    void createLobbyTest() {
        String userName = "Test";
        Lobbymanager lobbymanager = new Lobbymanager();
        try {
            UUID result = lobbymanager.createLobby(userName);

            assertNotNull(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getLobbyByUsernameTest() {
        String userName = "Test";
        String userName2 = "Test2";
        String userName3 = "Test3";
        Player player = new Player(userName);
        Player player2 = new Player(userName2);
        Player player3 = new Player(userName3);

        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);
        lobby.getPlayers().add(player2);

        Lobby lobby2 = new Lobby(player3);

        lobbymanager.getLobbies().add(lobby);
        lobbymanager.getLobbies().add(lobby2);

        Lobby result = lobbymanager.getLobbyByUsername(userName3);

        assertEquals(lobby2, result);
    }

    @Test
    void getLobbyByUsernameTest2() {
        String userName = "Test";
        String userName2 = "Test2";
        String userName3 = "Test3";
        Player player = new Player(userName);
        Player player2 = new Player(userName2);
        Player player3 = new Player(userName3);

        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);
        lobby.getPlayers().add(player2);

        Lobby lobby2 = new Lobby(player3);

        lobbymanager.getLobbies().add(lobby);
        lobbymanager.getLobbies().add(lobby2);


        assertThrows(NoSuchElementException.class, () -> {
            Lobby result = lobbymanager.getLobbyByUsername("userName3");
        });
    }

    @Test
    void getLobbyTest() {
        String userName = "Test";
        String userName2 = "Test2";
        String userName3 = "Test2";
        Player player = new Player(userName);
        Player player2 = new Player(userName2);
        Player player3 = new Player(userName3);

        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);
        lobby.getPlayers().add(player2);

        Lobby lobby2 = new Lobby(player3);

        lobbymanager.getLobbies().add(lobby);
        lobbymanager.getLobbies().add(lobby2);


        Lobby result = lobbymanager.getLobby(lobby.getId());

        assertEquals(lobby, result);
    }

    @Test
    void getLobbyTest2() {
        String userName = "Test";
        String userName2 = "Test2";
        String userName3 = "Test2";
        Player player = new Player(userName);
        Player player2 = new Player(userName2);
        Player player3 = new Player(userName3);

        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);
        lobby.getPlayers().add(player2);

        Lobby lobby2 = new Lobby(player3);

        lobbymanager.getLobbies().add(lobby);
        lobbymanager.getLobbies().add(lobby2);


        assertThrows(NoSuchElementException.class, () -> {
            Lobby result = lobbymanager.getLobby(UUID.randomUUID());
        });
    }

    @Test
    void checkUsernameTest() {
        String userName = "Test";
        String userName2 = "Test2";
        String userName3 = "Test2";
        Player player = new Player(userName);
        Player player2 = new Player(userName2);
        Player player3 = new Player(userName3);

        Lobbymanager lobbymanager = new Lobbymanager();
        Lobby lobby = new Lobby(player);
        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);

        lobbymanager.getLobbies().add(lobby);



       boolean result= lobbymanager.checkUsername(userName2);
       int expectedSize=2;
       int actualSize=lobby.getPlayers().size();

       assertTrue(result);
       assertEquals(expectedSize,actualSize);

    }
}