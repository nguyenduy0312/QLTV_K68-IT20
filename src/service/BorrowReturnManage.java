package main.java.service;

import main.java.model.User;
import main.java.model.Document;
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


}

