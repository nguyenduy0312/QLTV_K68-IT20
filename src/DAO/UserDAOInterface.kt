package DAO

import model.User

interface UserDAOInterface {
    // Phương thức thêm người dùng
    fun addUser(user: User?)

    // Phương thức cập nhật thông tin người dùng
    fun updateUser(user: User?)

    // Phương thức xóa người dùng theo mã người dùng
    fun deleteUser(maNguoiDung: String?)

    // Phương thức tìm người dùng theo mã người dùng
    fun findUserById(maNguoiDung: String?): User?

    // Phương thức tìm người dùng theo tên
    fun findUserByName(tenNguoiDung: String?): User?

    // Phương thức tìm người dùng theo mã người dùng
    fun findUserByUserName(userName: String?): User?

    // Phương thức tìm tất cả người dùng
    fun findAllUsers(): List<User>?
}
