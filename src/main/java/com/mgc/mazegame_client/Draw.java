package com.mgc.mazegame_client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;

public class Draw {

    public static final int FIRST_NOT_EMPTY_ELEMENT_INDEX = 1715;

    public static void drawPlayerVisibleArea(GameInfoPacket gameInfoPacket, GridPane gamePane){
        for (List<VisibleAreaMapPoint> elementsInRow : gameInfoPacket.mapVisibleAreaRepresentation){
            for (VisibleAreaMapPoint singleElement : elementsInRow){
                if (elementIsWall(singleElement)){
                    drawElementInXYPosition(gamePane, singleElement, GameImages.wall);
                } else if (elementIsPlayer(singleElement)) {
                    Image player = GameImages.getPlayerImageByNumber(singleElement.getElement());
                    drawElementInXYPosition(gamePane, singleElement, player);
                } else if (elementIsBeast(singleElement)){
                    drawElementInXYPosition(gamePane, singleElement, GameImages.beast);
                }
            }
        }
    }

    private static void drawElementInXYPosition(GridPane gamePane, VisibleAreaMapPoint singleElement, Image image) {
        gamePane.add(new ImageView(image), singleElement.getElementCords().getX(), singleElement.getElementCords().getY());
    }

    private static boolean elementIsBeast(VisibleAreaMapPoint singleElement) {
        return singleElement.getElement() == '*';
    }

    public static void clearVisibleAreaFromGamePane(GridPane gamePane){
        for (int currentElementIndexToDelete = gamePane.getChildren().size() - 1; currentElementIndexToDelete >= FIRST_NOT_EMPTY_ELEMENT_INDEX; currentElementIndexToDelete--){
            gamePane.getChildren().remove(currentElementIndexToDelete);
        }
    }

    private static boolean elementIsWall(VisibleAreaMapPoint singleElement) {
        return singleElement.getElement() == '#';
    }

    private static boolean elementIsPlayer(VisibleAreaMapPoint singleElement) {
        return singleElement.getElement() == '1' || singleElement.getElement() == '2' || singleElement.getElement() == '3' || singleElement.getElement() == '4';
    }

    public static Image generateImage(String path){
        return new Image(Draw.class.getResourceAsStream(path));
    }
}
