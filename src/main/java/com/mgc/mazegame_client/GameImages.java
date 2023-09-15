package com.mgc.mazegame_client;

import javafx.scene.image.Image;

public class GameImages {
    public static final Image emptyElement = Draw.generateImage("/gameImages/empty.png");
    public static final Image player1 = Draw.generateImage("/gameImages/player1.png");
    public static final Image player2 = Draw.generateImage("/gameImages/player2.png");
    public static final Image player3 = Draw.generateImage("/gameImages/player3.png");
    public static final Image player4 = Draw.generateImage("/gameImages/player4.png");
    public static final Image beast = Draw.generateImage("/gameImages/beast.png");
    public static final Image wall = Draw.generateImage("/gameImages/wall.png");
    public static final Image bigTreasure = Draw.generateImage("/gameImages/bigTreasure.png");
    public static final Image smallTreasure = Draw.generateImage("/gameImages/smallTreasure.png");
    public static final Image coin = Draw.generateImage("/gameImages/coin.png");
    public static final Image droppedCoins = Draw.generateImage("/gameImages/droppedCoins.png");
    public static final Image visibleTest = Draw.generateImage("/gameImages/visibleTest.png");


    public static Image getPlayerImageByNumber(int playerNumber){
        if (playerNumber == 1){
            return player1;
        } else if (playerNumber == 2){
            return player2;
        } else if (playerNumber == 3){
            return player3;
        } else if (playerNumber == 4){
            return player4;
        }
        return player1;
    }
}
