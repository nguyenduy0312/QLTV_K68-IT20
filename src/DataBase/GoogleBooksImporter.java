package DataBase;

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

    // API key của bạn
    private static final String API_KEY = "AIzaSyDKISCdSGCglV6eeYaRHvncSQxL4Wr6LDU";
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    // Phương thức tạo URL với từ khóa tìm kiếm và số lượng kết quả
    private static String createFullApiUrl(String query, int maxResults) {
        return API_URL + query + "&maxResults=" + maxResults + "&key=" + API_KEY;
    }

    // Phương thức này dùng để lấy sách từ Google Books API và bơm vào cơ sở dữ liệu
    public void importBooksToDatabase() {
        try {
            // Tạo URL với từ khóa và số lượng kết quả mong muốn
            String apiUrl = createFullApiUrl("book", 40);

            // Lấy dữ liệu sách từ Google Books API
            JSONArray booksArray = getBooksFromApi(apiUrl);

            // Bơm dữ liệu vào cơ sở dữ liệu
            insertBooksIntoDatabase(booksArray);

            // In ra thông báo thành công
            System.out.println("Đã tải và thêm sách thành công vào cơ sở dữ liệu!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Đã xảy ra lỗi khi tải hoặc bơm sách vào cơ sở dữ liệu.");

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


//            STT int,
//            MaSach varchar(50) not null,
//                    TenSach varchar(50) not null,
//                    TacGia varchar(255) not null,
//                    QRCode blob,
//                    TheLoaiSach varchar(50) not null,
//                    NhaXuatBan varchar(255) not null,
//                    SoLuong int not null,
//                    SoNgayMuon int not null,
//                    Picture mediumblob,
//                    constraint pk_MaSach primary key (MaSach)
            // Chuẩn bị câu lệnh SQL để chèn dữ liệu vào bảng Document
            String sql = "INSERT INTO Document (MaSach, TenSach, TacGia, QRCode , TheLoaiSach, NhaXuatBan, SoLuong, SoNgayMuon, Picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = connection.prepareStatement(sql);

            // Lặp qua các cuốn sách và bơm vào cơ sở dữ liệu
            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject book = booksArray.getJSONObject(i).getJSONObject("volumeInfo");

                // Lấy các thông tin sách từ API response
                String maSach = "Book" + (i + 1); // Tạo mã sách tạm thời
                String tenSach = book.optString("title", "Chưa có tên sách");

                // Kiểm tra tác giả
                String tacGia = "Chưa có tác giả";
                JSONArray authorsArray = book.optJSONArray("authors");
                if (authorsArray != null && authorsArray.length() > 0) {
                    tacGia = authorsArray.join(", ");
                }

                // Lấy thể loại sách, kiểm tra có tồn tại không
                String theLoaiSach = "Chưa có thể loại";
                JSONArray categoriesArray = book.optJSONArray("categories");
                if (categoriesArray != null && categoriesArray.length() > 0) {
                    theLoaiSach = categoriesArray.join(", ");
                }

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

//    public static void main(String[] args) {
//        // Tạo một đối tượng của GoogleBooksImporter
//        GoogleBooksImporter importer = new GoogleBooksImporter();
//
//        // Gọi phương thức để nhập sách vào cơ sở dữ liệu
//        System.out.println("Bắt đầu quá trình nhập sách...");
//        importer.importBooksToDatabase();
//    }
}
