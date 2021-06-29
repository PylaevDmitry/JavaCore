package toDoProject;

import toDoProject.models.Task;
import toDoProject.storages.FileStorage;
import toDoProject.userInterfaces.ConsoleUserInterface;
import toDoProject.userInterfaces.MobileUserInterface;
import java.time.temporal.ValueRange;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main (String[] args) {
        String[] invalidNameSymbols = new String[]{" ", "\\", "|", "/", ":", "?", "\"", "<", ">"};
        String[] commands = new String[] {"ARCH", "DONE", "WAIT", "BACK", "EXIT"};
        String[] tasksStates = new String[] {"ARCH", "DONE", "WAIT"};
        Map<String, String> env = System.getenv();

        var ui = new ConsoleUserInterface();
//        var ui = new MobileUserInterface();
//        var ui = new WindowUserInterface();
//        var ui = new TelegramBotUserInterface(env.get("BotToken"));
        String userInput = "";
        while (inputCheck(invalidNameSymbols, userInput)>=0) { userInput = ui.askInput("Введите имя или EXIT - выйти из программы"); }
//        var storage = new DBStorage(env.get("dbUserName"), env.get("dbUserPass"), userInput);
        var storage = new FileStorage("D:\\" + userInput + ".txt");

        while (true) {
            List<Task> list = Arrays.stream(storage.getAll()).filter(x -> !x.getStatus().equals("ARCH")).collect(Collectors.toList());
            IntStream.range(0, list.size()).forEach(i -> System.out.println(i+1 + " " + list.get(i)));

            if (list.size()==0 || userInput.equals("NEW")) {
                userInput = ui.askInput("Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы");
                if (!userInput.equals("BACK")) storage.add(new Task(userInput, new Date(), "WAIT"));
            }
            else {
                userInput = ui.askInput("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");
                int taskIndex = getIndex(userInput, list);
                if (taskIndex != -1) do {
                    userInput = ui.askInput("ARCH - убрать, DONE - пометить как выполненное, WAIT - возобновить задачу," +
                            "BACK - к списку заметок или EXIT - выйти из программы ");
                    if (inputCheck(tasksStates, userInput)>0) storage.setStatus(list.get(taskIndex-1).getId(),userInput);
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
