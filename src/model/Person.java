package model;
/**
 * Lớp đại diện cho một người dùng trong hệ thống thư viện.
 */

import util.Date;

public class Person {
    // Tên của người dùng
    private String name;

    // Ngày sinh của người dùng
    private Date dateOfBirth;

    // ID duy nhất của người dùng
    private String id;

    // Địa chỉ của người dùng
    private String address;

    // Số điện thoại của người dùng
    private String phoneNumber;

    // Email của người dùng
    private String email;

    // Giới tính của người dùng
    private String gender;

    // Chân dung
    private byte[] picture;  // Dữ liệu hình ảnh dạng byte[]

    public Person () {}
    /**
     * Hàm khởi tạo cho lớp Person.
     *
     * @param name Tên của người dùng
     * @param dateOfBirth Ngày sinh của người dùng
     * @param id ID duy nhất của người dùng
     * @param address Địa chỉ của người dùng
     * @param phoneNumber Số điện thoại của người dùng
     * @param gender Giới tính của người dùng
     * @param picture Chân dung của người dùng
     */
    public Person(String name, Date dateOfBirth, String id, String address, String phoneNumber, String gender,String email, byte[] picture) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
        this.picture = picture;
    }

    // Phương thức lấy tên của người dùng
    public String getName() {
        return name;
    }

    // Phương thức thiết lập tên cho người dùng
    public void setName(String name) {
        this.name = name;
    }

    // Phương thức lấy ngày sinh của người dùng
    public Date getBirthday() {
        return dateOfBirth;
    }

    // Phương thức thiết lập ngày sinh cho người dùng
    public void setBirthday(Date birthday) {
        this.dateOfBirth = birthday;
    }

    // Phương thức lấy ID của người dùng
    public String getId() {
        return id;
    }

    // Phương thức thiết lập ID cho người dùng
    public void setId(String id) {
        this.id = id;
    }

    // Phương thức lấy địa chỉ của người dùng
    public String getAddress() {
        return address;
    }

    // Phương thức thiết lập địa chỉ cho người dùng
    public void setAddress(String address) {
        this.address = address;
    }

    // Phương thức lấy số điện thoại của người dùng
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Phương thức thiết lập số điện thoại cho người dùng
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Phương thức lấy giới tính của người dùng
    public String getGender() {
        return gender;
    }

    // Phương thức thiết lập giới tính cho người dùng
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    /**
     * Phương thức hiển thị thông tin của người dùng.
     *
     * @return Chuỗi thông tin của người dùng
     */
    @Override
    public String toString() {
        return "Tên: " + name + ", Ngày sinh: " + dateOfBirth.toString() +
                ", ID: " + id + ", Địa chỉ: " + address +
                ", Số điện thoại: " + phoneNumber + ", Giới tính: " + gender;
    }
}

