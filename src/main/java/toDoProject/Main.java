package toDoProject;

import toDoProject.Models.Task;
import toDoProject.Storages.DBStorage;
import toDoProject.Storages.FileStorage;
import toDoProject.UserInterfaces.ConsoleUserInterface;
import toDoProject.UserInterfaces.TelegramBotUserInterface;
import toDoProject.UserInterfaces.WindowUserInterface;

import java.time.temporal.ValueRange;
import java.util.*;

public class Main {
    public static void main (String[] args) {
        Map<String, String> env = System.getenv();

        var ui = new ConsoleUserInterface();
//        var ui = new WindowUserInterface();
//        var ui = new TelegramBotUserInterface(env.get("BotToken"));
        String userInput = "";
        while (userInput.length()==0 || Arrays.stream(new String[]{"\\", "|", "/", ":", "?", "\"", "<", ">"}).anyMatch(userInput::contains)) {
            userInput = ui.askInput("Введите имя пользователя");
        }

        var storage = new DBStorage(env.get("dbUserName"), env.get("dbUserPass"), userInput);
//        var storage = new FileStorage("D:\\" + userInput + ".txt");

        while (true) {
            Task[] arrTask = storage.getAll();
            Map<Integer, Task> map = new LinkedHashMap<>();
            var countToShow = 0;
            for (Task task : arrTask) {
                if (!task.getStatus().equals("ARCH")) {
                    map.put(countToShow + 1, task);
                    ui.show(countToShow + 1 + " " + task.toString());
                    countToShow += 1;
                }
            }

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

    public static void storageErrorPrint ( ) { System.out.println("Ошибка доступа к данным"); }
}
