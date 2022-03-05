package codeWar;

import java.util.Arrays;
import java.util.LinkedList;

public class Solution {
    public static void main(String[] args) {

//        System.out.println(Arrays.toString(maxSumDig(2000, 3)));
        new Base1().meth1();
        new Base3().meth1();

        System.out.println((int) 99999999999999999.99999d);
        System.out.println(Integer.MAX_VALUE);


    }

    public static long[] maxSumDig(long nmax, int maxsm) {
        long count = 0;
        long value = 0;
        long sumAll = 0;

        LinkedList<Long> list = new LinkedList<>();

        for (long i = 1000; i <= nmax; i++) {
            int[] arrAll = String.valueOf(i).chars().toArray();
            for (int j = 0; j < arrAll.length-3; j++) {
                int[] arr = Arrays.copyOfRange(arrAll, j, j+4);
                if (sum(arr, arr.length) > maxsm) {
                    break;
                }
                else if (j == arrAll.length-4) {
                    count++;
                    sumAll += i;
                    list.add(i);
                }
            }


//            for (int j = arr.length-1; j >=0; j--) {
//                if (arr[j] == maxsm-sum(arr, j)) {
//                    long res = 1;
//                    for (int k = 0; k < arr.length - 1 - j; k++) {res=res*10;}
//                    i = i + (9 - arr[j]) * (res);
//                }
//            }
        }
        var mid = (double) sumAll/count;

        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i)>mid) {
                value = ((mid-list.get(i-1))>=(list.get(i)-mid))? list.get(i) : list.get(i-1);
                break;
            }
        }

        return new long[] {count, value, sumAll};
    }

    public static int sum(int[] arr, int index) {
        int result = 0;
        for (int i = 0; i < index; i++) {
            result+=arr[i];
        }
        return result;
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

    static int max(int[] arr) {
        try {
            return (arr.length == 1) ? arr[0] : Integer.max(max(Arrays.copyOfRange(arr, 1, arr.length)), arr[0]);
        } catch (NumberFormatException e) {
            arr[0] = Integer.MAX_VALUE;
            return (arr.length == 1) ? arr[0] : Integer.max(max(Arrays.copyOfRange(arr, 1, arr.length)), arr[0]);
        }
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b,a % b);
    }

    static int lcm(int a, int b) {
        return a / gcd(a,b) * b;
    }

    //Thumbtack

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
