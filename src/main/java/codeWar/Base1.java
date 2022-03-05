package codeWar;

public class Base1 <T> implements BaseInterface2 {
    T t;
    protected String s = "1";
    public Base1() {
        System.out.println("Base1 Here" + str);
    }
    public void meth1 ( ) {
        System.out.println("Base"+1);
    }
}
