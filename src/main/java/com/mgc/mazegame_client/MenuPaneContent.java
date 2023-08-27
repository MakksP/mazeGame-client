package com.mgc.mazegame_client;

import javafx.scene.control.TextField;

public class MenuPaneContent {
    private TextField ipTextField;
    private TextField nickNameField;
    private PlayButton playButton;

    public MenuPaneContent(){
        ipTextField = new TextField();
        nickNameField = new TextField();
        playButton = new PlayButton();
    }

    public TextField getIpTextField() {
        return ipTextField;
    }

    public void setIpTextField(TextField ipTextField) {
        this.ipTextField = ipTextField;
    }

    public TextField getNickNameField() {
        return nickNameField;
    }

    public void setNickNameField(TextField nickNameField) {
        this.nickNameField = nickNameField;
    }

    public PlayButton getPlayButton() {
        return playButton;
    }

    public void setPlayButton(PlayButton playButton) {
        this.playButton = playButton;
    }
}
