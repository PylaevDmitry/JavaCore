package ToDoProject;

import ToDoProject.Models.Task;
import ToDoProject.Storages.FileStorage;
import ToDoProject.UserInterfaces.ConsoleUserInterface;

import java.io.IOException;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws IOException {
        var storage = new FileStorage("D:\\ToDoList.txt");
        var taskList = new TaskList(storage.getAll());
        var ui = new ConsoleUserInterface();

        try {
            while (true) {
                String userInput=getUserInput("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");
                if (userInput.equals("NEW")) taskList.newTask();
                else taskList.editTask(userInput);
            }
        } catch (IOException e) { System.out.println("Файл не найден"); }
    }


    static boolean commandInList (String userInput, String[] list) {
        return Arrays.asList(list).contains(userInput);
    }

    public static void exit ( ) {
        System.out.println("Спасибо за использование программы");
        System.exit(0);
    }

    static String getUserInput (String message) {
        var scanner = new Scanner((System.in)).useDelimiter("\n");
        System.out.println(message);
        String userInput = scanner.next();
        if (userInput.equals("EXIT")) exit();
        return userInput;
    }

}