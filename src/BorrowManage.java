/**
 * Lớp chịu trách nhiệm quản lý danh sách các bản ghi mượn tài liệu.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowManage {

    private ArrayList<Borrowing> borrowings;

    /**
     * Khởi tạo một đối tượng BorrowManage mới với danh sách các bản ghi mượn.
     *
     * @param borrowings Một danh sách các đối tượng Borrowing đại diện cho các bản ghi mượn cần quản lý.
     */
    public BorrowManage(ArrayList<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    /**
     * Lấy danh sách các bản ghi mượn.
     *
     * @return Một danh sách các đối tượng Borrowing.
     */
    public ArrayList<Borrowing> getBorrowings() {
        return borrowings;
    }

    /**
     * Thiết lập danh sách các bản ghi mượn.
     *
     * @param borrowings Một danh sách mới các đối tượng Borrowing cần được quản lý.
     */
    public void setBorrowings(ArrayList<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    /**
     * Thêm một đối tượng Borrowing vào danh sách các đối tượng đang mượn.
     *
     * @param borrowing Đối tượng Borrowing cần thêm vào danh sách
     */
    public void addBorrowing(Borrowing borrowing) { borrowings.add(borrowing); }

    /**
     * Xóa một đối tượng Borrowing khỏi danh sách dựa trên ID.
     * Phương thức này sẽ tìm và xóa tất cả các đối tượng Borrowing có ID khớp với tham số id.
     *
     * @param id ID của đối tượng Borrowing cần xóa
     */
    public void removeUser(String id) {
        borrowings.removeIf(borrowing -> borrowing.getId().equals(id));
    }

    /**
     * Tìm kiếm và trả về danh sách các đối tượng Borrowing dựa trên ID.
     * Phương thức này sẽ tìm tất cả các đối tượng Borrowing có ID khớp với tham số id
     * và trả về dưới dạng một danh sách.
     *
     * @param id ID của đối tượng Borrowing cần tìm kiếm
     * @return Danh sách các đối tượng Borrowing có ID khớp với tham số id
     */
    public List<Borrowing> searchById(String id) {
        return borrowings.stream()
                .filter(borrowing -> borrowing.getId().equals(id))
                .collect(Collectors.toList());
    }

    /**
     * Tìm kiếm các đối tượng Borrowing dựa trên id của người dùng.
     *
     * @param user Đối tượng User mà chúng ta muốn tìm kiếm các khoản vay.
     * @return Danh sách các đối tượng Borrowing liên quan đến người dùng.
     */
    public List<Borrowing> searchByUser(User user) {
        return borrowings.stream()
                .filter(borrowing -> borrowing.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }



}
