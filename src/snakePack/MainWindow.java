package snakePack;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Змейка");
        setDefaultCloseOperation((WindowConstants.EXIT_ON_CLOSE));
        setSize(320, 345);
        setLocation(400, 400);
        setVisible(true);
        add(new GameField());
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();

    }
}
