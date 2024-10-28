/**
 * Class Document represents a document in the library system.
 * Each document has an ID, title, author, and availability state (available or not).
 */
public abstract class Document {
    private String id;
    private String title;
    private Person author;
    private Person publisher;
    private int quantity;
    private int maxBorrowDays;

    /**
     * Default constructor for Document.
     * Initializes a new empty document.
     */
    public Document() {}

    /**
     * Initializes a new Document object with the specified details.
     *
     * @param id The unique identifier for the document.
     * @param title The title of the document.
     * @param author The author of the document, represented by a {@link Person} object.
     * @param publisher The publisher of the document, represented by a {@link Person} object.
     * @param quantity The number of copies of the document available for borrowing.
     * @param maxBorrowDays The maximum number of days the document can be borrowed.
     */
    public Document(String id, String title, Person author, Person publisher, int quantity, int maxBorrowDays) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
        this.maxBorrowDays = maxBorrowDays;
    }

    /**
     * Gets the ID of the document.
     *
     * @return The unique identifier of the document.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets a new ID for the document.
     *
     * @param id The new unique identifier for the document.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the title of the document.
     *
     * @return The title of the document.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new title for the document.
     *
     * @param title The new title of the document.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the document.
     *
     * @return The author of the document.
     */
    public Person getAuthor() {
        return author;
    }

    /**
     * Sets a new author for the document.
     *
     * @param author The new author of the document.
     */
    public void setAuthor(Person author) {
        this.author = author;
    }

    /**
     * Gets the quantity of the document.
     *
     * @return The quantity of the document.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets a new quantity for the document.
     *
     * @param quantity The new quantity of the document.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the name of the book's publisher.
     *
     * @return The name of the publisher as a string.
     */
    public Person getPublisher() {
        return publisher;
    }

    /**
     * Sets the name of the book's publisher.
     *
     * @param publisher The name of the publisher to set for the book.
     */
    public void setPublisher(Person publisher) {
        this.publisher = publisher;
    }

    /**
     * Retrieves the maximum number of days allowed for borrowing the document.
     *
     * @return The maximum number of days the document can be borrowed.
     */
    public int getMaxBorrowDays() {
        return maxBorrowDays;
    }

    /**
     * Sets the maximum number of days allowed for borrowing the document.
     *
     * @param maxBorrowDays The new maximum number of days for borrowing.
     * @throws IllegalArgumentException if the provided maxBorrowDays is less than or equal to zero.
     */
    public void setMaxBorrowDays(int maxBorrowDays) {
        if (maxBorrowDays <= 0) {
            throw new IllegalArgumentException("Max borrow days must be greater than 0.");
        }
        this.maxBorrowDays = maxBorrowDays;
    }

    /**
     * Returns the document type.
     * This method can be overridden in subclasses to return more specific document types.
     *
     * @return The string "Document" representing the general type of the document.
     */
    public abstract String getDocumentType();


    /**
     * Returns a string representation of the document information.
     *
     * @return A string containing the document's ID, title, and author.
     */

    @Override
    public String toString() {
        return "Document{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", publisher=" + publisher +
                ", quantity=" + quantity +
                ", maxBorrowDays=" + maxBorrowDays +
                '}';
    }
}
