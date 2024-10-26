/**
 * Đại diện cho một tạp chí, là một loại tài liệu chuyên biệt.
 * Lớp này kế thừa từ lớp {@link Document} và bao gồm các thông tin
 */
public class Magazine extends Document {

    /**
     * No-argument constructor for the Magazine class.
     * Used to create a default Book object.
     */
    public Magazine() {}

    /**
     * Parameterized constructor for the Magazine class.
     * Used to create a Book object with specific details.
     *
     * @param id        the ID of the magazine
     * @param title     the title of the magazine
     * @param author    the author of the magazine
     * @param publisher the publisher of the magazine
     * @param quantity  the number of copies available for the magazine
     * @param maxBorrowDays The maximum number of days the document can be borrowed
     */
    public Magazine(String id, String title, Person author,Person publisher, int quantity, int maxBorrowDays) {
        super(id, title, author,publisher,quantity, maxBorrowDays); // Calls the parent class constructor (Document)
    }

    /**
     * Overridden method to return the type of the document.
     *
     * @return a string "Magazine" representing the document type as a book.
     */
    @Override
    public String getDocumentType() {
        return "Magazine";
    }
}
