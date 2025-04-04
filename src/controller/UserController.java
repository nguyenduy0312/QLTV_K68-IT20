package controller;

import DAO.BorrowReturnDAO;
import DAO.DocumentDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import login.LoginView;
import model.BorrowReturn;
import model.Document;
import model.User;
import util.Date;
import view.document.DocumentImageDisplay;
import view.user.UserInfoView;

import java.util.List;
import java.util.stream.Collectors;

public class UserController {
    private User user;

    // ObservableList và FilteredList toàn cục
    private ObservableList<Document> observableDocumentList = FXCollections.observableArrayList();
    private FilteredList<Document> filteredDocumentList;

    private ObservableList<BorrowReturn> observableBorrowReturnList;
    private FilteredList<BorrowReturn> filteredBorrowReturnList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private Label name ;
    @FXML
    private StackPane stackPane;
    @FXML
    private Button circleButton;
    @FXML
    private AnchorPane infoAnchorPane;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Button maximizeButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button infoButton;
    @FXML
    private Button homeButton;
    @FXML
    private AnchorPane home;
    @FXML
    private Button actionButton;    // Đại diện cho nút mượn trả
    @FXML
    private AnchorPane muonTra;

    // Trang chủ
    @FXML
    private VBox vboxImages;
    public static  final  int COL1 = 10;

    // Mượn/Trả
    @FXML
    private TextField searchBorrowBookField;
    @FXML
    private TableView<BorrowReturn> borrowBookTableView;
    @FXML private TableColumn<BorrowReturn, String> bookIDColumn;
    @FXML private TableColumn<BorrowReturn, String> bookTitleColumn;
    @FXML private TableColumn<BorrowReturn, Date> borrowDateColumn;
    @FXML private TableColumn<BorrowReturn, Date> dueDateColumn;

    @FXML
    private Button returnBookButton;

    @FXML
    private TableView<Document> bookTableView;

    @FXML private TableColumn<Document, String> bookID1Column;   // Cột ID
    @FXML private TableColumn<Document, String> bookTitle1Column;  // Cột Title
    @FXML private TableColumn<Document, String> categoryColumn;  // Cột Category
    @FXML private TableColumn<Document, String> authorColumn;  // Cột Category
    @FXML private TableColumn<Document, Integer> quantityColumn;  // Cột Quantity
    @FXML private TableColumn<Document, Integer> maxBorrowDaysColumn;  // Cột maxBorrowDays

    @FXML
    private Button borrowBookButton;
    @FXML
    private Button viewInfoBookButton;

    @FXML
    private TextField searchBookField;
    public void setName(String name) {
        this.name.setText(name);
    }

    public void closeButtonOnAction(ActionEvent e) {
        closeButton.getScene().getWindow().hide();
    }

    public void minimizeButtonOnAction(ActionEvent e) {
        // Lấy Stage hiện tại
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // Thu nhỏ cửa sổ
        stage.setIconified(true);
    }

