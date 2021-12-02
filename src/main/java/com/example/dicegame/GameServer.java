package com.example.dicegame;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

public class GameServer {
    private static GameServer gameServer;
    private Lobbymanager lobbymanager;

    private GameServer() {
    }

    public static GameServer getInstance() {
        if (gameServer == null) {
            gameServer = new GameServer();
        }
        return gameServer;
    }

    public Lobbymanager getLobbymanager() {
        return lobbymanager;
    }

    public void setLobbymanager(Lobbymanager lobbymanager) {
        this.lobbymanager = lobbymanager;
    }


}
