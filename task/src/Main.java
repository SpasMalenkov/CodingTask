import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    static Set<String> dictionary;

    static {
        try {
            dictionary = loadAllWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Set<String> loadAllWords() throws IOException {
        URL wordsUrl = new URL("https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader((wordsUrl.openConnection().getInputStream())))) {
            return br.lines().skip(2).collect(Collectors.toSet());
        }
    }

    public static void main(String[] args) {
        List<String> nineCharWords = dictionary.stream().filter(s -> s.length() == 9).toList();
        System.out.println("All nine character words: " + nineCharWords.size());
        List<String> result = nineCharWords.stream().filter(Main::findSmallerWord).toList();
        System.out.println("Final result: " + result.size());
        result.forEach(System.out::println);
    }

    public static boolean findSmallerWord(String word) {

        if (word.length() == 1 && ("A".equals(word) || "I".equals(word))) {
            return true;
        }

        if (!dictionary.contains(word)) {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            String shortenedWord = new StringBuilder(word).deleteCharAt(i).toString();
            boolean result = findSmallerWord(shortenedWord);
            if (result) {
                return findSmallerWord(shortenedWord);
            }
        }
        return false;
    }
}