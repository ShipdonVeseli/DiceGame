package com.example.dicegame.servlets;

import com.example.dicegame.random.HttpSend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LobbyServletTest {


    @Test
    public void testCreateLobby() {
        try {

            HttpSend httpSend = new HttpSend("http://localhost:8079/Lobby-servlet");
            httpSend.setHttpMethode("GET");

            httpSend.addParameter("mode", "create");
            httpSend.addParameter("username", "Test-User");

            httpSend.init();

            int code=httpSend.getStatus();
            httpSend.start();

            System.out.println(httpSend.getUrl());
            System.out.println(code);


        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }


    }

}