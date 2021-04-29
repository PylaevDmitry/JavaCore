package ToDoProject.UserInterfaces;

import ToDoProject.Abstractions.IUserInterface;
import ToDoProject.Main;
import ToDoProject.Storage;

import java.util.Scanner;

public class ConsoleUserInterface implements IUserInterface {

    private final Scanner _scanner;

    public ConsoleUserInterface ( ) {
        _scanner = new Scanner((System.in)).useDelimiter("\n");
    }

    @Override
    public String AskInput (String message) {
        System.out.println(message);
        var userInput = _scanner.next();
        if (userInput.equals("EXIT")) Main.exit();
        return userInput;
    }

    @Override
    public void Show (String message) {
        System.out.println(message);
    }
}
