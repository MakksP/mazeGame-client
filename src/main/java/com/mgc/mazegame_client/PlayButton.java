package com.mgc.mazegame_client;

import javafx.scene.control.Button;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PlayButton extends Button {
    public static final String playButtonText = "Play";

    public PlayButton(){
        super(playButtonText);
        this.setOnMouseClicked(mouseEvent -> {
            String ip = MenuPaneContent.getIpTextField().getText();
            String playerName = MenuPaneContent.getNickNameField().getText();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity("http://" + ip + "/joinGame/" + playerName, null, String.class);

        });
    }
}
