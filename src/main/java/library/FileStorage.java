package library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private final Path booksFile;
    private final Path membersFile;
    private final Path loansFile;

    public FileStorage(Path folder) {
        this.booksFile = folder.resolve("books.csv");
        this.membersFile = folder.resolve("members.csv");
        this.loansFile = folder.resolve("loans.csv");
    }

    public void save(Library library) throws IOException {
        Files.createDirectories(booksFile.getParent());

        List<String> bookLines = new ArrayList<>();
        for (Book b : library.getBooks()) {
            bookLines.add(b.getId() + "," + b.getTitle() + "," + b.getAuthor());
        }
        Files.write(booksFile, bookLines);

        List<String> memberLines = new ArrayList<>();
        for (Person p : library.getMembers()) {
            String type = (p instanceof Staff) ? "STAFF" : "MEMBER";
            memberLines.add(p.getId() + "," + p.getName() + "," + type);
        }
        Files.write(membersFile, memberLines);

        List<String> loanLines = new ArrayList<>();
        for (Loan l : library.getLoans()) {
            loanLines.add(l.getBookId() + "," + l.getPersonId() + "," + l.getIssueDate());
        }
        Files.write(loansFile, loanLines);
    }

    public Library load() throws IOException {
        Library library = new Library();

        for (String line : readLines(booksFile)) {
            String[] p = line.split(",");
            library.addBook(new Book(p[0], p[1], p[2]));
        }

        for (String line : readLines(membersFile)) {
            String[] p = line.split(",");
            if (p[2].equals("STAFF")) {
                library.addMember(new Staff(p[0], p[1]));
            } else {
                library.addMember(new Member(p[0], p[1]));
            }
        }

        for (String line : readLines(loansFile)) {
            String[] p = line.split(",");
            library.restoreLoan(new Loan(p[0], p[1], LocalDate.parse(p[2])));
        }

        return library;
    }

    private List<String> readLines(Path file) throws IOException {
        if (!Files.exists(file)) {
            return new ArrayList<>();
        }
        List<String> lines = new ArrayList<>();
        for (String line : Files.readAllLines(file)) {
            if (!line.isBlank()) {
                lines.add(line);
            }
        }
        return lines;
    }
}
