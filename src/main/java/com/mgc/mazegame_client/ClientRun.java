package com.mgc.mazegame_client;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientRun extends Application {

    public static final int SCREEN_WIDTH = 1600;
    public static final int SCREEN_HEIGHT = 900;
    public static final int MENU_PANE_VGAP = 15;
    public static final int ELEMENT_WIDTH = 25;
    public static final int ELEMENT_HEIGHT = 25;
    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FlowPane menuPane = initMenuPane();
        Scene scene = new Scene(menuPane, SCREEN_WIDTH, SCREEN_HEIGHT);

        stage.setTitle("Maze game");
        stage.setScene(scene);
        stage.show();
    }

    private static FlowPane initMenuPane() {
        FlowPane menuPane = new FlowPane();
        menuPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        menuPane.setVgap(MENU_PANE_VGAP);
        menuPane.setOrientation(Orientation.VERTICAL);
        menuPane.setAlignment(Pos.CENTER);
        initMenuPaneContent(menuPane);
        return menuPane;
    }

    private static void initMenuPaneContent(FlowPane menuPane) {
        Label ipAddress = initMenuInfoLabel("Enter server ip address");
        MenuPaneContent menuPaneContent = new MenuPaneContent();
        menuPane.getChildren().add(ipAddress);
        menuPane.getChildren().add(menuPaneContent.getIpTextField());
        Label name = initMenuInfoLabel("Enter your name");
        addContentToMenuPane(menuPane, menuPaneContent, name);
    }

    private static void addContentToMenuPane(FlowPane menuPane, MenuPaneContent menuPaneContent, Label name) {
        menuPane.getChildren().add(name);
        menuPane.getChildren().add(menuPaneContent.getNickNameField());
        menuPane.getChildren().add(menuPaneContent.getPlayButton());
    }

    private static Label initMenuInfoLabel(String text) {
        Label ipAddress = new Label(text);
        ipAddress.setFont(new Font("Arial", 20));
        ipAddress.setTextFill(Color.RED);
        return ipAddress;
    }

    public static void main(String[] args) {
        launch();
    }
}