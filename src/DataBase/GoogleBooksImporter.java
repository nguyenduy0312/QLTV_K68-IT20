package DataBase;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.io.*;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;

import model.Document;

import org.json.JSONArray;
import org.json.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
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
            String apiUrl = createFullApiUrl("doreamon", 30);

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
            String sqlInsertDocument = "INSERT INTO Document (MaSach, TenSach, TacGia, QRCode, TheLoaiSach, NhaXuatBan, SoLuong, SoNgayMuon, Picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String sqlCheckDocument = "SELECT COUNT(*) FROM Document WHERE MaSach = ?";
            String sqlInsertRating = "INSERT INTO Rating (MaSach, DiemSo, SoLuotDanhGia) VALUES (?, 0, 0)";

            PreparedStatement stmtInsertDocument = connection.prepareStatement(sqlInsertDocument);
            PreparedStatement stmtCheckDocument = connection.prepareStatement(sqlCheckDocument);
            PreparedStatement stmtInsertRating = connection.prepareStatement(sqlInsertRating);

            // Lặp qua các cuốn sách và chèn vào cơ sở dữ liệu
            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject book = booksArray.getJSONObject(i).getJSONObject("volumeInfo");

                // Mã sách
                JSONArray industryIdentifiersArray = book.optJSONArray("industryIdentifiers");
                String maSach;
                if (industryIdentifiersArray != null && industryIdentifiersArray.length() > 0) {
                    maSach = industryIdentifiersArray.getJSONObject(0).optString("identifier", "null");
                } else {
                    maSach = "NO_ISBN_" + System.currentTimeMillis(); // Tạo mã duy nhất nếu không có ISBN
                }

                // Kiểm tra nếu mã sách đã tồn tại
                stmtCheckDocument.setString(1, maSach);
                ResultSet rs = stmtCheckDocument.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    System.out.println("Sách với mã " + maSach + " đã tồn tại trong cơ sở dữ liệu, bỏ qua bản ghi này.");
                    rs.close();
                    continue; // Bỏ qua sách này nếu đã tồn tại
                }
                rs.close();

                // Lấy thông tin sách từ API response
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
                int soLuong = Document.QUANTITY; // Giả định số lượng là 100
                int soNgayMuon = Document.MAXBORROWDAYS; // Giả định thời gian mượn mặc định là 20 ngày

                // Lấy infoLink và tạo mã QR từ nó
                String infoLink = book.optString("infoLink", "");
                byte[] qrCode = generateQRCode(infoLink); // Tạo mã QR từ infoLink

                // Lấy ảnh bìa sách và thay đổi kích thước trước khi lưu
                String imageUrl = book.optJSONObject("imageLinks") != null ? book.optJSONObject("imageLinks").optString("thumbnail", "") : "";
                byte[] picture = null;
                if (!imageUrl.isEmpty()) {
                    picture = downloadImage(imageUrl, 200, 250); // Thay đổi kích thước ảnh
                }

                // Thiết lập các tham số cho câu lệnh SQL chèn vào Document
                stmtInsertDocument.setString(1, maSach);
                stmtInsertDocument.setString(2, tenSach);
                stmtInsertDocument.setString(3, tacGia);
                stmtInsertDocument.setBytes(4, qrCode);  // Chèn QR Code vào cơ sở dữ liệu
                stmtInsertDocument.setString(5, theLoaiSach);
                stmtInsertDocument.setString(6, nhaXuatBan);
                stmtInsertDocument.setInt(7, soLuong);
                stmtInsertDocument.setInt(8, soNgayMuon);
                stmtInsertDocument.setBytes(9, picture);  // Chèn hình ảnh vào cơ sở dữ liệu

                // Thực thi câu lệnh chèn dữ liệu vào Document
                stmtInsertDocument.executeUpdate();

                // Thêm bản ghi mặc định vào bảng BookRating
                stmtInsertRating.setString(1, maSach);
                stmtInsertRating.executeUpdate();
            }

            // Đóng câu lệnh và kết nối
            stmtInsertDocument.close();
            stmtCheckDocument.close();
            stmtInsertRating.close();
            connection.close();
            System.out.println("Dữ liệu đã được bơm thành công vào cơ sở dữ liệu!");
        } else {
            System.out.println("Kết nối cơ sở dữ liệu thất bại!");
        }
    }


    // Hàm để tạo mã QR từ liên kết và chuyển nó thành mảng byte
    private static byte[] generateQRCode(String text) throws IOException, WriterException {
        int width = 300;
        int height = 300;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        // Chuyển đổi BitMatrix thành BufferedImage
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Chuyển BufferedImage thành mảng byte
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();

        return imageBytes;
    }

    // Hàm tải ảnh từ URL, thay đổi kích thước và chuyển đổi thành mảng byte
    public static byte[] downloadImage(String imageUrl, int width, int height) throws IOException {
        // Tải ảnh từ URL
        URL url = new URL(imageUrl);
        BufferedImage image = ImageIO.read(url);

        if (image == null) {
            throw new IOException("Unable to load image from URL: " + imageUrl);
        }

        // Tạo BufferedImage mới với kích thước yêu cầu
        BufferedImage bufferedScaledImage = new BufferedImage(width, height, image.getType() == 0
                ? BufferedImage.TYPE_INT_ARGB : image.getType());
        Graphics2D g2d = bufferedScaledImage.createGraphics();

        // Sử dụng các RenderingHints để tăng chất lượng thay đổi kích thước
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ ảnh đã thay đổi kích thước
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();

        // Áp dụng bộ lọc Unsharp Masking để làm nét ảnh
        bufferedScaledImage = applyUnsharpMasking(bufferedScaledImage);

        // Chuyển BufferedImage thành mảng byte (sử dụng PNG để giữ màu tốt hơn)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedScaledImage, "png", baos); // Lưu ảnh dưới dạng PNG (không nén mất dữ liệu)
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();

        return imageBytes;
    }

    // Phương thức áp dụng Unsharp Masking
    private static BufferedImage applyUnsharpMasking(BufferedImage image) {
        // Tạo một bản sao của ảnh gốc và làm mờ nó
        BufferedImage blurredImage = blurImage(image);

        // Tạo ảnh mới để kết quả Unsharp Masking
        BufferedImage unsharpImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color originalColor = new Color(image.getRGB(x, y));
                Color blurredColor = new Color(blurredImage.getRGB(x, y));

                // Tính toán sự khác biệt giữa ảnh gốc và ảnh mờ
                int r = Math.min(255, Math.max(0, originalColor.getRed() - blurredColor.getRed() + originalColor.getRed()));
                int g = Math.min(255, Math.max(0, originalColor.getGreen() - blurredColor.getGreen() + originalColor.getGreen()));
                int b = Math.min(255, Math.max(0, originalColor.getBlue() - blurredColor.getBlue() + originalColor.getBlue()));

                // Lưu kết quả vào ảnh unsharpImage
                unsharpImage.setRGB(x, y, new Color(r, g, b).getRGB());
            }
        }
        return unsharpImage;
    }

    // Phương thức làm mờ ảnh (Gaussian Blur)
    private static BufferedImage blurImage(BufferedImage image) {
        // Tạo bộ lọc Gaussian Blur với bán kính 2px
        float[] matrix = {
                1 / 16f, 2 / 16f, 1 / 16f,
                2 / 16f, 4 / 16f, 2 / 16f,
                1 / 16f, 2 / 16f, 1 / 16f
        };
        Kernel kernel = new Kernel(3, 3, matrix);
        ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        // Áp dụng bộ lọc Gaussian Blur
        BufferedImage blurredImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        convolveOp.filter(image, blurredImage);
        return blurredImage;
    }

    public static void main(String[] args) {
        GoogleBooksImporter importer = new GoogleBooksImporter();
        importer.importBooksToDatabase();
    }
}
