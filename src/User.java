import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class User extends Person {
    private Account account;
    private List<Document> borrowedDocuments; // Danh sách tài liệu đã mượn

    private List<BorrowReturn> borrowReturns;  // Danh sách hành động mượn trả

    /**
     * Hàm khởi tạo cho lớp User.
     *
     * @param name Tên của người dùng
     * @param dateOfBirth Ngày sinh của người dùng
     * @param id ID duy nhất của người dùng
     * @param address Địa chỉ của người dùng
     * @param phoneNumber Số điện thoại của người dùng
     * @param gender Giới tính của người dùng
     */
    public User(String name, Date dateOfBirth, String id, String address, String phoneNumber, String gender) {
        super(name, dateOfBirth, id, address, phoneNumber, gender); // Gọi constructor lớp cha
        this.borrowedDocuments = new ArrayList<>();   // Khởi tạo danh sách tài liệu đã mượn
        this.borrowReturns = new ArrayList<>();      // Khởi tạo danh sách hành động mượn trả
    }
    public List<Document> getBorrowedDocuments() {
        return borrowedDocuments;
    }

    public List<BorrowReturn> getBorrowReturns() {
        return borrowReturns;
    }

    // Hàm in thông tin của người dùng
    public void inThongTin() {
        System.out.println(super.toString());
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void borrowDocument(Document document) {
        // Kiểm tra xem tài liệu đã được mượn hay chưa
        if (borrowedDocuments.contains(document)) {
            System.out.println("You have already borrowed this document.");
            return;
        }

        BorrowReturn borrowReturn = new BorrowReturn();
        borrowReturn.borrowDocument(document);
        borrowReturns.add(borrowReturn);
        borrowedDocuments.add(document);
    }

    // Trả tài liệu
    public void returnDocument(Document document) {
        // Kiểm tra nếu tài liệu chưa được mượn bởi người dùng
        if (!borrowedDocuments.contains(document)) {
            System.out.println("You haven't borrowed this document.");
            return;
        }

        // Tìm kiếm giao dịch mượn tương ứng và trả tài liệu
        for (BorrowReturn borrowReturn : borrowReturns) {
            if (borrowReturn.getDocument().equals(document)) {
                borrowReturn.returnDocument(document); // Thực hiện hành động trả
                borrowedDocuments.remove(document); // Xóa tài liệu khỏi danh sách mượn
                System.out.println("Document " + document.getId() + " returned successfully.");
                return;
            }
        }

        System.out.println("Error: Document was not found in borrow records.");
    }


    // In thông tin mượn trả
    public void printBorrowingInfo() {
        System.out.println("Borrowing Information for User: " + this.getName());
        for (BorrowReturn borrowReturn : borrowReturns) {
            borrowReturn.printInfo();
        }
    }

    public static void main(String argc[]) {
        Date date = new Date(23, 12, 2005);
        User user = new User("Hoàng", date, "23020666", "Nam Định", "0352712366", "Nam");
        User user1 = new User("Huy", date, "23020677", "Nam Định", "0352712366", "Nam");
        Person author = new Person("Đào Huy Hoàng", date, "23020666", "Yên Đồng", "0352712366", "Nam");
        Book book = new Book("B111", "CTDL_GT", author, author, 1, 10);
        Book book1 = new Book("B112", "CTDL_GT1", author, author, 1, 10);


        user1.borrowDocument(book);
        user1.borrowDocument(book1);

        user1.printBorrowingInfo();

    }
}
