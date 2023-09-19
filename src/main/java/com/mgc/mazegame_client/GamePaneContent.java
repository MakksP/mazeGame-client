package com.mgc.mazegame_client;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePaneContent {
    public static final int RIGHT_GAME_PANE_WIDTH = 1250;
    public static final int PLAYERS_INFO_PANE_WIDTH = 350;
    public static final int SINGLE_PLAYER_PANE_WIDTH = 320;
    public static final int SINGLE_PLAYER_PANE_HEIGHT = 170;
    public static final int V_GAP_PLAYER_INFO_PANE = 10;
    public static final int PLAYER_PANE_PADDING = 10;
    public static final int PLAYER_DETAILS_TEXT_SIZE = 18;
    public static final int MAX_PLAYERS_COUNT = 4;
    public static final int INFO_PANE_HEIGHT = 160;
    public static final int INFO_PANE_WIDTH = 320;
    public static final int INFO_PANE_HGAP = 5;
    public static final int LEGEND_LABEL_FONT_SIZE = 15;
    public static final int YOUR_INFO_COLUMN_INDEX = 0;
    public static final int YOUR_INFO_ROW_INDEX = 4;
    public static int MAP_HEIGHT = 35;
    public static int MAP_WIDTH = 49;
    private FlowPane mainGamePane;
    private final GridPane rightGamePane;
    private final FlowPane playersInfoPane;
    private final GridPane infoPane;
    private final FlowPane playerPanesContainer;

    public GamePaneContent(FlowPane mainGamePane, GridPane rightGamePane, FlowPane playersInfoPane){
        initMainGamePane(mainGamePane);
        this.rightGamePane = rightGamePane;
        setPaneProperties(this.rightGamePane, Color.BLACK, RIGHT_GAME_PANE_WIDTH);
        fillRightGamePaneWithEmptyBlocks(rightGamePane);
        this.playersInfoPane = playersInfoPane;
        setPaneProperties(this.playersInfoPane, Color.DIMGRAY, PLAYERS_INFO_PANE_WIDTH);
        setArrangementInPlayersInfoPane();
        playerPanesContainer = initPlayerPanesContainer();
        playersInfoPane.getChildren().add(playerPanesContainer);
        infoPane = initInfoPane();
        playersInfoPane.getChildren().add(infoPane);
    }

    private void setArrangementInPlayersInfoPane() {
        this.playersInfoPane.setAlignment(Pos.TOP_CENTER);
        this.playersInfoPane.setVgap(V_GAP_PLAYER_INFO_PANE);
    }

    private static void fillRightGamePaneWithEmptyBlocks(GridPane rightGamePane) {
        for (int rowIndex = 0; rowIndex < MAP_HEIGHT; rowIndex++){
            for (int columnIndex = 0; columnIndex < MAP_WIDTH; columnIndex++) {
                rightGamePane.add(new ImageView(GameImages.emptyElement), columnIndex, rowIndex);
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
        clearPlayerPanesContainerIfItIsNotEmpty();
        for (Player player : playerList){
            FlowPane playerPane = initSinglePlayerPane();
            ImageView playerImageView = getPlayerImageView(player);
            playerPane.getChildren().add(playerImageView);
            Label playerDetails = initPlayerDetails(player);
            playerPane.getChildren().add(playerDetails);
            playerPanesContainer.getChildren().add(playerPane);
        }
    }

    public void updateInfoPane(String yourId, List<Player> playerList){
        if (infoPaneHasYourId()){
            infoPane.getChildren().remove(getElementFromInfoPaneById("YOUR_INFO"));
        }
        int yourIdInt = Player.convertCharToIntPlayerNumber(yourId.charAt(0));
        Player you = Player.findPlayerInPlayersList(playerList, yourIdInt);
        String infoAboutYou = "Current cords: (" + you.playerCords.getX() + ", " + you.playerCords.getY() + ")";
        if (you.knowCampsiteLocation){
            infoAboutYou += "\nCampsite location: (" + PlayButton.campsiteLocation.getX() + ", " + PlayButton.campsiteLocation.getY() + ")";
        }
        Label yourInfoLabel = new Label(infoAboutYou);
        yourInfoLabel.setTextFill(Color.BLACK);
        yourInfoLabel.setFont(new Font(LEGEND_LABEL_FONT_SIZE));
        yourInfoLabel.setId("YOUR_INFO");
        infoPane.add(yourInfoLabel, YOUR_INFO_COLUMN_INDEX, YOUR_INFO_ROW_INDEX);
    }

    private boolean infoPaneHasYourId(){
        for (Node element : infoPane.getChildren()){
            if (element.getId() != null){
                if (element.getId().equals("YOUR_INFO")){
                    return true;
                }
            }
        }
        return false;
    }

    private Node getElementFromInfoPaneById(String id){
        for (Node element : infoPane.getChildren()){
            if (element.getId() != null){
                if (element.getId().equals("YOUR_INFO")){
                    return element;
                }
            }
        }
        return null;
    }

    private static GridPane initInfoPane() {
        GridPane infoPane = new GridPane();
        infoPane.setPrefSize(INFO_PANE_WIDTH, INFO_PANE_HEIGHT);
        infoPane.setHgap(INFO_PANE_HGAP);
        List<String> legendTags = List.of("Beast", "Players", "Valuable items", "Campsite");
        Map<String, List<ImageView>> legendElements = initLegendElements();
        int rowCounter = 0;
        for (String legendTag : legendTags){
            int columnCounter = 0;
            Label legendLabel = new Label(legendTag);
            legendLabel.setTextFill(Color.WHITE);
            legendLabel.setFont(new Font(LEGEND_LABEL_FONT_SIZE));
            infoPane.add(legendLabel, columnCounter, rowCounter);
            for (ImageView legendElement : legendElements.get(legendTag)){
                columnCounter++;
                infoPane.add(legendElement, columnCounter, rowCounter);
            }
            rowCounter++;

        }

        return infoPane;
    }

    private static Map<String, List<ImageView>> initLegendElements() {
        Map<String, List<ImageView>> legendElements = new HashMap<>();
        legendElements.put("Beast", List.of(new ImageView(GameImages.beast)));
        legendElements.put("Players", List.of(new ImageView(GameImages.player1), new ImageView(GameImages.player2), new ImageView(GameImages.player3), new ImageView(GameImages.player4)));
        legendElements.put("Valuable items", List.of(new ImageView(GameImages.coin), new ImageView(GameImages.smallTreasure), new ImageView(GameImages.bigTreasure)));
        legendElements.put("Campsite", List.of(new ImageView(GameImages.campsite)));
        return legendElements;
    }

    private static FlowPane initPlayerPanesContainer() {
        FlowPane playerPanesContainer = new FlowPane();
        playerPanesContainer.setPrefSize(SINGLE_PLAYER_PANE_WIDTH, (SINGLE_PLAYER_PANE_HEIGHT + PLAYER_PANE_PADDING) * MAX_PLAYERS_COUNT);
        playerPanesContainer.setVgap(PLAYER_PANE_PADDING);
        return playerPanesContainer;
    }

    private static Label initPlayerDetails(Player player) {
        Label playerDetails = new Label("Name: " + player.name + "\nStored Points: " + player.storedPoints + "\nPoints held: " + player.points + "\nDeaths: " + player.deaths);
        playerDetails.setFont(new Font(PLAYER_DETAILS_TEXT_SIZE));
        playerDetails.setTextFill(Color.LIGHTGREEN);
        return playerDetails;
    }

    private static FlowPane initSinglePlayerPane() {
        FlowPane playerPane = new FlowPane();
        playerPane.setPrefSize(SINGLE_PLAYER_PANE_WIDTH, SINGLE_PLAYER_PANE_HEIGHT);
        playerPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        playerPane.setOrientation(Orientation.VERTICAL);
        playerPane.setPadding(new Insets(PLAYER_PANE_PADDING));
        playerPane.setBorder(new Border(new BorderStroke(Color.DARKGOLDENROD, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        return playerPane;
    }

    private void clearPlayerPanesContainerIfItIsNotEmpty() {
        if (!playerPanesContainerDoesNotIncludesPlayers()){
            this.playerPanesContainer.getChildren().clear();
        }
    }

    private boolean playerPanesContainerDoesNotIncludesPlayers() {
        return this.playerPanesContainer.getChildren().size() == 0;
    }

    private static ImageView getPlayerImageView(Player player) {
        Image playerImage = GameImages.getPlayerImageByNumber(player.number);
        ImageView playerImageView = new ImageView(playerImage);
        return playerImageView;
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
