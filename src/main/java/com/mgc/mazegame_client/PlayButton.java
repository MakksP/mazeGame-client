package com.mgc.mazegame_client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicReference;


public class PlayButton extends Button {
    public static final String playButtonText = "Play";
    public static final int SCHEDULE_PERIOD_MS = 40;
    public static Cords campsiteLocation;
    private static boolean upButtonPressed = false;
    private static boolean leftButtonPressed = false;
    private static boolean downButtonPressed = false;
    private static boolean rightButtonPressed = false;

    public PlayButton(){
        super(playButtonText);
        this.setOnMouseClicked(mouseEvent -> {
            String ip = MenuPaneContent.getIpTextField().getText();
            String playerName = MenuPaneContent.getNickNameField().getText();

            RestTemplate restTemplate = new RestTemplate();
            AtomicReference<ResponseEntity<JoinInfo>> response = new AtomicReference<>(joinToTheGame(ip, playerName, restTemplate));
            JoinInfo joinInfo = response.get().getBody();
            campsiteLocation = joinInfo.campsiteLocation;

            GamePaneContent gamePaneContent = new GamePaneContent(new FlowPane(), new GridPane(), new FlowPane());

            Scene gameScene = new Scene(gamePaneContent.getMainGamePane(), ClientRun.SCREEN_WIDTH, ClientRun.SCREEN_HEIGHT);
            ClientRun.getMainStage().setScene(gameScene);

            addGameAndPlayerInfoPanesToMainPane(gamePaneContent);

            gameScene.setOnKeyPressed(event -> {
                serveMoveRequest(ip, restTemplate, response, Integer.toString(joinInfo.playerNumber), event);
            });

            gameScene.setOnKeyReleased(event -> {
                serveButtonReleased(event);
            });

            KeyFrame refreshGame = servePacketFromServerAndDrawActualGameState(ip, restTemplate, Integer.toString(joinInfo.playerNumber), gamePaneContent);
            serveGameLeave(ip, restTemplate, Integer.toString(joinInfo.playerNumber));
            configureAndStartScheduler(refreshGame);

        });
    }

    private static void configureAndStartScheduler(KeyFrame refreshGame) {
        Timeline scheduleRefreshGame = new Timeline(refreshGame);
        scheduleRefreshGame.setCycleCount(Timeline.INDEFINITE);
        scheduleRefreshGame.play();
    }

    private static void serveGameLeave(String ip, RestTemplate restTemplate, String yourId) {
        ClientRun.getMainStage().setOnCloseRequest(windowEvent -> {
            ResponseEntity<GameInfoPacket> leaveResponse = restTemplate.postForEntity("http://" + ip + "/leaveGame/" + yourId, null, GameInfoPacket.class);
            System.exit(0);
        });
    }

    private static KeyFrame servePacketFromServerAndDrawActualGameState(String ip, RestTemplate restTemplate, String yourId, GamePaneContent gamePaneContent) {
        KeyFrame refreshGame = new KeyFrame(Duration.millis(SCHEDULE_PERIOD_MS), event -> {
            GameInfoPacket gameInfoPacket = getGameInfo(ip, restTemplate, yourId);
            Draw.clearVisibleAreaFromGamePane(gamePaneContent.getRightGamePane());
            Draw.drawPlayerVisibleArea(gameInfoPacket, gamePaneContent.getRightGamePane());
            gamePaneContent.updatePlayersInfoPane(gameInfoPacket.playerList);
            gamePaneContent.updateInfoPane(yourId, gameInfoPacket.playerList);

        });
        return refreshGame;
    }

    private void serveButtonReleased(KeyEvent event) {
        KeyCode releasedButton = event.getCode();
        if (releasedButton == KeyCode.UP){
            upButtonPressed = false;
        } else if (releasedButton == KeyCode.RIGHT){
            rightButtonPressed = false;
        } else if (releasedButton == KeyCode.DOWN){
            downButtonPressed = false;
        } else if (releasedButton == KeyCode.LEFT){
            leftButtonPressed = false;
        }
    }

    private static void serveMoveRequest(String ip, RestTemplate restTemplate, AtomicReference<ResponseEntity<JoinInfo>> response, String yourId, KeyEvent event) {
        KeyCode pressedButton = event.getCode();
        if (pressedButton == KeyCode.UP && !upButtonPressed){
            upButtonPressed = true;
            response.set(restTemplate.postForEntity("http://" + ip + "/move/up/" + yourId, null, JoinInfo.class));
        } else if (pressedButton == KeyCode.RIGHT && !rightButtonPressed){
            rightButtonPressed = true;
            response.set(restTemplate.postForEntity("http://" + ip + "/move/right/" + yourId, null, JoinInfo.class));
        } else if (pressedButton == KeyCode.DOWN && !downButtonPressed){
            downButtonPressed = true;
            response.set(restTemplate.postForEntity("http://" + ip + "/move/down/" + yourId, null, JoinInfo.class));
        } else if (pressedButton == KeyCode.LEFT && !leftButtonPressed){
            leftButtonPressed = true;
            response.set(restTemplate.postForEntity("http://" + ip + "/move/left/" + yourId, null, JoinInfo.class));
        }
    }

    private static void addGameAndPlayerInfoPanesToMainPane(GamePaneContent gamePaneContent) {
        gamePaneContent.getMainGamePane().getChildren().add(gamePaneContent.getRightGamePane());
        gamePaneContent.getMainGamePane().getChildren().add(gamePaneContent.getPlayersInfoPane());
    }

    private static GameInfoPacket getGameInfo(String ip, RestTemplate restTemplate, String yourId) {
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<GameInfoPacket> gameInfoPacketResponseEntity = restTemplate.getForEntity("http://" + ip + "/getVisibleArea/" + yourId, GameInfoPacket.class);
        return gameInfoPacketResponseEntity.getBody();
    }

    private static ResponseEntity<JoinInfo> joinToTheGame(String ip, String playerName, RestTemplate restTemplate) {
        ResponseEntity<JoinInfo> response = restTemplate.postForEntity("http://" + ip + "/joinGame/" + playerName, null, JoinInfo.class);
        return response;
    }


}
