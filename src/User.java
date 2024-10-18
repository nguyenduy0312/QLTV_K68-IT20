

public class User extends Person {

    /**
     * Hàm khởi tạo cho lớp User.
     *
     * @param name Tên của người dùng
     * @param birthday Ngày sinh của người dùng
     * @param id ID duy nhất của người dùng
     * @param address Địa chỉ của người dùng
     * @param phoneNumber Số điện thoại của người dùng
     * @param gender Giới tính của người dùng
     */
    public User(String name, Date birthday, String id, String address, String phoneNumber, String gender) {
        super(name, birthday, id, address, phoneNumber, gender); // Gọi constructor lớp cha
    }

    // Hàm in thông tin của người dùng
    public void inThongTin() {
        System.out.println(super.toString());
    }
}
