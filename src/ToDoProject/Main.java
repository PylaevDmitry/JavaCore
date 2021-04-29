package ToDoProject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        try {
            IOStorage ioStorage = new IOStorage("D:\\ToDoList.txt");
            while (true) {
                ioStorage.showActualList();
                if (ioStorage.isEmpty()) ioStorage.newTask();
                else {
                    String userInput=getUserInput("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");
                    if (userInput.equals("NEW")) ioStorage.newTask();
                    else ioStorage.editTask(userInput);
                }
                ioStorage.writeFile();
            }
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
    }

    static boolean commandInList (String userInput, String[] list) {
        return Arrays.asList(list).contains(userInput);
    }

    static void exit() {
        System.out.println("Спасибо за использование программы");
        System.exit(0);
    }

    static String getUserInput (String message) {
        Scanner scanner = new Scanner((System.in)).useDelimiter("\n");
        System.out.println(message);
        String userInput = scanner.next();
        if (userInput.equals("EXIT")) exit();
        return userInput;
    }
}
