package BookAnalizer;

import java.io.File;
import java.util.Scanner;

public class BookAnalyzer {
    private final FileFinder fileFinder;
    private final BookStatisticCalculator statisticCalculator;

    public BookAnalyzer() {
        this.fileFinder = new FileFinder();
        this.statisticCalculator = new BookStatisticCalculator();
    }

    public void analyzeBooks(Scanner scanner) {
        boolean bookFound = false;
        while (!bookFound) {
            System.out.println("Enter the book name to analyze (or 'exit' to quit): ");
            String bookName = scanner.nextLine();

            if (bookName.equalsIgnoreCase("exit")) {
                break;
            }

            File bookFile = fileFinder.findBook(bookName);
            if (bookFile != null) {
                statisticCalculator.analyzeBook(bookFile);
                bookFound = true;
            } else {
                System.err.println("Book not found.");
            }
        }
    }
}
