package stepic;

import java.util.*;

public class Main {
    public static void main (String[] args) {

        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        int[] arr = Arrays.stream(scanner.next().split(" ")).mapToInt(Integer::parseInt).toArray();

        int W = arr[0];
        int n = arr[1];

        int max = Arrays.stream(arr).max().orElse(0);

        arr = Arrays.stream(scanner.next().split(" ")).mapToInt(Integer::parseInt).toArray();

        CustomClass customClass = new CustomClass("start");


    }
}

class CustomClass {

    private int[] arr;

    CustomClass (String input) {
        this.arr = Arrays.stream(input.split("")).mapToInt(Integer::parseInt).toArray();
    }
}
