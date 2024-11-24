package model;

/**
 * Lớp đại diện cho người quản lý trong hệ thống
 */

import util.Date;


public class Admin extends Person {

    // Tài khoản đăng nhập
    private Account account;

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
     */
    public Admin(String name, Date dateOfBirth, String id, String address, String phoneNumber, String gender, String email, byte[] picture,
                 Account account) {
        super(name, dateOfBirth, id, address, phoneNumber, gender, email, picture);
        this.account = account;
    }


    // Getter và Setter cho các thuộc tính của Admin

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

