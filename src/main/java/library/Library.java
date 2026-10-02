package library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private final Map<String, Book> books = new LinkedHashMap<>();
    private final Map<String, Person> members = new LinkedHashMap<>();
    private final List<Loan> loans = new ArrayList<>();

    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new LibraryException("Book with id " + book.getId() + " already exists");
        }
        books.put(book.getId(), book);
    }

    public void addMember(Person person) {
        if (members.containsKey(person.getId())) {
            throw new LibraryException("Member with id " + person.getId() + " already exists");
        }
        members.put(person.getId(), person);
    }

    public Book findBook(String id) {
        Book book = books.get(id);
        if (book == null) {
            throw new LibraryException("Book not found: " + id);
        }
        return book;
    }

    public Person findMember(String id) {
        Person person = members.get(id);
        if (person == null) {
            throw new LibraryException("Member not found: " + id);
        }
        return person;
    }

    public List<Book> searchBooks(String keyword) {
        String key = keyword.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(key) || book.getAuthor().toLowerCase().contains(key)) {
                result.add(book);
            }
        }
        return result;
    }

    public void issueBook(String bookId, String memberId, LocalDate date) {
        Book book = findBook(bookId);
        Person person = findMember(memberId);
        if (book.isIssued()) {
            throw new LibraryException("Book is already issued");
        }
        if (countLoans(memberId) >= person.getMaxBooks()) {
            throw new LibraryException(person.getName() + " has reached the limit of " + person.getMaxBooks() + " books");
        }
        book.setIssued(true);
        loans.add(new Loan(bookId, memberId, date));
    }

    public long returnBook(String bookId, LocalDate returnDate) {
        Book book = findBook(bookId);
        Loan loan = findLoan(bookId);
        long fine = FineCalculator.calculate(loan.getIssueDate(), returnDate);
        loans.remove(loan);
        book.setIssued(false);
        return fine;
    }

    public void restoreLoan(Loan loan) {
        findBook(loan.getBookId()).setIssued(true);
        loans.add(loan);
    }

    private Loan findLoan(String bookId) {
        for (Loan loan : loans) {
            if (loan.getBookId().equals(bookId)) {
                return loan;
            }
        }
        throw new LibraryException("Book " + bookId + " is not issued");
    }

    private int countLoans(String memberId) {
        int count = 0;
        for (Loan loan : loans) {
            if (loan.getPersonId().equals(memberId)) {
                count++;
            }
        }
        return count;
    }

    public Collection<Book> getBooks() {
        return books.values();
    }

    public Collection<Person> getMembers() {
        return members.values();
    }

    public List<Loan> getLoans() {
        return loans;
    }
}
