package ToDoProject;

import ToDoProject.Models.Task;
import ToDoProject.Storages.FileStorage;
import ToDoProject.UserInterfaces.ConsoleUserInterface;
import java.io.IOException;
import java.time.temporal.ValueRange;
import java.util.*;

public class Main {
    public static void main (String[] args) {
        try {
            var storage = new FileStorage("D:\\ToDoList.txt");
            var ui = new ConsoleUserInterface();
            while (true) {
                for (Task task:storage.getAll()) { ui.show(task.toString()); }
                String userInput;
                if (storage.getAll().length==0) {
                    userInput = ui.askInput("Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы");
                    if (!userInput.equals("BACK")) storage.add(new Task(storage.getAll().length + 1, userInput, new Date(), "Wait"));
                }
                else {
                    userInput = ui.askInput("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");
                    if (userInput.equals("NEW")) {
                        userInput = ui.askInput("Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы");
                        if (!userInput.equals("BACK")) storage.add(new Task(storage.getAll().length + 1, userInput, new Date(), "Wait"));
                    } else {
                        int taskIndex = getIndex(userInput, storage.getAll());
                        if (taskIndex != -1) do {
                            userInput = ui.askInput("DEL - удалить, DONE - пометить как выполненное, WAIT - возобновить задачу," +
                                    " BACK - к списку заметок или EXIT - выйти из программы ");
                            if (userInput.equals("DONE")) storage.setStatus(taskIndex,"Done");
                            if (userInput.equals("WAIT")) storage.setStatus(taskIndex,"Wait");
                            if (userInput.equals("DEL")) storage.delete(taskIndex);
                        } while (!Arrays.asList(new String[]{"DEL", "DONE", "WAIT", "BACK", "EXIT"}).contains(userInput));
                    }
                }
            }
        } catch (IOException e) { System.out.println("Файл не найден"); }
    }

    public static void exit ( ) {
        System.out.println("Спасибо за использование программы");
        System.exit(0);
    }

    static int getIndex (String userInput, Task[] tasks) {
        try {
            int taskIndex = Integer.parseInt(userInput);
            if (ValueRange.of(1, tasks.length).isValidIntValue(taskIndex)) return taskIndex;
            else return -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}