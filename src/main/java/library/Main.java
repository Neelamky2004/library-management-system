package library;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        FileStorage storage = new FileStorage(Path.of("data"));
        Library library = storage.load();

        while (true) {
            System.out.println();
            System.out.println("===== Library Management System =====");
            System.out.println("1. Add book");
            System.out.println("2. Add member");
            System.out.println("3. Show all books");
            System.out.println("4. Show all members");
            System.out.println("5. Search book");
            System.out.println("6. Issue book");
            System.out.println("7. Return book");
            System.out.println("8. Show issued books");
            System.out.println("0. Save and exit");
            String choice = ask("Enter choice: ");

            try {
                switch (choice) {
                    case "1" -> {
                        library.addBook(new Book(ask("Book id: "), ask("Title: "), ask("Author: ")));
                        System.out.println("Book added");
                    }
                    case "2" -> {
                        String id = ask("Member id: ");
                        String name = ask("Name: ");
                        String type = ask("Type (1 = Student, 2 = Staff): ");
                        library.addMember(type.equals("2") ? new Staff(id, name) : new Member(id, name));
                        System.out.println("Member added");
                    }
                    case "3" -> library.getBooks().forEach(System.out::println);
                    case "4" -> library.getMembers().forEach(System.out::println);
                    case "5" -> {
                        List<Book> found = library.searchBooks(ask("Title or author: "));
                        if (found.isEmpty()) {
                            System.out.println("No books found");
                        }
                        found.forEach(System.out::println);
                    }
                    case "6" -> {
                        library.issueBook(ask("Book id: "), ask("Member id: "), LocalDate.now());
                        System.out.println("Book issued. Return within " + FineCalculator.ALLOWED_DAYS + " days");
                    }
                    case "7" -> {
                        long fine = library.returnBook(ask("Book id: "), LocalDate.now());
                        System.out.println(fine > 0 ? "Book returned. Fine: Rs " + fine : "Book returned. No fine");
                    }
                    case "8" -> library.getLoans().forEach(System.out::println);
                    case "0" -> {
                        storage.save(library);
                        System.out.println("Data saved. Bye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice");
                }
            } catch (LibraryException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static String ask(String label) {
        System.out.print(label);
        return sc.nextLine().trim();
    }
}
