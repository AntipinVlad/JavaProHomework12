import BookAnalizer.BookAnalyzer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookAnalyzer bookAnalyzer = new BookAnalyzer();
        bookAnalyzer.analyzeBooks(scanner);
        scanner.close();
    }
}