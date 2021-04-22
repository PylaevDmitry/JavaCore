package pack1;

import pack2.Base;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class  Main {
    public static void main(String[] args) {


        String path = "E:\\YandexDisk\\Test\\Мои программы\\Java\\untitled\\out\\production\\untitled\\pack1\\test.txt";
        String testPhrase = "testPhrase7";
        List <String> list = null;
        try {

            list = Files.readAllLines(Paths.get(path));
            list.add(testPhrase);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            PrintStream printStream = new PrintStream(new FileOutputStream(path));

            assert list != null;
            for (String str:list) {
                printStream.println(str);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }





//        Fruit fruit = new Main().new Fruit();
//        Apple apple = (Apple) fruit;

        Apple apple1 = new Main().new Apple();
        Fruit fruit1 = (Fruit) apple1;

        Fruit fruit2= new Main().new Fruit();

        Main.meth1((Apple) fruit1);
        Main.meth1(apple1);
        Main.meth1(fruit1);
        Main.meth1((Fruit) apple1);

        Main.meth1(fruit2);

        System.out.println(fruit1.getClass());


        Sub s1 = new Sub();
        s1.par99="10";
        Sub.par1=2;
        Base.par1=3;

        Sub s2 = new Sub("weeeki");
        Sub s3 = new Sub("mat", 34);

        s3.mettt();

        Sub.st();
        Base.st();

        Base k = new Base() {
            @Override
            public void draw(String a) {
                System.out.println("Anonymous"+a);
            }
        };

        k.draw("");

        int[] l = {0xC, 10, 101, 0, 99};
        System.out.println(max(l));

    }

    public static int checkSumOfStream(InputStream inputStream) throws IOException {
        int checkSum=0;
        while (inputStream.read()>0) checkSum=Integer.rotateLeft(checkSum, 1)^(inputStream.read()&0xFF);
        return checkSum;
    }


    static void meth1 (Fruit fruit) {
        System.out.println(fruit.getTotalCalories());
        System.out.println(fruit.toString());
    }

    static void meth1 (Apple fruit) {
        System.out.println(fruit.getTotalCalories());
        System.out.println(fruit.toString());
    }

    interface Food {
        float getTotalCalories();

    }

    class  Fruit implements Food {

        public float getTotalCalories(){
            return 0.50f;
        }
    }

    class Apple extends Fruit {

        @Override
        public float getTotalCalories ( ) {
            return 0.40f;
        }
        public String getOrigin ( ) {
            return "someCity";
        }
    }




    static int max(int[] arr) {
        try {
            return (arr.length == 1) ? arr[0] : Integer.max(max(Arrays.copyOfRange(arr, 1, arr.length)), arr[0]);
        } catch (NumberFormatException e) {
            arr[0] = Integer.MAX_VALUE;
            return (arr.length == 1) ? arr[0] : Integer.max(max(Arrays.copyOfRange(arr, 1, arr.length)), arr[0]);
        }
    }


}













