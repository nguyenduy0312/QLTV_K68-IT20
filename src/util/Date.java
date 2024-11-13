package util;

import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.Scanner;

/**
 * Lớp Date đại diện cho một ngày cụ thể, bao gồm ngày, tháng và năm.
 * Nó lấy ngày tháng năm hiện tại của hệ thống.
 */
public class Date {
    private int day;
    private int month;
    private int year;

    /**
     * Khởi tạo một đối tượng Date mới với ngày, tháng và năm hiện tại của hệ thống.
     */
    public Date() {
        LocalDate currentDate = LocalDate.now();
        this.day = currentDate.getDayOfMonth();
        this.month = currentDate.getMonthValue();
        this.year = currentDate.getYear();
    }

    /**
     * Khởi tạo một đối tượng Date mới với ngày, tháng và năm được chỉ định.
     *
     * @param day   Ngày trong tháng (1-31).
     * @param month Tháng trong năm (1-12).
     * @param year  Năm (ví dụ: 2024).
     * @throws IllegalArgumentException nếu ngày, tháng hoặc năm được cung cấp không hợp lệ.
     */
    public Date(int day, int month, int year) {
        if (isValidDate(day, month, year)) {
            this.day = day;
            this.month = month;
            this.year = year;
        } else {
            System.out.println("Ngày tháng năm không hợp lệ!");
        }
    }

    /**
     * Nhập ngày, tháng, và năm từ người dùng qua console.
     */
    public void inputDate() {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;

        while (!valid) {
            System.out.print("Nhập ngày (dd): ");
            int day = scanner.nextInt();
            System.out.print("Nhập tháng (MM): ");
            int month = scanner.nextInt();
            System.out.print("Nhập năm (yyyy): ");
            int year = scanner.nextInt();

            if (isValidDate(day, month, year)) {
                this.day = day;
                this.month = month;
                this.year = year;
                valid = true;
            } else {
                System.out.println("Ngày không hợp lệ, vui lòng thử lại.");
            }
        }
    }

    /**
     * Kiểm tra xem ngày, tháng, năm có hợp lệ không.
     *
     * @param day   ngày (1-31)
     * @param month tháng (1-12)
     * @param year  năm
     * @return true nếu ngày, tháng, năm hợp lệ; ngược lại false
     */
    public boolean isValidDate(int day, int month, int year) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * So sánh ngày hiện tại với một đối tượng Date khác.
     *
     * @param other đối tượng Date khác để so sánh
     * @return 0 nếu hai ngày bằng nhau, >0 nếu ngày hiện tại lớn hơn, <0 nếu ngày hiện tại nhỏ hơn
     */
    public int compareTo(Date other) {
        LocalDate currentDate = LocalDate.of(this.year, this.month, this.day);
        LocalDate otherDate = LocalDate.of(other.year, other.month, other.day);
        return currentDate.compareTo(otherDate);
    }

    /**
     * Trả về ngày của đối tượng Date này.
     *
     * @return ngày (1-31)
     */
    public int getDay() {
        return day;
    }

    /**
     * Trả về tháng của đối tượng Date này.
     *
     * @return tháng (1-12)
     */
    public int getMonth() {
        return month;
    }

    /**
     * Trả về năm của đối tượng Date này.
     *
     * @return năm
     */
    public int getYear() {
        return year;
    }

    /**
     * Cộng thêm số ngày vào ngày hiện tại và cập nhật đối tượng Date.
     *
     * @param days số ngày cần cộng
     */
    public void addDays(int days) {
        LocalDate currentDate = LocalDate.of(year, month, day);
        LocalDate newDate = currentDate.plusDays(days);
        this.day = newDate.getDayOfMonth();
        this.month = newDate.getMonthValue();
        this.year = newDate.getYear();
    }

    // Phương pháp chuyển đổi Date thành LocalDate
    public LocalDate toLocalDate() {
        return LocalDate.of(year, month, day);
    }

    /**
     * Trả về chuỗi đại diện của Date theo định dạng "dd/mm/yyyy".
     *
     * @return chuỗi được định dạng đại diện cho Date này
     */
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    /**
     * Chuyển đổi đối tượng Date thành java.sql.Date.
     *
     * @return đối tượng java.sql.Date tương ứng
     */
    public java.sql.Date toSqlDate() {
        return java.sql.Date.valueOf(LocalDate.of(year, month, day));
    }

    /**
     * Converts a java.sql.Date to a custom Date object.
     *
     * @param sqlDate the java.sql.Date to convert
     * @return a new Date object with corresponding day, month, and year
     */
    public  static Date fromSqlDate(java.sql.Date sqlDate) {
        if (sqlDate == null) {
            return null;
        }

        java.time.LocalDate localDate = sqlDate.toLocalDate();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();

        return new Date(day, month, year);
    }
}
