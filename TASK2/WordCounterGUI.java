import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WordCounterGUI extends JFrame {

    JTextArea inputTA;
    JTextArea resultTA;

    private boolean isFilePath(String input) {
        File file = new File(input);
        return file.exists() && file.isFile();
    }

    public WordCounterGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        inputTA = new JTextArea(10, 30);
        resultTA = new JTextArea(10, 30);
        JButton countBt = new JButton("Count Words");

        // Add action listener to the count button
        countBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                countWords();
            }
        });

        // Set layout to FlowLayout
        setLayout(new FlowLayout());

        // Add components to the frame
        add(new JLabel("Enter text or provide a file path:"));
        add(new JScrollPane(inputTA));
        add(countBt);
        add(new JLabel("Word Count Results:"));
        add(new JScrollPane(resultTA));

        // Set up the frame
        setVisible(true);
    }

    private void countWords() {
        String input = inputTA.getText().trim().toLowerCase(); // Convert to lowercase for consistent processing

        // Simulated file reading for simplicity
        if (input.contains(".")) {
            input = "This is a sample text file. It contains some words. Sample words.";
        }

        String[] words = splitIntoWords(input);

        int wordCount = 0;
        int distinctWordsCount = 0;
        String[] distinctWords = new String[words.length];
        int[] wordFrequency = new int[words.length];

        // Iterate through the array of words
        for (String word : words) {
            if (!isStopWord(word, distinctWords, distinctWordsCount)) {
                wordCount++;
                distinctWordsCount = updateDistinctWordsAndFrequency(word, distinctWords, wordFrequency, distinctWordsCount);
            }
        }

        // Display the total count of words to the user
        resultTA.setText("Total number of words: " + wordCount + "\n");

        // Display number of unique words and their frequencies
        resultTA.append("Number of unique words: " + distinctWordsCount + "\n");
        resultTA.append("Word frequency:\n");
        for (int i = 0; i < distinctWordsCount; i++) {
            resultTA.append(distinctWords[i] + ": " + wordFrequency[i] + "\n");
        }
    }

    private String[] splitIntoWords(String text) {
        // Split the string into an array of words using space as a delimiter
        return text.split("\\s+");
    }

    private boolean isStopWord(String word, String[] distinctWords, int distinctWordsCount) {
        // Additional Step 7: Ignore common words (stop words)
        String[] stopWords = { "the", "and", "in", "to", "of", "a", "is", "that" };
        for (int i = 0; i < stopWords.length; i++) {
            if (stopWords[i].equalsIgnoreCase(word)) {
                return true;
            }
        }
        // Check if the word already exists in uniqueWords
        for (int i = 0; i < distinctWordsCount; i++) {
            if (distinctWords[i].equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    private int updateDistinctWordsAndFrequency(String word, String[] distinctWords, int[] wordFrequency,
                                                 int distinctWordsCount) {
        // Update uniqueWords and wordFrequency arrays
        for (int i = 0; i < distinctWordsCount; i++) {
            if (distinctWords[i].equalsIgnoreCase(word)) {
                wordFrequency[i]++;
                return distinctWordsCount; // Word already exists, frequency updated
            }
        }
        distinctWords[distinctWordsCount] = word;
        wordFrequency[distinctWordsCount] = 1;
        return distinctWordsCount + 1; // Increment uniqueWordsCount
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordCounterGUI::new);
    }
}
