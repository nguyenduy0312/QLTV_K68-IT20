package DAO;

import DataBase.JDBCConnection;
import model.BookRating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRatingDAO {

    private String maSach; // Mã sách
    private double diemSo; // Điểm số của sách
    private int soLuotDanhGia; // Số lượt đánh giá

    public BookRatingDAO() {
        ;
    }

    // Constructor khởi tạo với mã sách
    public BookRatingDAO(String maSach) {
        this.maSach = maSach;
        this.diemSo = 0.0; // Mặc định điểm số là 0.0
        this.soLuotDanhGia = 0; // Mặc định số lượt đánh giá là 0
    }

    // Hàm khởi tạo rating cho sách nếu chưa có trong bảng BookRating
    public void initializeRating() throws SQLException {
        Connection connection = JDBCConnection.getJDBCConnection();
        if (connection != null) {
            String checkSQL = "SELECT * FROM Rating WHERE MaSach = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSQL);
            checkStmt.setString(1, maSach);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                // Nếu sách chưa có trong bảng BookRating, thêm vào với điểm số 0.0 và số lượt đánh giá 0
                String insertSQL = "INSERT INTO Rating (MaSach, DiemSo, SoLuotDanhGia) VALUES (?, 0.0, 0)";
                PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
                insertStmt.setString(1, maSach);
                insertStmt.executeUpdate();
                insertStmt.close();
            }

            rs.close();
            checkStmt.close();
            connection.close();
        }
    }

    // Hàm cập nhật điểm số và số lượt đánh giá của sách
    public void rateBook(int rating) throws SQLException {
        // Kiểm tra giá trị đầu vào có hợp lệ hay không
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Điểm đánh giá phải là số nguyên trong khoảng từ 0 đến 5.");
        }

        // Kiểm tra và khởi tạo rating nếu chưa có
        initializeRating();

        // Lấy điểm số và số lượt đánh giá hiện tại từ cơ sở dữ liệu
        Connection connection = JDBCConnection.getJDBCConnection();
        if (connection != null) {
            String selectSQL = "SELECT DiemSo, SoLuotDanhGia FROM Rating WHERE MaSach = ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
            selectStmt.setString(1, maSach);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                diemSo = rs.getDouble("DiemSo"); // Lấy điểm số hiện tại
                soLuotDanhGia = rs.getInt("SoLuotDanhGia"); // Lấy số lượt đánh giá hiện tại
            }

            // Tính điểm số mới và cập nhật số lượt đánh giá
            double newDiemSo = calculateNewRating(rating);

            // Cập nhật điểm số mới và số lượt đánh giá trong cơ sở dữ liệu
            String updateSQL = "UPDATE Rating SET DiemSo = ?, SoLuotDanhGia = SoLuotDanhGia + 1 WHERE MaSach = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
            updateStmt.setDouble(1, newDiemSo); // Cập nhật điểm số mới
            updateStmt.setString(2, maSach); // Cập nhật mã sách
            updateStmt.executeUpdate(); // Thực thi câu lệnh cập nhật

            updateStmt.close(); // Đóng PreparedStatement
            selectStmt.close(); // Đóng PreparedStatement
            connection.close(); // Đóng kết nối
        }
    }

    // Tính toán điểm số mới sau khi đánh giá
    private double calculateNewRating(int rating) {
        // Tính điểm số mới theo công thức trung bình cộng
        double newDiemSo = (diemSo * soLuotDanhGia + rating) / (soLuotDanhGia + 1);
        soLuotDanhGia++; // Tăng số lượt đánh giá lên 1
        return newDiemSo;
    }

    public BookRating getRatingByBookId(String bookId) {
        String query = "SELECT DiemSo, SoLuotDanhGia FROM rating WHERE MaSach = ?";
        BookRating bookRating = null;

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double score = resultSet.getDouble("DiemSo");
                    int numReviews = resultSet.getInt("SoLuotDanhGia");
                    bookRating = new BookRating(bookId, score, numReviews);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookRating;
    }

    // Getter và Setter
    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public double getDiemSo() {
        return diemSo;
    }

    public void setDiemSo(double diemSo) {
        this.diemSo = diemSo;
    }

    public int getSoLuotDanhGia() {
        return soLuotDanhGia;
    }

    public void setSoLuotDanhGia(int soLuotDanhGia) {
        this.soLuotDanhGia = soLuotDanhGia;
    }

    // Phương thức main dùng để test
    public static void main(String[] args) {
        try {
            // Mã sách cần đánh giá
            String maSach = "9780300262650";

            // Tạo đối tượng BookRatingDAO
            BookRatingDAO bookRatingDAO = new BookRatingDAO(maSach);

            // Đánh giá sách với điểm số là 4
            bookRatingDAO.rateBook(5);
            System.out.println("Đánh giá sách " + maSach + " thành công!");

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
