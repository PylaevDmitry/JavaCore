package stepic;

import java.util.*;

public class Main {
    public static void main (String[] args) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println(i+" :"+random.nextInt(22));
        }
        new Main().run();
    }

    void run () {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        int[] input = Arrays.stream(scanner.next().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] sortedA = mergeSort(input);
        Arrays.stream(sortedA).forEach(System.out::println);
    }

//    void run () {
//        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
//        int[] a = Arrays.stream(scanner.next().split(" ")).mapToInt(Integer::parseInt).toArray();
//        int[] b = Arrays.stream(scanner.next().split(" ")).mapToInt(Integer::parseInt).toArray();
//        for (int i = 1; i < b[0]+1; i++) { System.out.print(binarySearch(a, b[i]) + " "); }
//    }

    int[] merge (int[] a, int[] b) {
        int i = 0;
        int j = 0;
        int[] res = new int[a.length+b.length];
        for (int k = 0; k < res.length; k++) {
            if (j==b.length || (i<a.length && a[i]<b[j])) {
                res[k] = a[i];
                i++;
            } else {
                res[k] = b[j];
                j++;
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





