package controller;

import DAO.BorrowReturnDAO;
import DAO.DocumentDAO;
import DAO.UserDAO;
import javafx.application.Platform;
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
import model.BorrowReturn;
import model.Document;
import model.User;
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

import util.Date;
import java.awt.*;
import java.io.IOException;
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
    private VBox vboxImages;
    public static final int COL = 10;

    // QL sách
    @FXML
    private TableView<Document> tableViewBook;

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


    // Quản Lý User
    @FXML
    private TableView<User> tableViewUser;

    @FXML private TableColumn<User, String> personIdColumn;   // Cột ID
    @FXML private TableColumn<User, String> personNameColumn;  // Cột Name
    @FXML private TableColumn<User, String> genderColumn;  // Cột Gender
    @FXML private TableColumn<User, Date> dateOfBirthColumn;  // Cột dateOfBirth
    @FXML private TableColumn<User, String> phoneNumberColumn;  // Cột phoneNumber

    @FXML
    private TextField searchUserField;
    @FXML
    private Button addUserButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Button editUserButton;
    @FXML
    private Button viewInfoUserButton;
    @FXML
    private Button refreshUserButton;


    // Lưu trữ danh sách tài liệu và FilteredList
    private ObservableList<Document> observableDocumentList;
    private FilteredList<Document> filteredListDocument;
    private ObservableList<Document> observableDocument1List;
    private FilteredList<Document> filteredListDocument1;


    // Lưu trữ danh sách người dùng và FilteredList
    private ObservableList<User> observableUserList;
    private FilteredList<User> filteredListUser;
    private ObservableList<User> observableUser1List;
    private FilteredList<User> filteredListUser1;

    // Lưu trữ danh sách mượn trả và FilteredList
    private ObservableList<BorrowReturn> observableBorrowReturnList;
    private FilteredList<BorrowReturn> filteredListBorrowReturn;

    public void setUsername(String username) {
        if (username != null) {
            usernameLabel.setText(username);
        } else {
            System.out.println("Username is null!");
        }
    }


    //QL mượn
    @FXML
    private TextField searchUserField1;
    @FXML
    private  TableView<User>tableViewUser1;
    @FXML private TableColumn<User, String> personId1Column;   // Cột ID
    @FXML private TableColumn<User, String> personName1Column;  // Cột Name
    @FXML private TableColumn<User, String> phoneNumber1Column;  // Cột Gender

    @FXML
    private TextField searchBookField1;
    @FXML
    private TableView<Document>tableViewBook1;
    @FXML private TableColumn<Document, String> id1Column;   // Cột ID
    @FXML private TableColumn<Document, String> title1Column;  // Cột Title
    @FXML private TableColumn<Document, Integer> quantity1Column;  // Cột Quantity

    @FXML
    private Button borrowBookButton;


    // QL trả
    @FXML
    private TextField searchInfoBorrowField;
    @FXML
    private TableView<BorrowReturn> infoBorrowTableView;
    @FXML private TableColumn<BorrowReturn, String> borrowIDColumn;
    @FXML private TableColumn<BorrowReturn, String> personID2Column;
    @FXML private TableColumn<BorrowReturn, String> bookID2Column;
    @FXML private TableColumn<BorrowReturn, Date> borrowDateColumn;
    @FXML private TableColumn<BorrowReturn, Date> dueDateColumn;

    @FXML private Button returnBookButton;



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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/document/adddocument.fxml"));
            Parent root = loader.load();

            // Lấy controller của AddDocument
            AddDocumentController addDocumentController = loader.getController();
            // Truyền tham chiếu của AdminController cho AddDocumentController
            addDocumentController.setAdminController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Xóa sách
    public void deleteBookButtonOnAction(ActionEvent e) {
        Document selectedBook = tableViewBook.getSelectionModel().getSelectedItem();
        BorrowReturnDAO borrowReturnDAO = new BorrowReturnDAO();
        if(selectedBook != null && borrowReturnDAO.findBookById(selectedBook.getId())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi Xóa Sách");
            alert.setHeaderText("Không thể xóa vì người dùng vẫn đang mượn sách");
            alert.setContentText("Vui lòng kiểm tra lại thông tin hoặc thử lại sau.");
            alert.showAndWait();

        } else if(selectedBook != null && !borrowReturnDAO.findBookById(selectedBook.getId()) ) {
            DocumentDAO documentDAO = new DocumentDAO();
            documentDAO.deleteDocument(selectedBook.getId());

            loadBook();
            loadBook1();
            loadImage();
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
                editDocumentController.setAdminController(this);
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


    // Load ảnh
    public void loadImage() {
        // Gọi phương thức để hiển thị ảnh sách từ cơ sở dữ liệu
        String sqlQuery = "SELECT MaSach, Picture FROM document";  // Truy vấn ảnh sách cho Admin
        DocumentImageDisplay.displayBookImages(vboxImages, sqlQuery, COL);
    }

    public void loadBook() {
        // Thiết lập các cột trong TableView
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        maxBorrowDaysColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMaxBorrowDays()).asObject());

        // Lấy dữ liệu từ cơ sở dữ liệu một lần và lưu vào biến
        DocumentDAO documentDAO = new DocumentDAO();
        List<Document> documentList = documentDAO.findAllDocuments();

        // Chuyển List thành ObservableList
        observableDocumentList = FXCollections.observableArrayList(documentList);

        // Tạo FilteredList từ ObservableList (mặc định hiển thị tất cả)
        filteredListDocument = new FilteredList<>(observableDocumentList, p -> true);

        // Đặt FilteredList vào TableView
        tableViewBook.setItems(filteredListDocument);

        // Lắng nghe thay đổi từ TextField để lọc dữ liệu
        searchBookField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTableViewBook(newValue);  // Gọi hàm lọc khi có thay đổi từ khóa tìm kiếm
        });
    }

    public void updateTableViewBook(String keyword) {
        // Kiểm tra từ khóa tìm kiếm
        if (keyword == null || keyword.trim().isEmpty()) {
            // Nếu không có từ khóa, hiển thị toàn bộ danh sách
            filteredListDocument.setPredicate(p -> true);
        } else {
            // Nếu có từ khóa, lọc danh sách và cập nhật TableView
            String lowerCaseKeyword = keyword.toLowerCase();
            filteredListDocument.setPredicate(document -> {
                return document.getId().toLowerCase().contains(lowerCaseKeyword) ||
                        document.getTitle().toLowerCase().contains(lowerCaseKeyword) ||
                        document.getCategory().toLowerCase().contains(lowerCaseKeyword);
            });
        }
    }

    public void searchBookFieldOnAction(ActionEvent e) {
        String keyword = searchBookField.getText();
        updateTableViewBook(keyword);
    }



    // Add user
    public void addUserButtonOnAction(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user/adduser.fxml"));
            Parent root = loader.load();

            // Lấy controller của AddDocument
            AddUserController addUserController = loader.getController();
            // Truyền tham chiếu của AdminController cho AddDocumentController
           addUserController.setAdminController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Xóa user
    public void deleteUserButtonOnAction(ActionEvent e) {
        User selectedUser = tableViewUser.getSelectionModel().getSelectedItem();
        BorrowReturnDAO borrowReturnDAO = new BorrowReturnDAO();
        if(selectedUser != null && borrowReturnDAO.findUserById(selectedUser.getId())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi Xóa Người Dùng");
            alert.setHeaderText("Không thể xóa vì người dùng vẫn đang mượn sách");
            alert.setContentText("Vui lòng kiểm tra lại thông tin hoặc thử lại sau.");
            alert.showAndWait();

        } else if(selectedUser != null && !borrowReturnDAO.findUserById(selectedUser.getId())) {
           UserDAO userDAO = new UserDAO();
           userDAO.deleteUser(selectedUser.getId());
           loadUser();
        }
    }

    // Edit user
    public void editUserButtonOnAction(ActionEvent e) {
       User selectedUser = tableViewUser.getSelectionModel().getSelectedItem();
        if(selectedUser != null) {
            try {
                // Tạo FXMLLoader để tải edituser.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user/edituser.fxml"));

                // Tải FXML và tạo Scene
                Parent root = loader.load();

                // Lấy controller của cửa sổ Edit Document từ FXMLLoader
                EditUserController editUserController = loader.getController();
                editUserController.setAdminController(this);
                editUserController.setUser(selectedUser);
                editUserController.printInfo(selectedUser);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.UNDECORATED);

                stage.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Infor user
    public void viewInfoUserButtonOnAction(ActionEvent e) {
        User selectedUser = tableViewUser.getSelectionModel().getSelectedItem();
        try {
            // Tạo FXMLLoader để tải doucmentview.fxml
            FXMLLoader loader = new FXMLLoader(DocumentImageDisplay.class.getResource("/view/user/userinfoview.fxml"));

            // Tải FXML và tạo Scene
            Parent root = loader.load();


            UserInfoController userInfoController = loader.getController();
            userInfoController.setInfoById(selectedUser.getId());

            // Tạo và hiển thị cửa sổ DocumentView
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void loadUser() {
        // Thiết lập các cột trong TableView
        personIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        personNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
        dateOfBirthColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBirthday()));
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));

        // Lấy dữ liệu từ cơ sở dữ liệu một lần và lưu vào biến
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.findAllUsers();

        // Chuyển List thành ObservableList
        observableUserList = FXCollections.observableArrayList(userList);

        // Tạo FilteredList từ ObservableList (mặc định hiển thị tất cả)
        filteredListUser = new FilteredList<>(observableUserList, p -> true);

        // Đặt FilteredList vào TableView
        tableViewUser.setItems(filteredListUser);

        // Lắng nghe thay đổi từ TextField để lọc dữ liệu
        searchUserField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTableViewUser(newValue);  // Gọi hàm lọc khi có thay đổi từ khóa tìm kiếm
        });
    }

    public void updateTableViewUser(String keyword) {
        // Kiểm tra từ khóa tìm kiếm
        if (keyword == null || keyword.trim().isEmpty()) {
            // Nếu không có từ khóa, hiển thị toàn bộ danh sách
            filteredListUser.setPredicate(p -> true);
        } else {
            // Nếu có từ khóa, lọc danh sách và cập nhật TableView
            String lowerCaseKeyword = keyword.toLowerCase();
            filteredListUser.setPredicate(user -> {
                return user.getId().toLowerCase().contains(lowerCaseKeyword) ||
                        user.getName().toLowerCase().contains(lowerCaseKeyword) ||
                        user.getPhoneNumber().toLowerCase().contains(lowerCaseKeyword);
            });
        }
    }

    public void searchUserFieldOnAction(ActionEvent e) {
        String keyword = searchUserField.getText();
        updateTableViewUser(keyword);
    }


    // Quản lý Mượn

    public void loadUser1() {
        // Thiết lập các cột trong TableView
        personId1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        personName1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        phoneNumber1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));

        // Lấy dữ liệu từ cơ sở dữ liệu một lần và lưu vào biến
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.findAllUsers();

        // Chuyển List thành ObservableList
        observableUser1List = FXCollections.observableArrayList(userList);

        // Tạo FilteredList từ ObservableList (mặc định hiển thị tất cả)
        filteredListUser1 = new FilteredList<>(observableUser1List, p -> true);

        // Đặt FilteredList vào TableView
        tableViewUser1.setItems(filteredListUser1);

        // Lắng nghe thay đổi từ TextField để lọc dữ liệu
        searchUserField1.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTableViewUser1(newValue);  // Gọi hàm lọc khi có thay đổi từ khóa tìm kiếm
        });
    }

    // Hàm lọc danh sách theo từ khóa
    public void updateTableViewUser1(String keyword) {
        // Kiểm tra từ khóa tìm kiếm
        if (keyword == null || keyword.trim().isEmpty()) {
            // Nếu không có từ khóa, hiển thị toàn bộ danh sách
            filteredListUser1.setPredicate(p -> true);
        } else {
            // Nếu có từ khóa, lọc danh sách và cập nhật TableView
            String lowerCaseKeyword = keyword.toLowerCase();
            filteredListUser1.setPredicate(user -> {
                return user.getId().toLowerCase().contains(lowerCaseKeyword) ||
                        user.getName().toLowerCase().contains(lowerCaseKeyword) ||
                        user.getPhoneNumber().toLowerCase().contains(lowerCaseKeyword);
            });
        }
    }
    public void searchUserField1OnAction(ActionEvent e) {
        String keyword = searchUserField1.getText();
        updateTableViewUser1(keyword);
    }

    public void loadBook1() {
        // Thiết lập các cột trong TableView
        id1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        title1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        quantity1Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        // Lấy dữ liệu từ cơ sở dữ liệu một lần và lưu vào biến
        DocumentDAO documentDAO = new DocumentDAO();
        List<Document> documentList = documentDAO.findAllDocuments();

        // Chuyển List thành ObservableList
        observableDocument1List = FXCollections.observableArrayList(documentList);

        // Tạo FilteredList từ ObservableList (mặc định hiển thị tất cả)
        filteredListDocument1 = new FilteredList<>(observableDocument1List, p -> true);

        // Đặt FilteredList vào TableView
        tableViewBook1.setItems(filteredListDocument1);

        // Lắng nghe thay đổi từ TextField để lọc dữ liệu
        searchBookField1.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTableViewBook1(newValue);  // Gọi hàm lọc khi có thay đổi từ khóa tìm kiếm
        });
    }

    // Hàm lọc danh sách theo từ khóa
    public void updateTableViewBook1(String keyword) {
        // Kiểm tra từ khóa tìm kiếm
        if (keyword == null || keyword.trim().isEmpty()) {
            // Nếu không có từ khóa, hiển thị toàn bộ danh sách
            filteredListDocument1.setPredicate(p -> true);
        } else {
            // Nếu có từ khóa, lọc danh sách và cập nhật TableView
            String lowerCaseKeyword = keyword.toLowerCase();
            filteredListDocument1.setPredicate(document -> {
                return document.getId().toLowerCase().contains(lowerCaseKeyword) ||
                        document.getTitle().toLowerCase().contains(lowerCaseKeyword) ||
                        document.getCategory().toLowerCase().contains(lowerCaseKeyword);
            });
        }
    }

    public void searchBookField1OnAction(ActionEvent e) {
        String keyword = searchBookField1.getText();
        updateTableViewBook1(keyword);
    }



    // Borrow book

    public void borrowBookButtonOnAction(ActionEvent e) {
        // Lấy hàng được chọn từ bảng User và Document
        User selectedUser = tableViewUser1.getSelectionModel().getSelectedItem();
        Document selectedDocument = tableViewBook1.getSelectionModel().getSelectedItem();

        // Kiểm tra xem cả hai bảng đều có hàng được chọn
        if (selectedUser != null && selectedDocument != null) {
            if (selectedDocument.getQuantity() > 0) {
                // Nếu cả hai bảng đều có lựa chọn, thực hiện hành động mong muốn
                System.out.println("Selected User: " + selectedUser.getName());
                System.out.println("Selected Document: " + selectedDocument.getTitle());

                try {
                    // Tạo FXMLLoader để tải borrowcardview.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/borrowcard/borrowcardview.fxml"));

                    // Tải FXML và tạo Scene
                    Parent root = loader.load();

                    // Lấy controller của cửa sổ
                    BorrowCardController borrowCardController = loader.getController();
                    borrowCardController.setAdminController(this);
                    borrowCardController.setUser(selectedUser);
                    borrowCardController.setDocument(selectedDocument);

                    borrowCardController.borrowDocument(selectedUser, selectedDocument);

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


    // QL trả sách

    // Return book
    public void returnBookButtonOnAction(ActionEvent e) {
        BorrowReturn selectedBorrowReturn = infoBorrowTableView.getSelectionModel().getSelectedItem();
        // Kiểm tra xem cả hai bảng đều có hàng được chọn
        if (selectedBorrowReturn != null) {
            try {
                // Tạo FXMLLoader để tải borrowcardview.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/returncard/returncardview.fxml"));

                // Tải FXML và tạo Scene
                Parent root = loader.load();

                // Lấy controller của cửa sổ
                ReturnCardController returnCardController = loader.getController();
                returnCardController.setAdminController(this);
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

    public void loadInfoBorrow() {
        // Thiết lập các cột trong TableView
        borrowIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaMuon()));
        personID2Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaNguoiMuon()));
        bookID2Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaSach()));
        borrowDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNgayMuon()));
        dueDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNgayHenTra()));

        // Lấy dữ liệu từ cơ sở dữ liệu một lần và lưu vào biến
        BorrowReturnDAO borrowReturnDAO = new BorrowReturnDAO();
        List<BorrowReturn> borrowReturnList = borrowReturnDAO.findAllInfo();

        // Chuyển List thành ObservableList
        observableBorrowReturnList = FXCollections.observableArrayList(borrowReturnList);

        // Tạo FilteredList từ ObservableList (mặc định hiển thị tất cả)
        filteredListBorrowReturn = new FilteredList<>(observableBorrowReturnList, p -> true);

        // Đặt FilteredList vào TableView
        infoBorrowTableView.setItems(filteredListBorrowReturn);

        // Lắng nghe thay đổi từ TextField để lọc dữ liệu
        searchInfoBorrowField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateInfoBorrowTableView(newValue);  // Gọi hàm lọc khi có thay đổi từ khóa tìm kiếm
        });
    }

    // Hàm lọc danh sách theo từ khóa
    public void updateInfoBorrowTableView(String keyword) {
        // Kiểm tra từ khóa tìm kiếm
        if (keyword == null || keyword.trim().isEmpty()) {
            // Nếu không có từ khóa, hiển thị toàn bộ danh sách
            filteredListBorrowReturn.setPredicate(p -> true);
        } else {
            // Nếu có từ khóa, lọc danh sách và cập nhật TableView
            String lowerCaseKeyword = keyword.toLowerCase();
            filteredListBorrowReturn.setPredicate(borrowReturn -> {
                return borrowReturn.getMaMuon().toLowerCase().contains(lowerCaseKeyword) ||
                        borrowReturn.getMaNguoiMuon().toLowerCase().contains(lowerCaseKeyword) ||
                        borrowReturn.getNgayMuon().toString().toLowerCase().contains(lowerCaseKeyword) ||
                        borrowReturn.getMaSach().toLowerCase().contains(lowerCaseKeyword);
            });
        }
    }

    public void searchInfoBorrowFieldOnAction(ActionEvent e) {
        String keyword = searchInfoBorrowField.getText();
        updateTableViewBook(keyword);
    }


}
