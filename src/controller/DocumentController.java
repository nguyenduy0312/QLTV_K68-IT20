package controller;

import DAO.BookRatingDAO;
import DAO.DocumentDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.BookRating;
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

    @FXML
    private HBox starHbox;
    @FXML
    private Label score;
    @FXML
    private Label numReviews;

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

        BookRatingDAO bookRatingDAO = new BookRatingDAO();
        BookRating bookRating = bookRatingDAO.getRatingByBookId(id);
        System.out.println(bookRating);
        this.getStarRating(bookRating.getScore());
        this.score.setText(bookRating.getScore() + "/5");
        this.numReviews.setText("("+ formatNumber(bookRating.getNumReviews()) + " đánh giá" + ")");

    }

    public  void getStarRating(double score) {
        starHbox.setAlignment(Pos.CENTER);
        starHbox.setSpacing(10); // Khoảng cách giữa các ngôi sao là 10px

        // Số ngôi sao đầy
        int fullStars = (int) score;

        // Xác định có ngôi sao nửa không
        boolean hasHalfStar = (score - fullStars) >= 0.5;

        // Tổng cộng số ngôi sao là 5
        int emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);

        // Thêm ngôi sao đầy
        for (int i = 0; i < fullStars; i++) {
            Text fullStar = new Text(BookRating.STAR_FULL);
            fullStar.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 20px; -fx-fill: gold;");
            starHbox.getChildren().add(fullStar);
        }

        // Thêm ngôi sao nửa (nếu có)
        if (hasHalfStar) {
            Text halfStar = new Text(BookRating.STAR_HALF);
            halfStar.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 20px; -fx-fill: gold;");
            starHbox.getChildren().add(halfStar);
        }

        // Thêm ngôi sao rỗng
        for (int i = 0; i < emptyStars; i++) {
            Text emptyStar = new Text(BookRating.STAR_EMPTY);
            emptyStar.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 20px; -fx-fill: gold;");
            starHbox.getChildren().add(emptyStar);
        }
    }

    public  String formatNumber(int num) {
        if (num >= 1000) {
            double formattedNum = num / 1000.0;
            // Sử dụng String.format để làm tròn và thay đổi dấu '.' thành ','
            return String.format("%.1f", formattedNum).replace('.', ',') + "k";
        }
        return String.valueOf(num);
    }

}
