package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;

public class AdminController {
    @FXML
    private Button closeButton;
    private Button minimizeButton;

    public void closeButtonOnAction(ActionEvent e) {
        closeButton.getScene().getWindow().hide();
    }

    public void minimizeButtonOnAction(ActionEvent e) {
        // Lấy Stage hiện tại
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // Thu nhỏ cửa sổ
        stage.setIconified(true);
    }
}
