package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserInfoController {
    @FXML
    private Button closeButton;
    public void closeButtonOnAction(ActionEvent e) {
        closeButton.getScene().getWindow().hide();
    }

}
