package com.example.dicegame;


public class GameServer {
    private static GameServer gameServer;
    private Lobbymanager lobbymanager = new Lobbymanager();

    private GameServer() {
        this.lobbymanager = new Lobbymanager();
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
