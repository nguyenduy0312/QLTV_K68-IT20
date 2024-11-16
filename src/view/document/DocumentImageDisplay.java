package view.document;

import controller.AdminController;
import controller.DocumentController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import DataBase.JDBCConnection;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DocumentImageDisplay {

    // Phương thức chung để hiển thị tất cả ảnh sách vào VBox
    public static void displayBookImages(VBox vbox, String sqlQuery,  int COL) {
        try {
            // Kết nối đến cơ sở dữ liệu
            Connection connection = JDBCConnection.getJDBCConnection();
            if (connection != null) {
                PreparedStatement stmt = connection.prepareStatement(sqlQuery);
                ResultSet rs = stmt.executeQuery();

                int row = 0;  // Dùng để điều khiển vị trí HBox trong VBox
                int imageCount = 0; // Đếm số lượng ảnh trong mỗi HBox

                // Lặp qua các kết quả từ cơ sở dữ liệu
                while (rs.next()) {
                    byte[] imageBytes = rs.getBytes("Picture");
                    String bookId = rs.getString("MaSach");

                    if (imageBytes != null) {
                        // Chuyển byte array thành Image
                        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                        Image image = new Image(bis);

                        // Lấy HBox tương ứng từ VBox
                        HBox currentHBox = getHBoxByRow(vbox, row);  // Truy xuất HBox theo chỉ số

                        // Lấy ImageView từ HBox
                        ImageView imageView = (ImageView) currentHBox.getChildren().get(imageCount);

                        // Cập nhật ảnh cho ImageView
                        imageView.setImage(image);
                        // Giữ tỷ lệ cho ảnh
                        imageView.setPreserveRatio(true);

                        // Gắn mã sách vào UserData của ImageView
                        imageView.setUserData(bookId);

                        // Gắn sự kiện click vào ImageView
                        imageView.setOnMouseClicked(event -> {
                            String clickedBookId = (String) imageView.getUserData();
                            System.out.println("Mã sách được chọn: " + clickedBookId);
                            try {

                                // Tạo FXMLLoader để tải doucmentview.fxml
                                FXMLLoader loader = new FXMLLoader(DocumentImageDisplay.class.getResource("/view/document/documentview.fxml"));

                                // Tải FXML và tạo Scene
                                Parent root = loader.load();


                               DocumentController documentController = loader.getController();
                               documentController.setInfoById(clickedBookId);

                                // Tạo và hiển thị cửa sổ DocumentView
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.initStyle(StageStyle.UNDECORATED);

                                stage.show();

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });

                        imageCount++;  // Tăng số lượng ảnh đã thêm vào HBox

                        // Nếu mỗi HBox chứa đủ 5 ImageView, chuyển sang HBox tiếp theo
                        if (imageCount == COL) {
                            row++;  // Chuyển sang HBox tiếp theo
                            imageCount = 0;  // Reset số ảnh trong HBox
                        }
                    }
                }

                rs.close();
                stmt.close();
                connection.close();
            } else {
                System.out.println("Kết nối cơ sở dữ liệu thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi hiển thị ảnh: " + e.getMessage());
        }
    }

    // Phương thức để lấy HBox từ VBox dựa trên chỉ số
    private static HBox getHBoxByRow(VBox vbox, int rowIndex) {
        // Dựa vào rowIndex để lấy HBox
        return (HBox) vbox.getChildren().get(rowIndex);
    }
}
