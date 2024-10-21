public class Borrowing {
    private String id;
    private User user;
    private Document documentType;
    private String state;
    private Date borrowDate;
    private Date expectedReturnDate;
    private Date actualReturnDate;
    /**
     * Khởi tạo một đối tượng Borrowing với tất cả các tham số.
     *
     * @param id Mã định danh duy nhất cho lần mượn tài liệu.
     * @param user Người dùng thực hiện việc mượn tài liệu.
     * @param documentType Loại tài liệu được mượn (sách, luận văn, tạp chí, v.v.).
     * @param state Trạng thái của lần mượn (đang mượn, đã trả, quá hạn, v.v.).
     * @param borrowDate Ngày mượn tài liệu.
     * @param expectedReturnDateDate Ngày dự kiến trả tài liệu.
     * @throws IllegalArgumentException nếu tham số nào không hợp lệ.
     */
    public Borrowing(String id, User user, Document documentType, String state,
                     Date borrowDate, Date expectedReturnDateDate) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        if (documentType == null) {
            throw new IllegalArgumentException("Invalid document type");
        }
        if (state == null || state.isEmpty()) {
            throw new IllegalArgumentException("State cannot be null or empty.");
        }

        if (borrowDate == null) {
            throw new IllegalArgumentException("Invalid borrow date");
        }
        if (expectedReturnDateDate == null ) {
            throw new IllegalArgumentException("Invalid return date");
        }

        this.id = id;
        this.user = user;
        this.documentType = documentType;
        this.state = state;
        this.borrowDate = borrowDate;
        this.expectedReturnDate = expectedReturnDateDate;
    }


    // Getter và Setter cho tất cả các thuộc tính

    /**
     * Lấy mã định danh của lần mượn.
     *
     * @return Mã định danh của lần mượn.
     */
    public String getId() {
        return id;
    }

    /**
     * Thiết lập mã định danh cho lần mượn.
     *
     * @param id Mã định danh mới.
     * @throws IllegalArgumentException nếu id null hoặc rỗng.
     */
    public void setId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Borrowing ID cannot be null or empty.");
        }
        this.id = id;
    }

    /**
     * Lấy người dùng thực hiện mượn tài liệu.
     *
     * @return Người dùng mượn tài liệu.
     */
    public User getUser() {
        return user;
    }

    /**
     * Thiết lập người dùng cho lần mượn tài liệu.
     *
     * @param user Người dùng mới.
     * @throws IllegalArgumentException nếu user null.
     */
    public void setUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        this.user = user;
    }

    /**
     * Lấy loại tài liệu được mượn.
     *
     * @return Loại tài liệu được mượn.
     */
    public Document getDocumentType() {
        return documentType;
    }

    /**
     * Thiết lập loại tài liệu cho lần mượn.
     *
     * @param documentType Loại tài liệu mới.
     * @throws IllegalArgumentException nếu documentType null.
     */
    public void setDocumentType(Document documentType) {
        if (documentType == null) {
            throw new IllegalArgumentException("Document type cannot be null.");
        }
        this.documentType = documentType;

        // Cập nhập trạng thái của sách
        documentType.setState(true);
    }

    /**
     * Lấy trạng thái của lần mượn.
     *
     * @return Trạng thái của lần mượn.
     */
    public String getState() {
        return state;
    }

    /**
     * Thiết lập trạng thái của lần mượn.
     *
     * @param state Trạng thái mới.
     * @throws IllegalArgumentException nếu state null hoặc rỗng.
     */
    public void setState(String state) {
        if (state == null || state.isEmpty()) {
            throw new IllegalArgumentException("State cannot be null or empty.");
        }
        this.state = state;
    }

    /**
     * Lấy ngày mượn tài liệu.
     *
     * @return Ngày mượn tài liệu.
     */
    public Date getBorrowDate() {
        return borrowDate;
    }

    /**
     * Thiết lập ngày mượn tài liệu.
     *
     * @param borrowDate Ngày mượn tài liệu mới.
     * @throws IllegalArgumentException nếu borrowDate null.
     */
    public void setBorrowDate(Date borrowDate) {
        if (borrowDate == null) {
            throw new IllegalArgumentException("Borrow date cannot be null.");
        }
        this.borrowDate = borrowDate;
    }

    /**
     * Lấy ngày trả tài liệu (dự kiến hoặc thực tế).
     *
     * @return Ngày trả tài liệu.
     */
    public Date getExpectedReturnDate() {
        return expectedReturnDate;
    }

    /**
     * Thiết lập ngày trả tài liệu.
     *
     * @param expectedReturnDate Ngày trả tài liệu mới.
     * @throws IllegalArgumentException nếu returnDate null hoặc trước borrowDate.
     */
    public void setReturnDate(Date expectedReturnDate) {
        if (expectedReturnDate == null) {
            throw new IllegalArgumentException("Return date cannot be null or before borrow date.");
        }
        this.expectedReturnDate = expectedReturnDate;
    }

}
