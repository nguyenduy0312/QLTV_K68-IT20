/**
 * Class Document đại diện cho tài liệu trong hệ thống thư viện.
 * Mỗi tài liệu có ID, tiêu đề, tác giả, và trạng thái (có sẵn hoặc không).
 */
public class Document {

    private String id;
    private String title;
    private String author;
    private boolean state;

    public Document() {}

    /**
     * Khởi tạo một Document mới.
     *
     * @param id     Mã định danh của tài liệu
     * @param title  Tiêu đề của tài liệu
     * @param author Tác giả của tài liệu
     * @param state  Trạng thái của tài liệu (true nếu có sẵn, false nếu đã mượn)
     */
    public Document(String id, String title, String author, boolean state) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.state = state;
    }

    /**
     * Lấy ID của tài liệu.
     *
     * @return Mã định danh của tài liệu
     */
    public String getId() {
        return id;
    }

    /**
     * Đặt ID mới cho tài liệu.
     *
     * @param id Mã định danh mới của tài liệu
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Lấy tiêu đề của tài liệu.
     *
     * @return Tiêu đề của tài liệu
     */
    public String getTitle() {
        return title;
    }

    /**
     * Đặt tiêu đề mới cho tài liệu.
     *
     * @param title Tiêu đề mới của tài liệu
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Lấy tên tác giả của tài liệu.
     *
     * @return Tên tác giả của tài liệu
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Đặt tên tác giả mới cho tài liệu.
     *
     * @param author Tên tác giả mới của tài liệu
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Lấy trạng thái của tài liệu.
     *
     * @return Trạng thái của tài liệu (true nếu có sẵn, false nếu đã mượn)
     */
    public boolean getState() {
        return state;
    }

    /**
     * Đặt trạng thái mới cho tài liệu.
     *
     * @param state Trạng thái mới của tài liệu (true nếu có sẵn, false nếu đã mượn)
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * Phương thức trả về loại tài liệu hiện tại.
     * Phương thức này có thể được ghi đè trong các lớp con để trả về các loại tài liệu cụ thể hơn.
     *
     * @return chuỗi "Document" đại diện cho loại tài liệu tổng quát
     */
    public String getDocumentType() {
        return "Document";
    }

}
