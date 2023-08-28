package com.mgc.mazegame_client;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;

public class Draw {

    public static final int FIRST_NOT_EMPTY_ELEMENT_INDEX = 2736;

    public static void drawPlayerVisibleArea(GameInfoPacket gameInfoPacket, GridPane gamePane){
        for (List<VisibleAreaMapPoint> elementsInRow : gameInfoPacket.mapVisibleAreaRepresentation){
            for (VisibleAreaMapPoint singleElement : elementsInRow){
                if (elementIsWall(singleElement)){
                    Platform.runLater(() -> {
                        gamePane.add(generateImage("/gameImages/wall.png"), singleElement.getElementCords().getY(), singleElement.getElementCords().getX());
                    });
                } else if (elementIsPlayer(singleElement)) {
                    Platform.runLater(() -> {
                        gamePane.add(generateImage("/gameImages/player" + singleElement.getElement() + ".png"), singleElement.getElementCords().getY(), singleElement.getElementCords().getX());
                    });
                }

            }
        }
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

    public static ImageView generateImage(String path){
        Image image = new Image(Draw.class.getResourceAsStream(path));
        return new ImageView(image);
    }
}
