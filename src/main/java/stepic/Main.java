package stepic;

import java.util.*;

public class Main {
    long count = 0;

    public static void main (String[] args) {
        new Main().run();
    }

    void run () {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        scanner.nextInt();
        int[] a = Arrays.stream(scanner.next().split(" ")).mapToInt(Integer::parseInt).toArray();
        mergeSort(a);
        System.out.println(count);
    }

    int[] merge (int[] a, int[] b) {
        int i = 0;
        int j = 0;
        int[] res = new int[a.length+b.length];
        for (int k = 0; k < res.length; k++) {
            if (j==b.length || (i<a.length && a[i]<=b[j])) {
                res[k] = a[i];
                i++;
            } else {
                res[k] = b[j];
                j++;
                count+=a.length - i;
            }
        }
        return res;
    }

    int[] mergeSort(int[] a) {
        int n = a.length;
        int m = n/2;
        if (n==1) return a;
        int[] left = new int[m];
        int[] right = new int[n-m];
        System.arraycopy(a, 0, left, 0, m);
        System.arraycopy(a, m, right, 0, n - m);
        left = mergeSort(left);
        right = mergeSort(right);
        return merge(left, right);
    }
}





