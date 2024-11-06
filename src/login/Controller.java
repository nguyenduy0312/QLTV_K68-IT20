package login;

import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import view.admin.AdminView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


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


    // Xử lý sự kiện nút Login
    public void loginButtonOnAction(ActionEvent e) {
        if(usernameTextField.getText().trim().isEmpty() || passwordPasswordField.getText().trim().isEmpty()) {
            loginMessageLabel.setText("Vui lòng nhập tên người dùng và mật khẩu của bạn..");
        } else if(adminRadioButton.isSelected() && usernameTextField.getText().equals("admin") && passwordPasswordField.getText().equals("123")) {
            // Hiển thị thông báo
            successLogin.setVisible(true);

            // Sử dụng PauseTransition để trì hoãn chuyển đổi
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5)); // Thời gian chờ 3 giây
            pause.setOnFinished(event -> {
                // Mở cửa sổ AdminView
                AdminView adminView = new AdminView();
                try {
                    adminView.start(new Stage()); // Mở cửa sổ AdminView
                    closeButton.getScene().getWindow().hide(); // Đóng cửa sổ đăng nhập
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            pause.play(); // Chạy PauseTransition
        }
        else {
            loginMessageLabel.setText("Tên đăng nhập hoặc mật khẩu không chính xác!");
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
