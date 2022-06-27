package com.example.dicegame;

import com.example.dicegame.game.Game;
import com.example.dicegame.game.Resource;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LobbyTest {

    @Test
    void initTerminationTimerTest() {

    }

    @Test
    void resetTerminationTimerTest() {

    }

    @Test
    void terminationTimerTask() {

    }

    @Test
    void autoTerminateTest() {

    }

    @Test
    void terminate() {
        String playerName1 = "Test_1";

        Lobby lobby = new Lobby(playerName1);
        UUID lobbyId = lobby.getId();
        Lobbymanager lobbymanager = GameServer.getInstance().getLobbymanager();

        lobbymanager.getLobbies().add(lobby);

        lobby.terminate();

        assertThrows(NoSuchElementException.class, () -> {
            lobbymanager.getLobby(lobbyId);
        });
    }

    @Test
    void terminate2() {
        String playerName1 = "Test_1";

        Lobby lobby = new Lobby(playerName1);
        UUID lobbyId = lobby.getId();

        assertThrows(NoSuchElementException.class, () -> {
            lobby.terminate();
        });
    }

    @Test
    void checkIfAllPlayerAreAITest() {
        String playerName1 = "Test_1";
        String playerName2 = "Test_2";
        String playerName3 = "Test_3";
        String playerName4 = "Test_4";

        Lobby lobby = new Lobby(playerName1);

        Player player1 = lobby.getPlayers().get(0);
        Player player2 = new Player(playerName2);
        Player player3 = new Player(playerName3);
        Player player4 = new Player(playerName4);

        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);

        player1.setAI(true);
        player2.setAI(true);
        player3.setAI(true);
        player4.setAI(true);

        assertTrue(lobby.checkIfAllPlayerAreAI());
    }

    @Test
    void checkIfAllPlayerAreAITest2() {
        String playerName1 = "Test_1";
        String playerName2 = "Test_2";
        String playerName3 = "Test_3";
        String playerName4 = "Test_4";

        Lobby lobby = new Lobby(playerName1);

        Player player1 = lobby.getPlayers().get(0);
        Player player2 = new Player(playerName2);
        Player player3 = new Player(playerName3);
        Player player4 = new Player(playerName4);

        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);

        player1.setAI(true);
        player2.setAI(false);
        player3.setAI(true);
        player4.setAI(true);

        assertFalse(lobby.checkIfAllPlayerAreAI());
    }

    @Test
    void checkIfAllPlayerAreAITest3() {
        String playerName1 = "Test_1";

        Lobby lobby = new Lobby(playerName1);
        lobby.getPlayers().remove(0);

        assertTrue(lobby.checkIfAllPlayerAreAI());
    }

    @Test
    void startGameTest() {
        String playerName = "Test_1";
        Lobby lobby = new Lobby(playerName);
        Game game = lobby.getGame();
        int expectedPlayerNumber = 4;
        int actualPlayerNumber = 0;

        game.setNumberOfPlayers(expectedPlayerNumber);

        lobby.startGame();

        actualPlayerNumber = lobby.getPlayers().size();

        assertEquals(expectedPlayerNumber, actualPlayerNumber);

        assertTrue(lobby.isHasGameStarted());
        //Check if auto created Players are Ai (first player is not ari)
        assertFalse(lobby.getPlayers().get(0).isAI());
        assertTrue(lobby.getPlayers().get(1).isAI());
        assertTrue(lobby.getPlayers().get(2).isAI());
        assertTrue(lobby.getPlayers().get(3).isAI());
    }


    @Test
    void isPlayerInThatLobby() {
        String playerName = "Test_1";
        Lobby lobby = new Lobby(playerName);

        assertTrue(lobby.isPlayerInThatLobby(playerName));
    }

    @Test
    void isPlayerInThatLobby2() {
        String playerName = "Test_1";
        Lobby lobby = new Lobby(playerName);

        assertFalse(lobby.isPlayerInThatLobby("playerName"));
    }

    @Test
    void isPlayerInThatLobby3() {
        String playerName = "Test_1";
        String playerName2 = "tEST_1";
        Lobby lobby = new Lobby(playerName);

        assertFalse(lobby.isPlayerInThatLobby(playerName2));
    }

    @Test
    void getPlayerTest() {
        String playerName = "Test_1";
        Lobby lobby = new Lobby(playerName);

        Player expectedPlayer = lobby.getPlayers().get(0);
        Player result = lobby.getPlayer(playerName);

        assertEquals(expectedPlayer, result);
    }

    @Test
    void getPlayerTest2() {
        String playerName = "Test_1";
        Lobby lobby = new Lobby(playerName);

        assertThrows(NoSuchElementException.class, () -> {
            Player result = lobby.getPlayer("playerName");
        });
    }

    @Test
    void getWeakestPlayerTest() {
        String playerName1 = "Test_1";
        String playerName2 = "Test_2";
        String playerName3 = "Test_3";
        String playerName4 = "Test_4";

        Lobby lobby = new Lobby(playerName1);

        Player player1 = lobby.getPlayers().get(0);
        Player player2 = new Player(playerName2);
        Player player3 = new Player(playerName3);
        Player player4 = new Player(playerName4);

        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);

        player1.setDiceRanges(1, 10);
        player2.setDiceRanges(2, 3);
        player3.setDiceRanges(3, 5);
        player4.setDiceRanges(6, 7);

        Player result = lobby.getWeakestPlayer();

        assertEquals(result, player2);
    }

    @Test
    void getWeakestPlayerTest2() {
        String playerName1 = "Test_1";

        Lobby lobby = new Lobby(playerName1);

        Player player1 = lobby.getPlayers().get(0);

        player1.setDiceRanges(1, 10);

        Player result = lobby.getWeakestPlayer();

        assertEquals(result, player1);
    }

    @Test
    void getWeakestPlayerTest3() {
        String playerName1 = "Test_1";

        Lobby lobby = new Lobby(playerName1);

        lobby.getPlayers().remove(0);

        assertThrows(IllegalStateException.class, () -> {
            Player result = lobby.getWeakestPlayer();
        });
    }

    @Test
    void getIndexFromPlayerTest() {
        String playerName1 = "Test_1";
        String playerName2 = "Test_2";
        String playerName3 = "Test_3";
        String playerName4 = "Test_4";

        Lobby lobby = new Lobby(playerName1);

        Player player1 = lobby.getPlayers().get(0);
        Player player2 = new Player(playerName2);
        Player player3 = new Player(playerName3);
        Player player4 = new Player(playerName4);

        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);

        int exceedResult = 3;
        int actualResult = lobby.getIndexFromPlayer(player4);

        assertEquals(exceedResult, actualResult);
    }


    @Test
    void getIndexFromPlayerTes2() {
        String playerName1 = "Test_1";
        String playerName2 = "Test_2";
        String playerName3 = "Test_3";
        String playerName4 = "Test_4";

        Lobby lobby = new Lobby(playerName1);

        Player player1 = lobby.getPlayers().get(0);
        Player player2 = new Player(playerName2);
        Player player3 = new Player(playerName3);
        Player player4 = new Player(playerName4);

        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);

        assertThrows(NoSuchElementException.class, () -> {
            int actualResult = lobby.getIndexFromPlayer(new Player("1234"));
        });
    }


    @Test
    void getNumberOfAllResourcesFromAllPlayersTest() {
        String playerName1 = "Test_1";
        String playerName2 = "Test_2";
        String playerName3 = "Test_3";
        String playerName4 = "Test_4";

        Lobby lobby = new Lobby(playerName1);

        Player player1 = lobby.getPlayers().get(0);
        Player player2 = new Player(playerName2);
        Player player3 = new Player(playerName3);
        Player player4 = new Player(playerName4);

        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);

        player1.getResources().add(new Resource());
        player2.getResources().add(new Resource());
        player3.getResources().add(new Resource());
        player4.getResources().add(new Resource());

        int exceedResult = 4;
        int actualResult = lobby.getNumberOfAllResourcesFromAllPlayers();

        assertEquals(exceedResult, actualResult);
    }

    @Test
    void getNumberOfAllResourcesFromAllPlayersTest2() {
        String playerName1 = "Test_1";
        String playerName2 = "Test_2";
        String playerName3 = "Test_3";
        String playerName4 = "Test_4";

        Lobby lobby = new Lobby(playerName1);

        Player player1 = lobby.getPlayers().get(0);
        Player player2 = new Player(playerName2);
        Player player3 = new Player(playerName3);
        Player player4 = new Player(playerName4);

        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);

        int exceedResult = 0;
        int actualResult = lobby.getNumberOfAllResourcesFromAllPlayers();

        assertEquals(exceedResult, actualResult);
    }

    @Test
    void getNumberOfAllResourcesFromAllPlayersTest3() {
        String playerName1 = "Test_1";

        Lobby lobby = new Lobby(playerName1);

        lobby.getPlayers().remove(0);

        int exceedResult = 0;
        int actualResult = lobby.getNumberOfAllResourcesFromAllPlayers();

        assertEquals(exceedResult, actualResult);
    }


    @Test
    void convertToJSONTest() {

    }
}