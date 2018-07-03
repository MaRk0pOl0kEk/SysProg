import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;


public class Parser {
    private static final HashSet<Character> vowels =
            new HashSet<>(Arrays.asList('a', 'e', 'y', 'u', 'o', 'i', 'A', 'E', 'Y', 'U', 'O', 'I'));
    private final TreeSet<String> sortedStrings;

    private class MyComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            int cmp = Double.compare(quantOfConsonant(o1), quantOfConsonant(o2));
            return cmp != 0 ? cmp : o1.compareToIgnoreCase(o2);
        }
    }

    Parser(String filename) {
        if (filename == null) throw new IllegalArgumentException();
        sortedStrings = new TreeSet<>(new MyComparator());
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String str;
            String[] array;
            while((str = in.readLine()) != null) {
                array = str.split("\\W|_+|[0-9]+");
                for (int i = 0; i < array.length; ++i)
                    if (array[i].length() != 0) sortedStrings.add(array[i]);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Iterable<String> getSorted() {
        return new ArrayList<>(sortedStrings);
    }

    public static int quantOfConsonant(String str) {
        if (str == null) throw new IllegalArgumentException();
        int result = 0, count = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (!vowels.contains(str.charAt(i))) {
                ++count;
                result = Math.max(result, count);
            }
            else {
                count = 0;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Parser demo = new Parser("lorem.txt");
        String str;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("res.txt"));
            for (String elem : demo.getSorted()) {
                if (quantOfConsonant(demo.sortedStrings.last()) == quantOfConsonant(elem)) {
                    str = elem + "\n";
                    writer.write(str);
                    System.out.print(str);
                }
            }
            writer.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
