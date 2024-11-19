package DAO;

import DataBase.JDBCConnection;
import model.Account;
import model.User;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Date;
import java.util.ArrayList;

/**
 * {@code UserDAO} là lớp chịu trách nhiệm thực hiện các thao tác liên quan đến
 * người dùng trong cơ sở dữ liệu. Lớp này triển khai {@code UserDAOInterface} để cung cấp
 * các chức năng như thêm, cập nhật, xóa và tìm kiếm người dùng.
 */
public class UserDAO implements UserDAOInterface {

    public int STT = 1;

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public static UserDAO getInstance() {
        return new UserDAO();
    }

    /**
     * Thêm một người dùng vào cơ sở dữ liệu.
     *
     * @param user đối tượng {@code User} cần thêm vào cơ sở dữ liệu
     */
    @Override
    public void addUser(@Nullable User user) {
        if (user == null) {
            System.err.println("User cannot be null.");
            return;
        }

        String sql = "INSERT INTO user (STT,personID, HoTen, NgaySinh, GioiTinh, DiaChi, Email, SoDienThoai, TenDangNhap, MatKhau, Picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,STT++);
            preparedStatement.setString(2, user.getId());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setDate(4, new java.sql.Date(user.getBirthday().toSqlDate().getTime()));
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getEmail());
            preparedStatement.setString(8, user.getPhoneNumber());
            preparedStatement.setString(9, user.getAccount().getUserName());
            preparedStatement.setString(10, user.getAccount().getPassWord());
            preparedStatement.setBytes(11, user.getPicture());

            int result = preparedStatement.executeUpdate();

            System.out.println("Thêm người dùng thành công...");
        } catch (SQLException e) {
            System.err.println("Lỗi thêm người dùng... " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin của người dùng trong cơ sở dữ liệu.
     *
     * @param user đối tượng {@code User} chứa thông tin người dùng cần cập nhật
     */
    @Override
    public void updateUser(@Nullable User user) {
        if (user == null) {
            System.err.println("User cannot be null.");
            return;
        }

        String sql = "UPDATE user SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, Email = ?, SoDienThoai = ?, TenDangNhap = ?, MatKhau = ? WHERE personID = ?";

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setDate(2, new java.sql.Date(user.getBirthday().toSqlDate().getTime()));
            preparedStatement.setString(3, user.getGender());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setString(7, user.getAccount().getUserName());
            preparedStatement.setString(8, user.getAccount().getPassWord());
            preparedStatement.setString(9, user.getId());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Update nguời dùng thành công...");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi update người dùng...");
        }
    }

    /**
     * Xóa người dùng theo mã người dùng từ cơ sở dữ liệu.
     *
     * @param maNguoiDung mã người dùng cần xóa
     */
    @Override
    public void deleteUser(@Nullable String maNguoiDung) {
        if (maNguoiDung == null || maNguoiDung.isEmpty()) {
            System.err.println("User ID cannot be null or empty.");
            return;
        }

        String sql = "DELETE FROM user WHERE personID = ?";

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, maNguoiDung);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Xóa người dùng thanành công...");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi xóa người dùng...");
        }
    }

    /**
     * Tìm kiếm người dùng theo mã người dùng.
     *
     * @param ID mã người dùng cần tìm
     * @return đối tượng {@code User} tương ứng nếu tìm thấy, {@code null} nếu không tìm thấy
     */
    @Nullable
    @Override
    public User findUserById(@Nullable String ID) {
        if (ID == null || ID.isEmpty()) {
            System.err.println("ID người dùng không tồn tại...");
            return null;
        }

        String sql = "SELECT * FROM user WHERE personID = ?";
        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, ID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm người dùng theo ID");
        }
        return null;
    }

    /**
     * Tìm kiếm người dùng theo tên.
     *
     * @param Name tên người dùng cần tìm
     * @return đối tượng {@code User} tương ứng nếu tìm thấy, {@code null} nếu không tìm thấy
     */
    @Nullable
    @Override
    public User findUserByName(@Nullable String Name) {
        if (Name == null || Name.isEmpty()) {
            System.err.println("Tên người dùng không tồn tại...");
            return null;
        }

        String sql = "SELECT * FROM user WHERE HoTen = ?";
        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, Name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm người dùng theo tên...");
        }
        return null;
    }

    /**
     * Truy vấn tất cả người dùng từ cơ sở dữ liệu.
     *
     * @return danh sách các đối tượng {@code User}
     */
    @Nullable
    @Override
    public ArrayList<User> findAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                userList.add(extractUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
        return userList;
    }

    /**
     * Trích xuất thông tin người dùng từ {@code ResultSet}.
     *
     * @param resultSet đối tượng {@code ResultSet} chứa dữ liệu người dùng
     * @return đối tượng {@code User} chứa dữ liệu được trích xuất
     * @throws SQLException nếu có lỗi trong quá trình đọc dữ liệu
     */
    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        String personID = resultSet.getString("personID");
        String name = resultSet.getString("HoTen");
        java.sql.Date sqlBirthday = resultSet.getDate("NgaySinh");
        Date birthday = Date.fromSqlDate(sqlBirthday);
        String gender = resultSet.getString("GioiTinh");
        String address = resultSet.getString("DiaChi");
        String email = resultSet.getString("Email");
        String phoneNumber = resultSet.getString("SoDienThoai");
        String username = resultSet.getString("TenDangNhap");
        String password = resultSet.getString("MatKhau");

        Account account = new Account(username, password);
        byte[] picture = resultSet.getBytes("Picture");
        return new User(name, birthday, personID, address, phoneNumber, gender, email, account, picture);
    }
}
