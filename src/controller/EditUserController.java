package controller;

import DAO.DocumentDAO;
import DAO.UserDAO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Account;
import model.Document;
import model.User;

import util.Date;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class EditUserController {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private AdminController adminController;

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    @FXML
    private Button closeButton;
    @FXML
    private Label id;
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> genderChoiceBox;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;
    @FXML
    private TextField email;
    @FXML
    private Label userName;
    @FXML
    private TextField password;
    @FXML
    private ImageView imageView;
    @FXML
    private Button confirmButton;

    public void initialize() {
        setGender(); // Đảm bảo được gọi khi giao diện được tải
    }

    public void setGender() {
        // Thêm các giá trị vào ChoiceBox
        genderChoiceBox.getItems().addAll("Nam", "Nữ", "Khác");

        // Đặt giá trị mặc định
        genderChoiceBox.setValue("Nam");
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
            System.out.println("Người dùng đã chọn Hủy.");
            // Không thực hiện gì nếu người dùng hủy
        }
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

    public void printInfo(User user) {
        if (user != null) {
            this.id.setText(user.getId());
            this.name.setText(user.getName());
            this.genderChoiceBox.setValue(user.getGender());

            Date customDate = user.getBirthday(); // Giả sử trả về đối tượng Date tự tạo
            LocalDate localDate = LocalDate.of(customDate.getYear(), customDate.getMonth(), customDate.getDay());
            this.dateOfBirth.setValue(localDate);

            this.phoneNumber.setText(user.getPhoneNumber());
            this.address.setText(user.getAddress());
            this.email.setText(user.getEmail());

            this.userName.setText(user.getAccount().getUserName());
            this.password.setText(user.getAccount().getPassWord());

            // Kiểm tra và hiển thị ảnh nếu có
            if (user.getPicture() != null && user.getPicture().length > 0) {
                ByteArrayInputStream bis = new ByteArrayInputStream(user.getPicture());
                Image image = new Image(bis);
                this.imageView.setImage(image);
                this.imageView.setPreserveRatio(true);
            } else {

            }
        } else {
            System.out.println("User is null!");
        }
    }

    public void updateUser(User user) {
        // Lấy thông tin từ các TextField, ComboBox hoặc ImageView

        String newName = this.name.getText();
        String newGender = this.genderChoiceBox.getValue();

        LocalDate localDate = this.dateOfBirth.getValue();
        Date birthDate = new util.Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
        Date newDateOfBirht = birthDate;

        String newPhoneNumber = this.phoneNumber.getText();
        String newAddress = this.address.getText();
        String newEmail = this.email.getText();

        String userName = this.userName.getText();
        String newPassWord = this.password.getText();
        Account newAccount = new Account(userName, newPassWord);

        // Cập nhật thông tin cho đối tượng user
       user.setName(newName);
       user.setGender(newGender);
       user.setBirthday(newDateOfBirht);
       user.setPhoneNumber(newPhoneNumber);
       user.setAddress(newAddress);
       user.setEmail(newEmail);
       user.setAccount(newAccount);

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

        // Cập nhật lại cơ sở dữ liệu (gọi phương thức DAO để cập nhật vào DB)
        UserDAO userDAO = new UserDAO();
        userDAO.updateUser(user);
    }

    public void confirmButtonOnAction(ActionEvent e) {
        // Tạo hộp thoại xác nhận với hai nút OK và Hủy
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận thêm");
        alert.setHeaderText("Bạn có chắc chắn muốn sửa sách không?");
        alert.setContentText("Hành động này không thể hoàn tác!");

        // Hiển thị hộp thoại và chờ người dùng lựa chọn
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            updateUser(this.user);
            adminController.loadUser();
            adminController.loadUser1();
            adminController.loadInfoBorrow();
            closeButton.getScene().getWindow().hide();
        } else {
            // Không thực hiện gì nếu người dùng hủy
        }
    }

}
