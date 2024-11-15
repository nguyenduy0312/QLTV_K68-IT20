package test;

import DAO.UserDAO;
import model.User;
import model.Account;
import util.Date;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;

public class TestUserDAO {
    public static void main (String[] args) {

        // thêm người dùng thành công
//        Account account1 = new Account("hoangyd123", "12345");
//        java.util.Date date1 = new java.util.Date(2005-1900, 11, 2);
//        User user1 = new User("Nguyễn Văn Hoàng", date1,"P001","Thái Bình", "012345678", "Nam","hoangyd@gmail.com",account1);
//        UserDAO.getInstance().addUser(user1);



//        Account account = new Account("hoangyd2005", "hoangyd123");
//        Date date = new Date(1, 1, 2005);
//        User user = new User("Nguyễn Văn Hoàng", date,"K68_01","Thái Bình", "123456789", "Nam","hoangyd@gmail.com",account);
//        UserDAO.getInstance().addUser(user);

        Account account1 = new Account("hoangdao2005", "hoangdao123");
        Date date1 = new Date(23, 12, 2005);
        User user1 = new User("Đào Huy Hoàng", date1,"K68_02","Nam Định", "12345", "Nam","hoangdao@gmail.com",account1);
        UserDAO.getInstance().addUser(user1);


        // Xóa người dùng thành công
//        String id = "K68_02";
//        UserDAO.getInstance().deleteUser(id);


        // Tìm kiếm người dùng theo ID thành công
//        User userr = UserDAO.getInstance().findUserById("P001");
//        System.out.println(userr);


        // Tìm kiếm người dùng theo tên thành công
//        User user1 = UserDAO.getInstance().findUserByName("Đào Huy Hoàng");
//        System.out.println(user1);


        // In cả danh sách người dùng
//        ArrayList<User> userArrayList = UserDAO.getInstance().findAllUsers();
//        for (User user2 : userArrayList) {
//            System.out.println(user2.toString());
//        }
    }
}
