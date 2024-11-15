package login;

import DataBase.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginDAO {
    public String authenticate1(String tenDangNhap, String matKhau) {
        // Kiểm tra trong bảng admin
        if (isAdmin(tenDangNhap, matKhau)) {
            return "admin";
        }

        return null; // Không phải admin
    }

    public String authenticate2(String tenDangNhap, String matKhau) {
        // Kiểm tra trong bảng user
        if (isUser(tenDangNhap, matKhau)) {
            return "user";
        }
        return null; // Không phải user
    }


    // Phương thức kiểm tra admin
    private boolean isAdmin(String username, String password) {
        String query = "SELECT * FROM admin WHERE TenDangNhap = ? AND MatKhau = ?";
        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();  // Trả về true nếu có bản ghi phù hợp

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức kiểm tra user
    private boolean isUser(String username, String password) {
        String query = "SELECT * FROM user WHERE TenDangNhap = ? AND MatKhau = ?";
        try (Connection connection = JDBCConnection.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();  // Trả về true nếu có bản ghi phù hợp

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}