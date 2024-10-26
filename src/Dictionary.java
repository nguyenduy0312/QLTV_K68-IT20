/**
 * Đại diện cho một từ điển, là một loại tài liệu chuyên biệt.
 * Lớp này kế thừa từ lớp {@link Document} và bao gồm các thông tin
 */
public class Dictionary extends Document {

    /**
     * No-argument constructor for the Dictionary class.
     * Used to create a default Dictionary object.
     */
    public Dictionary() {}

    /**
     * Parameterized constructor for the Dictionary class.
     * Used to create a Book object with specific details.
     *
     * @param id        the ID of the book
     * @param title     the title of the book
     * @param author    the author of the book
     * @param publisher the publisher of the book
     * @param quantity  the number of copies available for the book
     * @param maxBorrowDays The maximum number of days the document can be borrowed
     */
    public Dictionary(String id, String title, Person author, Person publisher, int quantity, int maxBorrowDays) {
        super(id, title, author,publisher,quantity, maxBorrowDays); // Calls the parent class constructor (Document)
    }

    /**
     * Overridden method to return the type of the document.
     *
     * @return a string "Dictionary" representing the document type as a book.
     */
    @Override
    public String getDocumentType() {
        return "Dictionary";
    }
}
