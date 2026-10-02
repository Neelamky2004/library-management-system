package library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineCalculator {
    public static final int ALLOWED_DAYS = 14;
    public static final int FINE_PER_DAY = 5;

    public static long calculate(LocalDate issueDate, LocalDate returnDate) {
        if (returnDate.isBefore(issueDate)) {
            throw new LibraryException("Return date cannot be before issue date");
        }
        long days = ChronoUnit.DAYS.between(issueDate, returnDate);
        long lateDays = days - ALLOWED_DAYS;
        return lateDays > 0 ? lateDays * FINE_PER_DAY : 0;
    }
}
