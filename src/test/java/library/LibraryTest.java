package library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    private Library library;
    private final LocalDate today = LocalDate.of(2026, 10, 1);

    @BeforeEach
    void setUp() {
        library = new Library();
        library.addBook(new Book("B1", "Clean Code", "Robert Martin"));
        library.addBook(new Book("B2", "Effective Java", "Joshua Bloch"));
        library.addBook(new Book("B3", "Head First Java", "Kathy Sierra"));
        library.addBook(new Book("B4", "Java Concurrency", "Brian Goetz"));
        library.addMember(new Member("M1", "Rahul"));
        library.addMember(new Staff("S1", "Priya"));
    }

    @Test
    void issueBookMarksItAsIssued() {
        library.issueBook("B1", "M1", today);
        assertTrue(library.findBook("B1").isIssued());
        assertEquals(1, library.getLoans().size());
    }

    @Test
    void cannotIssueSameBookTwice() {
        library.issueBook("B1", "M1", today);
        assertThrows(LibraryException.class, () -> library.issueBook("B1", "S1", today));
    }

    @Test
    void studentCannotTakeMoreThanThreeBooks() {
        library.issueBook("B1", "M1", today);
        library.issueBook("B2", "M1", today);
        library.issueBook("B3", "M1", today);
        assertThrows(LibraryException.class, () -> library.issueBook("B4", "M1", today));
    }

    @Test
    void returnOnTimeHasNoFine() {
        library.issueBook("B1", "M1", today);
        long fine = library.returnBook("B1", today.plusDays(14));
        assertEquals(0, fine);
        assertFalse(library.findBook("B1").isIssued());
    }

    @Test
    void lateReturnHasFine() {
        library.issueBook("B1", "M1", today);
        long fine = library.returnBook("B1", today.plusDays(17));
        assertEquals(15, fine);
    }

    @Test
    void returnBookThatIsNotIssuedThrowsError() {
        assertThrows(LibraryException.class, () -> library.returnBook("B2", today));
    }

    @Test
    void searchByTitleOrAuthor() {
        assertEquals(3, library.searchBooks("java").size());
        assertEquals(1, library.searchBooks("bloch").size());
    }

    @Test
    void dataIsSavedAndLoadedFromFile(@TempDir Path folder) throws Exception {
        library.issueBook("B2", "S1", today);
        FileStorage storage = new FileStorage(folder);
        storage.save(library);

        Library loaded = storage.load();
        assertEquals(4, loaded.getBooks().size());
        assertEquals(2, loaded.getMembers().size());
        assertTrue(loaded.findBook("B2").isIssued());
        assertInstanceOf(Staff.class, loaded.findMember("S1"));
    }
}
