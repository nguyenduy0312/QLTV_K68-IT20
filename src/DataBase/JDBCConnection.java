package DataBase;

import java.sql.*;

public class JDBCConnection {
    private static final String url = "jdbc:mysql://localhost:3306/qltv_k68_it20"; // Cấu trúc kết nối hệ quản trị mySQL.
    private static final String user = "root";
    private static final String password = "123456789";

    public static Connection getJDBCConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printDatabase(Connection c) {
        try {
            if (c != null) {
                DatabaseMetaData data = c.getMetaData();
                System.out.println(data.getDatabaseProductName());
                System.out.println(data.getDatabaseProductVersion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = getJDBCConnection(); // tạo kết nối
            JDBCConnection.closeConnection(connection);  // ngắt ket nối
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
