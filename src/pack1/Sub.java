package pack1;
import pack2.*;

public class Sub extends Base {
    public static int newS;
    public String par99="99";

    static {
        newS = 55;
    }
    public int par1=44;

    Sub(String a, int b) {
        super(a);
        this.b=b;
    }

    Sub(String a) {
        super(a);
    }

    Sub() {
        par1=3;
    }

    {
        System.out.println("Sub is here");
    }

    Integer b;

    @Override
    public void draw (String b) {
        this.b=Integer.parseInt(b)+1;
        System.out.println(b);
        System.out.println(par99);
    }

    @Override
    public void mettt () {
        System.out.println("meth22");
    }

    public void meth3 (String[] args) {
        this.mettt();
        super.mettt();
    }

}
