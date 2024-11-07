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

    // Phương thức này dùng để lấy sách từ Google Books API và bơm vào cơ sở dữ liệu
    public void importBooksToDatabase() {
        try {
            // Lấy dữ liệu sách từ Google Books API
            JSONArray booksArray = getBooksFromApi(API_URL);
            // Bơm dữ liệu vào cơ sở dữ liệu
            insertBooksIntoDatabase(booksArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm để lấy dữ liệu sách từ Google Books API
    private static JSONArray getBooksFromApi(String apiUrl) throws Exception {
        String jsonResponse = getJsonFromApi(apiUrl);
        // Trả về danh sách sách dưới dạng JSONArray
        return new JSONObject(jsonResponse).getJSONArray("items");
    }

    // Hàm gửi yêu cầu HTTP và lấy dữ liệu JSON
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

    // Hàm để chèn danh sách sách vào cơ sở dữ liệu
    private static void insertBooksIntoDatabase(JSONArray booksArray) throws Exception {
        // Kết nối tới cơ sở dữ liệu
        Connection connection = JDBCConnection.getJDBCConnection();
        if (connection != null) {
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
            // Chuẩn bị câu lệnh SQL để chèn dữ liệu vào bảng Document
            String sql = "INSERT INTO Document (MaSach, TenSach, TacGia, TheLoaiSach, NhaXuatBan, SoLuong, SoNgayMuon) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            // Lặp qua các cuốn sách và bơm vào cơ sở dữ liệu
            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject book = booksArray.getJSONObject(i).getJSONObject("volumeInfo");

                // Lấy các thông tin sách từ API response
                String maSach = "Book" + (i + 1); // Tạo mã sách tạm thời
                String tenSach = book.optString("title", "Chưa có tên sách");
                String tacGia = book.optJSONArray("authors") != null ? book.getJSONArray("authors").join(", ") : "Chưa có tác giả";
                String theLoaiSach = "Chưa có thể loại"; // API không cung cấp trực tiếp thể loại
                String nhaXuatBan = book.optString("publisher", "Chưa có nhà xuất bản");
                int soLuong = 100; // Giả định số lượng là 100
                int soNgayMuon = 30; // Giả định thời gian mượn mặc định là 30 ngày

                // Thiết lập các tham số cho câu lệnh SQL
                stmt.setString(1, maSach);
                stmt.setString(2, tenSach);
                stmt.setString(3, tacGia);
                stmt.setString(4, theLoaiSach);
                stmt.setString(5, nhaXuatBan);
                stmt.setInt(6, soLuong);
                stmt.setInt(7, soNgayMuon);

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
    }
}
