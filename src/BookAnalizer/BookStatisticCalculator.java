package BookAnalizer;

import java.io.*;
import java.util.*;

public class BookStatisticCalculator {
    public void analyzeBook(File file) {
        try {
            Set<String> uniqueWords = readWordsFromFile(file);
            List<String> filteredWords = filterWords(uniqueWords);
            Map<String, Integer> wordCount = countWordOccurrences(filteredWords);
            List<Map.Entry<String, Integer>> sortedWordCount = sortWordCount(wordCount);
            int uniqueWordCount = wordCount.size();
            writeStatisticToFile(file.getName(), sortedWordCount, uniqueWordCount);
            displayStatistic(sortedWordCount, uniqueWordCount);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private Set<String> readWordsFromFile(File file) throws IOException {
        Set<String> uniqueWords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("[\\s\\W]+");
                uniqueWords.addAll(Arrays.asList(words));
            }
        }
        return uniqueWords;
    }

    private List<String> filterWords(Set<String> uniqueWords) {
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
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        return wordCount;
    }

    private List<Map.Entry<String, Integer>> sortWordCount(Map<String, Integer> wordCount) {
        List<Map.Entry<String, Integer>> sortedWordCount = new ArrayList<>(wordCount.entrySet());
        sortedWordCount.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        return sortedWordCount;
    }

    private void writeStatisticToFile(String fileName, List<Map.Entry<String, Integer>> wordCount, int uniqueWordCount) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName.split("\\.")[0] + "_statistic.txt"));
            for (Map.Entry<String, Integer> entry : wordCount) {
                writer.write(entry.getKey() + " -> " + entry.getValue() + "\n");
            }
            writer.write("Total number of words: " + uniqueWordCount);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private void displayStatistic(List<Map.Entry<String, Integer>> wordCount, int uniqueWordCount) {
        System.out.println("10 most popular words:");
        for (int i = 0; i < Math.min(10, wordCount.size()); i++) {
            Map.Entry<String, Integer> entry = wordCount.get(i);
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println("Total number of unique words: " + uniqueWordCount);
    }
}
