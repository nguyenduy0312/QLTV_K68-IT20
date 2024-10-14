/**
 * Class Document represents a document in the library system.
 * Each document has an ID, title, author, and availability state (available or not).
 */
public abstract class Document {

    private String id;
    private String title;
    private String author;
    private String publisher;
    private boolean state;

    /**
     * Default constructor for Document.
     * Initializes a new empty document.
     */
    public Document() {}

    /**
     * Constructs a new Document with the specified details.
     *
     * @param id     The document's unique identifier.
     * @param title  The title of the document.
     * @param author The author of the document.
     * @param state  The availability state of the document (true if available, false if borrowed).
     */
    public Document(String id, String title, String author, boolean state) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.state = state;
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
    public String getAuthor() {
        return author;
    }

    /**
     * Sets a new author for the document.
     *
     * @param author The new author of the document.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the current state of the document.
     *
     * @return True if the document is available, false if it is borrowed.
     */
    public boolean getState() {
        return state;
    }

    /**
     * Sets the availability state of the document.
     *
     * @param state True if the document is available, false if it is borrowed.
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * Retrieves the name of the book's publisher.
     *
     * @return The name of the publisher as a string.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the name of the book's publisher.
     *
     * @param publisher The name of the publisher to set for the book.
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
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
    public String getInfo() {
        return "Document ID: " + id + ", Title: " + title + ", Author: " + author;
    }
}
