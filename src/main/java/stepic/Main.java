package stepic;

import java.util.*;

public class Main {
    public static void main (String[] args) {

        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        int[] arr = Arrays.stream(scanner.next().split(" ")).mapToInt(Integer::parseInt).toArray();

        int W = arr[0];
        int n = arr[1];
        arr = Arrays.stream(scanner.next().split(" ")).mapToInt(Integer::parseInt).toArray();


    }
}
