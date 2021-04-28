package ToDoProject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        try {
            Scanner scanner = new Scanner((System.in)).useDelimiter("\n");
            IOStorage ioStorage = new IOStorage("D:\\ToDoList.txt");
            while (true) {
                ioStorage.showActualList();
                if (ioStorage.isEmpty()) ioStorage.newTask();
                else {
                    System.out.println("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");
                    String userInput = scanner.next();
                    if (userInput.equals("EXIT")) exit();
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
}
