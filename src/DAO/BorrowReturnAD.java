package DAO;

import DataBase.JDBCConnection;
import javafx.scene.image.Image;
import model.Document;
import model.User;
import util.Date;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowReturnAD {


    // Ngày mặc định nếu không có ngày cụ thể
    public static final Date DEFAULT_DATE = new Date(1, 1, 2005);

    // Thuộc tính của lớp
    private Document document; // Tài liệu đang mượn

    private LocalDate borrowDate; // Ngày mượn tài liệu
    private LocalDate maximumBorrowDate; // Ngày tối đa cho phép mượn tài liệu
    private Date dueDate; // Ngày phải trả
    private LocalDate actualReturnDate; // Ngày thực tế trả tài liệu

    // Constructor khởi tạo đối tượng `BorrowReturn` với trạng thái mặc định là "Đã trả"
    public BorrowReturnAD() {
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
                System.out.println("Document quantity updated successfully.");
            } else {
                System.out.println("No document was found to update.");
            }
        } catch (SQLException e) {
            System.out.println("Error while updating document quantity: " + e.getMessage());
        }


        this.borrowDate = LocalDate.now(); // Lưu ngày mượn hiện tại

        // Nhập ngày phải trả từ người dùng
        dueDate.inputDate();
        LocalDate dueLocalDate = dueDate.toLocalDate();
        this.dueDate = new Date(dueLocalDate.getDayOfMonth(), dueLocalDate.getMonthValue(), dueLocalDate.getYear());

        // Tính ngày tối đa có thể mượn
        this.maximumBorrowDate = borrowDate.plusDays(document.getMaxBorrowDays());

        // Kiểm tra tính hợp lệ của ngày trả
        if (dueLocalDate.isBefore(borrowDate)) {
            System.out.println("Return date cannot be before borrow date. Please try again.");
        } else if (dueLocalDate.isAfter(maximumBorrowDate)) {
            System.out.println("Cannot borrow beyond the allowed borrowing period.");
        } else {
            System.out.println("You have successfully borrowed the book.");
        }
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
                System.out.println("Record inserted successfully into the borrowing table.");
            }
        } catch (SQLException e) {
            System.out.println("Error while inserting record: " + e.getMessage());
        }


    }
    //Tao mã mượn
    private String generateNewMaMuon() {
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
        int finePerDay = 2000; // Phí phạt mỗi ngày

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement getBorrowInfoStmt = connection.prepareStatement(getBorrowInfoSql);
             PreparedStatement updateDocumentStmt = connection.prepareStatement(updateDocumentSql);
             PreparedStatement updateReturnDateStmt = connection.prepareStatement(updateReturnDateSql)) {

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

                    // Tính số ngày trễ và tiền phạt
                    if (actualReturnDate.isAfter(dueDate)) {
                        long daysLate = java.time.temporal.ChronoUnit.DAYS.between(dueDate, actualReturnDate);
                        long fine = daysLate * finePerDay;
                        System.out.println("Bị chậm " + daysLate + " ngày. Phạt: " + fine + " VND.");
                    } else {
                        System.out.println("Sách được trả đúng hạn ");
                    }

                    // Tăng số lượng sách lên 1
                    updateDocumentStmt.setString(1, maSach);
                    updateDocumentStmt.executeUpdate();
                    System.out.println("Sách đã được trả thành công và số lượng đã được cập nhật.");

                } else {
                    System.out.println("Không tìm thấy bản ghi mượn sách với mã mượn: " + maMuon);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi trả sách: " + e.getMessage());
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


    public static void main(String[] args) {
        // Tạo một đối tượng BorrowReturnAD
        BorrowReturnAD borrowReturnAD = new BorrowReturnAD();

        // Tạo đối tượng Date và User
        Date date1 = new Date(23, 12, 2005);
        User user1 = new User("Đào Huy Hoàng", date1, "K68_02", "Nam Định", "12345", "Nam", "hoangdao@gmail.com");

        // Tạo đối tượng Document
        Document document = new Document("0935848827", "Lập trình Java cơ bản", "Nguyễn Văn A", "Giáo trình", "NXB Khoa Học", 100, 30);

        // Gọi phương thức borrowDocument từ đối tượng borrowReturnAD
        borrowReturnAD.borrowDocument(user1, document);
       // borrowReturnAD.returnDocument("HhD3");
    }
}

