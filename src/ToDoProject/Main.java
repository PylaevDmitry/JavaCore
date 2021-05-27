package ToDoProject;

import ToDoProject.Models.Task;
import ToDoProject.Storages.DBStorage;
import ToDoProject.UserInterfaces.ConsoleUserInterface;

import java.io.IOException;
import java.time.temporal.ValueRange;
import java.util.*;

public class Main {
    public static void main (String[] args) {
        try {
            var storage = new DBStorage("localhost", "5432", "postgres", "12345", "todo", "org.postgresql.Driver");
            var ui = new ConsoleUserInterface();
            while (true) {
                Task[] arrTask = storage.getAll();
                Map<Integer, Task> map = new LinkedHashMap<>();
                for (int i = 0; i < arrTask.length; i++) {
                    map.put(i+1, arrTask[i]);
                    ui.show(i+1 + " " + arrTask[i].toString());
                }
                String userInput;
                if (arrTask.length==0) {
                    userInput = ui.askInput("Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы");
                    if (!userInput.equals("BACK")) storage.add(new Task(generateContactId(), userInput, new Date(), "Wait"));
                }
                else {
                    userInput = ui.askInput("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");
                    if (userInput.equals("NEW")) {
                        userInput = ui.askInput("Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы");
                        if (!userInput.equals("BACK")) storage.add(new Task(generateContactId(), userInput, new Date(), "Wait"));
                    } else {
                        int taskIndex = getIndex(userInput, arrTask);
                        if (taskIndex != -1) do {
                            userInput = ui.askInput("DEL - удалить, DONE - пометить как выполненное, WAIT - возобновить задачу," +
                                    " BACK - к списку заметок или EXIT - выйти из программы ");
                            if (userInput.equals("DONE")) storage.setStatus(map.get(taskIndex).getId(),"Done");
                            if (userInput.equals("WAIT")) storage.setStatus(map.get(taskIndex).getId(),"Wait");
                            if (userInput.equals("DEL")) storage.delete(map.get(taskIndex).getId());
                        } while (!Arrays.asList(new String[]{"DEL", "DONE", "WAIT", "BACK", "EXIT"}).contains(userInput));
                    }
                }
            }
        } catch (IOException e) { System.out.println("Файл не найден"); }
    }

    static int getIndex (String userInput, Task[] tasks) {
        try {
            int taskIndex = Integer.parseInt(userInput);
            if (ValueRange.of(1, tasks.length).isValidIntValue(taskIndex)) return taskIndex;
            return -1;
        } catch (NumberFormatException e) { return -1; }
    }

    static long generateContactId() { return Math.round(Math.random() * 1000 + System.currentTimeMillis()); }
}