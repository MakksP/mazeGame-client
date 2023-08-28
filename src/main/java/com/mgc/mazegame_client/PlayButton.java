package com.mgc.mazegame_client;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class PlayButton extends Button {
    public static final String playButtonText = "Play";
    public static final int SCHEDULE_PERIOD_MS = 500;

    public PlayButton(){
        super(playButtonText);
        this.setOnMouseClicked(mouseEvent -> {
            String ip = MenuPaneContent.getIpTextField().getText();
            String playerName = MenuPaneContent.getNickNameField().getText();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = joinToTheGame(ip, playerName, restTemplate);
            String yourId = response.getBody();

            GamePaneContent gamePaneContent = new GamePaneContent(new FlowPane(), new GridPane(), new FlowPane());

            Scene gameScene = new Scene(gamePaneContent.getMainGamePane(), ClientRun.SCREEN_WIDTH, ClientRun.SCREEN_HEIGHT);
            ClientRun.getMainStage().setScene(gameScene);

            addGameAndPlayerInfoPanesToMainPane(gamePaneContent);

            ScheduledExecutorService scheduleGettingInfo = Executors.newScheduledThreadPool(1);

            Runnable getGameInfo = () -> {
                GameInfoPacket gameInfoPacket = getGameInfo(ip, restTemplate, yourId);
                Draw.clearVisibleAreaFromGamePane(gamePaneContent.getRightGamePane());
                Draw.drawPlayerVisibleArea(gameInfoPacket, gamePaneContent.getRightGamePane());
            };

            gameScene.setOnKeyPressed(event -> {
                KeyCode pressedButton = event.getCode();
                if (pressedButton == KeyCode.UP){

                } else if (pressedButton == KeyCode.RIGHT){

                } else if (pressedButton == KeyCode.DOWN){

                } else if (pressedButton == KeyCode.LEFT){

                }
            });

            scheduleGettingInfo.scheduleAtFixedRate(getGameInfo, 0, SCHEDULE_PERIOD_MS, TimeUnit.MILLISECONDS);


        });
    }

    private static void addGameAndPlayerInfoPanesToMainPane(GamePaneContent gamePaneContent) {
        Platform.runLater(() -> {
            gamePaneContent.getMainGamePane().getChildren().add(gamePaneContent.getRightGamePane());
            gamePaneContent.getMainGamePane().getChildren().add(gamePaneContent.getPlayersInfoPane());
        });
    }

    private static GameInfoPacket getGameInfo(String ip, RestTemplate restTemplate, String yourId) {
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<GameInfoPacket> gameInfoPacketResponseEntity = restTemplate.getForEntity("http://" + ip + "/getVisibleArea/" + yourId, GameInfoPacket.class);
        return gameInfoPacketResponseEntity.getBody();
    }

    private static ResponseEntity<String> joinToTheGame(String ip, String playerName, RestTemplate restTemplate) {
        ResponseEntity<String> response = restTemplate.postForEntity("http://" + ip + "/joinGame/" + playerName, null, String.class);
        return response;
    }


}
