package DAO;

import DataBase.JDBCConnection;
import model.User;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;

public class UserDAO implements UserDAOInterface{

    public static UserDAO getInstance() {
        return new UserDAO();
    }
    @Override
    public void addUser(@Nullable User user) {
        String sql = "INSERT INTO user (personID, HoTen, NgaySinh, GioiTinh, DiaChi, Email, SoDienThoai, TenDangNhap, MatKhau) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        try (Connection connection = JDBCConnection.getJDBCConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
           // preparedStatement.setDate(3,sqlDate);
            preparedStatement.setString(4,user.getGender());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6,user.getEmail());
            preparedStatement.setString(7, user.getPhoneNumber());
            preparedStatement.setString(8,user.getAccount().getUserName());
            preparedStatement.setString(9,user.getAccount().getPassWord());

            int result = preparedStatement.executeUpdate();
            System.out.println(result + " row(s) affected.");
        } catch (SQLException e) {
            System.err.println("Error adding document: " + e.getMessage());
        }
    }

    @Override
    public void updateUser(@Nullable User user) {

    }

    @Override
    public void deleteUser(@Nullable String maNguoiDung) {

    }

    @Nullable
    @Override
    public User findUserById(@Nullable String maNguoiDung) {
        return null;
    }

    @Nullable
    @Override
    public User findUserByName(@Nullable String tenNguoiDung) {
        return null;
    }

    @Nullable
    @Override
    public List<User> findAllUsers() {
        return null;
    }
}
