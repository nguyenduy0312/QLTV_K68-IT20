package controller;

import DAO.DocumentDAO;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.LoginView;
import model.Document;
import view.admin.AdminView;
import view.borrowcard.BorrowCardView;
import view.document.AddDocument;
import view.document.DocumentImageDisplay;
import view.document.EditDocument;
import view.document.DocumentView;
import view.returncard.ReturnCardView;
import view.user.AddUser;
import view.user.EditUser;
import view.user.UserInfoView;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController {

    @FXML
    private StackPane myPane;
    Stage stage = null;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Button maximizeButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button homeButton;
    @FXML
    private AnchorPane home;
    @FXML
    private Button qlSachButton;
    @FXML
    private AnchorPane quanLySach;
    @FXML
    private Button qlMuonSachButton;
    @FXML
    private AnchorPane quanLyMuonSach;
    @FXML
    private Button qlTraSachButton;
    @FXML
    private AnchorPane quanLyTraSach;
    @FXML
    private Button qlNguoiDungButton;
    @FXML
    private AnchorPane quanLyNguoiDung;
    @FXML
    private Button addUserButton;
    @FXML
    private Button editUserButton;
    @FXML
    private Button viewInfoUserButton;
    @FXML
    private Button borrowBookButton;
    @FXML
    private Button returnBookButton;
    @FXML
    private VBox vboxImages;
    public static final int COL = 7;

    // QL sách
    @FXML
    private TableView<Document> tableViewBook;
    @FXML private TableColumn<Integer, Integer> STT;  // Cột STT
    @FXML private TableColumn<Document, String> idColumn;   // Cột ID
    @FXML private TableColumn<Document, String> titleColumn;  // Cột Title
    @FXML private TableColumn<Document, String> categoryColumn;  // Cột Category
    @FXML private TableColumn<Document, Integer> quantityColumn;  // Cột Quantity
    @FXML private TableColumn<Document, Integer> maxBorrowDaysColumn;  // Cột maxBorrowDays

    @FXML
    private TextField searchBookField;
    @FXML
    private Button addBookButton;
    @FXML
    private Button deleteBookButton;
    @FXML
    private Button editBookButton;
    @FXML
    private Button viewInfoBookButton;
    @FXML
    private Button refreshBookButton;

    public void setUsername(String username) {
        if (username != null) {
            usernameLabel.setText(username);
        } else {
            System.out.println("Username is null!");
        }
    }

    public void closeButtonOnAction(ActionEvent e) {
        closeButton.getScene().getWindow().hide();
    }

    public void minimizeButtonOnAction(ActionEvent e) {
        // Lấy Stage hiện tại
        stage = (Stage) myPane.getScene().getWindow();
        // Thu nhỏ cửa sổ
        stage.setIconified(true);
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

    public void logoutButtonOnAction(ActionEvent e) {
        LoginView loginView = new LoginView();
        try {
            loginView.start(new Stage()); // Mở cửa sổ login
            closeButton.getScene().getWindow().hide(); // Đóng cửa sổ admin
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Ẩn tất cả các pane;
    private void hideAllPanes() {
        home.setVisible(false);
        quanLySach.setVisible(false);
        quanLyMuonSach.setVisible(false);
        quanLyTraSach.setVisible(false);
        quanLyNguoiDung.setVisible(false);
    }

    //Cửa sổ chính
    public void homeButtonOnAction(ActionEvent e) {
        hideAllPanes();
        home.setVisible(!home.isVisible());
    }

    //Cửa sổ quản lý sách
    public void qlSachButtonOnAction(ActionEvent e) {
        hideAllPanes();
        quanLySach.setVisible(!quanLySach.isVisible());
    }

    //Cửa sổ quản lý mượn sách
    public void qlMuonSachButtonOnAction(ActionEvent e) {
        hideAllPanes();
        quanLyMuonSach.setVisible(!quanLyMuonSach.isVisible());
    }

    //Cửa sổ quản lý trả sách
    public void qlTraSachButtonOnAction(ActionEvent e) {
        hideAllPanes();
        quanLyTraSach.setVisible(!quanLyTraSach.isVisible());
    }

    //Cửa sổ quản lý người dùng
    public void qlNguoiDungButtonOnAction(ActionEvent e) {
        hideAllPanes();
        quanLyNguoiDung.setVisible(!quanLyNguoiDung.isVisible());
    }

    // Add book
    public void addBookButtonOnAction(ActionEvent e) {
        AddDocument addDocument = new AddDocument();
        try {
            addDocument.start(new Stage()); // Mở cửa sổ addUser
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // Xóa sách
    public void deleteBookButtonOnAction(ActionEvent e) {
        Document selectedBook = tableViewBook.getSelectionModel().getSelectedItem();
        if(selectedBook != null) {
            DocumentDAO documentDAO = new DocumentDAO();
            documentDAO.deleteDocument(selectedBook.getId());
            loadBook();
        }
    }

    // Edit book
    public void editBookButtonOnAction(ActionEvent e) {
        Document selectedBook = tableViewBook.getSelectionModel().getSelectedItem();
        if(selectedBook != null) {
            try {
                // Tạo FXMLLoader để tải editdocument.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/document/editdocument.fxml"));

                // Tải FXML và tạo Scene
                Parent root = loader.load();

                // Lấy controller của cửa sổ Edit Document từ FXMLLoader
                EditDocumentController editDocumentController = loader.getController();
                editDocumentController.setDocument(selectedBook);
                editDocumentController.printInfo(selectedBook);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.UNDECORATED);

                stage.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Infor book
    public void viewInfoBookButtonOnAction(ActionEvent e) {
        Document selectedBook = tableViewBook.getSelectionModel().getSelectedItem();
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

    // Refresh Table View Book
    public void refreshBookButtonOnAction(ActionEvent e) {
        loadBook();
    }

    // Add user
    public void addUserButtonOnAction(ActionEvent e) {
        AddUser addUser = new AddUser();
        try {
            addUser.start(new Stage()); // Mở cửa sổ addUser
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Edit user
    public void editUserButtonOnAction(ActionEvent e) {
        EditUser editUser = new EditUser();
        try {
            editUser.start(new Stage()); // Mở cửa sổ editUser
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Infor user
    public void viewInfoUserButtonOnAction(ActionEvent e) {
        UserInfoView userInfoView = new UserInfoView();
        try {
           userInfoView.start(new Stage()); // Mở cửa sổ xem thông tin người dùng
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Borrow book
    public void borrowBookButtonOnAction(ActionEvent e) {
        BorrowCardView borrowCardView = new BorrowCardView();
        try {
            borrowCardView.start(new Stage()); // Mở cửa sổ mượn sách
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Return book
    public void returnBookButtonOnAction(ActionEvent e) {
        ReturnCardView returnCardView = new ReturnCardView();
        try {
           returnCardView.start(new Stage()); // Mở cửa sổ mượn sách
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Load ảnh
    public void loadImage() {
        // Gọi phương thức để hiển thị ảnh sách từ cơ sở dữ liệu
        String sqlQuery = "SELECT MaSach, Picture FROM document";  // Truy vấn ảnh sách cho Admin
        DocumentImageDisplay.displayBookImages(vboxImages, sqlQuery, COL);
    }

    // Load dữ liệu lên bảng sách
    public void loadBook() {
        // Thiết lập các cột trong TableView

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        categoryColumn.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getCategory()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        maxBorrowDaysColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMaxBorrowDays()).asObject());

        // Lấy dữ liệu từ cơ sở dữ liệu và hiển thị lên TableView
        DocumentDAO documentDAO = new DocumentDAO();
        List<Document> documentList = documentDAO.findAllDocuments();
        // Hiển thị danh sách sách
        if (documentList != null && !documentList.isEmpty()) {
            ObservableList<Document> observableDocumentList = FXCollections.observableArrayList(documentList);
            tableViewBook.setItems(observableDocumentList);  // Đặt dữ liệu cho TableView
        }

        // Lắng nghe thay đổi
        searchBookField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                // Nếu TextField trống, hiển thị lại toàn bộ danh sách sách
                if (documentList != null && !documentList.isEmpty()) {
                    ObservableList<Document> observableDocumentList = FXCollections.observableArrayList(documentList);
                    tableViewBook.setItems(observableDocumentList);  // Đặt dữ liệu cho TableView
                } else {
                    System.out.println("No documents to display.");
                }
            } else {
                // Nếu có nội dung, lọc dữ liệu và cập nhật TableView
                updateTableView(newValue);
            }
        });
    }


    //Update bảng theo từ khóa
    public void updateTableView(String keyword) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        DocumentDAO documentDAO = new DocumentDAO();
        List<Document> documentList = documentDAO.findAllDocuments();

        // Lọc danh sách theo keyword
        List<Document> filteredList = documentList.stream()
                .filter(document -> document.getId().toLowerCase().contains(keyword.toLowerCase()) || // Tìm trong mã sách
                        document.getTitle().toLowerCase().contains(keyword.toLowerCase()) || // Tìm trong thể loại
                        document.getCategory().toLowerCase().contains(keyword.toLowerCase())  )  // Tìm trong tiêu đề
                .collect(Collectors.toList());

        // Cập nhật TableView với dữ liệu được lọc
        tableViewBook.setItems(FXCollections.observableArrayList(filteredList));
    }


    public void searchBookFieldOnAction(ActionEvent e) {
        String keyword = searchBookField.getText();
        updateTableView(keyword);
    }


}
