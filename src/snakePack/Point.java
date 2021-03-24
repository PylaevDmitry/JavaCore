package snakePack;

public class Point {
    public  int x;
    public  int y;
    public char symbol;

    public Point (int x, int y, char symbol) {
        this.x=x;
        this.y=y;
        this.symbol=symbol;
    }

    public Point(Point p) {
        this.x=p.x;
        this.y=p.y;
        this.symbol=p.symbol;
    }

    public void move (int offset, Direction direction) {
        if (direction==Direction.left) x--;
        if (direction==Direction.right) x++;
        if (direction==Direction.up) y++;
        if (direction==Direction.down) y--;
    }

    public boolean isHit (Point p) {
        return this.x==p.x||this.y==p.y;
    }
}
