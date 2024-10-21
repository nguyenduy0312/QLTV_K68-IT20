import java.time.LocalDate;

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
