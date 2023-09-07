package com.mgc.mazegame_client;

public class Player {
    public Cords playerCords;
    public Cords spawnPoint;
    public int points;
    public int storedPoints;
    public int number;
    public int deaths;
    public String name;
    public char standsOn;

    public Player(Cords cords, int playerNumber, String nick){
        this.playerCords = cords;
        this.spawnPoint = cords;
        points = 0;
        storedPoints = 0;
        number = playerNumber;
        deaths = 0;
        name = nick;
        standsOn = ' ';
    }

    public Player(){

    }

}
