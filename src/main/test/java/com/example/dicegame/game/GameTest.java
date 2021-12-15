package com.example.dicegame.game;

import com.example.dicegame.Lobby;
import com.example.dicegame.Player;
import com.example.dicegame.random.DiceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @BeforeEach
    void setUp() {
        DiceManager diceManager=DiceManager.getInstanz();
    }


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


    @Test
    void moveResourcesToStorageTest(){
        Player p1=new Player("Test1");
        Player p2 =new Player("Test2");
        Lobby lobby=new Lobby(p1);
        lobby.addPlayer(p2);
        Game game=new Game(lobby);

        ArrayList<Resource>resourcesP2=new ArrayList<>();
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());

        p2.addResources(resourcesP2);

        System.out.println("p2 res size= "+p2.getResources().size());
        int expectedNumberOfResurcesINP2=p2.getResources().size()-p2.getSummOfDiceValues();
        int expectedNumberOfResurcesINPStorage=p2.getSummOfDiceValues();



        game.moveResourcesToStorage();

        int actuallNumberOfResurcesINP2=p2.getResources().size();
        int actuallNumberOfResurcesINPStorage=game.getStorage().size();
        System.out.println("Dice= "+p2.getSummOfDiceValues());
        System.out.println("expectedNumberOfResurcesINP2= "+expectedNumberOfResurcesINP2+"\nactuallNumberOfResurcesINP2= "+actuallNumberOfResurcesINP2);
        System.out.println("expectedNumberOfResurcesINPStorage= "+expectedNumberOfResurcesINPStorage+"\nactuallNumberOfResurcesINPStorage= "+actuallNumberOfResurcesINPStorage);

        assertEquals(expectedNumberOfResurcesINP2,actuallNumberOfResurcesINP2);
        assertEquals(expectedNumberOfResurcesINPStorage,actuallNumberOfResurcesINPStorage);

    }



    @Test
    void moveResourcesToStorageTest2(){
        Player p1=new Player("Test1");
        Player p2 =new Player("Test2");
        Lobby lobby=new Lobby(p1);
        lobby.addPlayer(p2);
        Game game=new Game(lobby);

        ArrayList<Resource>resourcesP2=new ArrayList<>();


        p2.addResources(resourcesP2);

        System.out.println("p2 res size= "+p2.getResources().size());
        int expectedNumberOfResurcesINP2=0;
        int expectedNumberOfResurcesINPStorage=0;



        game.moveResourcesToStorage();

        int actuallNumberOfResurcesINP2=p2.getResources().size();
        int actuallNumberOfResurcesINPStorage=game.getStorage().size();
        System.out.println("Dice= "+p2.getSummOfDiceValues());
        System.out.println("expectedNumberOfResurcesINP2= "+expectedNumberOfResurcesINP2+"\nactuallNumberOfResurcesINP2= "+actuallNumberOfResurcesINP2);
        System.out.println("expectedNumberOfResurcesINPStorage= "+expectedNumberOfResurcesINPStorage+"\nactuallNumberOfResurcesINPStorage= "+actuallNumberOfResurcesINPStorage);

        assertEquals(expectedNumberOfResurcesINP2,actuallNumberOfResurcesINP2);
        assertEquals(expectedNumberOfResurcesINPStorage,actuallNumberOfResurcesINPStorage);

    }


    @Test
    void moveResourcesToStorageTest3(){
        Player p1=new Player("Test1");
        Player p2 =new Player("Test2");
        Lobby lobby=new Lobby(p1);
        lobby.addPlayer(p2);
        Game game=new Game(lobby);

        ArrayList<Resource>resourcesP2=new ArrayList<>();
        resourcesP2.add(new Resource());
        resourcesP2.add(new Resource());


        p2.getDices().get(0).setValueForTests(4);

        p2.addResources(resourcesP2);

        System.out.println("p2 res size= "+p2.getResources().size());
        int expectedNumberOfResurcesINP2=0;
        int expectedNumberOfResurcesINPStorage=2;



        game.moveResourcesToStorage();

        int actuallNumberOfResurcesINP2=p2.getResources().size();
        int actuallNumberOfResurcesINPStorage=game.getStorage().size();
        System.out.println("Dice= "+p2.getSummOfDiceValues());
        System.out.println("expectedNumberOfResurcesINP2= "+expectedNumberOfResurcesINP2+"\nactuallNumberOfResurcesINP2= "+actuallNumberOfResurcesINP2);
        System.out.println("expectedNumberOfResurcesINPStorage= "+expectedNumberOfResurcesINPStorage+"\nactuallNumberOfResurcesINPStorage= "+actuallNumberOfResurcesINPStorage);

        assertEquals(expectedNumberOfResurcesINP2,actuallNumberOfResurcesINP2);
        assertEquals(expectedNumberOfResurcesINPStorage,actuallNumberOfResurcesINPStorage);

    }



}