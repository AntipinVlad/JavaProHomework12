package BookAnalizer;

import java.io.*;
import java.util.*;

public class BookStatisticCalculator {
    public void analyzeBook(File file) {
        try {
            List<String> filteredWords = filterWords(readWordsFromFile(file));
            Map<String, Integer> wordCount = countWordOccurrences(filteredWords);
            writeStatisticToFile(file.getName(), wordCount);
            displayStatistic(wordCount);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private List<String> readWordsFromFile(File file) throws IOException {
        List<String> allWords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("[\\s\\W]+");
                allWords.addAll(Arrays.asList(words));
            }
        }
        return allWords;
    }

    private List<String> filterWords(List<String> uniqueWords) {
        List<String> filteredWords = new ArrayList<>();
        for (String word : uniqueWords) {
            if (word.length() > 2) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }

    private Map<String, Integer> countWordOccurrences(List<String> words) {
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            word = word.toLowerCase();
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        // Create a list of Map.Entry entries and sort it in descending order of frequency
        List<Map.Entry<String, Integer>> sortedWordCount = new ArrayList<>(wordCount.entrySet());
        sortedWordCount.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Save the list into a sorted structure
        Map<String, Integer> sortedWordCountMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : sortedWordCount) {
            sortedWordCountMap.put(entry.getKey(), entry.getValue());
        }

        return sortedWordCountMap;
    }

    private void writeStatisticToFile(String fileName, Map<String, Integer> wordCount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName.split("\\.")[0] + "_statistic.txt"))) {
            for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                writer.write(entry.getKey() + " -> " + entry.getValue() + "\n");
            }
            writer.write("Total number of words: " + wordCount.size());
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private void displayStatistic(Map<String, Integer> wordCount) {
        System.out.println("10 most popular words:");
        int count = 0;
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (count == 10) break;
            System.out.println(entry.getKey() + " -> " + entry.getValue());
            count++;
        }
        System.out.println("Total number of unique words: " + wordCount.size());
    }
}
