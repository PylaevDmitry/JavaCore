package toDoProject.UserInterfaces;

import toDoProject.Abstractions.IUserInterface;
import java.util.Scanner;

public class ConsoleUserInterface implements IUserInterface {

    private final Scanner _scanner;

    public ConsoleUserInterface ( ) {
        _scanner = new Scanner((System.in)).useDelimiter("\n");
    }

    @Override
    public String askInput (String message) {
        System.out.println(message);
        var userInput = _scanner.next();
        if (userInput.equals("EXIT")) {
            System.out.println("Выход из программы...");
            System.exit(0);
        }
        return userInput;
    }

    @Override
    public void show (String message) {
        System.out.println(message);
    }
}
