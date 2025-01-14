import java.io.Serializable;

class Book implements Serializable {
    private String title;
    private String author;
    private String isbn;
    private boolean isBorrowed;
    private String borrower;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isBorrowed = false;
        this.borrower = null;
    }

    // Getter and Setter methods
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isBorrowed() { return isBorrowed; }
    public String getBorrower() { return borrower; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setBorrowed(boolean borrowed) { isBorrowed = borrowed; }
    public void setBorrower(String borrower) { this.borrower = borrower; }
}