    public void maximizeButtonOnAction(ActionEvent e) {
        // Lấy cửa sổ hiện tại
        Stage stage = (Stage) closeButton.getScene().getWindow();

        // Nếu cửa sổ chưa phóng to, phóng to nó
        if (!stage.isMaximized()) {
            stage.setMaximized(true);

            // Đảm bảo StackPane chiếm toàn bộ không gian có sẵn
            stackPane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE); // Cho phép StackPane tự động thay đổi kích thước
        } else {
            // Nếu cửa sổ đã phóng to, khôi phục về kích thước mặc định
            stage.setMaximized(false);
        }
    }

    public void circleButtonOnAction(ActionEvent e) {
        infoAnchorPane.setVisible(!infoAnchorPane.isVisible());

        infoAnchorPane.setLayoutX(circleButton.getLayoutX() - 200);
        infoAnchorPane.setLayoutY(circleButton.getLayoutY() + 100);

    }

    public void logoutButtonOnAction(ActionEvent e) {
        LoginView loginView = new LoginView();
        try {
            loginView.start(new Stage()); // Mở cửa sổ login
            closeButton.getScene().getWindow().hide(); // Đóng cửa sổ admin
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Infor user
    public void infoButtonOnAction(ActionEvent e) {
        infoAnchorPane.setVisible(!infoAnchorPane.isVisible());
        try {
            // Tạo FXMLLoader để tải doucmentview.fxml
            FXMLLoader loader = new FXMLLoader(DocumentImageDisplay.class.getResource("/view/user/userinfoview.fxml"));

            // Tải FXML và tạo Scene
            Parent root = loader.load();


            UserInfoController userInfoController = loader.getController();
            userInfoController.setInfoById(this.user.getId());

            // Tạo và hiển thị cửa sổ DocumentView
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // Ẩn tất cả các pane;
    private void hideAllPanes() {
        home.setVisible(false);
        muonTra.setVisible(false);

    }

    // Trang chủ
    public void homeButtonOnAction(ActionEvent e) {
        hideAllPanes();
        home.setVisible(!home.isVisible());
    }

    // Mượn/Trả sách
    public void actionButtonOnAction(ActionEvent e) {
        hideAllPanes();
        muonTra.setVisible(!muonTra.isVisible());
    }

    // Load ảnh
    public void loadImage() {
        // Gọi phương thức để hiển thị ảnh sách từ cơ sở dữ liệu
        String sqlQuery = "SELECT MaSach, Picture FROM document";  // Truy vấn ảnh sách cho Admin
        DocumentImageDisplay.displayBookImages(vboxImages, sqlQuery, COL1);
    }

    public void loadBorrowBook() {
        // Thiết lập các cột trong TableView
        bookIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaSach()));
        bookTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTenSach()));
        borrowDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNgayMuon()));
        dueDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNgayHenTra()));

        // Lấy dữ liệu từ cơ sở dữ liệu
        BorrowReturnDAO borrowReturnDAO = new BorrowReturnDAO();
        List<BorrowReturn> borrowReturnList = borrowReturnDAO.findInfoByUserID(this.user.getId());

        // Hiển thị danh sách sách nếu có dữ liệu
        if (borrowReturnList != null) {
            // Chuyển danh sách sang ObservableList
            observableBorrowReturnList = FXCollections.observableArrayList(borrowReturnList);

            // Tạo FilteredList để hỗ trợ tìm kiếm
            filteredBorrowReturnList = new FilteredList<>(observableBorrowReturnList, b -> true);

            // Gán FilteredList vào TableView
            borrowBookTableView.setItems(filteredBorrowReturnList);
        } else {
            // Nếu không có dữ liệu, tạo danh sách trống
            observableBorrowReturnList = FXCollections.observableArrayList();
            filteredBorrowReturnList = new FilteredList<>(observableBorrowReturnList, b -> true);
            borrowBookTableView.setItems(filteredBorrowReturnList);
        }

        // Lắng nghe thay đổi trên ô tìm kiếm
        searchBorrowBookField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredBorrowReturnList.setPredicate(borrowReturn -> {
                // Nếu không có từ khóa, hiển thị tất cả
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }

                // Chuyển từ khóa về chữ thường
                String lowerCaseKeyword = newValue.toLowerCase();

                // Kiểm tra các trường thông tin có chứa từ khóa hay không
                return borrowReturn.getMaSach().toLowerCase().contains(lowerCaseKeyword)
                        || borrowReturn.getNgayMuon().toString().toLowerCase().contains(lowerCaseKeyword)
                        || borrowReturn.getTenSach().toLowerCase().contains(lowerCaseKeyword);
            });
        });
    }

    // Update bảng theo từ khóa
    public void updateBorrowBookTableView(String keyword) {
        // Sử dụng FilteredList để lọc dữ liệu
        filteredBorrowReturnList.setPredicate(borrowReturn -> {
            // Nếu từ khóa rỗng, hiển thị tất cả
            if (keyword == null || keyword.trim().isEmpty()) {
                return true;
            }

            // Chuyển từ khóa và dữ liệu sang chữ thường
            String lowerCaseKeyword = keyword.toLowerCase();

            // Kiểm tra các trường thông tin có chứa từ khóa hay không
            return borrowReturn.getMaSach().toLowerCase().contains(lowerCaseKeyword)
                    || borrowReturn.getNgayMuon().toString().toLowerCase().contains(lowerCaseKeyword)
                    || borrowReturn.getTenSach().toLowerCase().contains(lowerCaseKeyword);
        });
    }

    public void searchBorrowBookFieldOnAction(ActionEvent e) {
        String keyword = searchBorrowBookField.getText();
        updateBorrowBookTableView(keyword);
    }

    public void returnBookButtonOnAction(ActionEvent e) {
        BorrowReturn selectedBorrowReturn = borrowBookTableView.getSelectionModel().getSelectedItem();
        // Kiểm tra xem cả hai bảng đều có hàng được chọn
        if (selectedBorrowReturn != null) {
            try {
                // Tạo FXMLLoader để tải borrowcardview.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/returncard/returncardview.fxml"));

                // Tải FXML và tạo Scene
                Parent root = loader.load();

                // Lấy controller của cửa sổ
                ReturnCardController returnCardController = loader.getController();
                returnCardController.setUserController(this);
                returnCardController.setBorrowReturn(selectedBorrowReturn);
                returnCardController.returnDocument(selectedBorrowReturn);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.UNDECORATED);

                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void loadBook() {
        // Thiết lập các cột trong TableView
        bookID1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        bookTitle1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        maxBorrowDaysColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMaxBorrowDays()).asObject());

        // Lấy dữ liệu từ cơ sở dữ liệu
        DocumentDAO documentDAO = new DocumentDAO();
        List<Document> documentList = documentDAO.findAllDocuments();

        // Cập nhật ObservableList
        if (documentList != null) {
            observableDocumentList.setAll(documentList);
        } else {
            observableDocumentList.clear();
        }

        // Tạo FilteredList để lọc dữ liệu
        filteredDocumentList = new FilteredList<>(observableDocumentList, p -> true);

        // Đặt FilteredList cho TableView
        bookTableView.setItems(filteredDocumentList);

        // Lắng nghe thay đổi trong TextField để lọc dữ liệu
        searchBookField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateBookTableView(newValue);
        });
    }

    public void updateBookTableView(String keyword) {
        filteredDocumentList.setPredicate(document -> {
            // Nếu từ khóa rỗng, hiển thị toàn bộ
            if (keyword == null || keyword.trim().isEmpty()) {
                return true;
            }

            // Chuyển từ khóa và thông tin thành chữ thường để so sánh
            String lowerCaseKeyword = keyword.toLowerCase();

            // Kiểm tra từ khóa trong các trường
            return document.getId().toLowerCase().contains(lowerCaseKeyword) ||
                    document.getTitle().toLowerCase().contains(lowerCaseKeyword) ||
                    document.getCategory().toLowerCase().contains(lowerCaseKeyword) ||
                    document.getAuthor().toLowerCase().contains(lowerCaseKeyword);
        });
    }

    public void searchBookFieldOnAction(ActionEvent e) {
        String keyword = searchBookField.getText();
        updateBookTableView(keyword);
    }

    // Infor book
    public void viewInfoBookButtonOnAction(ActionEvent e) {
        Document selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        try {
            // Tạo FXMLLoader để tải doucmentview.fxml
            FXMLLoader loader = new FXMLLoader(DocumentImageDisplay.class.getResource("/view/document/documentview.fxml"));

            // Tải FXML và tạo Scene
            Parent root = loader.load();


            DocumentController documentController = loader.getController();
            documentController.setInfoById(selectedBook.getId());

            // Tạo và hiển thị cửa sổ DocumentView
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void borrowBookButtonOnAction(ActionEvent e) {

        Document selectedDocument = bookTableView.getSelectionModel().getSelectedItem();

        // Kiểm tra xem cả hai bảng đều có hàng được chọn
        if (this.user != null && selectedDocument != null) {
            if (selectedDocument.getQuantity() > 0) {
                // Nếu cả hai bảng đều có lựa chọn, thực hiện hành động mong muốn
                System.out.println("Selected Document: " + selectedDocument.getTitle());

                try {
                    // Tạo FXMLLoader để tải borrowcardview.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/borrowcard/borrowcardview.fxml"));

                    // Tải FXML và tạo Scene
                    Parent root = loader.load();

                    // Lấy controller của cửa sổ
                    BorrowCardController borrowCardController = loader.getController();
                    borrowCardController.setUserController(this);
                    borrowCardController.setUser(this.user);
                    borrowCardController.setDocument(selectedDocument);

                    borrowCardController.borrowDocument(this.user, selectedDocument);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.initStyle(StageStyle.UNDECORATED);

                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Lỗi Mượn Sách");
                alert1.setHeaderText("Không thể mượn sách");
                alert1.setContentText("Số lượng sách đã hết");
                alert1.showAndWait();
            }
        }
    }
}
