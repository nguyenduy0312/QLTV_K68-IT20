package model;

import util.Date;

import java.util.List;
import java.util.ArrayList;

public class User extends Person {
    private Account account;


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

    public User(String name, Date birthday, String personID, String address, String phoneNumber, String gender, String email, byte[] picture){
        super(name, birthday, personID, address, phoneNumber, gender, email, picture);
    }

    public User() {}



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

