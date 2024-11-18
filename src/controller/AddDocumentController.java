package controller;

import DAO.DocumentDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.Document;

import java.io.IOException;
import java.util.Optional;

public class AddDocumentController {
    @FXML
    private Button closeButton;
    @FXML
    private Button addButton;
    @FXML
    private Button importImageButton;

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

    public boolean addBook() {
        Document document = new Document();
        DocumentDAO documentDAO = new DocumentDAO();
        // Lắng nghe thay đổi trong TextField khi nhập id
        this.id.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                checkLabel.setText(""); // Không hiển thị thông báo nếu trống
                document.setId(this.id.getText());
            } else if (documentDAO.findDocumentById(newValue) != null) {
                checkLabel.setText("Mã sách đã tồn tại."); // Hiển thị thông báo lỗi
                return false;
            } else {
                checkLabel.setText(""); // Xóa thông báo nếu hợp lệ
            }
        });
        document.setTitle(this.title.getText());
        document.setAuthor(this.author.getText());
        document.setCategory(this.category.getText());

        int quantityValue = Integer.parseInt(this.quantity.getText().trim()); // Chuyển đổi chuỗi thành số nguyên
        document.setQuantity(quantityValue);
        document.setPublisher(this.publisher.getText());
        document.setMaxBorrowDays(Document.MAXBORROWDAYS);
        documentDAO.addDocument(document);
         return true;
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
