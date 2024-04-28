package BookAnalizer;

import java.io.File;

public class FileFinder {
    public File findBook(String bookName) {
        File directory = new File("src/Utils");
        File[] files = directory.listFiles((dir, name) -> name.equalsIgnoreCase(bookName + ".txt"));
        return (files != null && files.length > 0) ? files[0] : null;
    }
}
