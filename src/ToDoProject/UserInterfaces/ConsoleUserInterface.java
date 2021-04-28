package ToDoProject.UserInterfaces;

import ToDoProject.Abstractions.IUserInterface;

import java.util.Scanner;

public class ConsoleUserInterface implements IUserInterface {

    private final Scanner _scanner;

    public ConsoleUserInterface ( ) {
        _scanner = new Scanner((System.in)).useDelimiter("\n");
    }

    @Override
    public String AskInput ( ) {
        return _scanner.next();
    }

    @Override
    public void Show (String message) {
        System.out.println(message);
    }
}
