import java.util.ArrayList;
import java.util.List;

public class BorrowReturnManage {
    private List<User> users; // Danh sách người dùng

    public BorrowReturnManage() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Mượn sách cho người dùng
     *
     * @param user Người dùng muốn mượn sách
     * @param document Tài liệu muốn mượn
     */
    public void borrowDocumentForUser(User user, Document document) {
        if (!users.contains(user)) {
            System.out.println("The user does not exist in the system.");
            return;
        }

        try {
            user.borrowDocument(document);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // In ra thông báo lỗi nếu có
        }
    }

    /**
     * Trả sách cho người dùng
     *
     * @param user Người dùng trả sách
     * @param document Tài liệu cần trả
     */
    public void returnDocumentFromUser(User user, Document document) {
        BorrowReturn borrowReturn = findBorrowReturnByDocument(user,document);
        if (borrowReturn != null) {
            user.returnDocument(document);
        } else {
            System.out.println("This user has not borrowed this document.");
        }
    }

    /**
     * Tìm kiếm hành động mượn trả theo tài liệu
     *
     * @param user Người dùng cần tìm
     * @param document Tài liệu cần tìm
     * @return Hành động mượn trả tương ứng
     */
    public BorrowReturn findBorrowReturnByDocument (User user, Document document) {
        for (BorrowReturn borrowReturn : user.getBorrowReturns()) {
            if(borrowReturn.getDocument().equals(document)) {
                return borrowReturn;
            }
        }
        return null; // Không tìm thấy
    }


    /**
     * In tất cả thông tin
     */
    public void printAllInfo() {
        for(User user : users) {
            for (BorrowReturn borrowReturn : user.getBorrowReturns()) {
                System.out.print(user.getId() + " " + user.getName() + " ");
                borrowReturn.printInfo();
            }
        }
    }

    public void findBorrowReturnsByUserId(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                user.printBorrowingInfo();
                return;
            }
        }

        System.out.println("This user has not borrowed this document.");

    }



    public static void main(String argc[]) {
        Date date = new Date(23, 12, 2005);
        User user = new User("Hoàng", date, "23020666", "Nam Định", "0352712366", "Nam");
        User user1 = new User("Huy", date, "23020677", "Nam Định", "0352712366", "Nam");
        Person author = new Person("Đào Huy Hoàng", date, "23020666", "Yên Đồng", "0352712366", "Nam");
        Book book = new Book("B111", "CTDL_GT", author, author, 4, 10);
        Book book1 = new Book("B112", "CTDL_GT1", author, author, 3, 10);

        BorrowReturnManage borrowReturnManage = new BorrowReturnManage();
        borrowReturnManage.addUser(user);
        borrowReturnManage.addUser(user1);

        System.out.println("User:");
        borrowReturnManage.borrowDocumentForUser(user, book1);
        borrowReturnManage.borrowDocumentForUser(user, book);

        borrowReturnManage.findBorrowReturnsByUserId("23020666");

        System.out.println("User1");
        borrowReturnManage.borrowDocumentForUser(user1, book1);
        borrowReturnManage.borrowDocumentForUser(user1, book);

        System.out.println("------------------------");
        borrowReturnManage.returnDocumentFromUser(user1, book1);
        borrowReturnManage.returnDocumentFromUser(user1, book);
        borrowReturnManage.returnDocumentFromUser(user, book1);
        borrowReturnManage.returnDocumentFromUser(user, book);

        borrowReturnManage.findBorrowReturnsByUserId("23020666");
    }

}
