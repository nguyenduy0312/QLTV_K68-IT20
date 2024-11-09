package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.LoginView;
import view.admin.AdminView;

import java.awt.*;

public class AdminController {
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Button maximizeButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button homeButton;
    @FXML
    private AnchorPane home;
    @FXML
    private Button qlSachButton;
    @FXML
    private AnchorPane quanLySach;
    @FXML
    private Button qlMuonSachButton;
    @FXML
    private AnchorPane quanLyMuonSach;
    @FXML
    private Button qlTraSachButton;
    @FXML
    private AnchorPane quanLyTraSach;
    @FXML
    private Button qlNguoiDungButton;
    @FXML
    private AnchorPane quanLyNguoiDung;
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
        } else {
            // Nếu cửa sổ đã phóng to, khôi phục về kích thước ban đầu
            stage.setMaximized(false);
        }
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

    // Ẩn tất cả các pane;
    private void hideAllPanes() {
        home.setVisible(false);
        quanLySach.setVisible(false);
        quanLyMuonSach.setVisible(false);
        quanLyTraSach.setVisible(false);
        quanLyNguoiDung.setVisible(false);
    }

    //Cửa sổ chính
    public void homeButtonOnAction(ActionEvent e) {
        hideAllPanes();
        home.setVisible(!home.isVisible());
    }

    //Cửa sổ quản lý sách
    public void qlSachButtonOnAction(ActionEvent e) {
        hideAllPanes();
        quanLySach.setVisible(!quanLySach.isVisible());
    }

    //Cửa sổ quản lý mượn sách
    public void qlMuonSachButtonOnAction(ActionEvent e) {
        hideAllPanes();
        quanLyMuonSach.setVisible(!quanLyMuonSach.isVisible());
    }

    //Cửa sổ quản lý trả sách
    public void qlTraSachButtonOnAction(ActionEvent e) {
        hideAllPanes();
        quanLyTraSach.setVisible(!quanLyTraSach.isVisible());
    }

    //Cửa sổ quản lý người dùng
    public void qlNguoiDungButtonOnAction(ActionEvent e) {
        hideAllPanes();
        quanLyNguoiDung.setVisible(!quanLyNguoiDung.isVisible());
    }

}
