package codeWar;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {

        System.out.println(sumFracts(new int[][] { {2,1}, {2,9}, {3,18}, {4,24}, {6,48} }));

    }

    public static String sumFracts (int[][] l) {
        if (l.length==0) return null;
        int[][] l1= l.clone();
        int common=0;
        int commonDiv=1;

        for (int[] item:l1) {
            if (item[1]>=item[0]&&(item[1]%item[0]==0)) {
                item[1]=item[1]/item[0];
                item[0]=1;
            }
            else if (item[0]>item[1]&&(item[0]%item[1]==0)) {
                item[0]=item[0]/item[1];
                item[1]=1;
            }
        }

        for (int[] item:l1) { commonDiv=lcm(commonDiv, item[1]); }
        for (int[] item:l1) { common+=item[0]*commonDiv/item[1]; }

        return (common%commonDiv==0)? String.valueOf(common/commonDiv) : "[" + common+", " + commonDiv+ "]";
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b,a % b);
    }

    static int lcm(int a, int b) {
        return a / gcd(a,b) * b;
    }

    static int sum (int[] array) {
        return Arrays.stream(array).sum();
    }

    static int mul (int[] array) {
        return Arrays.stream(array).reduce(0, (a, b) -> a*b);
    }

    static int min (int[] array) {
        return Arrays.stream(array).min().orElse(Integer.MAX_VALUE);
    }

    static int average (int[] array) {
        return sum(array)/ array.length;
    }

    public boolean isSortedDescendant(int[] array) {
        for (int i = 1; i < array.length; i++) { if (array[i]-array[i-1]>=0) return true; }
        return false;
    }

    public int[] cube(int[] array) {
        return Arrays.stream(array).map(n -> n*n).toArray();
    }

}
