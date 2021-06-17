package stepic;

import java.util.*;

public class Main {
    public static void main (String[] args) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println(i+" :"+random.nextInt(22));
        }
//        new Main().run();

    }

    void run () {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        int n = scanner.nextInt();
        int[] input = Arrays.stream(scanner.next().split(" ")).mapToInt(Integer::parseInt).toArray();
        int result = 0;
        for (int i = 0; i < n; i++) {
            int[] temp = Arrays.copyOfRange(input, i+1, input.length);
            Arrays.sort(temp);
            result+=binarySearch(temp, input[i]);
        }
        System.out.println(result);
    }

//    void run () {
//        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
//        int[] a = Arrays.stream(scanner.next().split(" ")).mapToInt(Integer::parseInt).toArray();
//        int[] b = Arrays.stream(scanner.next().split(" ")).mapToInt(Integer::parseInt).toArray();
//        for (int i = 1; i < b[0]+1; i++) { System.out.print(binarySearch(a, b[i]) + " "); }
//    }

    int binarySearch (int[] a, int x) {
        int l=-1;
        int r=a.length;
        while (r>l+1) {
            int m = (l + r) >> 1;
            if (a[m] < x) l = m;
            else r = m;
        }
        if ( r < a.length && a[r] == x) return r;
        else return l+1;
    }
}





