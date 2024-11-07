package main.java.model;

/**
 * Lớp đại diện cho tài khoản đăng nhập trong hệ thống
 */
import java.util.regex.Pattern;
public class Account {
    // Tên đăng nhập
    String userName;

    // Mật khẩu
    String passWord;



    /**
     * Khởi tạo đối tượng Account với tên đăng nhập, mật khẩu và trạng thái kích hoạt.
     *
     * @param userName tên đăng nhập của tài khoản
     * @param passWord mật khẩu của tài khoản
     */
    public Account(String userName, String passWord) {
        if(!isValidPassword(passWord)) {
            throw new IllegalArgumentException("Invalid password.");
        }
        this.userName = userName;
        this.passWord = passWord;
    }

    /**
     * Lấy tên đăng nhập của tài khoản.
     *
     * @return tên đăng nhập của tài khoản
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Thiết lập tên đăng nhập cho tài khoản.
     *
     * @param userName tên đăng nhập mới cho tài khoản
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Lấy mật khẩu của tài khoản.
     *
     * @return mật khẩu của tài khoản
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * Thiết lập mật khẩu mới cho tài khoản.
     *
     * @param passWord mật khẩu mới cho tài khoản
     */
    public void setPassWord(String passWord) {
        if(!isValidPassword(passWord)) {
            throw new IllegalArgumentException("Invalid password.");
        }
        this.passWord = passWord;
    }



    /**
     * Kiểm tra xem mật khẩu có hợp lệ hay không.
     *
     * Mật khẩu được coi là hợp lệ nếu nó đáp ứng các tiêu chí sau:
     * - Độ dài tối thiểu là 8 ký tự.
     * - Ít nhất một ký tự chữ hoa.
     * - Ít nhất một ký tự chữ thường.
     * - Ít nhất một ký tự số.
     * - Nếu được yêu cầu, có thể yêu cầu ít nhất một ký tự đặc biệt.
     *
     * @param password Mật khẩu cần kiểm tra.
     * @return {@code true} nếu mật khẩu hợp lệ, {@code false} nếu không hợp lệ.
     */
    public boolean isValidPassword(String password) {
        // Kiểm tra độ dài tối thiểu
        if (password.length() < 8) {
            return false;
        }

        // Kiểm tra có ít nhất một ký tự chữ hoa
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }

        // Kiểm tra có ít nhất một ký tự chữ thường
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            return false;
        }

        // Kiểm tra có ít nhất một ký tự số
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return false;
        }
        return true;
    }


}
