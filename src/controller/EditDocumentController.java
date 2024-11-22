package controller;

import DAO.DocumentDAO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Document;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class EditDocumentController {
    private Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
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
    private Button chooseImageButton;
    @FXML
    private Button confirmButton;
    @FXML
    private ImageView qrCode;

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

    public void printInfo(Document document) {
        if (document != null) {
            this.id.setText(document.getId());
            this.title.setText(document.getTitle());
            this.author.setText(document.getAuthor());
            this.category.setText(document.getCategory());
            this.quantity.setText(Integer.toString(document.getQuantity()));
            this.publisher.setText(document.getPublisher());

            // Kiểm tra và hiển thị ảnh nếu có
            if (document.getPicture() != null && document.getPicture().length > 0) {
                ByteArrayInputStream bis = new ByteArrayInputStream(document.getPicture());
                Image image = new Image(bis);
                this.imageView.setImage(image);
                this.imageView.setPreserveRatio(true);
            } else {
            }

            // Kiểm tra và hiển thị ảnh nếu có
            if (document.getQrCode() != null && document.getQrCode().length > 0) {
                ByteArrayInputStream bis = new ByteArrayInputStream(document.getQrCode());
                Image image = new Image(bis);
                this.qrCode.setImage(image);
                this.qrCode.setPreserveRatio(true);
            } else {
            }
        } else {
            System.out.println("Document is null!");
        }
    }

    public void updateBook(Document document) {
        // Lấy thông tin từ các TextField, ComboBox hoặc ImageView

        String newTitle = this.title.getText();
        String newAuthor = this.author.getText();
        String newCategory = this.category.getText();
        int newQuantity = Integer.parseInt(this.quantity.getText());
        String newPublisher = this.publisher.getText();

        // Cập nhật thông tin cho đối tượng document
        document.setTitle(newTitle);
        document.setAuthor(newAuthor);
        document.setCategory(newCategory);
        document.setQuantity(newQuantity);
        document.setPublisher(newPublisher);

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

        // Kiểm tra xem có ảnh mới không
        if (this.qrCode.getImage() != null) {
            // Nếu có ảnh mới, cập nhật ảnh
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(this.qrCode.getImage(), null);
            try {
                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                document.setQrCode(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Cập nhật lại cơ sở dữ liệu (gọi phương thức DAO để cập nhật vào DB)
        DocumentDAO documentDAO = new DocumentDAO();
        documentDAO.updateDocument(document);
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
           updateBook(this.document);
           adminController.loadBook();
           adminController.loadBook1();
           adminController.loadInfoBorrow();
           closeButton.getScene().getWindow().hide();
        } else {
            // Không thực hiện gì nếu người dùng hủy
        }
    }

}
