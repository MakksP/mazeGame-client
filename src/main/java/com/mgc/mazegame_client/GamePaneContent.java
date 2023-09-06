package com.mgc.mazegame_client;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

public class GamePaneContent {
    public static final int RIGHT_GAME_PANE_WIDTH = 1250;
    public static final int PLAYERS_INFO_PANE_WIDTH = 350;
    public static final int SINGLE_PLAYER_PANE_WIDTH = 320;
    public static final int SINGLE_PLAYER_PANE_HEIGHT = 170;
    public static final int V_GAP_PLAYER_INFO_PANE = 10;
    public static int MAP_HEIGHT = 35;
    public static int MAP_WIDTH = 49;
    private FlowPane mainGamePane;
    private final GridPane rightGamePane;
    private final FlowPane playersInfoPane;

    public GamePaneContent(FlowPane mainGamePane, GridPane rightGamePane, FlowPane playersInfoPane){
        initMainGamePane(mainGamePane);
        this.rightGamePane = rightGamePane;
        setPaneProperties(this.rightGamePane, Color.BLACK, RIGHT_GAME_PANE_WIDTH);
        fillRightGamePaneWithEmptyBlocks(rightGamePane);
        this.playersInfoPane = playersInfoPane;
        setPaneProperties(this.playersInfoPane, Color.DIMGRAY, PLAYERS_INFO_PANE_WIDTH);
        setArrangementInPlayersInfoPane();
    }

    private void setArrangementInPlayersInfoPane() {
        this.playersInfoPane.setAlignment(Pos.TOP_CENTER);
        this.playersInfoPane.setVgap(V_GAP_PLAYER_INFO_PANE);
    }

    private static void fillRightGamePaneWithEmptyBlocks(GridPane rightGamePane) {
        Image empty = Draw.generateImage("/gameImages/empty.png");
        for (int rowIndex = 0; rowIndex < MAP_HEIGHT; rowIndex++){
            for (int columnIndex = 0; columnIndex < MAP_WIDTH; columnIndex++) {
                rightGamePane.add(new ImageView(empty), columnIndex, rowIndex);
            }
        }
    }

    private void setPaneProperties(Pane pane, Color color, int width) {
        pane.setPrefWidth(width);
        pane.setPrefHeight(ClientRun.SCREEN_HEIGHT);
        pane.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    private void initMainGamePane(FlowPane mainGamePane) {
        this.mainGamePane = mainGamePane;
        this.mainGamePane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    }


    public void updatePlayersInfoPane(List<Player> playerList){
        if (!playersInfoPaneIsEmpty()){
            this.playersInfoPane.getChildren().clear();
        }

        for (Player player : playerList){
            FlowPane playerPane = initSinglePlayerPane();
            ImageView playerImageView = getPlayerImageView(player);
            playerPane.getChildren().add(playerImageView);
            playersInfoPane.getChildren().add(playerPane);
        }
    }

    private boolean playersInfoPaneIsEmpty() {
        return this.playersInfoPane.getChildren().size() == 0;
    }

    private static ImageView getPlayerImageView(Player player) {
        Image playerImage = Draw.generateImage("/gameImages/player" + player.getNumber() + ".png");
        ImageView playerImageView = new ImageView(playerImage);
        return playerImageView;
    }

    private static FlowPane initSinglePlayerPane() {
        FlowPane playerPane = new FlowPane();
        playerPane.setPrefSize(SINGLE_PLAYER_PANE_WIDTH, SINGLE_PLAYER_PANE_HEIGHT);
        playerPane.setBackground(new Background(new BackgroundFill(Color.DARKGOLDENROD, null, null)));
        return playerPane;
    }

    public FlowPane getMainGamePane() {
        return mainGamePane;
    }

    public GridPane getRightGamePane() {
        return rightGamePane;
    }

    public FlowPane getPlayersInfoPane() {
        return playersInfoPane;
    }
}
