package model;

/**
 * Lớp đại diện cho người quản lý trong hệ thống
 */

import util.Date;
import service.UserManage;
import service.DocumentManage;

public class Admin extends Person {

    // Tài khoản đăng nhập
    private Account account;

    // Quản lý người dùng
    private UserManage userManage;

    // Quản lý tài liệu
    private DocumentManage documentManage;


    /**
     * Hàm khởi tạo cho lớp Admin với tất cả các tham số.
     *
     * @param name Tên của người quản lý
     * @param dateOfBirth Ngày sinh của người quản lý
     * @param id ID duy nhất của người quản lý
     * @param address Địa chỉ của người quản lý
     * @param phoneNumber Số điện thoại của người quản lý
     * @param gender Giới tính của người quản lý
     * @param email Email
     * @param account Tài khoản người dùng mà quản lý này có quyền quản lý
     * @param userManage Đối tượng quản lý người dùng
     * @param documentManage Đối tượng quản lý tài liệu
     */
    public Admin(String name, Date dateOfBirth, String id, String address, String phoneNumber, String gender, String email, byte[] picture,
                 Account account, UserManage userManage, DocumentManage documentManage) {
        super(name, dateOfBirth, id, address, phoneNumber, gender, email, picture);
        this.account = account;
        this.userManage = userManage;
        this.documentManage = documentManage;
    }


    // Getter và Setter cho các thuộc tính của Admin

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public UserManage getUserManage() {
        return userManage;
    }

    public void setUserManage(UserManage userManage) {
        this.userManage = userManage;
    }

    public DocumentManage getDocumentManage() {
        return documentManage;
    }

    public void setDocumentManage(DocumentManage documentManage) {
        this.documentManage = documentManage;
    }


    /**
     * Phương thức thực hiện đăng nhập cho người quản lý.
     *
     * @param userName Tên đăng nhập của người quản lý
     * @param passWord Mật khẩu của người quản lý
     * @return {@code true} nếu đăng nhập thành công, {@code false} nếu đăng nhập không thành công
     * @throws IllegalArgumentException nếu tên đăng nhập hoặc mật khẩu không hợp lệ (null hoặc chuỗi rỗng)
     */
    public boolean login(String userName, String passWord) {
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("Invalid username.");
        }

        if (passWord == null || passWord.isEmpty() || !this.getAccount().isValidPassword(passWord)) {
            throw new IllegalArgumentException("Invalid password.");
        }


        if(account.getUserName().equals(userName) && account.getPassWord().equals(passWord)) {
            System.out.println("LoginView successful");
            return true;
        }

        System.out.println("Error: Invalid username or password.");
        return false;
    }

    /**
     * Đặt lại mật khẩu cho tài khoản quản trị viên.
     * Phương thức này sẽ kiểm tra trạng thái kích hoạt của tài khoản. Nếu tài khoản bị khóa,
     * một ngoại lệ sẽ được ném ra. Nếu mật khẩu mới không hợp lệ hoặc giống với mật khẩu cũ,
     * một ngoại lệ khác cũng sẽ được ném ra.
     *
     * @param newPassWord Mật khẩu mới mà quản trị viên muốn đặt.
     * @throws IllegalArgumentException nếu tài khoản bị khóa, mật khẩu mới không hợp lệ (null hoặc chuỗi rỗng)
     *         hoặc mật khẩu mới giống với mật khẩu cũ.
     */
    public void resetPassWord(String newPassWord) {

        if (newPassWord == null || newPassWord.isEmpty() || this.getAccount().isValidPassword(newPassWord)) {
            throw new IllegalArgumentException("Invalid new password.");
        }

        if(this.getAccount().getPassWord().equals(newPassWord)) {
            throw new IllegalArgumentException("Error: New password cannot be the same as the old password.");
        }
        this.getAccount().setPassWord(newPassWord);
    }



    @Override
    public String toString() {
        return super.toString();
    }
}

