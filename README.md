# Library Management System

A console based library management system written in Core Java.

## Features

- Add books and members (Student or Staff)
- Search books by title or author
- Issue and return books
- Book limit per member: Student 3 books, Staff 5 books
- Fine of Rs 5 per day if a book is returned after 14 days
- Data is saved in CSV files (`data/` folder), so it is not lost after closing the app
- Unit tests with JUnit 5

## Tech Used

- Java 17
- OOP (abstract class, inheritance, encapsulation, polymorphism)
- Collections (ArrayList, LinkedHashMap)
- Custom exception handling
- File handling (java.nio)
- JUnit 5, Maven

## Class Design

```
Person (abstract)
 ├── Member  (max 3 books)
 └── Staff   (max 5 books)

Book            id, title, author, issued
Loan            bookId, personId, issueDate
Library         add / search / issue / return logic
FineCalculator  late fine calculation
FileStorage     save and load data from CSV files
Main            console menu
```

## How to Run

```bash
mvn clean package
java -jar target/library-management-system-1.0.jar
```

Run tests:

```bash
mvn test
```

## Sample Output

```
===== Library Management System =====
1. Add book
2. Add member
3. Show all books
4. Show all members
5. Search book
6. Issue book
7. Return book
8. Show issued books
0. Save and exit
Enter choice: 6
Book id: B1
Member id: M1
Book issued. Return within 14 days
```
