/**
 * The Book class represents a book in the library.
 * This class extends the Document class and overrides the getDocumentType()
 * method to return the document type as "Book".
 */
public class Book extends Document {

    /**
     * No-argument constructor for the Book class.
     * Used to create a default Book object.
     */
    public Book() {}

    /**
     * Parameterized constructor for the Book class.
     * Used to create a Book object with specific details.
     *
     * @param id        the ID of the book
     * @param title     the title of the book
     * @param author    the author of the book
     * @param publisher the publisher of the book
     * @param quantity  the number of copies available for the book
     * @param maxBorrowDays The maximum number of days the document can be borrowed/
     */
    public Book(String id, String title, Person author,Person publisher, int quantity, int maxBorrowDays) {
        super(id, title, author,publisher,quantity, maxBorrowDays); // Calls the parent class constructor (Document)
    }

    /**
     * Overridden method to return the type of the document.
     *
     * @return a string "Book" representing the document type as a book.
     */
    @Override
    public String getDocumentType() {
        return "Book";
    }
}
