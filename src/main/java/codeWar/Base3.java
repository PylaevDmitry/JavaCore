package codeWar;

public class Base3 extends Base2 {
    Base1 base1 = new Base1();
    @Override
    public void meth1 ( ) {
        s = "3";
        System.out.println("Base"+s + t.toString());
    }

    @Override
    void meth2 ( ) {
        super.meth1();
    }
}
