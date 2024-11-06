package DAO;
import DataBase.JDBCConnection;
import model.Document;
import org.jetbrains.annotations.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
                preparedStatement.setString(3, document.getAuthor().getName());
                preparedStatement.setString(4, document.getCategory());
                preparedStatement.setString(5, document.getPublisher().getName());
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

    }

    @Override
    public void updateDocument(@Nullable Document document) {

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
        return null;
    }
}
