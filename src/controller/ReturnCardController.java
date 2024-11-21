package controller;

import DAO.BorrowReturnDAO;
import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
            adminController.loadInfoBorrow();
            adminController.loadBook();
            adminController.loadBook1();
            closeButton.getScene().getWindow().hide();

        } else {
            // Không thực hiện gì nếu người dùng hủy
        }
    }
}
