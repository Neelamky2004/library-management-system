package library;

import java.time.LocalDate;

public class Loan {
    private final String bookId;
    private final String personId;
    private final LocalDate issueDate;

    public Loan(String bookId, String personId, LocalDate issueDate) {
        this.bookId = bookId;
        this.personId = personId;
        this.issueDate = issueDate;
    }

    public String getBookId() {
        return bookId;
    }

    public String getPersonId() {
        return personId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    @Override
    public String toString() {
        return String.format("Book %-6s -> Member %-6s on %s", bookId, personId, issueDate);
    }
}
