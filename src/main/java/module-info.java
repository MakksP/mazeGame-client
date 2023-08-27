module com.mgc.mazegame_client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mgc.mazegame_client to javafx.fxml;
    exports com.mgc.mazegame_client;
}