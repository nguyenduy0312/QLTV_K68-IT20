package DAO;

import DataBase.JDBCConnection;
import model.BorrowReturn;
import model.Document;
import model.User;
import org.jetbrains.annotations.Nullable;
import util.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowReturnDAO {


    // Ngày mặc định nếu không có ngày cụ thể
    public static final Date DEFAULT_DATE = new Date(1, 1, 2005);

    // Thuộc tính của lớp
    private Document document; // Tài liệu đang mượn

    private LocalDate borrowDate; // Ngày mượn tài liệu
    private LocalDate maximumBorrowDate ; // Ngày tối đa cho phép mượn tài liệu
    private Date dueDate; // Ngày hẹn trả
    private LocalDate actualReturnDate; // Ngày thực tế trả tài liệu

    public LocalDate getMaximumBorrowDate() {
        return maximumBorrowDate;
    }

    public void setMaximumBorrowDate(LocalDate maximumBorrowDate) {
        this.maximumBorrowDate = maximumBorrowDate;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    // Constructor khởi tạo đối tượng `BorrowReturn` với trạng thái mặc định là "Đã trả"
    public BorrowReturnDAO() {
        this.document = null;
        this.dueDate = DEFAULT_DATE; // Gán giá trị ngày mặc định
    }

    // Phương thức để mượn tài liệu
    public void borrowDocument(User user, Document document) {
        // Kiểm tra nếu tài liệu không còn sẵn sàng để mượn
        if (document.getQuantity() <= 0) {
            throw new IllegalArgumentException("Sorry, Document " + document.getId() + " is currently not available.");
        }


        // Cập nhật trạng thái và số lượng tài liệu
        setDocument(document);
        document.setQuantity(document.getQuantity() - 1);
        // Cập nhật số lượng sách trong bảng document
        String updateSql = "UPDATE document SET SoLuong = ? WHERE MaSach = ?";
        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {

            updateStatement.setInt(1, document.getQuantity()); // Giá trị mới của số lượng
            updateStatement.setString(2, document.getId()); // ID của tài liệu cần cập nhật

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Số lượng tài liệu đã được cập nhật thành công");
            } else {
                System.out.println("Không tìm thấy tài liệu nào để cập nhật");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật số lượng tài liệu " + e.getMessage());
        }


        LocalDate dueLocalDate = dueDate.toLocalDate();

        // Mã mượn
        String newMaMuon = generateNewMaMuon();

        String sql = "INSERT INTO borrowing (MaMuon, personID, MaSach, NgayMuon, NgayHenTra, NgayTra) " + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


            preparedStatement.setString(1, newMaMuon); // MaMuon
            preparedStatement.setString(2, user.getId()); // personID
            preparedStatement.setString(3, document.getId()); // MaSach
            preparedStatement.setDate(4, java.sql.Date.valueOf(borrowDate)); // NgayMuon
            preparedStatement.setDate(5, java.sql.Date.valueOf(dueLocalDate)); // NgayHenTra
            preparedStatement.setDate(6, java.sql.Date.valueOf(maximumBorrowDate)); // NgayTra (null khi chưa trả)
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Bản ghi đã được chèn thành công vào bảng mượn");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi chèn bản ghi " + e.getMessage());
        }


    }
    //Tao mã mượn
    public String generateNewMaMuon() {
        String prefix = "HhD";
        int newNumber = 1; // Giá trị bắt đầu nếu không tìm thấy bản ghi nào

        try (Connection connection = JDBCConnection.getJDBCConnection()) {
            String sql = "SELECT MaMuon FROM borrowing ORDER BY CAST(SUBSTRING(MaMuon, LENGTH(?) + 1) AS UNSIGNED) DESC LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, prefix);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String lastMaMuon = resultSet.getString("MaMuon");
                        if (lastMaMuon != null && lastMaMuon.startsWith(prefix)) {
                            int lastNumber = Integer.parseInt(lastMaMuon.substring(prefix.length()));
                            newNumber = lastNumber + 1;
                        }
                    }
                }
            }
        } catch (SQLException | NumberFormatException e) {
            System.err.println("Error generating new MaMuon: " + e.getMessage());
            e.printStackTrace();
        }

        return prefix + newNumber;
    }


    // Phương thức để trả tài liệu
    public void returnDocument(String maMuon) {
        String getBorrowInfoSql = "SELECT MaSach, NgayHenTra FROM borrowing WHERE MaMuon = ?";
        String updateDocumentSql = "UPDATE document SET SoLuong = SoLuong + 1 WHERE MaSach = ?";
        String updateReturnDateSql = "UPDATE borrowing SET NgayTra = ? WHERE MaMuon = ?";
        String deleteBorrowInfoSql = "DELETE FROM borrowing WHERE MaMuon = ?"; // Xóa thông tin mượn trả sau khi trả sách

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement getBorrowInfoStmt = connection.prepareStatement(getBorrowInfoSql);
             PreparedStatement updateDocumentStmt = connection.prepareStatement(updateDocumentSql);
             PreparedStatement updateReturnDateStmt = connection.prepareStatement(updateReturnDateSql);
             PreparedStatement deleteBorrowInfoStmt = connection.prepareStatement(deleteBorrowInfoSql)) {

            // Lấy thông tin mượn sách dựa trên mã mượn
            getBorrowInfoStmt.setString(1, maMuon);
            try (ResultSet rs = getBorrowInfoStmt.executeQuery()) {
                if (rs.next()) {
                    String maSach = rs.getString("MaSach");
                    LocalDate dueDate = rs.getDate("NgayHenTra").toLocalDate();

                    // Cập nhật ngày thực tế trả sách là ngày hiện tại
                    LocalDate actualReturnDate = LocalDate.now();
                    updateReturnDateStmt.setDate(1, java.sql.Date.valueOf(actualReturnDate));
                    updateReturnDateStmt.setString(2, maMuon);
                    updateReturnDateStmt.executeUpdate();

                    // Tăng số lượng sách lên 1
                    updateDocumentStmt.setString(1, maSach);
                    updateDocumentStmt.executeUpdate();
                    System.out.println("Trả sách thành công :)");

                    // Xóa thông tin mượn trả khỏi bảng borrowing
                    deleteBorrowInfoStmt.setString(1, maMuon);
                    deleteBorrowInfoStmt.executeUpdate();
                    System.out.println("Bản ghi mượn đã được xóa thành công");

                } else {
                    System.out.println("Không tìm thấy bản ghi với mã mượn: " + maMuon);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi trả sách : " + e.getMessage());
        }
    }


    // Getter và Setter cho các thuộc tính của lớp
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }



    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Retrieves all documents from the database.
     *
     * @return a list of all {@link BorrowReturn} objects.
     */
    public List<BorrowReturn> findAllInfo() {
        List<BorrowReturn> borrowReturnList = new ArrayList<>();
        String sql = "SELECT * FROM Borrowing";

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                borrowReturnList.add(extractBorrowReturnFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all documents: " + e.getMessage());
        }
        return borrowReturnList;
    }

    /**
     * Tìm kiếm người dùng theo mã người dùng.
     *
     * @param ID mã người dùng cần tìm
     * @return đối tượng {@code User} tương ứng nếu tìm thấy, {@code null} nếu không tìm thấy
     */

    public  boolean findUserById(String ID) {
        if (ID == null || ID.isEmpty()) {
            System.err.println("ID người dùng không tồn tại...");
            return false;
        }

        String sql = "SELECT * FROM borrowing WHERE personID = ?";
        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, ID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm người dùng theo ID");
        }
        return false;
    }

    public  boolean findBookById(String ID) {
        if (ID == null || ID.isEmpty()) {
            System.err.println("ID người dùng không tồn tại...");
            return false;
        }

        String sql = "SELECT * FROM borrowing WHERE MaSach = ?";
        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, ID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm sách theo ID");
        }
        return false;
    }

    private BorrowReturn extractBorrowReturnFromResultSet(ResultSet resultSet) throws SQLException {
        BorrowReturn borrowReturn = new BorrowReturn();
        borrowReturn.setMaMuon(resultSet.getString("MaMuon"));
        borrowReturn.setMaNguoiMuon(resultSet.getString("personID"));
        borrowReturn.setMaSach(resultSet.getString("MaSach"));

        java.sql.Date sqlBorrowDate = resultSet.getDate("NgayMuon");
        Date borrowDate = Date.fromSqlDate(sqlBorrowDate);
        borrowReturn.setNgayMuon(borrowDate);

        java.sql.Date sqlDueDate = resultSet.getDate("NgayHenTra");
        Date dueDate = Date.fromSqlDate(sqlDueDate);
        borrowReturn.setNgayHenTra(dueDate);

        java.sql.Date sqlReturnDate = resultSet.getDate("NgayTra");
        Date returnDate = Date.fromSqlDate(sqlDueDate);
        borrowReturn.setNgayTra(returnDate);
        return borrowReturn;
    }
}

