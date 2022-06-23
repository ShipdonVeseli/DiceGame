package com.example.dicegame.game;

import com.example.dicegame.Lobby;
import com.example.dicegame.Player;
import com.example.dicegame.gameSatistic.StatisticObserver;
import com.example.dicegame.gameSatistic.Statistics;
import com.example.dicegame.random.DiceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @BeforeEach
    void setUp() {
        DiceManager diceManager = DiceManager.getInstanz();
    }

    @Test
    void testVote() {
        try {
            int gameMode = 4;
            int numberOfPlayers = 3;

            String playerName1 = "Test1";
            String playerName2 = "Test2";
            String playerName3 = "Test3";

            Lobby lobby = new Lobby(playerName1);
            Game game = lobby.getGame();


            Player player2 = new Player(playerName2);
            Player player3 = new Player(playerName3);

            lobby.getPlayers().add(player2);
            lobby.getPlayers().add(player3);

            game.setGameMode(gameMode);
            game.setNumberOfPlayers(numberOfPlayers);

            lobby.startGame();
            game.voteForPlayer(playerName1, playerName2, 0);
            String result = lobby.getVotesInJson();

            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testConvertToJson() {
        Lobby lobby = new Lobby("Test User");
        Game game = new Game(lobby);

        String actualResult = game.convertToJSON();

        System.out.println(actualResult);
    }

    @Test
    void testConvertToJson2() {
        Lobby lobby = new Lobby("Test User");
        Game game = new Game(lobby);
        game.move();
        String actualResult = game.convertToJSON();

        System.out.println(actualResult);
    }

    @Test
    void testConvertToJson3() {
        Lobby lobby = new Lobby("Test User");
        lobby.addPlayer(new Player("lucas"));
        Game game = new Game(lobby);
        game.move();
        String actualResult = game.convertToJSON();

        System.out.println(actualResult);
    }


    @Test
    void addNewResourcesTest() {
        Player p1 = new Player("Test1");
        Player p2 = new Player("Test2");
        Lobby lobby = new Lobby(p1);
        lobby.addPlayer(p2);
        Game game = new Game(1, lobby);

        int expected = p1.getSummOfDiceValues();
        System.out.println("anz= " + expected);

        game.addNewResources();

        int result = p1.getResources().size();

        assertEquals(result, expected);
    }


    @Test
    void moveResourcesToStorageTest() {
        Player p1 = new Player("Test1");
        Player p2 = new Player("Test2");
        Lobby lobby = new Lobby(p1);
        lobby.addPlayer(p2);
        Game game = new Game(1, lobby);

        ArrayList<Resource> resourcesP2 = new ArrayList<>();
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());

        p2.addResources(resourcesP2);

        System.out.println("p2 res size= " + p2.getResources().size());
        int expectedNumberOfResurcesINP2 = p2.getResources().size() - p2.getSummOfDiceValues();
        int expectedNumberOfResurcesINPStorage = p2.getSummOfDiceValues();


        game.moveResourcesToStorage();

        int actuallNumberOfResurcesINP2 = p2.getResources().size();
        int actuallNumberOfResurcesINPStorage = game.getStorage().size();
        System.out.println("Dice= " + p2.getSummOfDiceValues());
        System.out.println("expectedNumberOfResurcesINP2= " + expectedNumberOfResurcesINP2 + "\nactuallNumberOfResurcesINP2= " + actuallNumberOfResurcesINP2);
        System.out.println("expectedNumberOfResurcesINPStorage= " + expectedNumberOfResurcesINPStorage + "\nactuallNumberOfResurcesINPStorage= " + actuallNumberOfResurcesINPStorage);

        assertEquals(expectedNumberOfResurcesINP2, actuallNumberOfResurcesINP2);
        assertEquals(expectedNumberOfResurcesINPStorage, actuallNumberOfResurcesINPStorage);

    }


    @Test
    void moveResourcesToStorageTest2() {
        Player p1 = new Player("Test1");
        Player p2 = new Player("Test2");
        Lobby lobby = new Lobby(p1);
        lobby.addPlayer(p2);
        Game game = new Game(1, lobby);

        ArrayList<Resource> resourcesP2 = new ArrayList<>();


        p2.addResources(resourcesP2);

        System.out.println("p2 res size= " + p2.getResources().size());
        int expectedNumberOfResurcesINP2 = 0;
        int expectedNumberOfResurcesINPStorage = 0;


        game.moveResourcesToStorage();

        int actuallNumberOfResurcesINP2 = p2.getResources().size();
        int actuallNumberOfResurcesINPStorage = game.getStorage().size();
        System.out.println("Dice= " + p2.getSummOfDiceValues());
        System.out.println("expectedNumberOfResurcesINP2= " + expectedNumberOfResurcesINP2 + "\nactuallNumberOfResurcesINP2= " + actuallNumberOfResurcesINP2);
        System.out.println("expectedNumberOfResurcesINPStorage= " + expectedNumberOfResurcesINPStorage + "\nactuallNumberOfResurcesINPStorage= " + actuallNumberOfResurcesINPStorage);

        assertEquals(expectedNumberOfResurcesINP2, actuallNumberOfResurcesINP2);
        assertEquals(expectedNumberOfResurcesINPStorage, actuallNumberOfResurcesINPStorage);

    }


    @Test
    void moveResourcesToStorageTest3() {
        Player p1 = new Player("Test1");
        Player p2 = new Player("Test2");
        Lobby lobby = new Lobby(p1);
        lobby.addPlayer(p2);
        Game game = new Game(1, lobby);

        ArrayList<Resource> resourcesP2 = new ArrayList<>();
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());


        p2.getDices().get(0).setValueForTests(4);

        p2.addResources(resourcesP2);

        System.out.println("p2 res size= " + p2.getResources().size());
        int expectedNumberOfResurcesINP2 = 0;
        int expectedNumberOfResurcesINPStorage = 2;


        game.moveResourcesToStorage();

        int actuallNumberOfResurcesINP2 = p2.getResources().size();
        int actuallNumberOfResurcesINPStorage = game.getStorage().size();
        System.out.println("Dice= " + p2.getSummOfDiceValues());
        System.out.println("expectedNumberOfResurcesINP2= " + expectedNumberOfResurcesINP2 + "\nactuallNumberOfResurcesINP2= " + actuallNumberOfResurcesINP2);
        System.out.println("expectedNumberOfResurcesINPStorage= " + expectedNumberOfResurcesINPStorage + "\nactuallNumberOfResurcesINPStorage= " + actuallNumberOfResurcesINPStorage);

        assertEquals(expectedNumberOfResurcesINP2, actuallNumberOfResurcesINP2);
        assertEquals(expectedNumberOfResurcesINPStorage, actuallNumberOfResurcesINPStorage);

    }

    @Test
    void moveResourcesTest() {
        Player p1 = new Player("Test1");
        Player p2 = new Player("Test2");
        Player p3 = new Player("Test3");
        Player p4 = new Player("Test4");

        Lobby lobby = new Lobby(p1);
        lobby.addPlayer(p2);
        lobby.addPlayer(p3);
        lobby.addPlayer(p4);

        ArrayList<Resource> resourcesP3 = new ArrayList<>();
        resourcesP3.add(new Resource());
        resourcesP3.add(new Resource());

        ArrayList<Resource> resourcesP2 = new ArrayList<>();
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());

        p2.addResources(resourcesP2);
        p3.addResources(resourcesP3);

        p2.getDices().get(0).setValueForTests(3);
        p3.getDices().get(0).setValueForTests(2);

        int expetedP2ResourceCount = 1;
        int expetedP3ResourceCount = 3;

        Game game = new Game(1, lobby);
        System.out.println(game);
        System.out.println();
        game.moveResources();
        System.out.println();
        System.out.println(game);

        int actualP2ResourceCount = p2.getResources().size();
        int actualP3ResourceCount = p3.getResources().size();
        System.out.println(actualP2ResourceCount);
        System.out.println(actualP3ResourceCount);

        assertEquals(expetedP2ResourceCount, actualP2ResourceCount);
        assertEquals(expetedP3ResourceCount, actualP3ResourceCount);


    }


    @Test
    void game6ConfigCheckTest() {
        Lobby lobby;
        Game game;
        Boolean result;
        String playerName1="test1";
        String playerName2="test2";
        String playerName3="test3";
        String playerName4="test4";

        Player player1;
        Player player2 =new Player(playerName2);
        Player player3 =new Player(playerName3);
        Player player4 =new Player(playerName4);

        lobby=new Lobby(playerName1);
        player1=lobby.getPlayer(0);
        lobby.addPlayer(player2);
        lobby.addPlayer(player3);
        lobby.addPlayer(player4);

        game=new Game(6,lobby);

        player1.getDices().get(0).setRange(1,6);
        player2.getDices().get(0).setRange(2,6);
        player3.getDices().get(0).setRange(2,6);
        player4.getDices().get(0).setRange(2,6);

        assertTrue(game.game6ConfigCheck());
    }

    @Test
    void game6ConfigCheckTest2() {
        Lobby lobby;
        Game game;
        Boolean result;
        String playerName1="test1";
        String playerName2="test2";
        String playerName3="test3";
        String playerName4="test4";

        Player player1;
        Player player2 =new Player(playerName2);
        Player player3 =new Player(playerName3);
        Player player4 =new Player(playerName4);

        lobby=new Lobby(playerName1);
        player1=lobby.getPlayer(0);
        lobby.addPlayer(player2);
        lobby.addPlayer(player3);
        lobby.addPlayer(player4);

        game=new Game(1,lobby);

        player1.getDices().get(0).setRange(1,6);
        player2.getDices().get(0).setRange(2,6);
        player3.getDices().get(0).setRange(2,6);
        player4.getDices().get(0).setRange(2,6);

        assertFalse(game.game6ConfigCheck());
    }

    @Test
    void game6ConfigCheckTest3() {
        Lobby lobby;
        Game game;
        Boolean result;
        String playerName1="test1";
        String playerName2="test2";
        String playerName3="test3";
        String playerName4="test4";

        Player player1;
        Player player2 =new Player(playerName2);
        Player player3 =new Player(playerName3);
        Player player4 =new Player(playerName4);

        lobby=new Lobby(playerName1);
        player1=lobby.getPlayer(0);
        lobby.addPlayer(player2);
        lobby.addPlayer(player3);
        lobby.addPlayer(player4);

        game=new Game(6,lobby);

        player1.getDices().get(0).setRange(1,6);
        player2.getDices().get(0).setRange(1,6);
        player3.getDices().get(0).setRange(2,6);
        player4.getDices().get(0).setRange(2,6);

        assertFalse(game.game6ConfigCheck());
    }

    @Test
    void game6ConfigCheckTest4() {
        Lobby lobby;
        Game game;

        String playerName1="test1";
        String playerName2="test2";
        String playerName3="test3";
        String playerName4="test4";

        Player player1;
        Player player2 =new Player(playerName2);
        Player player3 =new Player(playerName3);
        Player player4 =new Player(playerName4);

        lobby=new Lobby(playerName1);
        player1=lobby.getPlayer(0);
        lobby.addPlayer(player2);
        lobby.addPlayer(player3);
        lobby.addPlayer(player4);

        game=new Game(6,lobby);

        player1.getDices().get(0).setRange(2,6);
        player2.getDices().get(0).setRange(2,6);
        player3.getDices().get(0).setRange(2,6);
        player4.getDices().get(0).setRange(2,6);

        assertFalse(game.game6ConfigCheck());
    }

    @Test
    void deleteTest(){
        //ToDo
    }

    @Test
    void initTest(){
        Lobby lobby;
        Game game;

        String playerName1="test1";
        String playerName2="test2";
        String playerName3="test3";
        String playerName4="test4";

        Player player1;
        Player player2 =new Player(playerName2);
        Player player3 =new Player(playerName3);
        Player player4 =new Player(playerName4);

        lobby=new Lobby(playerName1);
        player1=lobby.getPlayer(0);
        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);


        game=new Game(1,lobby);

        game.init();

        Statistics actuallStatistics=game.getStatistics();

        StatisticObserver p1statisic=player1.getObservers().get(0);
        StatisticObserver p2statisic=player2.getObservers().get(0);
        StatisticObserver p3statisic=player3.getObservers().get(0);
        StatisticObserver p4statisic=player4.getObservers().get(0);

        assertNotNull(actuallStatistics);
        assertNotNull(p1statisic);
        assertNotNull(p2statisic);
        assertNotNull(p3statisic);
        assertNotNull(p4statisic);
    }

    @Test
    void initTest2(){
        Lobby lobby;
        Game game;

        String playerName1="test1";
        String playerName2="test2";
        String playerName3="test3";
        String playerName4="test4";

        Player player1;
        Player player2 =new Player(playerName2);
        Player player3 =new Player(playerName3);
        Player player4 =new Player(playerName4);

        lobby=new Lobby(playerName1);
        player1=lobby.getPlayer(0);
        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);


        game=new Game(1,lobby);

        game.init();

        Statistics actuallStatistics=game.getStatistics();

        StatisticObserver p1statisic=player1.getObservers().get(0);
        StatisticObserver p2statisic=player2.getObservers().get(0);
        StatisticObserver p3statisic=player3.getObservers().get(0);
        StatisticObserver p4statisic=player4.getObservers().get(0);


        assertEquals(actuallStatistics,p1statisic);
        assertEquals(actuallStatistics,p2statisic);
        assertEquals(actuallStatistics,p3statisic);
        assertEquals(actuallStatistics,p4statisic);
    }

    @Test
    void addStartResourcesTest(){
        Lobby lobby;
        Game game;

        ArrayList<Resource>expectedResources=new ArrayList<>();

        String playerName1="test1";
        String playerName2="test2";
        String playerName3="test3";
        String playerName4="test4";

        Player player1;
        Player player2 =new Player(playerName2);
        Player player3 =new Player(playerName3);
        Player player4 =new Player(playerName4);

        lobby=new Lobby(playerName1);
        player1=lobby.getPlayer(0);
        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);

        expectedResources.add(new Resource(false));
        expectedResources.add(new Resource(false));
        expectedResources.add(new Resource(false));
        expectedResources.add(new Resource(false));

        game=new Game(1,lobby);

        game.addStartResources();


        assertIterableEquals(expectedResources,player1.getResources());
        assertIterableEquals(expectedResources,player2.getResources());
        assertIterableEquals(expectedResources,player3.getResources());
        assertIterableEquals(new ArrayList<Resource>(),player4.getResources());


    }

    @Test
    void addStartResourcesTest2(){
        Lobby lobby;
        Game game;

        String playerName1="test1";

        Player player1;

        lobby=new Lobby(playerName1);
        player1=lobby.getPlayer(0);

        game=new Game(1,lobby);

        game.addStartResources();

        assertIterableEquals(new ArrayList<Resource>(),player1.getResources());
    }

    @Test
    void addStartResourcesTest3(){
        try {

            Lobby lobby;
            Game game;

            String playerName1 = "test1";

            Player player1;

            lobby = new Lobby(playerName1);
            player1 = lobby.getPlayer(0);

            lobby.getPlayers().remove(0);
            System.out.println("player count= "+lobby.getPlayers().size());
            game = new Game(1, lobby);

            game.addStartResources();

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void resetTest(){
        //ToDo
    }

    @Test
    void checkIfPlayerIsActivePlayerTest(){
        Lobby lobby;
        Game game;

       boolean actualResult;

        String playerName1="test1";
        String playerName2="test2";
        String playerName3="test3";
        String playerName4="test4";

        Player player1;
        Player player2 =new Player(playerName2);
        Player player3 =new Player(playerName3);
        Player player4 =new Player(playerName4);

        lobby=new Lobby(playerName1);
        player1=lobby.getPlayer(0);
        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);

        game=new Game(1,lobby);

        actualResult= game.checkIfPlayerIsActivePlayer(playerName1);

        assertTrue(actualResult);
    }

    @Test
    void checkIfPlayerIsActivePlayerTest2(){
        Lobby lobby;
        Game game;

        boolean actualResult;

        String playerName1="test1";
        String playerName2="test2";
        String playerName3="test3";
        String playerName4="test4";

        Player player1;
        Player player2 =new Player(playerName2);
        Player player3 =new Player(playerName3);
        Player player4 =new Player(playerName4);

        lobby=new Lobby(playerName1);
        player1=lobby.getPlayer(0);
        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);

        game=new Game(1,lobby);

        actualResult= game.checkIfPlayerIsActivePlayer(playerName2);

        assertFalse(actualResult);
    }

    @Test
    void checkIfPlayerIsActivePlayerTest3(){
        Lobby lobby;
        Game game;

        boolean actualResult;

        String playerName1="test1";
        String playerName2="test2";
        String playerName3="test3";
        String playerName4="test4";

        Player player1;
        Player player2 =new Player(playerName2);
        Player player3 =new Player(playerName3);
        Player player4 =new Player(playerName4);

        lobby=new Lobby(playerName1);
        player1=lobby.getPlayer(0);
        lobby.getPlayers().add(player2);
        lobby.getPlayers().add(player3);
        lobby.getPlayers().add(player4);

        game=new Game(1,lobby);

        actualResult= game.checkIfPlayerIsActivePlayer("test 1234");

        assertFalse(actualResult);
    }

    @Test
    void rollDicesFromOnePlayerTest(){
        //ToDo
    }

    @Test
    void rollAllDiceInGameTest(){
        //ToDo
    }

    @Test
    void giveDiceToOtherPlayerTest(){
        //ToDo
    }

    @Test
    void setDiceRangeFromPlayerTest(){
        //ToDo
    }

    @Test
    void moveTest(){
        //ToDo
    }

    @Test
    void resetPlayerForTheNextRoundTest(){
        //ToDo
    }

    @Test
    void changeActivePlayerTest(){
        //ToDo
    }

    @Test
    void aiRoundTest(){
        //ToDo
    }

    @Test
    void getWeakestLinkTest(){
        //ToDo
    }

    @Test
    void initGameMode4Test(){
        //ToDo
    }

    @Test
    void voteForPlayerTest(){
        //ToDo
    }

    @Test
    void timerTaskTest(){
        //ToDo
    }

    @Test
    void statisticValuesSavingTest(){
        //ToDo
    }



}