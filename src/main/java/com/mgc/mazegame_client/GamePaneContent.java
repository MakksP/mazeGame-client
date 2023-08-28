package com.mgc.mazegame_client;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GamePaneContent {
    public static final int RIGHT_GAME_PANE_WIDTH = 1250;
    public static final int PLAYERS_INFO_PANE_WIDTH = 350;
    public static int MAP_HEIGHT = 36;
    public static int MAP_WIDTH = 76;
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

    }

    private static void fillRightGamePaneWithEmptyBlocks(GridPane rightGamePane) {
        for (int rowIndex = 0; rowIndex < MAP_HEIGHT; rowIndex++){
            for (int columnIndex = 0; columnIndex < MAP_WIDTH; columnIndex++) {
                rightGamePane.add(Draw.generateImage("/gameImages/empty.png"), rowIndex, columnIndex);
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
