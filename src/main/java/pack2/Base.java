package pack2;

public class Base implements interface1 {

    public static int par1 =1;
    public int par99=0;
    protected int par100=100;
    private String a;

    protected Base (String a) {
        this.a=a;
    }

    public Base() {
    }

    public  Base (int par1, int par99) {
        this.par1 = par1;
        this.par99 = par99;
    }


    {
        System.out.println("pack2.Base is here");
    }
    static {
        System.out.println("pack2.Base is here1");
    }

    public void draw (String a) {
        System.out.println(a);
    }

    public void meth1 (int t) {
        System.out.print(t);
        stat();
    }
    public static void st () {
        System.out.println("base static");
    }

    public void mettt () {
        System.out.println("meth2");
    }

    public static void stat () {
        System.out.println("epp");
    }

}
