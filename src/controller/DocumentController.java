package controller;

import DAO.DocumentDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Document;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.ByteArrayInputStream;

public class DocumentController {

    @FXML
    private AnchorPane myPane;
    Stage stage = null;
    @FXML
    private Button maximizeButton;
    @FXML
    private Button closeButton;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label author;
    @FXML
    private Label category;
    @FXML
    private Label quantity;
    @FXML
    private Label publisher;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView qrCode;

    public void closeButtonOnAction(ActionEvent e) {
        closeButton.getScene().getWindow().hide();
    }

    public void maximizeButtonOnAction(ActionEvent e) {
        // Lấy cửa sổ hiện tại
        stage = (Stage) myPane.getScene().getWindow();

        // Nếu cửa sổ chưa phóng to, phóng to nó
        if (!stage.isMaximized()) {
            stage.setMaximized(true);
        } else {
            // Nếu cửa sổ đã phóng to, khôi phục về kích thước ban đầu
            stage.setMaximized(false);
        }
    }

    public void setInfoById(String id) {
        DocumentDAO documentDAO = new DocumentDAO();
        Document document = documentDAO.findDocumentById(id);

        this.id.setText(document.getId());
        this.title.setText(document.getTitle());
        this.author.setText(document.getAuthor());
        this.category.setText(document.getCategory());
        this.quantity.setText(Integer.toString(document.getQuantity()));
        this.publisher.setText(document.getPublisher());

        if(document.getPicture() != null) {
            // Chuyển byte array thành Image
            ByteArrayInputStream bis = new ByteArrayInputStream(document.getPicture());
            Image image = new Image(bis);
            this.imageView.setImage(image);
            this.imageView.setPreserveRatio(true);
        }

        if(document.getQrCode() != null) {
            // Chuyển byte array thành Image
            ByteArrayInputStream bis = new ByteArrayInputStream(document.getQrCode());
            Image image = new Image(bis);
            this.qrCode.setImage(image);
            this.qrCode.setPreserveRatio(true);
        }

    }

}
