package DataBase;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import org.json.JSONArray;
import org.json.JSONObject;
import DataBase.JDBCConnection;


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
            String apiUrl = createFullApiUrl("hary", 21);

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

            // Chuẩn bị câu lệnh SQL để chèn dữ liệu vào bảng Document
            String sqlInsert = "INSERT INTO Document (MaSach, TenSach, TacGia, QRCode, TheLoaiSach, NhaXuatBan, SoLuong, SoNgayMuon, Picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String sqlCheck = "SELECT COUNT(*) FROM Document WHERE MaSach = ?";

            PreparedStatement stmtInsert = connection.prepareStatement(sqlInsert);
            PreparedStatement stmtCheck = connection.prepareStatement(sqlCheck);

            // Lặp qua các cuốn sách và bơm vào cơ sở dữ liệu
            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject book = booksArray.getJSONObject(i).getJSONObject("volumeInfo");

                // Mã sách
                JSONArray industryIdentifiersArray = book.optJSONArray("industryIdentifiers");
                String maSach;
                if (industryIdentifiersArray != null && industryIdentifiersArray.length() > 0) {
                    maSach = industryIdentifiersArray.getJSONObject(0).optString("identifier", "null");
                } else {
                    maSach = "NO_ISBN_" + System.currentTimeMillis(); // Tạo mã dựa trên thời gian hiện tại để duy nhất
                }

                // Kiểm tra nếu mã sách đã tồn tại
                stmtCheck.setString(1, maSach);
                ResultSet rs = stmtCheck.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    System.out.println("Sách với mã " + maSach + " đã tồn tại trong cơ sở dữ liệu, bỏ qua bản ghi này.");
                    rs.close();
                    continue; // Bỏ qua sách này nếu đã tồn tại
                }
                rs.close();

                // Lấy các thông tin sách từ API response
                String tenSach = book.optString("title", "Chưa có tên sách");

                // Kiểm tra tác giả
                String tacGia = "Chưa có tác giả";
                JSONArray authorsArray = book.optJSONArray("authors");
                if (authorsArray != null && authorsArray.length() > 0) {
                    tacGia = authorsArray.getString(0); // Lấy tác giả đầu tiên
                }

                // Lấy thể loại sách
                String theLoaiSach = "Chưa có thể loại";
                JSONArray categoriesArray = book.optJSONArray("categories");
                if (categoriesArray != null && categoriesArray.length() > 0) {
                    theLoaiSach = categoriesArray.getString(0); // Lấy thể loại đầu tiên
                }

                String nhaXuatBan = book.optString("publisher", "Chưa có nhà xuất bản");
                int soLuong = 100; // Giả định số lượng là 100
                int soNgayMuon = 30; // Giả định thời gian mượn mặc định là 30 ngày

                // Lấy infoLink và tạo mã QR từ nó
                String infoLink = book.optString("infoLink", "");
                byte[] qrCode = generateQRCode(infoLink); // Tạo mã QR từ infoLink

                // Lấy ảnh bìa sách
                String imageUrl = book.optJSONObject("imageLinks") != null ? book.optJSONObject("imageLinks").optString("thumbnail", "") : "";
                byte[] picture = null;
                if (!imageUrl.isEmpty()) {
                    picture = downloadImage(imageUrl);
                }

                // Thiết lập các tham số cho câu lệnh SQL
                stmtInsert.setString(1, maSach);
                stmtInsert.setString(2, tenSach);
                stmtInsert.setString(3, tacGia);
                stmtInsert.setBytes(4, qrCode);  // Chèn QR Code vào cơ sở dữ liệu
                stmtInsert.setString(5, theLoaiSach);
                stmtInsert.setString(6, nhaXuatBan);
                stmtInsert.setInt(7, soLuong);
                stmtInsert.setInt(8, soNgayMuon);
                stmtInsert.setBytes(9, picture);  // Chèn hình ảnh vào cơ sở dữ liệu

                // Thực thi câu lệnh chèn dữ liệu
                stmtInsert.executeUpdate();
            }

            // Đóng câu lệnh và kết nối
            stmtInsert.close();
            stmtCheck.close();
            connection.close();
            System.out.println("Dữ liệu đã được bơm thành công vào cơ sở dữ liệu!");
        } else {
            System.out.println("Kết nối cơ sở dữ liệu thất bại!");
        }
    }


    // Hàm để tạo mã QR từ liên kết và chuyển nó thành mảng byte
    private static byte[] generateQRCode(String text) throws IOException {
        // Sử dụng BufferedImage để vẽ QR code (chỉ là hình minh họa, mã QR thực tế sẽ cần thư viện bên ngoài)
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.BLACK);

        // Vẽ một số điểm vào BufferedImage (QR code thực tế sẽ phức tạp hơn nhiều)
        g.fillRect(50, 50, 100, 100);  // Vẽ một hình vuông đơn giản làm ví dụ
        g.dispose();

        // Chuyển BufferedImage thành mảng byte
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();

        return imageBytes;
    }

    // Hàm tải ảnh từ URL và chuyển đổi thành mảng byte
    private static byte[] downloadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        BufferedImage image = ImageIO.read(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Chuyển đổi BufferedImage thành byte[]
        ImageIO.write(image, "jpg", baos);  // Lưu ảnh dưới dạng JPG hoặc định dạng phù hợp khác
        baos.flush();  // Đảm bảo mọi byte được ghi vào ByteArrayOutputStream
        byte[] imageBytes = baos.toByteArray();
        baos.close();

        return imageBytes;
    }


    public static void main(String[] args) {
        // Tạo đối tượng GoogleBooksImporter
        GoogleBooksImporter importer = new GoogleBooksImporter();

        // Chạy phương thức importBooksToDatabase để tải và bơm sách vào cơ sở dữ liệu
        importer.importBooksToDatabase();
    }

}