package toDoProject;

import toDoProject.Models.Task;
import toDoProject.Storages.FileStorage;
import toDoProject.UserInterfaces.ConsoleUserInterface;

import java.time.temporal.ValueRange;
import java.util.*;

public class Main {
    public static void main (String[] args) {
        Map<String, String> env = System.getenv();

//        var storage = new DBStorage(env.get("dbUserName"), env.get("dbUserPass"));
        var storage = new FileStorage(env.get("StorageFilePath"));
        var ui = new ConsoleUserInterface();
//        var ui = new WindowUserInterface();
//        var ui = new TelegramBotUserInterface(env.get("BotToken"));

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
                if (!userInput.equals("BACK")) storage.add(new Task(userInput, new Date(), "Wait"));
            }
            else {
                userInput = ui.askInput("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");
                if (userInput.equals("NEW")) {
                    userInput = ui.askInput("Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы");
                    if (!userInput.equals("BACK")) storage.add(new Task(userInput, new Date(), "Wait"));
                } else {
                    int taskIndex = getIndex(userInput, arrTask);
                    if (taskIndex != -1) do {
                        userInput = ui.askInput("DEL - удалить, DONE - пометить как выполненное, WAIT - возобновить задачу," +
                                    " BACK - к списку заметок или EXIT - выйти из программы ");
                        if (userInput.equals("DONE")) storage.setStatus(map.get(taskIndex).getId(),"Done");
                        if (userInput.equals("WAIT")) storage.setStatus(map.get(taskIndex).getId(),"Wait");
                        if (userInput.equals("DEL")) storage.setStatus(map.get(taskIndex).getId(),"ARCH");
                    } while (!Arrays.asList(new String[] {"DEL", "DONE", "WAIT", "BACK", "EXIT"}).contains(userInput));
                }
            }
        }
    }

    static int getIndex (String userInput, Task[] tasks) {
        try {
            int taskIndex = Integer.parseInt(userInput);
            if (ValueRange.of(1, tasks.length).isValidIntValue(taskIndex)) return taskIndex;
            return -1;
        } catch (NumberFormatException e) { return -1; }
    }

    public static void storageErrorPrint ( ) { System.out.println("Файл не найден"); }
}
