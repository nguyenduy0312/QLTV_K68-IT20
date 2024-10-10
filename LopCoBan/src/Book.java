/**
 * Lớp Book đại diện cho một cuốn sách trong thư viện.
 * Lớp này kế thừa từ lớp Document và ghi đè phương thức getDocumentType()
 * để trả về loại tài liệu là "Book".
 */
public class Book extends Document {

    /**
     * Constructor không tham số của lớp Book.
     * Sử dụng để tạo đối tượng Book mặc định.
     */
    public Book() {}

    /**
     * Constructor có tham số của lớp Book.
     * Sử dụng để tạo đối tượng Book với thông tin cụ thể.
     *
     * @param id      mã ID của cuốn sách
     * @param title   tiêu đề của cuốn sách
     * @param author  tác giả của cuốn sách
     * @param state   trạng thái của cuốn sách (còn hay không)
     */
    public Book(String id, String title, String author, boolean state) {
        super(id, title, author, state); // Gọi constructor của lớp cha (Document)
    }

    /**
     * Phương thức ghi đè để trả về loại tài liệu.
     *
     * @return chuỗi "Book" đại diện cho loại tài liệu là sách
     */
    @Override
    public String getDocumentType() {
        return "Book";
    }
}
