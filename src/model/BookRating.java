package model;

public class BookRating {

    public static final String STAR_FULL = "\uf005"; // Ngôi sao đầy
    public static final String STAR_EMPTY = "\uf006"; // Ngôi sao rỗng
    public static final String STAR_HALF = "\uf123"; // Ngôi sao nửa

    private String bookId;
    private double score;
    private int numReviews;

    public BookRating(String bookId, double score, int numReviews) {
        this.bookId = bookId;
        this.score = score;
        this.numReviews = numReviews;
    }

    public String getBookId() {
        return bookId;
    }

    public double getScore() {
        return score;
    }

    public int getNumReviews() {
        return numReviews;
    }

    @Override
    public String toString() {
        return "BookRating{" +
                "bookId='" + bookId + '\'' +
                ", score=" + score +
                ", numReviews=" + numReviews +
                '}';
    }
}
