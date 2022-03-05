package interview;

import java.util.Arrays;

public class Interview {
    public static void main (String[] args) {
        System.out.println(search(new int[ ]{1, 3, 5, 5, 10}, -1));
        System.out.println(Arrays.toString(customSort(new int[]{10, 3, -5, 5, 10})));
    }

    static boolean search (int[] arr, int value) {
        int result = -1;
        int index;
        int l = 1;
        int r = arr.length;
        while (l<=r) {
            index = (l + r)/2;
            if (arr[index]==value) {
                result = index;
                break;
            }
            else {
                if (arr[index]>value) {
                    r = index - 1;
                }
                else {
                    l = index + 1;
                }
            }
        }
        return result!=-1;
    }

    static int[] customSort (int[] arr) {

        int n = arr.length;

        for (int i = 0; i < n; i++) {
            int max = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j]>arr[max]) {
                    max = j;
                }
            }

            int temp = arr[i];
            arr[i] = arr[max];
            arr[max] = temp;

        }

        return arr;
    }

}


