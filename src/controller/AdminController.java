package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import login.LoginView;
import view.admin.AdminView;
import view.borrowcard.BorrowCardView;
import view.document.AddDocument;
import view.document.DocumentImageDisplay;
import view.document.EditDocument;
import view.document.DocumentView;
import view.returncard.ReturnCardView;
import view.user.AddUser;
import view.user.EditUser;
import view.user.UserInfoView;

import java.awt.*;

public class AdminController {

    @FXML
    private Label usernameLabel;
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
    @FXML
    private Button addBookButton;
    @FXML
    private Button editBookButton;
    @FXML
    private Button viewInfoBookButton;
    @FXML
    private Button addUserButton;
    @FXML
    private Button editUserButton;
    @FXML
    private Button viewInfoUserButton;
    @FXML
    private Button borrowBookButton;
    @FXML
    private Button returnBookButton;

    @FXML
    private VBox vboxImages;
    public static final int COL = 7;


    public void setUsername(String username) {
        if (username != null) {
            usernameLabel.setText(username);
        } else {
            System.out.println("Username is null!");
        }
    }

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
        initialize();
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

    // Add book
    public void addBookButtonOnAction(ActionEvent e) {
        AddDocument addDocument = new AddDocument();
        try {
            addDocument.start(new Stage()); // Mở cửa sổ addDocument
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Edit book
    public void editBookButtonOnAction(ActionEvent e) {
        EditDocument editDocument = new EditDocument();
        try {
           editDocument.start(new Stage()); // Mở cửa sổ editdocument
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Infor book
    public void viewInfoBookButtonOnAction(ActionEvent e) {
        DocumentView documentView = new DocumentView();
        try {
           documentView.start(new Stage()); // Mở cửa sổ documentview
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Add user
    public void addUserButtonOnAction(ActionEvent e) {
        AddUser addUser = new AddUser();
        try {
            addUser.start(new Stage()); // Mở cửa sổ addUser
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Edit user
    public void editUserButtonOnAction(ActionEvent e) {
        EditUser editUser = new EditUser();
        try {
            editUser.start(new Stage()); // Mở cửa sổ editUser
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Infor user
    public void viewInfoUserButtonOnAction(ActionEvent e) {
        UserInfoView userInfoView = new UserInfoView();
        try {
           userInfoView.start(new Stage()); // Mở cửa sổ xem thông tin người dùng
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Borrow book
    public void borrowBookButtonOnAction(ActionEvent e) {
        BorrowCardView borrowCardView = new BorrowCardView();
        try {
            borrowCardView.start(new Stage()); // Mở cửa sổ mượn sách
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Return book
    public void returnBookButtonOnAction(ActionEvent e) {
        ReturnCardView returnCardView = new ReturnCardView();
        try {
           returnCardView.start(new Stage()); // Mở cửa sổ mượn sách
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void initialize() {
        // Gọi phương thức để hiển thị ảnh sách từ cơ sở dữ liệu
        String sqlQuery = "SELECT Picture FROM document";  // Truy vấn ảnh sách cho Admin
        DocumentImageDisplay.displayBookImages(vboxImages, sqlQuery, COL);
    }
}
