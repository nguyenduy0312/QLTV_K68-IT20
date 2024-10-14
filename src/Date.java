/**
 * Lớp Date đại diện cho một ngày và giờ cụ thể, bao gồm ngày, tháng, năm, giờ, phút và giây.
 * Nó cung cấp các phương thức tiện ích để thao tác và so sánh ngày giờ.
 */
public class Date {
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private int second;

    /**
     * Khởi tạo một đối tượng Date mới với ngày, tháng, năm, giờ, phút và giây được chỉ định.
     *
     * @param day    từ 1 đến 31 (tùy tháng)
     * @param month  từ 1-12
     * @param year   năm (ví dụ: 2024)
     * @param hour   giờ trong ngày (0-23)
     * @param minute phút trong giờ (0-59)
     * @param second giây trong phút (0-59)
     */
    public Date(int day, int month, int year, int hour, int minute, int second) {
        if (!isValidDateTime(day, month, year, hour, minute, second)) {
            throw new IllegalArgumentException("Ngày hoặc giờ không hợp lệ");
        }
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
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
     * Trả về giờ của đối tượng Date này.
     *
     * @return giờ (0-23)
     */
    public int getHour() {
        return hour;
    }

    /**
     * Trả về phút của đối tượng Date này.
     *
     * @return phút (0-59)
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Trả về giây của đối tượng Date này.
     *
     * @return giây (0-59)
     */
    public int getSecond() {
        return second;
    }

    /**
     * Thiết lập ngày cho đối tượng Date này.
     *
     * @param day ngày mới (1-31)
     */
    public void setDay(int day) {
        if (!isValidDateTime(day, this.month, this.year, this.hour, this.minute, this.second)) {
            throw new IllegalArgumentException("Ngày không hợp lệ");
        }
        this.day = day;
    }

    /**
     * Thiết lập tháng cho đối tượng Date này.
     *
     * @param month tháng mới (1-12)
     */
    public void setMonth(int month) {
        if (!isValidDateTime(this.day, month, this.year, this.hour, this.minute, this.second)) {
            throw new IllegalArgumentException("Tháng không hợp lệ");
        }
        this.month = month;
    }

    /**
     * Thiết lập năm cho đối tượng Date này.
     *
     * @param year năm mới
     */
    public void setYear(int year) {
        if (!isValidDateTime(this.day, this.month, year, this.hour, this.minute, this.second)) {
            throw new IllegalArgumentException("Năm không hợp lệ");
        }
        this.year = year;
    }

    /**
     * Thiết lập giờ cho đối tượng Date này.
     *
     * @param hour giờ mới (0-23)
     */
    public void setHour(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Giờ không hợp lệ");
        }
        this.hour = hour;
    }

    /**
     * Thiết lập phút cho đối tượng Date này.
     *
     * @param minute phút mới (0-59)
     */
    public void setMinute(int minute) {
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Phút không hợp lệ");
        }
        this.minute = minute;
    }

    /**
     * Thiết lập giây cho đối tượng Date này.
     *
     * @param second giây mới (0-59)
     */
    public void setSecond(int second) {
        if (second < 0 || second > 59) {
            throw new IllegalArgumentException("Giây không hợp lệ");
        }
        this.second = second;
    }

    /**
     * Kiểm tra xem ngày và giờ đã cho có hợp lệ hay không.
     *
     * @param day    ngày trong tháng (1-31)
     * @param month  tháng trong năm (1-12)
     * @param year   năm
     * @param hour   giờ (0-23)
     * @param minute phút (0-59)
     * @param second giây (0-59)
     * @return true nếu ngày và giờ hợp lệ, false nếu không
     */
    public static boolean isValidDateTime(int day, int month, int year, int hour, int minute, int second) {
        return isValidDate(day, month, year) && hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59 && second >= 0 && second <= 59;
    }

    /**
     * Kiểm tra xem ngày (ngày, tháng, năm) đã cho có hợp lệ hay không.
     *
     * @param day   ngày trong tháng (1-31)
     * @param month tháng trong năm (1-12)
     * @param year  năm
     * @return true nếu ngày hợp lệ, false nếu không
     */
    public static boolean isValidDate(int day, int month, int year) {
        if (month < 1 || month > 12 || day < 1) {
            return false;
        }

        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // Kiểm tra năm nhuận trong tháng 2
        if (isLeapYear(year)) {
            daysInMonth[1] = 29;
        }

        return day <= daysInMonth[month - 1];
    }

    /**
     * Kiểm tra xem năm đã cho có phải là năm nhuận hay không.
     *
     * @param year năm cần kiểm tra
     * @return true nếu năm là năm nhuận, false nếu không
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * So sánh đối tượng Date này với một đối tượng Date khác.
     *
     * @param other đối tượng Date để so sánh
     * @return số âm nếu ngày này trước ngày khác,
     *         zero nếu ngày giống nhau, và số dương
     *         nếu ngày này sau ngày khác
     */
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;
        } else if (this.month != other.month) {
            return this.month - other.month;
        } else if (this.day != other.day) {
            return this.day - other.day;
        } else if (this.hour != other.hour) {
            return this.hour - other.hour;
        } else if (this.minute != other.minute) {
            return this.minute - other.minute;
        } else {
            return this.second - other.second;
        }
    }

    /**
     * Trả về chuỗi đại diện của Date theo định dạng "dd/mm/yyyy hh:mm:ss".
     *
     * @return chuỗi được định dạng đại diện cho Date này
     */
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d %02d:%02d:%02d", day, month, year, hour, minute, second);
    }
}
