package library;

public class Book {
    private final String id;
    private final String title;
    private final String author;
    private boolean issued;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    @Override
    public String toString() {
        return String.format("%-6s %-30s %-20s %s", id, title, author, issued ? "Issued" : "Available");
    }
}
