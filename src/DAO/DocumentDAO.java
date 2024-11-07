package DAO;
import DataBase.JDBCConnection;
import model.Document;
import model.Person;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO implements DocumentDAOInterface {
    public static DocumentDAO getInstance() {
        return new DocumentDAO();
    }
    @Override
    public void addDocument(@Nullable Document document) {
        String sql = "INSERT INTO Document (MaSach, TenSach, TacGia, TheLoaiSach, NhaXuatBan, SoLuong, SoNgayMuon) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = JDBCConnection.getJDBCConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                // Gán giá trị cho các tham số trong câu lệnh SQL
                preparedStatement.setString(1, document.getId());
                preparedStatement.setString(2, document.getTitle());
                preparedStatement.setString(3, document.getAuthor());
                preparedStatement.setString(4, document.getCategory());
                preparedStatement.setString(5, document.getPublisher());
                preparedStatement.setInt(6, document.getQuantity());
                preparedStatement.setInt(7, document.getMaxBorrowDays());

                // Thực thi câu lệnh SQL
                int result = preparedStatement.executeUpdate();

                // In kết quả
                System.out.println(result + " row(s) inserted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDocument(@Nullable String maSach) {
        // Tạo câu lệnh truy vấn sql
        String sql = "DELETE FROM Document WHERE MaSach = ?";

        // Tạo kết nối tới csdl
        try (Connection connection = JDBCConnection.getJDBCConnection()) {

            // Tạo đối tượng thực thi câu lệnh sql
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                // Gán giá trị cho các tham số trong câu lệnh sql
                preparedStatement.setString(1,maSach);

                // Thực thi câu lệnh
                int result = preparedStatement.executeUpdate();

                // In kết quả
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDocument(@Nullable Document document) {
        // Tạo câu lệnh truy vấn SQL
        String sql ="";

        // Tạo kết nối tới CSDL
        try (Connection connection = JDBCConnection.getJDBCConnection()) {
            // Tạo đối tượng thực thi câu lệnh SQL
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Gán giá trị cho tham số trong câu lệnh SQL
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Thực thi câu lệnh SQL
        // In ra kết quả
    }

    @Nullable
    @Override
    public Document findDocumentById(@Nullable String maSach) {
        return null;
    }

    @Nullable
    @Override
    public Document findDocumentByTitle(@Nullable String tenSach) {
        return null;
    }

    @Nullable
    @Override
    public Document findDocumentByCategory(@Nullable String theLoai) {
        return null;
    }

    @Nullable
    @Override
    public List<Document> findAllDocuments() {
        ArrayList<Document> documentList = new ArrayList<>();
        // Tạo câu lệnh truy vấn.
        String sql ="SELECT * FROM Document";
        // Tạo kết nối tới CSDL.
        try (Connection connection = JDBCConnection.getJDBCConnection()) {
            // Tạo đối tượng thực thi câu lệnh sql.
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // resultSet lưu trữ dữ liệu lấy ra từ CSDL
                try (ResultSet resultSet = statement.executeQuery()) {
                    // lấy dữ liệu từ resultSet gán vào biến
                    while (resultSet.next()) {
                        String id = resultSet.getString("MaSach");
                        String title = resultSet.getString("TenSach");
                        String category = resultSet.getString("TheLoai");
                        String author = resultSet.getString("TacGia");
                        String publisher = resultSet.getString("NhaXuatBan");
                        int quantity = resultSet.getInt("SoLuong");
                        int maxBorrowed = resultSet.getInt("SoNgayMuon");
                       Document document = new Document(id,title,category,author, publisher, quantity,maxBorrowed);
                       documentList.add(document);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
