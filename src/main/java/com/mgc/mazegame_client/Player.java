package com.mgc.mazegame_client;

import java.util.List;

public class Player {
    public Cords playerCords;
    public Cords spawnPoint;
    public int points;
    public int storedPoints;
    public int number;
    public int deaths;
    public String name;
    public char standsOn;
    public boolean knowCampsiteLocation;

    public Player(Cords cords, int playerNumber, String nick){
        this.playerCords = cords;
        this.spawnPoint = cords;
        points = 0;
        storedPoints = 0;
        number = playerNumber;
        deaths = 0;
        name = nick;
        standsOn = ' ';
        knowCampsiteLocation = false;
    }

    public Player(){

    }

    public static int convertCharToIntPlayerNumber(char number) {
        return number - '0';
    }

    public static Player findPlayerInPlayersList(List<Player> playerList, int id){
        for (Player player : playerList){
            if (player.number == id){
                return player;
            }
        }
        return null;
    }
}
