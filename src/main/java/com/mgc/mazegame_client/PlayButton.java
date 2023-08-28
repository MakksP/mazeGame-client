package com.mgc.mazegame_client;

import javafx.scene.control.Button;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
            String yourId = response.getBody();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<GameInfoPacket> gameInfoPacketResponseEntity = restTemplate.getForEntity("http://" + ip + "/getVisibleArea/" + yourId, GameInfoPacket.class);
            GameInfoPacket gameInfoPacket = gameInfoPacketResponseEntity.getBody();

        });
    }
}
