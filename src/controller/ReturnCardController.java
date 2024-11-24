package controller;

import DAO.BookRatingDAO;
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
import model.BookRating;
import model.BorrowReturn;
import model.User;
import util.Date;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


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
    @FXML
    private Label level;
    private int selectStar = 0; // Tổng điểm

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

            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(borrowReturn.getNgayHenTra().toLocalDate() ,returnDate.toLocalDate());
            long fine = daysLate * FINEADAY;
            this.fine.setText(Long.toString(fine) + "VNĐ");
            this.reason.setText("Trả tài liệu chậm " + daysLate + " ngày.");
        } else {
            this.Phat.setVisible(false);
            this.fine.setVisible(false);
            this.reason.setVisible(false);
        }

        if(selectStar != 0) {
            BookRatingDAO bookRatingDAO = new BookRatingDAO(this.bookID.getText());
            try  {
               bookRatingDAO.rateBook(selectStar);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        starHbox.setAlignment(Pos.CENTER);
        starHbox.setSpacing(10); // Khoảng cách giữa các ngôi sao là 10px

        String[] levels = {"Rất tệ", "Không hay", "Bình thường", "Hay", "Xuất sắc"};

        for (int i = 1; i <= 5; i++) {
            // Tạo ngôi sao ban đầu là rỗng
            Text star = new Text(BookRating.STAR_EMPTY);
            star.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 25; -fx-fill: gold;");
            AtomicInteger rating = new AtomicInteger();

            // Sự kiện khi nhấn vào ngôi sao
            star.setOnMouseClicked(event -> {
                if (star.getText().equals(BookRating.STAR_FULL)) {
                    selectStar -= 1;
                    // Nếu đang là ngôi sao đầy, chuyển thành rỗng
                    star.setText(BookRating.STAR_EMPTY);
                    star.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 24; -fx-fill: gold;");
                } else {
                    selectStar += 1;
                    // Nếu đang là ngôi sao rỗng, chuyển thành đầy
                    star.setText(BookRating.STAR_FULL);
                    star.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 24; -fx-fill: gold;");
                }
                 rating.set(starHbox.getChildren().indexOf(star) + 1); // Tính số sao được chọn

                // Cập nhật mức đánh giá dựa trên số sao
                if (selectStar > 0 && selectStar <= 3) {
                    level.setText(levels[selectStar - 1]); // Hiển thị mức đánh giá
                    level.setStyle( "-fx-text-fill: gray; -fx-font-size: 18px;");
                } else if(selectStar >= 4 && selectStar <= levels.length) {
                    level.setText(levels[selectStar - 1]); // Hiển thị mức đánh giá
                    level.setStyle( "-fx-text-fill: #867507; -fx-font-size: 18px;");
                } else {
                    level.setText(""); // Xóa nếu không có sao nào được chọn
                }
            });
            // Thêm ngôi sao vào HBox
            starHbox.getChildren().add(star);
        }
    }

}
