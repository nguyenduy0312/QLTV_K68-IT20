package main.java.service;

import main.java.model.Person;
import main.java.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lớp quản lý người dùng trong hệ thống.
 */
public class UserManage {
    // Danh sách người dùng
    private List<User> users;

    /**
     * Hàm khởi tạo mặc định cho lớp UserManage.
     * Khởi tạo một danh sách người dùng trống.
     */
    public UserManage() {
        users = new ArrayList<>();
    }

    /**
     * Trả về danh sách người dùng.
     *
     * @return danh sách các đối tượng User
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Thiết lập danh sách người dùng.
     *
     * @param users danh sách các đối tượng User cần thiết lập
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }


    /**
     * Thêm một người dùng mới vào danh sách.
     *
     * @param user Người dùng cần thêm
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Xóa một người dùng khỏi danh sách dựa trên ID.
     *
     * @param id ID của người dùng cần xóa
     */
    public void removeUser(String id) {
        users.removeIf(user -> user.getId().equals(id));
    }

    /**
     * Chỉnh sửa thông tin của người dùng dựa trên ID.
     * Nếu người dùng có ID tương ứng tồn tại, thông tin của người dùng đó sẽ bị thay thế bằng thông tin mới.
     *
     * @param id ID của người dùng cần chỉnh sửa
     * @param newUser Thông tin người dùng mới để thay thế
     */
    public void editUser(String id, User newUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, newUser);
                return;
            }
        }
    }

    /**
     * Tìm kiếm người dùng theo ID.
     *
     * @param id ID của người dùng cần tìm
     * @return Danh sách các đối tượng người dùng có ID phù hợp
     */
    public List<Person> searchById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .collect(Collectors.toList());
    }

    /**
     * Tìm kiếm người dùng theo tên.
     * Phương thức này sẽ tìm tất cả người dùng có tên chứa chuỗi được cung cấp (không phân biệt hoa thường).
     *
     * @param name Tên hoặc một phần của tên người dùng cần tìm
     * @return Danh sách các đối tượng người dùng có tên phù hợp
     */
    public List<Person> searchByName(String name) {
        return users.stream()
                .filter(user -> user.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Hiển thị tất cả người dùng trong hệ thống.
     *
     * @return Danh sách tất cả các đối tượng người dùng
     */
    public List<Person> viewAllUser() {
        return new ArrayList<>(users);
    }
}

