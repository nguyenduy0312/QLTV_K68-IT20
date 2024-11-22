package controller;

import DAO.BorrowReturnDAO;
import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.BorrowReturn;
import model.User;
import util.Date;

import java.time.LocalDate;
import java.util.Optional;


public class ReturnCardController {
    private AdminController adminController;

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    private UserController userController;

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    private BorrowReturn borrowReturn;
    public static  final int FINEADAY = 2000;

    public BorrowReturn getBorrowReturn() {
        return borrowReturn;
    }

    public void setBorrowReturn(BorrowReturn borrowReturn) {
        this.borrowReturn = borrowReturn;
    }

    @FXML
    private Button closeButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Label bookID;
    @FXML
    private Label personID;
    @FXML
    private Label name;
    @FXML
    private Label borrowDate;
    @FXML
    private Label returnDate;
    @FXML
    private Label fine;
    @FXML
    private Label reason;
    @FXML
    private Label Phat;
    @FXML
    private HBox starHbox;

    private int scorePerStar = 5; // Điểm cho mỗi ngôi sao
    private int totalScore = 0; // Tổng điểm

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

    public void returnDocument(BorrowReturn borrowReturn) {
        this.bookID.setText(borrowReturn.getMaSach());
        this.personID.setText(borrowReturn.getMaNguoiMuon());
        UserDAO userDAO = new UserDAO();
        User user = userDAO.findUserById(borrowReturn.getMaNguoiMuon());
        this.name.setText(user.getName());
        this.borrowDate.setText(borrowReturn.getNgayMuon().toString());

        Date returnDate = new Date();
        this.returnDate.setText(returnDate.toString());

        if(returnDate.toLocalDate().isAfter(borrowReturn.getNgayHenTra().toLocalDate())) {
            this.Phat.setVisible(true);
            this.fine.setVisible(true);
            this.reason.setVisible(true);

            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(returnDate.toLocalDate(), borrowReturn.getNgayHenTra().toLocalDate());
            long fine = daysLate * FINEADAY;
            this.fine.setText(Long.toString(fine));
            this.reason.setText("Trả tài liệu châm " + daysLate + "ngày.");
        } else {
            this.Phat.setVisible(false);
            this.fine.setVisible(false);
            this.reason.setVisible(false);
        }


    }

    public void confirmButtonOnAction(ActionEvent e) {
        // Tạo hộp thoại xác nhận với hai nút OK và Hủy
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận trả");
        alert.setHeaderText("Bạn có chắc chắn muốn muốn trả sách không?");
        alert.setContentText("Hành động này không thể hoàn tác!");

        // Hiển thị hộp thoại và chờ người dùng lựa chọn
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnDocument(this.borrowReturn);
            BorrowReturnDAO borrowReturnDAO = new BorrowReturnDAO();
            borrowReturnDAO.returnDocument(borrowReturn.getMaMuon());
            if(adminController != null) {
                adminController.loadInfoBorrow();
                adminController.loadBook();
                adminController.loadBook1();
            } else if(userController != null) {
                userController.loadBorrowBook();
                userController.loadBook();
            }
            closeButton.getScene().getWindow().hide();
        } else {
            // Không thực hiện gì nếu người dùng hủy
        }
    }

    @FXML
    private void initialize() {
        // Mã Unicode của ngôi sao đầy và ngôi sao rỗng
        String starFullUnicode = "\uf005"; // Ngôi sao đầy
        String starEmptyUnicode = "\uf006"; // Ngôi sao rỗng

        starHbox.setAlignment(Pos.CENTER);
        starHbox.setSpacing(10); // Khoảng cách giữa các ngôi sao là 10px

        for (int i = 1; i <= 5; i++) {
            // Tạo ngôi sao ban đầu là rỗng
            Text star = new Text(starEmptyUnicode);
            star.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 25; -fx-fill: gold;");

            // Sự kiện khi nhấn vào ngôi sao
            star.setOnMouseClicked(event -> {
                if (star.getText().equals(starFullUnicode)) {
                    // Nếu đang là ngôi sao đầy, chuyển thành rỗng
                    star.setText(starEmptyUnicode);
                    star.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 24; -fx-fill: gold;");
                } else {
                    // Nếu đang là ngôi sao rỗng, chuyển thành đầy
                    star.setText(starFullUnicode);
                    star.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 24; -fx-fill: gold;");
                }
            });

            // Thêm ngôi sao vào HBox
            starHbox.getChildren().add(star);
        }
    }

    private void setStarRating(int rating) {
        totalScore = rating * scorePerStar; // Cập nhật điểm
        for (int i = 0; i < starHbox.getChildren().size(); i++) {
            Text star = (Text) starHbox.getChildren().get(i);
            if (i < rating) {
                // Ngôi sao đầy
                star.setText("\uf005");
                star.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 25; -fx-fill: gold;");
            } else {
                // Ngôi sao rỗng
                star.setText("\uf006");
                star.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 25; -fx-fill: gold;");
            }
        }
        System.out.println("Số điểm hiện tại: " + totalScore); // Hiển thị điểm
    }
}
