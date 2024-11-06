package model;

/**
 * Class Document represents a document in the library system.
 * Each document has an ID, title, author, and availability state (available or not).
 */
public class Document {
    private String id;
    private String title;
    private String category;
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
     * @param category The category of the document.
     * @param author The author of the document, represented by a {@link Person} object.
     * @param publisher The publisher of the document, represented by a {@link Person} object.
     * @param quantity The number of copies of the document available for borrowing.
     * @param maxBorrowDays The maximum number of days the document can be borrowed.
     */

    public Document(String id, String title, String category, Person author, Person publisher, int quantity, int maxBorrowDays) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
        this.maxBorrowDays = maxBorrowDays;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Person getAuthor() {
        return author;
    }


    public void setAuthor(Person author) {
        this.author = author;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Person getPublisher() {
        return publisher;
    }


    public void setPublisher(Person publisher) {
        this.publisher = publisher;
    }


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
}

