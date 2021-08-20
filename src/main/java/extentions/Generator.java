package extentions;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Generator {
    public static void main (String[] args) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(("src/input.txt"));
        int n = 100000;
        Random random = new Random();
        printWriter.println(n);
        for (int i = 0; i < n; i++) {
            printWriter.print(random.nextInt() + " ");
        }
        printWriter.close();
    }
}
