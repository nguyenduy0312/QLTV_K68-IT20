package model;

import util.Date;
import service.BorrowReturn;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class User extends Person {
    private Account account;

    private List<Document> borrowedDocuments = new ArrayList<>();  ; // Danh sách tài liệu đã mượn

    private List<BorrowReturn> borrowReturns = new ArrayList<>();  ;  // Danh sách hành động mượn trả

    /**
     * Hàm khởi tạo cho lớp User.
     *
     * @param name Tên của người dùng
     * @param birthday Ngày sinh của người dùng
     * @param personID ID duy nhất của người dùng
     * @param address Địa chỉ của người dùng
     * @param phoneNumber Số điện thoại của người dùng
     * @param gender Giới tính của người dùng
     */
    public User(String name, Date birthday, String personID, String address, String phoneNumber, String gender, String email, Account account, byte[] picture) {
        super(name, birthday, personID, address, phoneNumber, gender, email, picture); // Gọi constructor lớp cha
        this.account = new Account(account.userName,account.passWord);
    }

    public User() {}


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

    @Override
    public String toString() {
        return "User {" +
                "Name='" + getName() + '\'' +
                ", Date of Birth = " + getBirthday() +
                ", ID = '" + getId() + '\'' +
                ", Address ='" + getAddress() + '\'' +
                ", Phone Number ='" + getPhoneNumber() + '\'' +
                ", Gender ='" + getGender()+ '\'' +
                ", Email ='" + getEmail() + '\'' +
                ", Account = { Username='" + account.userName + "', passWord='" + account.passWord + "' }";
    }
}

