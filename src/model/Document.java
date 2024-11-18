package model;

import java.util.Arrays;

/**
 * Class Document represents a document in the library system.
 * Each document has an ID, title, author, and availability state (available or not).
 */
public class Document {

    public static final int QUANTITY = 100;
    public static final int MAXBORROWDAYS = 30;

    private String id;
    private String title;
    private String category;
    private String author;
    private String publisher;
    private int quantity;
    private int maxBorrowDays;
    private byte[] qrCode;  // Dữ liệu QRCode dạng byte[]
    private byte[] picture;  // Dữ liệu hình ảnh bìa sách dạng byte[]

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
     * @param author The author of the document, represented by a {@link String} object.
     * @param publisher The publisher of the document, represented by a {@link String} object.
     * @param quantity The number of copies of the document available for borrowing.
     * @param maxBorrowDays The maximum number of days the document can be borrowed.
     * @param qrCode The QR code data as a byte array.
     * @param picture The image data of the document's cover as a byte array.
     */
    public Document(String id, String title, String category, String author, String publisher, int quantity,
                    int maxBorrowDays, byte[] qrCode, byte[] picture) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
        this.maxBorrowDays = maxBorrowDays;
        this.qrCode = qrCode;
        this.picture = picture;
    }
    public Document(String id, String title, String category, String author, String publisher, int quantity,
                    int maxBorrowDays) {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getMaxBorrowDays() {
        return maxBorrowDays;
    }

    public void setMaxBorrowDays(int maxBorrowDays) {
        if (maxBorrowDays <= 0) {
            throw new IllegalArgumentException("Max borrow days must be greater than 0.");
        }
        this.maxBorrowDays = maxBorrowDays;
    }

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Document {" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", quantity=" + quantity +
                ", maxBorrowDays=" + maxBorrowDays +
                ", qrCode=" + (qrCode != null ? Arrays.toString(qrCode) : "null") +
                ", picture=" + (picture != null ? Arrays.toString(picture) : "null") +
                '}';
    }
}
