package DAO;

import DataBase.JDBCConnection;
import javafx.scene.image.Image;
import model.Document;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link DocumentDAOInterface} for managing documents in the database.
 */
public class DocumentDAO implements DocumentDAOInterface {

    /**
     * Returns a singleton instance of DocumentDAO.
     *
     * @return an instance of {@link DocumentDAO}.
     */
    public static DocumentDAO getInstance() {
        return new DocumentDAO();
    }

    @Override
    /**
     * Adds a new document to the database.
     *
     * @param document the {@link Document} object to be added.
     */
    public void addDocument(Document document) {

        String sql = "INSERT INTO Document (MaSach, TenSach, TacGia, TheLoaiSach, NhaXuatBan, SoLuong, SoNgayMuon, Picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlInsertDocument = "INSERT INTO Document (MaSach, TenSach, TacGia, TheLoaiSach, NhaXuatBan, SoLuong, SoNgayMuon) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlCheckRating = "SELECT * FROM Rating WHERE MaSach = ?";
        String sqlInsertRating = "INSERT INTO Rating (MaSach, DiemSo, SoLuotDanhGia) VALUES (?, 0.0, 0)"; // Đặt điểm số mặc định là 0 và số lượt đánh giá là 0


        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatementInsertDocument = connection.prepareStatement(sqlInsertDocument);
             PreparedStatement preparedStatementCheckRating = connection.prepareStatement(sqlCheckRating);
             PreparedStatement preparedStatementInsertRating = connection.prepareStatement(sqlInsertRating)) {

            // Thêm sách vào bảng Document
            preparedStatementInsertDocument.setString(1, document.getId());
            preparedStatementInsertDocument.setString(2, document.getTitle());
            preparedStatementInsertDocument.setString(3, document.getAuthor());
            preparedStatementInsertDocument.setString(4, document.getCategory());
            preparedStatementInsertDocument.setString(5, document.getPublisher());
            preparedStatementInsertDocument.setInt(6, document.getQuantity());
            preparedStatementInsertDocument.setInt(7, document.getMaxBorrowDays());

            int result = preparedStatementInsertDocument.executeUpdate();
            System.out.println(result + " row(s) affected in Document table.");

            // Kiểm tra nếu sách đã có trong bảng BookRating chưa
            preparedStatementCheckRating.setString(1, document.getId());
            ResultSet rs = preparedStatementCheckRating.executeQuery();

            // Nếu sách chưa có trong bảng BookRating, thêm vào
            if (!rs.next()) {
                preparedStatementInsertRating.setString(1, document.getId());
                preparedStatementInsertRating.executeUpdate();
                System.out.println("Added rating entry for the book " + document.getId());
            }


        } catch (SQLException e) {
            System.err.println("Error adding document: " + e.getMessage());
        }
    }


    @Override
    /**
     * Deletes a document from the database by its ID.
     *
     * @param maSach the ID of the document to be deleted.
     */
    public void deleteDocument(String maSach) {
        String sql = "DELETE FROM Document WHERE MaSach = ?";

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, maSach);
            int result = preparedStatement.executeUpdate();
            System.out.println(result + " row(s) deleted.");
        } catch (SQLException e) {
            System.err.println("Error deleting document: " + e.getMessage());
        }
    }

    @Override
    /**
     * Updates an existing document in the database.
     *
     * @param document the {@link Document} object containing updated information.
     */
    public void updateDocument(Document document) {
        String sql = "UPDATE document SET TacGia = ?, TenSach = ?, TheLoaiSach = ?, NhaXuatBan = ?, SoLuong = ?, SoNgayMuon = ?, Picture = ?, QRCode = ? WHERE MaSach = ?";

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, document.getAuthor());
            preparedStatement.setString(2, document.getTitle());
            preparedStatement.setString(3, document.getCategory());
            preparedStatement.setString(4, document.getPublisher());
            preparedStatement.setInt(5, document.getQuantity());
            preparedStatement.setInt(6, document.getMaxBorrowDays());
            // Set ảnh (Picture) dưới dạng byte[]
            if (document.getPicture() != null) {
                preparedStatement.setBytes(7, document.getPicture());  // Chuyển ảnh thành mảng byte
            } else {
                preparedStatement.setNull(7, java.sql.Types.BLOB);  // Nếu không có ảnh, set NULL cho Picture
            }

            if (document.getQrCode() != null) {
                preparedStatement.setBytes(8, document.getQrCode());  // Chuyển ảnh thành mảng byte
            } else {
                preparedStatement.setNull(8, java.sql.Types.BLOB);  // Nếu không có ảnh, set NULL cho QRCode
            }

            // Set ID sách (MaSach)
            preparedStatement.setString(9, document.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Document updated successfully.");
            } else {
                System.out.println("No document found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating document: " + e.getMessage());
        }
    }

    @Override
    /**
     * Finds a document by its ID.
     *
     * @param maSach the ID of the document to search for.
     * @return a {@link Document} object if found, or {@code null} if no document with the given ID exists.
     */
    public Document findDocumentById(String maSach) {
        String sql = "SELECT * FROM document WHERE MaSach = ?";

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, maSach);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractDocumentFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding document by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    /**
     * Finds a document by its title.
     *
     * @param tenSach the title of the document to search for.
     * @return a {@link Document} object if found, or {@code null} if no document with the given title exists.
     */
    public Document findDocumentByTitle(String tenSach) {
        String sql = "SELECT * FROM document WHERE TenSach = ?";

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, tenSach);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractDocumentFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding document by title: " + e.getMessage());
        }
        return null;
    }

    @Override
    /**
     * Finds a document by its category.
     *
     * @param theLoai the category of the document to search for.
     * @return a {@link Document} object if found, or {@code null} if no document with the given category exists.
     */
    public Document findDocumentByCategory(String theLoai) {
        String sql = "SELECT * FROM document WHERE TheLoaiSach = ?";

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, theLoai);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractDocumentFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding document by category: " + e.getMessage());
        }
        return null;
    }

    @Override
    /**
     * Retrieves all documents from the database.
     *
     * @return a list of all {@link Document} objects.
     */
    public List<Document> findAllDocuments() {
        List<Document> documentList = new ArrayList<>();
        String sql = "SELECT * FROM Document";

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                documentList.add(extractDocumentFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all documents: " + e.getMessage());
        }
        return documentList;
    }

    /**
     * Extracts a {@link Document} object from the given {@link ResultSet}.
     *
     * @param resultSet the {@link ResultSet} containing document data.
     * @return a {@link Document} object.
     * @throws SQLException if a database access error occurs.
     */
    private Document extractDocumentFromResultSet(ResultSet resultSet) throws SQLException {
        Document document = new Document();
        document.setId(resultSet.getString("MaSach"));
        document.setTitle(resultSet.getString("TenSach"));
        document.setAuthor(resultSet.getString("TacGia"));
        document.setCategory(resultSet.getString("TheLoaiSach"));
        document.setPublisher(resultSet.getString("NhaXuatBan"));
        document.setQuantity(resultSet.getInt("SoLuong"));
        document.setMaxBorrowDays(resultSet.getInt("SoNgayMuon"));

        document.setPicture(resultSet.getBytes("Picture"));
        document.setQrCode(resultSet.getBytes("QRCode"));
        return document;
    }

    public static void main(String argc[]) throws SQLException {

        DocumentDAO documentDAO = new DocumentDAO();
        List<Document>documents = documentDAO.findAllDocuments();
        System.out.println(documents + "\n");
    }
}
