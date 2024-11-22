package login;

import DAO.UserDAO;
import controller.AdminController;
import controller.UserController;
import javafx.stage.StageStyle;
import login.loginDAO;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import model.User;
import view.admin.AdminView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import view.user.UserView;


import java.io.IOException;

public class Controller {
    @FXML
    private Button closeButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton adminRadioButton;
    @FXML
    private RadioButton userRadioButton;
    @FXML
    private TextField successLogin;

    private loginDAO loginDAO = new loginDAO();
    // Xử lý sự kiện nút Login
    public void loginButtonOnAction(ActionEvent e) {
        if(usernameTextField.getText().trim().isEmpty() || passwordPasswordField.getText().trim().isEmpty()) {
            loginMessageLabel.setText("Vui lòng nhập tên người dùng và mật khẩu của bạn..");
        } else if(adminRadioButton.isSelected()) {
            String type = loginDAO.authenticate1(usernameTextField.getText(), passwordPasswordField.getText());
            if(type == null) {
                loginMessageLabel.setText("Tên đăng nhập hoặc mật khẩu không chính xác!");
            } else {
                // Hiển thị thông báo
                successLogin.setVisible(true);

                // Sử dụng PauseTransition để trì hoãn chuyển đổi
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5)); // Thời gian chờ 3 giây
                pause.setOnFinished(event -> {
                    try {
                        // Đóng cửa sổ đăng nhập
                        closeButton.getScene().getWindow().hide();

                        // Tạo FXMLLoader để tải Admin.fxml
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin/adminview1.fxml"));

                        // Tải FXML và tạo Scene
                        Parent root = loader.load();

                        // Lấy controller của cửa sổ Admin từ FXMLLoader
                        AdminController adminController = loader.getController();

                        // Truyền tên người dùng vào controller của Admin
                        adminController.setUsername(usernameTextField.getText());
                        adminController.loadImage();
                        adminController.loadBook();
                        adminController.loadUser();
                        adminController.loadUser1();
                        adminController.loadBook1();
                        adminController.loadInfoBorrow();

                        // Tạo và hiển thị cửa sổ AdminView
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.initStyle(StageStyle.UNDECORATED);

                        stage.show();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                pause.play(); // Chạy PauseTransition
            }
        }  else if(userRadioButton.isSelected()) {
            String type = loginDAO.authenticate2(usernameTextField.getText(), passwordPasswordField.getText());
            if(type == null) {
                loginMessageLabel.setText("Tên đăng nhập hoặc mật khẩu không chính xác!");
            } else {
                // Hiển thị thông báo
                successLogin.setVisible(true);
                UserDAO userDAO = new UserDAO();
                User user = userDAO.findUserByUserName(usernameTextField.getText());

                // Sử dụng PauseTransition để trì hoãn chuyển đổi
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5)); // Thời gian chờ 3 giây
                pause.setOnFinished(event -> {
                    try {

                        // Đóng cửa sổ đăng nhập
                        closeButton.getScene().getWindow().hide();

                        // Tạo FXMLLoader để tải userview.fxml
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user/userview.fxml"));

                        // Tải FXML và tạo Scene
                        Parent root = loader.load();

                        // Lấy controller của cửa sổ User từ FXMLLoader
                        UserController userController = loader.getController();
                        userController.loadImage();
                        userController.setUser(user);
                        userController.setName(user.getName());
                        userController.loadBook();
                        userController.loadBorrowBook();

                        // Tạo và hiển thị cửa sổ UserView
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.initStyle(StageStyle.UNDECORATED);

                        stage.show();

                } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                pause.play(); // Chạy PauseTransition
            }
        }
    }


    // Xử lý sự kiện nút close
    public void closeButtonOnAction(ActionEvent e) {
        closeButton.getScene().getWindow().hide();
    }


    /**
     * Khởi tạo lớp điều khiển.
     * <p>
     * Phương thức này được gọi tự động sau khi tệp FXML đã được tải.
     * Nó thiết lập {@link ToggleGroup} cho các nút radio, cho phép chỉ một
     * nút radio được chọn tại một thời điểm. Các nút radio quản trị và người dùng
     * được thêm vào nhóm chuyển đổi để đảm bảo tính loại trừ lẫn nhau.
     * </p>
     */
    public void initialize() {
        toggleGroup = new ToggleGroup();

        // Gán ToggleGroup cho các RadioButton
        adminRadioButton.setToggleGroup(toggleGroup);
        userRadioButton.setToggleGroup(toggleGroup);
    }

    // Xử lý sự kiện khi nút radio được chọn
    public void radioButton(ActionEvent e) {
        if(adminRadioButton.isSelected()) {
            System.out.println(adminRadioButton.getText());
        } else {
            System.out.println(userRadioButton.getText());
        }
    }

}
