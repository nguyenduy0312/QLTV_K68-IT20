import java.time.LocalDate;

public class BorrowReturn {

    public static final String  NOT_RETURNED = "Chưa trả";
    public static final String RETURNED = "Đã trả";
    public static final String OVERDUE = "Quá hạn";


    public static final Date DEFAULT_DATE = new Date(1, 1, 2005);

    private Document document;
    private String state;
    private LocalDate borrowDate;
    private LocalDate maximumBorrowDate;
    private Date dueDate;
    private LocalDate actualReturnDate;

    public BorrowReturn() {
        this.document = null;
        this.state = RETURNED; // Khởi tạo trạng thái
        this.dueDate = DEFAULT_DATE;

    }

    public void  borrowDocument(Document document) {

        if(document.getQuantity() <= 0) {
            throw new IllegalArgumentException("Sorry, Document " + document.getId() + " is currently not available.");
        }

        setState(NOT_RETURNED);
        setDocument(document);
        document.setQuantity(document.getQuantity() - 1);
        this.borrowDate = LocalDate.now();

        dueDate.inputDate();
        LocalDate dueLocalDate = dueDate.toLocalDate();
        this.dueDate = new Date(dueLocalDate.getDayOfMonth(), dueLocalDate.getMonthValue(), dueLocalDate.getYear());

        this.maximumBorrowDate = borrowDate.plusDays(document.getMaxBorrowDays());
        if(dueLocalDate.isBefore(borrowDate)) {
            System.out.println("Return date cannot be before borrow date. Please try again.");
        } else if(dueLocalDate.isAfter(maximumBorrowDate)) {
            System.out.println("Cannot borrow beyond the allowed borrowing period.");
        } else {
            System.out.println("You have successfully borrowed the book.");
        }
    }

    public void returnDocument(Document document) {
        this.actualReturnDate = LocalDate.now();
        document.setQuantity(document.getQuantity() + 1);
        if(this.actualReturnDate.isBefore(maximumBorrowDate)) {
            setState(RETURNED);
        } else if(this.actualReturnDate.isAfter(maximumBorrowDate) || this.actualReturnDate.isAfter(dueDate.toLocalDate())) {
            setState(OVERDUE);
        }
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }



    public void printInfo() {
        if(state.equals(NOT_RETURNED)) {
            System.out.println(document.getId() + " " + document.getTitle() + " " + borrowDate + " " + this.getDueDate() + " ------ " + state);
        } else {
            System.out.println(document.getId() + " " + document.getTitle() + " " + borrowDate + " " + this.getDueDate() + " " + actualReturnDate + " " + state);
        }

    }

    public static void main(String argc[]) {
        Date date = new Date();
        Person author = new Person("Đào Huy Hoàng", date, "23020666", "Yên Đồng", "0352712366", "Nam");
        Book book = new Book("B111", "CTDL_GT", author, author, 4, 10);
        Book book1 = new Book("B111", "CTDL_GT", author, author, 4, 10);
        BorrowReturn borrowDocument = new BorrowReturn();
        borrowDocument.borrowDocument(book);
        borrowDocument.printInfo();
        borrowDocument.borrowDocument(book1);
        borrowDocument.printInfo();

    }
}
