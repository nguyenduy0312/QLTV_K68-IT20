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
        } else {
            tableViewBook.setItems(FXCollections.observableArrayList()); // Đặt danh sách trống
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
                updateTableViewBook(newValue);
            }
        });
    }


    //Update bảng theo từ khóa
    public void updateTableViewBook(String keyword) {
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

    // Refresh Table View Book
    public void refreshUserButtonOnAction(ActionEvent e) {
        loadUser();
        loadUser1();
    }

    // Load dữ liệu lên bảng user
    public void loadUser() {
        // Thiết lập các cột trong TableView

        personIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        personNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        genderColumn.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getGender()));
        dateOfBirthColumn.setCellValueFactory(cellData -> {
            return new SimpleObjectProperty<>(cellData.getValue().getBirthday());
        });
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));

        // Lấy dữ liệu từ cơ sở dữ liệu và hiển thị lên TableView
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.findAllUsers();

        // Hiển thị danh sách user
        if (userList != null && !userList.isEmpty()) {
            ObservableList<User> observableUserList = FXCollections.observableArrayList(userList);
            tableViewUser.setItems(observableUserList);  // Đặt dữ liệu cho TableView
        } else {
            tableViewUser.setItems(FXCollections.observableArrayList()); // Đặt danh sách trống
        }

        // Lắng nghe thay đổi
        searchUserField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                // Nếu TextField trống, hiển thị lại toàn bộ danh sách user
                if (userList != null && !userList.isEmpty()) {
                    ObservableList<User> observableUserList = FXCollections.observableArrayList(userList);
                    tableViewUser.setItems(observableUserList);  // Đặt dữ liệu cho TableView
                } else {
                    System.out.println("No user to display.");
                }
            } else {
                // Nếu có nội dung, lọc dữ liệu và cập nhật TableView
                updateTableViewUser(newValue);
            }
        });
    }


    //Update bảng theo từ khóa
    public void updateTableViewUser(String keyword) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.findAllUsers();

        // Lọc danh sách theo keyword
        List<User> filteredList = userList.stream()
                .filter(user -> user.getId().toLowerCase().contains(keyword.toLowerCase()) || // Tìm theo id
                        user.getName().toLowerCase().contains(keyword.toLowerCase()) || // Tìm theo tên
                        user.getPhoneNumber().toLowerCase().contains(keyword.toLowerCase()) )  // Tìm theo  số điện thoại
                .collect(Collectors.toList());

        // Cập nhật TableView với dữ liệu được lọc
        tableViewUser.setItems(FXCollections.observableArrayList(filteredList));
    }

    public void searchUserFieldOnAction(ActionEvent e) {
        String keyword = searchUserField.getText();
        updateTableViewUser(keyword);
    }


    // Quản lý Mượn

    // Load bảng user1
    public void loadUser1() {
        // Thiết lập các cột trong TableView

        personId1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        personName1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        phoneNumber1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));

        // Lấy dữ liệu từ cơ sở dữ liệu và hiển thị lên TableView
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.findAllUsers();

        // Hiển thị danh sách user
        if (userList != null && !userList.isEmpty()) {
            ObservableList<User> observableUserList = FXCollections.observableArrayList(userList);
            tableViewUser1.setItems(observableUserList);  // Đặt dữ liệu cho TableView
        } else {
            tableViewUser1.setItems(FXCollections.observableArrayList()); // Đặt danh sách trống
        }

        // Lắng nghe thay đổi
        searchUserField1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                // Nếu TextField trống, hiển thị lại toàn bộ danh sách user
                if (userList != null && !userList.isEmpty()) {
                    ObservableList<User> observableUserList = FXCollections.observableArrayList(userList);
                    tableViewUser.setItems(observableUserList);  // Đặt dữ liệu cho TableView
                } else {
                    System.out.println("No user to display.");
                }
            } else {
                // Nếu có nội dung, lọc dữ liệu và cập nhật TableView
                updateTableViewUser1(newValue);
            }
        });
    }


    //Update bảng theo từ khóa
    public void updateTableViewUser1(String keyword) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.findAllUsers();

        // Lọc danh sách theo keyword
        List<User> filteredList = userList.stream()
                .filter(user -> user.getId().toLowerCase().contains(keyword.toLowerCase()) || // Tìm theo id
                        user.getName().toLowerCase().contains(keyword.toLowerCase()) || // Tìm theo tên
                        user.getPhoneNumber().toLowerCase().contains(keyword.toLowerCase()) )  // Tìm theo  số điện thoại
                .collect(Collectors.toList());

        // Cập nhật TableView với dữ liệu được lọc
        tableViewUser1.setItems(FXCollections.observableArrayList(filteredList));
    }

    public void searchUserField1OnAction(ActionEvent e) {
        String keyword = searchUserField1.getText();
        updateTableViewUser1(keyword);
    }

    // Load bảng book1

    public void loadBook1() {
        // Thiết lập các cột trong TableView

        id1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        title1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        quantity1Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        // Lấy dữ liệu từ cơ sở dữ liệu và hiển thị lên TableView
        DocumentDAO documentDAO = new DocumentDAO();
        List<Document> documentList = documentDAO.findAllDocuments();
        // Hiển thị danh sách sách
        if (documentList != null && !documentList.isEmpty()) {
            ObservableList<Document> observableDocumentList = FXCollections.observableArrayList(documentList);
            tableViewBook1.setItems(observableDocumentList);  // Đặt dữ liệu cho TableView
        } else {
            tableViewBook1.setItems(FXCollections.observableArrayList()); // Đặt danh sách trống
        }

        // Lắng nghe thay đổi
        searchBookField1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                // Nếu TextField trống, hiển thị lại toàn bộ danh sách sách
                if (documentList != null && !documentList.isEmpty()) {
                    ObservableList<Document> observableDocumentList = FXCollections.observableArrayList(documentList);
                    tableViewBook1.setItems(observableDocumentList);  // Đặt dữ liệu cho TableView
                } else {
                    System.out.println("No documents to display.");
                }
            } else {
                // Nếu có nội dung, lọc dữ liệu và cập nhật TableView
                updateTableViewBook1(newValue);
            }
        });
    }


    //Update bảng theo từ khóa
    public void updateTableViewBook1(String keyword) {
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
        tableViewBook1.setItems(FXCollections.observableArrayList(filteredList));
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
        bookID2Column.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getMaSach()));
        borrowDateColumn.setCellValueFactory(cellData -> {
            return new SimpleObjectProperty<>(cellData.getValue().getNgayMuon());
        });
        dueDateColumn.setCellValueFactory(cellData -> {
            return new SimpleObjectProperty<>(cellData.getValue().getNgayHenTra());
        });


        // Lấy dữ liệu từ cơ sở dữ liệu và hiển thị lên TableView
        BorrowReturnDAO borrowReturnDAO = new BorrowReturnDAO();
        List<BorrowReturn> borrowReturnList = borrowReturnDAO.findAllInfo();
        // Hiển thị danh sách sách
        if (borrowReturnList != null && !borrowReturnList.isEmpty()) {
            ObservableList<BorrowReturn> observableBorrowReturnList = FXCollections.observableArrayList(borrowReturnList);
            infoBorrowTableView.setItems(observableBorrowReturnList);  // Đặt dữ liệu cho TableView
        } else {
            infoBorrowTableView.setItems(FXCollections.observableArrayList()); // Đặt danh sách trống
        }

        // Lắng nghe thay đổi
        searchInfoBorrowField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                if (borrowReturnList != null && !borrowReturnList.isEmpty()) {
                    ObservableList<BorrowReturn> observableBorrowReturnList = FXCollections.observableArrayList(borrowReturnList);
                    infoBorrowTableView.setItems(observableBorrowReturnList);  // Đặt dữ liệu cho TableView
                } else {
                    System.out.println("No info borrow to display.");
                }
            } else {
                // Nếu có nội dung, lọc dữ liệu và cập nhật TableView
                updateInfoBorrowTableView(newValue);
            }
        });
    }


    //Update bảng theo từ khóa
    public void updateInfoBorrowTableView(String keyword) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        BorrowReturnDAO borrowReturnDAO = new BorrowReturnDAO();
        List<BorrowReturn> borrowReturnList = borrowReturnDAO.findAllInfo();

        // Lọc danh sách theo keyword
        List<BorrowReturn> filteredList = borrowReturnList.stream()
                .filter(borrowReturn -> borrowReturn.getMaMuon().toLowerCase().contains(keyword.toLowerCase()) || // Tìm trong mã sách
                        borrowReturn.getMaNguoiMuon().toLowerCase().contains(keyword.toLowerCase()) || // Tìm trong mã người mượn
                        borrowReturn.getNgayMuon().toString().toLowerCase().contains(keyword.toLowerCase()) ||  // Tìm trong ngày mượn
                        borrowReturn.getMaSach().toLowerCase().contains(keyword.toLowerCase())  )  // Tìm trong mã sách
                .collect(Collectors.toList());

        // Cập nhật TableView với dữ liệu được lọc
        infoBorrowTableView.setItems(FXCollections.observableArrayList(filteredList));
    }

    public void searchInfoBorrowFieldOnAction(ActionEvent e) {
        String keyword = searchInfoBorrowField.getText();
        updateTableViewBook(keyword);
    }
}
