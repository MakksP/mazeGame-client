package com.mgc.mazegame_client;

import javafx.scene.control.TextField;

public class MenuPaneContent {
    private static TextField ipTextField;
    private static TextField nickNameField;
    private static PlayButton playButton;

    public MenuPaneContent(){
        ipTextField = new TextField();
        nickNameField = new TextField();
        playButton = new PlayButton();
    }

    public static TextField getIpTextField() {
        return ipTextField;
    }

    public void setIpTextField(TextField ipAddress) {
        ipTextField = ipAddress;
    }

    public static TextField getNickNameField() {
        return nickNameField;
    }

    public void setNickNameField(TextField nickName) {
        nickNameField = nickName;
    }

    public static PlayButton getPlayButton() {
        return playButton;
    }

}
