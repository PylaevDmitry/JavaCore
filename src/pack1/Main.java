package pack1;

import pack2.Base;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        int[] l  = {Integer.parseInt("c"), 10, 101,0 , 99 };
        System.out.println(max(l));

        Sub s1 = new Sub();
//
//        Sub s2 = new Sub("weeeki");
//
//        Sub s3 = new Sub("mat", 34);
//
//        s1.par1=2;
//
//        System.out.println(s1.b);
//
//        s3.mettt();

        System.out.println(s1.par99);
    }

    Base k = new Base() {
        @Override
        public void draw(String a) {
            System.out.println("Anonymous");
        }
    };

    static int max (int [] arr) {
        try {
            return (arr.length==1)?arr[0]:Integer.max(max(Arrays.copyOfRange(arr, 1, arr.length)), arr[0]);
        } catch (NumberFormatException e) {
            arr[0]=Integer.MAX_VALUE;
            return (arr.length==1)?arr[0]:Integer.max(max(Arrays.copyOfRange(arr, 1, arr.length)), arr[0]);
        }
    }
}
