import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.json.JSONArray;
import org.json.JSONObject;
import DataBase.JDBCConnection;  // Import lớp JDBCConnection của bạn

public class GoogleBooksImporter {

    // URL của Google Books API
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=book&maxResults=62"; // Lấy 62 cuốn sách ngẫu nhiên

    public static void main(String[] args) {
        try {
            // Lấy dữ liệu từ Google Books API
            String jsonResponse = getJsonFromApi(API_URL);

            // Phân tích dữ liệu JSON
            JSONArray booksArray = new JSONObject(jsonResponse).getJSONArray("items");

            // Kết nối tới cơ sở dữ liệu bằng phương thức từ lớp JDBCConnection
            Connection connection = JDBCConnection.getJDBCConnection();

            // Kiểm tra kết nối
            if (connection != null) {
                System.out.println("Kết nối cơ sở dữ liệu thành công!");

                // Chuẩn bị câu lệnh SQL để chèn dữ liệu vào bảng Document
                String sql = "INSERT INTO Document (MaSach, TenSach, TacGia, TheLoaiSach, NhaXuatBan, SoLuong, SoNgayMuon) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);

                // Lặp qua các cuốn sách và bơm vào cơ sở dữ liệu
                for (int i = 0; i < booksArray.length(); i++) {
                    JSONObject book = booksArray.getJSONObject(i).getJSONObject("volumeInfo");

                    // Lấy các thông tin sách từ API response
                    String maSach = "Book" + (i + 1); // Tạo mã sách tạm thời (có thể cải tiến thêm)
                    String tenSach = book.optString("title", "Chưa có tên sách");
                    String tacGia = book.optJSONArray("authors") != null ? book.getJSONArray("authors").join(", ") : "Chưa có tác giả";
                    String theLoaiSach = "Chưa có thể loại"; // API không cung cấp trực tiếp thể loại
                    String nhaXuatBan = book.optString("publisher", "Chưa có nhà xuất bản");
                    int soLuong = 100; // Giả định số lượng là 100 (hoặc lấy từ đâu đó)
                    int soNgayMuon = 30; // Giả định thời gian mượn mặc định là 30 ngày

                    // Thiết lập các tham số cho câu lệnh SQL
                    stmt.setString(2, maSach);
                    stmt.setString(3, tenSach);
                    stmt.setString(4, tacGia);
                    stmt.setString(6, theLoaiSach);
                    stmt.setString(7, nhaXuatBan);
                    stmt.setInt(8, soLuong);
                    stmt.setInt(9, soNgayMuon);

                    // Thực thi câu lệnh chèn dữ liệu
                    stmt.executeUpdate();
                }

                // Đóng câu lệnh và kết nối
                stmt.close();
                connection.close();
                System.out.println("Dữ liệu đã được bơm thành công vào cơ sở dữ liệu!");
            } else {
                System.out.println("Kết nối cơ sở dữ liệu thất bại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm để gửi yêu cầu HTTP và lấy dữ liệu JSON
    private static String getJsonFromApi(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        // Đóng các luồng
        in.close();
        connection.disconnect();

        return content.toString();
    }
    private static void printBooks(Connection connection) {
        try {
            // Chuẩn bị câu lệnh SQL để lấy dữ liệu sách
            String sql = "SELECT MaSach, TenSach, TacGia, TheLoaiSach, NhaXuatBan FROM Document";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // In kết quả ra màn hình
            System.out.println("Danh sách sách đã được thêm vào cơ sở dữ liệu:");
            while (rs.next()) {
                String maSach = rs.getString("MaSach");
                String tenSach = rs.getString("TenSach");
                String tacGia = rs.getString("TacGia");
                String theLoaiSach = rs.getString("TheLoaiSach");
                String nhaXuatBan = rs.getString("NhaXuatBan");
                System.out.println("Mã sách: " + maSach + ", Tên sách: " + tenSach + ", Tác giả: " + tacGia + ", Thể loại: " + theLoaiSach + ", Nhà xuất bản: " + nhaXuatBan);
            }

            // Đóng ResultSet và PreparedStatement
            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
