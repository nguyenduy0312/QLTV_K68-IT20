package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import login.LoginView;
import view.user.UserInfoView;

public class UserController {
    @FXML
    private StackPane stackPane;
    @FXML
    private Button circleButton;
    @FXML
    private AnchorPane infoAnchorPane;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Button maximizeButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button infoButton;
    @FXML
    private Button homeButton;
    @FXML
    private AnchorPane home;
    @FXML
    private Button actionButton;    // Đại diện cho nút mượn trả
    @FXML
    private AnchorPane muonTra;
    public void closeButtonOnAction(ActionEvent e) {
        closeButton.getScene().getWindow().hide();
    }

    public void minimizeButtonOnAction(ActionEvent e) {
        // Lấy Stage hiện tại
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // Thu nhỏ cửa sổ
        stage.setIconified(true);
    }

    public void maximizeButtonOnAction(ActionEvent e) {
        // Lấy cửa sổ hiện tại
        Stage stage = (Stage) closeButton.getScene().getWindow();

        // Nếu cửa sổ chưa phóng to, phóng to nó
        if (!stage.isMaximized()) {
            stage.setMaximized(true);

            // Đảm bảo StackPane chiếm toàn bộ không gian có sẵn
            stackPane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE); // Cho phép StackPane tự động thay đổi kích thước
        } else {
            // Nếu cửa sổ đã phóng to, khôi phục về kích thước mặc định
            stage.setMaximized(false);
        }
    }

    public void circleButtonOnAction(ActionEvent e) {
        infoAnchorPane.setVisible(!infoAnchorPane.isVisible());

        infoAnchorPane.setLayoutX(circleButton.getLayoutX() - 200);
        infoAnchorPane.setLayoutY(circleButton.getLayoutY() + 100);

    }

    public void logoutButtonOnAction(ActionEvent e) {
        LoginView loginView = new LoginView();
        try {
            loginView.start(new Stage()); // Mở cửa sổ login
            closeButton.getScene().getWindow().hide(); // Đóng cửa sổ admin
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Infor user
    public void infoButtonOnAction(ActionEvent e) {
        UserInfoView userInfoView = new UserInfoView();
        infoAnchorPane.setVisible(!infoAnchorPane.isVisible());
        try {
            userInfoView.start(new Stage()); // Mở cửa sổ xem thông tin người dùng
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // Ẩn tất cả các pane;
    private void hideAllPanes() {
        home.setVisible(false);
        muonTra.setVisible(false);

    }

    // Trang chủ
    public void homeButtonOnAction(ActionEvent e) {
        hideAllPanes();
        home.setVisible(!home.isVisible());
    }

    // Mượn/Trả sách
    public void actionButtonOnAction(ActionEvent e) {
        hideAllPanes();
        muonTra.setVisible(!muonTra.isVisible());
    }
}
