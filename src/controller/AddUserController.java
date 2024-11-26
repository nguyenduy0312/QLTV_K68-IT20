package controller;

import DAO.DocumentDAO;
import DAO.UserDAO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Account;
import model.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public class AddUserController {
    @FXML
    private AdminController adminController;


    @FXML
    private Button closeButton;
    @FXML
    private Button chooseImageButton;

    @FXML
    private TextField id;
    @FXML
    private Label checkUserNameLabel;
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> genderChoiceBox;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private TextField numberPhone;
    @FXML
    private TextField address;
    @FXML
    private TextField email;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private ImageView imageView;


    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    public void initialize() {
        setGender(); // Đảm bảo được gọi khi giao diện được tải
    }

    public void closeButtonOnAction(ActionEvent e) {
        // Tạo hộp thoại xác nhận với hai nút OK và Hủy
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận hủy");
        alert.setHeaderText("Bạn có chắc chắn muốn hủy?");
        alert.setContentText("Hành động này không thể hoàn tác!");

        // Hiển thị hộp thoại và chờ người dùng lựa chọn
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Thực hiện hành động hủy ở đây
            closeButton.getScene().getWindow().hide();
        } else {
            // Không thực hiện gì nếu người dùng hủy
        }
    }

    public void setGender() {
        // Thêm các giá trị vào ChoiceBox
        genderChoiceBox.getItems().addAll("Nam", "Nữ", "Khác");

        // Đặt giá trị mặc định
        genderChoiceBox.setValue("Nam");
    }

    public void chooseImageButtonOnAction(ActionEvent e) {
        // Khởi tạo FileChooser
        FileChooser fileChooser = new FileChooser();

        // Thiết lập bộ lọc file cho phép chọn chỉ những file ảnh
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(imageFilter);

        // Mở cửa sổ chọn file
        Stage stage = (Stage) imageView.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Nếu người dùng chọn một file, hiển thị ảnh trong ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }


    // check userName
    public void checkUserName() {
        UserDAO userDAO = new UserDAO();

        this.username.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                checkUserNameLabel.setStyle(""); // Đặt lại style mặc định
                checkUserNameLabel.setText("");  // Không hiển thị thông báo nếu trống
            } else if (userDAO.findUserByUserName(newValue) != null) {
                checkUserNameLabel.setStyle("-fx-text-fill: red;"); // Đặt màu chữ là đỏ
                checkUserNameLabel.setText("Tên đăng nhập đã tồn tại."); // Hiển thị thông báo lỗi
            } else {
                checkUserNameLabel.setStyle("-fx-text-fill: green;"); // Đặt màu chữ là xanh
                checkUserNameLabel.setText("Tên đăng nhập hợp lệ."); // Hiển thị thông báo hợp lệ
            }
        });
    }

    public boolean addUser() {

        User user = new User();

        user.setId(this.id.getText());
        if (this.id.getText() == null || this.id.getText().trim().isEmpty()) {
            return false;
        }

        user.setName(this.name.getText());
        if (this.name.getText() == null || this.name.getText().trim().isEmpty()) {
            return false;
        }

        user.setGender(this.genderChoiceBox.getValue());

        LocalDate localDate = this.dateOfBirth.getValue();
        util.Date birthDate = new util.Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
        user.setBirthday(birthDate);

        user.setPhoneNumber(this.numberPhone.getText());
        if (this.numberPhone.getText() == null || this.numberPhone.getText().trim().isEmpty()) {
            return false;
        }

        user.setAddress(this.address.getText());
        if (this.address.getText() == null || this.address.getText().trim().isEmpty()) {
            return false;
        }

        user.setEmail(this.email.getText());
        if (this.email.getText() == null || this.email.getText().trim().isEmpty()) {
            return false;
        }

        Account account = new Account(this.username.getText(), this.password.getText());
        // Kiểm tra tên đăng nhập
        if ("Tên đăng nhập hợp lệ.".equals(checkUserNameLabel.getText())) {
            user.setAccount(account);
        } else {
            return false;  // Nếu tên đăng nhập không hợp lệ, không thêm người dùng
        }

        if (this.password.getText() == null || this.password.getText().trim().isEmpty()) {
            return false;
        }


        // Kiểm tra xem có ảnh mới không
        if (this.imageView.getImage() != null) {
            // Nếu có ảnh mới, cập nhật ảnh
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(this.imageView.getImage(), null);
            try {
                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                user.setPicture(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Thêm tài liệu vào hệ thống
        UserDAO userDAO = new UserDAO();
        userDAO.addUser(user);
        return true;  // Trả về true nếu thêm thành công
    }

    public void addButtonOnAction(ActionEvent e) throws IOException {

        // Tạo hộp thoại xác nhận với hai nút OK và Hủy
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận thêm");
        alert.setHeaderText("Bạn có chắc chắn muốn thêm người dùng không?");
        alert.setContentText("Hành động này không thể hoàn tác!");

        // Hiển thị hộp thoại và chờ người dùng lựa chọn
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (addUser()) { // Giả sử bạn đã định nghĩa phương thức addUser() để thêm người dùng
                adminController.loadUser();
                adminController.loadUser1();
                // Ẩn cửa sổ hiện tại sau khi thêm thành công
                closeButton.getScene().getWindow().hide();
            } else {
                // Hiển thị thông báo lỗi nếu thêm thất bại
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Lỗi Thêm Người Dùng");
                alert1.setHeaderText("Không thể thêm người dùng vào hệ thống");
                alert1.setContentText("Vui lòng kiểm tra lại thông tin hoặc thử lại sau.");
                alert1.showAndWait();
            }
        } else {
            // Không thực hiện gì nếu người dùng hủy
        }
    }
}
