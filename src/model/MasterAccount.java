package model;

import java.util.ArrayList;
import java.util.List;

public class MasterAccount {
    private static final String USERNAME = "hoanghoangduy";
    private static final String PASSWORD = "123456789!@#$%^&*(";

    private List<User> users = new ArrayList<>();
    private List<Admin> admins = new ArrayList<>();

    /**
     * Kiểm tra thông tin đăng nhập.
     *
     * @param username Tên người dùng
     * @param password Mật khẩu
     * @return True nếu thông tin đăng nhập hợp lệ, false nếu không
     */
    public boolean login(String username, String password) {
        return USERNAME.equals(username) && PASSWORD.equals(password);
    }



    /**
     * Tạo tài khoản admin mới.
     *
     * @param admin Tài khoản admin cần tạo
     * @return True nếu tạo thành công, false nếu tài khoản đã tồn tại
     */
    public boolean createAdminAccount(Admin admin) {
        for (Admin existingAdmin : admins) {
            if (existingAdmin.getAccount().equals(admin.getAccount())) {
                System.out.println("Tài khoản admin đã tồn tại" );
                return false; // Tài khoản đã tồn tại
            }
        }
        admins.add(admin);
        System.out.println("Tài khoản admin đã được tạo: " + admin.getName());
        return true; // Tạo tài khoản thành công
    }

    /**
     * Tạo tài khoản user mới.
     *
     * @param user Tài khoản user cần tạo
     * @return True nếu tạo thành công, false nếu tài khoản đã tồn tại
     */
    public boolean createUserAccount(User user) {
        for (User existingUser : users) {
            if (existingUser.getAccount().equals(user.getAccount())) {
                System.out.println("Tài khoản user đã tồn tại: " + user.getAccount());
                return false; // Tài khoản đã tồn tại
            }
        }
        users.add(user);
        System.out.println("Tài khoản user đã được tạo: " + user.getName());
        return true; // Tạo tài khoản thành công
    }

    /**
     * Xóa tài khoản user.
     *
     * @param user Tài khoản user cần xóa
     * @return True nếu xóa thành công, false nếu không tìm thấy tài khoản
     */
    public boolean deleteUserAccount(User user) {
        if (users.remove(user)) {
            System.out.println("Tài khoản user đã được xóa: " + user.getName());
            return true;
        }
        System.out.println("Không tìm thấy tài khoản user: " + user.getName());
        return false;
    }

    /**
     * Xóa tài khoản admin.
     *
     * @param admin Tài khoản admin cần xóa
     * @return True nếu xóa thành công, false nếu không tìm thấy tài khoản
     */
    public boolean deleteAdminAccount(Admin admin) {
        if (admins.remove(admin)) {
            System.out.println("Tài khoản admin đã được xóa: " + admin.getName());
            return true;
        }
        System.out.println("Không tìm thấy tài khoản admin: " + admin.getName());
        return false;
    }
}

