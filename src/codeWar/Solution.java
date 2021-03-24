package codeWar;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {


    }


//    public static String sumFracts (int[][] l) {
//        if (l.length==0) return null;
//        int[][] l1= l.clone();
//        int common=0;
//        int commonDiv=1;
//
//        for (int[] item:l1) {
//            if (item[1]>item[0]&&(item[1]%item[0]==0)) {
//                item[1]=item[1]/item[0];
//                item[0]=1;
//            }
//            else if (item[0]>item[1]&&(item[0]%item[1]==0)) {
//                item[0]=item[0]/item[1];
//                item[1]=1;
//            }
//            else if (item[0]==item[1]) {
//                item[0]=1;
//                item[1]=1;
//            }
//        }
//
//        for (int[] item:l1) {
//            commonDiv=lcm(commonDiv, item[1]);
//        }
//        for (int[] item:l1) {
//            common+=item[0]*commonDiv/item[1];
//        }
//        return (common%commonDiv==0)? String.valueOf(common/commonDiv) :"["+common+", "+commonDiv+"]";
//    }
//
//    static int gcd(int a, int b){
//        return b == 0 ? a : gcd(b,a % b);
//    }
//
//    static int lcm(int a, int b){
//        return a / gcd(a,b) * b;
//    }

}