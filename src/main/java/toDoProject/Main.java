package toDoProject;

import toDoProject.Models.Task;
import toDoProject.Storages.FileStorage;
import toDoProject.UserInterfaces.ConsoleUserInterface;
import java.time.temporal.ValueRange;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    public static void main (String[] args) {
        String[] invalidNameSymbols = new String[]{" ", "\\", "|", "/", ":", "?", "\"", "<", ">"};
        String[] commands = new String[] {"ARCH", "DONE", "WAIT", "BACK", "EXIT"};
        String[] tasksStates = new String[] {"ARCH", "DONE", "WAIT"};
        String userMenuString = "Введите имя или EXIT - выйти из программы";
        String mainMenuString = "Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы";
        String newMenuString = "Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы";
        String editMenuString = "ARCH - убрать, DONE - пометить как выполненное, WAIT - возобновить задачу, BACK - к списку заметок или EXIT - выйти из программы ";

        Map<String, String> env = System.getenv();
        String userInput = "";
        var ui = new ConsoleUserInterface();
//        var ui = new WindowUserInterface();
//        var ui = new TelegramBotUserInterface(env.get("BotToken"));
        while (inputCheck(invalidNameSymbols, userInput)>=0) { userInput = ui.askInput(userMenuString); }
//        var storage = new DBStorage(env.get("dbUserName"), env.get("dbUserPass"), userInput);
        var storage = new FileStorage("D:\\" + userInput + ".txt");

        while (true) {
//            Map<Integer, Task> map = new LinkedHashMap<>();
            AtomicInteger countToShow = new AtomicInteger(1);
            List<Task> list = Arrays.stream(storage.getAll()).filter(task -> !task.getStatus().equals("ARCH")).collect(Collectors.toList());
            list.forEach(x -> System.out.println(countToShow.getAndIncrement() + " " + x));
//            for (Task task : storage.getAll()) {
//                if (!task.getStatus().equals("ARCH")) {
//                    map.put(countToShow.get() + 1, task);
//                    ui.show(countToShow.get() + 1 + " " + task.toString());
//                    countToShow.addAndGet(1);
//                }
//            }

            if (list.size()==0 || userInput.equals("NEW")) {
                userInput = ui.askInput(newMenuString);
                if (!userInput.equals("BACK")) storage.add(new Task(userInput, new Date(), "WAIT"));
            }

            else {
                userInput = ui.askInput(mainMenuString);
                int taskIndex = getIndex(userInput, list);
                if (taskIndex != -1) do {
                    userInput = ui.askInput(editMenuString);
                    if (inputCheck(tasksStates, userInput)>0) storage.setStatus(list.get(taskIndex).getId(),userInput);
                } while (inputCheck(commands, userInput)<=0);
            }
        }
    }

    static int getIndex (String userInput, List <Task> list) {
        try {
            int taskIndex = Integer.parseInt(userInput);
            return  (ValueRange.of(1, list.size()).isValidIntValue(taskIndex))?taskIndex:-1;
        } catch (NumberFormatException e) { return -1; }
    }

    public static void storageErrorPrint ( ) { System.out.println("Ошибка доступа к данным"); }

    public static int inputCheck (String[] arrString, String userInput) {
        boolean anyMatch = Arrays.stream(arrString).anyMatch(userInput::contains);
        if (userInput.length() > 0) return (anyMatch)?1:-1;
        else return 0;
    }

}
