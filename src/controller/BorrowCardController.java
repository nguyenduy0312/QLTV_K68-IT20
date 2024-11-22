package controller;

import DAO.BookRatingDAO;
import DAO.BorrowReturnDAO;
import DAO.DocumentDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.BookRating;
import model.Document;
import model.User;
import util.Date;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;


public class BorrowCardController {

    private AdminController adminController;
    private UserController userController;
    private User user;
    private Document document;
    private  LocalDate borrowBookDate = LocalDate.now();
    private LocalDate maximumBorrowDate = borrowBookDate.plusDays(Document.MAXBORROWDAYS);

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    public LocalDate getBorrowBookDate() {
        return borrowBookDate;
    }

    public LocalDate getMaximumBorrowDate() {
        return maximumBorrowDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @FXML
    private Button closeButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Label borrowID;
    @FXML
    private Label personID;
    @FXML
    private Label personName;
    @FXML
    private Label gender;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label address;
    @FXML
    private Label borrowDate;
    @FXML
    private DatePicker dueDate;
    @FXML
    private Label bookID;
    @FXML
    private Label title;
    @FXML
    private Label author;
    @FXML
    private Label category;
    @FXML
    private ImageView imageBook;
    @FXML
    private Label checkDueDateLabel;

    @FXML
    private HBox starHbox1;
    @FXML
    private Label score1;
    @FXML
    private Label numReviews1;

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

    @FXML
    public void initialize() {

        checkDueDate(); // Gọi logic kiểm tra ngày trả
    }


    // check id
    public void checkDueDate() {

        this.dueDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);

            if (newValue.isBefore(this.borrowBookDate)) {
                checkDueDateLabel.setStyle("-fx-text-fill: red;");
                checkDueDateLabel.setText("Ngày trả không được trước ngày mượn");  // Không hiển thị thông báo nếu trống
            } else if (newValue.isAfter(this.maximumBorrowDate)) {
                checkDueDateLabel.setStyle("-fx-text-fill: red;"); // Đặt màu chữ là đỏ
                checkDueDateLabel.setText("Ngày hẹn trả vượt quá thời gian cho phép!"); // Hiển thị thông báo lỗi
            } else {
                checkDueDateLabel.setStyle("-fx-text-fill: green;"); // Đặt màu chữ là xanh
                checkDueDateLabel.setText("Ngày trả hợp lệ."); // Hiển thị thông báo hợp lệ
            }
        });
    }
    public boolean borrowDocument(User user, Document document) {

        BorrowReturnDAO borrowReturnDAO = new BorrowReturnDAO();

        BookRatingDAO bookRatingDAO = new BookRatingDAO();
        BookRating bookRating = bookRatingDAO.getRatingByBookId(document.getId());
        System.out.println(bookRating);
        this.getStarRating(bookRating.getScore());
        this.score1.setText(bookRating.getScore() + "/5");
        this.numReviews1.setText("("+ formatNumber(bookRating.getNumReviews()) + " đánh giá" + ")");

        borrowID.setText(borrowReturnDAO.generateNewMaMuon());
        personID.setText(user.getId());
        personName.setText(user.getName());
        gender.setText(user.getGender());
        phoneNumber.setText(user.getPhoneNumber());
        address.setText(user.getAddress());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        borrowDate.setText(borrowBookDate.format(formatter));

        bookID.setText(document.getId());
        title.setText(document.getTitle());
        author.setText(document.getAuthor());
        category.setText(document.getCategory());

        if(document.getPicture() != null) {
            // Chuyển byte array thành Image
            ByteArrayInputStream bis = new ByteArrayInputStream(document.getPicture());
            Image image = new Image(bis);
            this.imageBook.setImage(image);
            this.imageBook.setPreserveRatio(true);
        }


        if(!"Ngày trả hợp lệ.".equals(checkDueDateLabel.getText())) {
            System.out.println(checkDueDateLabel.getText());
            return false;
        }

        LocalDate localDate = this.dueDate.getValue();
        util.Date date1 = new util.Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());

        borrowReturnDAO.setBorrowDate(borrowBookDate);
        borrowReturnDAO.setDueDate(date1);
        borrowReturnDAO.setMaximumBorrowDate(this.maximumBorrowDate);
        borrowReturnDAO.borrowDocument(user, document);

        return true;
    }

    public void confirmButtonOnAction(ActionEvent e) throws IOException {

        // Tạo hộp thoại xác nhận với hai nút OK và Hủy
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận mượn");
        alert.setHeaderText("Bạn có chắc chắn muốn muốn mượn sách không?");
        alert.setContentText("Hành động này không thể hoàn tác!");

        // Hiển thị hộp thoại và chờ người dùng lựa chọn
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (borrowDocument(this.user, this.document)) {
                if(adminController != null) {
                    adminController.loadBook();
                    adminController.loadBook1();
                    adminController.loadInfoBorrow();
                } else if(userController != null) {
                    userController.loadBorrowBook();
                    userController.loadBook();
                }
                // Ẩn cửa sổ hiện tại sau khi thêm thành công
                closeButton.getScene().getWindow().hide();
            } else {
                // Hiển thị thông báo lỗi nếu thêm thất bại
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Lỗi Mượn Sách");
                alert1.setHeaderText("Không thể mượn sách");
                alert1.setContentText("Vui lòng kiểm tra lại thông tin hoặc thử lại sau.");
                alert1.showAndWait();
            }
        } else {
            // Không thực hiện gì nếu người dùng hủy
        }
    }


    public  void getStarRating(double score) {
        starHbox1.setAlignment(Pos.CENTER);
        starHbox1.setSpacing(10); // Khoảng cách giữa các ngôi sao là 10px

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
            starHbox1.getChildren().add(fullStar);
        }

        // Thêm ngôi sao nửa (nếu có)
        if (hasHalfStar) {
            Text halfStar = new Text(BookRating.STAR_HALF);
            halfStar.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 20px; -fx-fill: gold;");
            starHbox1.getChildren().add(halfStar);
        }

        // Thêm ngôi sao rỗng
        for (int i = 0; i < emptyStars; i++) {
            Text emptyStar = new Text(BookRating.STAR_EMPTY);
            emptyStar.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 20px; -fx-fill: gold;");
            starHbox1.getChildren().add(emptyStar);
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
