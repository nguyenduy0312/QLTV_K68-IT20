package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

    // Xử lý sự kiện nút Login
    public void loginButtonOnAction(ActionEvent e) {
        if(usernameTextField.getText().trim().isEmpty() || passwordPasswordField.getText().trim().isEmpty()) {
            loginMessageLabel.setText("Vui lòng nhập tên người dùng và mật khẩu của bạn..");
        } else {
            loginMessageLabel.setText("Bạn hãy thử đăng nhập!");
        }
    }

    // Xử lý sự kiện nút close
    public void colseButtonOnAction(ActionEvent e) {
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
