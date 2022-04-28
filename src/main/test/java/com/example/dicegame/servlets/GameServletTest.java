package com.example.dicegame.servlets;

import com.example.dicegame.GameServer;
import com.example.dicegame.Lobby;
import com.example.dicegame.Player;
import com.example.dicegame.game.Game;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServletTest extends Mockito {


    @Test
    public void testserv(){
        try {
            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);

            String playerName1="test 1";
            String playerName2="test 2";

            Player player1;
            Player player2=new Player(playerName2);
            Lobby lobby = new Lobby(playerName1);
            lobby.addPlayer(player2);
            player1=lobby.getPlayers().get(0);


            GameServer gameServer=GameServer.getInstance();
            gameServer.getLobbymanager().getLobbies().add(lobby);
            Map<String, String[]> map=new HashMap<>();

            String [] strArray1=new String[1];
            strArray1[0]=String.valueOf(lobby.getId());
            String [] strArray2=new String[1];
            strArray2[0]=playerName1;
            String [] strArray3=new String[1];
            strArray3[0]="start-game";


            map.put("lobbyID",strArray1);
            map.put("username",strArray2);
            map.put("mode",strArray3);
            when(request.getParameterMap()).thenReturn(map);

            GameServlet gameServlet= new GameServlet();
            gameServlet.doGet(request, response);

            Map<String, String[]> map2=new HashMap<>();

            String [] strArrayx=new String[1];
            strArray1[0]=String.valueOf(lobby.getId());
            String [] strArrayy=new String[1];
            strArray2[0]=playerName1;
            String [] strArrayz=new String[1];
            strArray3[0]="roll-all";


            map2.put("lobbyID",strArrayx);
            map2.put("username",strArrayy);
            map2.put("mode",strArrayz);


            when(request.getParameterMap()).thenReturn(map);
            gameServlet.doGet(request, response);


            String result =lobby.getGame().getStatistics().getActivity2();
            System.out.println(result);



        }catch (Exception e){
            e.printStackTrace();
        }
    }

}