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
     * Đặt ngày, tháng và năm theo giá trị được cung cấp nếu hợp lệ.
     *
     * @param day ngày (1-31)
     * @param month tháng (1-12)
     * @param year năm
     */
    public void setDate(int day, int month, int year) {
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

        System.out.print("Nhập ngày: ");
        int day = scanner.nextInt();

        System.out.print("Nhập tháng: ");
        int month = scanner.nextInt();

        System.out.print("Nhập năm: ");
        int year = scanner.nextInt();

        if (isValidDate(day, month, year)) {
            this.day = day;
            this.month = month;
            this.year = year;
        } else {
            System.out.println("Ngày tháng năm không hợp lệ!");
        }
    }

    /**
     * Kiểm tra xem ngày, tháng, năm có hợp lệ không.
     *
     * @param day ngày (1-31)
     * @param month tháng (1-12)
     * @param year năm
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

    /**
     * Trả về chuỗi đại diện của Date theo định dạng "dd/mm/yyyy".
     *
     * @return chuỗi được định dạng đại diện cho Date này
     */
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }
}
