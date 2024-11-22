package controller;

import DAO.DocumentDAO;
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
import model.Document;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class AddDocumentController {

    private AdminController adminController;
    private Document addedBook;


    @FXML
    private Button closeButton;
    @FXML
    private Button addButton;
    @FXML
    private Button chooseImageButton;

    @FXML
    private TextField id;
    @FXML
    private Label checkLabel;
    @FXML
    private TextField title;
    @FXML
    private TextField author;
    @FXML
    private TextField category;
    @FXML
    private TextField quantity;
    @FXML
    private TextField publisher;
    @FXML
    private ImageView imageView;
    @FXML
    private Button chooseImageButtonOnAction;

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    public Document getAddedBook() {
        return addedBook;
    }

    public void setAddedBook(Document addedBook) {
        this.addedBook = addedBook;
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

    // check id
    public void checkId() {
        DocumentDAO documentDAO = new DocumentDAO();

        this.id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                checkLabel.setStyle(""); // Đặt lại style mặc định
                checkLabel.setText("");  // Không hiển thị thông báo nếu trống
            } else if (documentDAO.findDocumentById(newValue) != null) {
                checkLabel.setStyle("-fx-text-fill: red;"); // Đặt màu chữ là đỏ
                checkLabel.setText("Mã sách đã tồn tại."); // Hiển thị thông báo lỗi
            } else {
                checkLabel.setStyle("-fx-text-fill: green;"); // Đặt màu chữ là xanh
                checkLabel.setText("Mã sách hợp lệ."); // Hiển thị thông báo hợp lệ
            }
        });
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

    public boolean addBook() {

        Document document = new Document();

        // Kiểm tra ID hợp lệ ngay sau khi gọi checkId()
        if ("Mã sách hợp lệ.".equals(checkLabel.getText())) {
            document.setId(this.id.getText());
        } else {
            return false;  // Nếu ID không hợp lệ, không thêm sách
        }

        // Kiểm tra các thông tin khác (title, author, category, ...)
        document.setTitle(this.title.getText());
        if (this.title.getText() == null || this.title.getText().trim().isEmpty()) {
            return false;
        }

        document.setAuthor(this.author.getText());
        if (this.author.getText() == null || this.author.getText().trim().isEmpty()) {
            return false;
        }

        document.setCategory(this.category.getText());
        if (this.category.getText() == null || this.category.getText().trim().isEmpty()) {
            return false;
        }

        // Chuyển đổi và kiểm tra quantity
        int quantityValue;
        try {
            quantityValue = Integer.parseInt(this.quantity.getText().trim());  // Chuyển đổi chuỗi thành số nguyên
        } catch (NumberFormatException e) {
            return false;  // Dừng lại nếu không thể chuyển đổi thành số
        }
        document.setQuantity(quantityValue);

        // Kiểm tra publisher
        document.setPublisher(this.publisher.getText());
        if (this.publisher.getText() == null || this.publisher.getText().trim().isEmpty()) {
            return false;
        }

        document.setMaxBorrowDays(Document.MAXBORROWDAYS);

        // Kiểm tra xem có ảnh mới không
        if (this.imageView.getImage() != null) {
            // Nếu có ảnh mới, cập nhật ảnh
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(this.imageView.getImage(), null);
            try {
                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                document.setPicture(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Thêm tài liệu vào hệ thống
        DocumentDAO documentDAO = new DocumentDAO();
        documentDAO.addDocument(document);
        this.setAddedBook(document);
        return true;  // Trả về true nếu thêm thành công
    }

    public void addButtonOnAction(ActionEvent e) throws IOException {

        // Tạo hộp thoại xác nhận với hai nút OK và Hủy
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận thêm");
        alert.setHeaderText("Bạn có chắc chắn muốn thêm sách không?");
        alert.setContentText("Hành động này không thể hoàn tác!");

        // Hiển thị hộp thoại và chờ người dùng lựa chọn
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if(addBook()) {
                adminController.loadBook();
                adminController.loadBook1();
                adminController.loadImage();
                closeButton.getScene().getWindow().hide();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Lỗi Thêm Sách");
                alert1.setHeaderText("Không thể thêm sách vào hệ thống");
                alert1.setContentText("Vui lòng kiểm tra lại thông tin hoặc thử lại sau.");
                alert1.showAndWait();
            }
        } else {
            // Không thực hiện gì nếu người dùng hủy
        }
    }

}
