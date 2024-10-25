import java.util.ArrayList;
import java.util.List;

public class BorrowingService {

    // Danh sách các tài liệu đang được mượn
    private List<Document> borrowedDocuments = new ArrayList<>();

    /**
     * Thực hiện mượn tài liệu.
     *
     * @param user Người dùng thực hiện mượn tài liệu
     * @param document Tài liệu cần mượn
     * @return True nếu mượn thành công, false nếu tài liệu đã bị mượn
     */
    public boolean borrowDocument(User user, Document document) {
        if (document.getState()) { // Kiểm tra nếu tài liệu có sẵn
            document.setState(false); // Cập nhật trạng thái tài liệu thành đã mượn
            borrowedDocuments.add(document); // Thêm tài liệu vào danh sách mượn
            user.getBorrowedDocuments().add(document); // Thêm tài liệu vào danh sách của người dùng
            System.out.println(user.getName() + " đã mượn tài liệu: " + document.getTitle());
            return true;
        } else {
            System.out.println("Tài liệu " + document.getTitle() + " đã được mượn.");
            return false;
        }
    }

    /**
     * Thực hiện trả tài liệu.
     *
     * @param user Người dùng trả tài liệu
     * @param document Tài liệu cần trả
     * @return True nếu trả thành công, false nếu tài liệu không có trong danh sách mượn của người dùng
     */
    public boolean returnDocument(User user, Document document) {
        if (borrowedDocuments.contains(document) && user.getBorrowedDocuments().contains(document)) {
            document.setState(true); // Cập nhật trạng thái tài liệu thành có sẵn
            borrowedDocuments.remove(document); // Xóa tài liệu khỏi danh sách mượn chung
            user.getBorrowedDocuments().remove(document); // Xóa tài liệu khỏi danh sách của người dùng
            System.out.println(user.getName() + " đã trả tài liệu: " + document.getTitle());
            return true;
        } else {
            System.out.println("Không tìm thấy tài liệu " + document.getTitle() + " trong danh sách đã mượn của " + user.getName());
            return false;
        }
    }
}
