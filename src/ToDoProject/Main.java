package ToDoProject;

import ToDoProject.Models.Task;
import ToDoProject.Storages.FileStorage;
import ToDoProject.UserInterfaces.ConsoleUserInterface;

import java.io.IOException;
import java.time.temporal.ValueRange;
import java.util.*;

public class Main {
    public static void main (String[] args) throws IOException {
        try {
            var storage = new FileStorage("D:\\ToDoList.txt");
            final List<Task> taskList = new ArrayList<>();
            for (Task task: storage.getAll()) {
                if (task.toString().isEmpty()) continue;
                taskList.add(task);
            }
            var ui = new ConsoleUserInterface();
            while (true) {
                if (taskList.size()>0) String userInput = ui.askInput("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");
                if (userInput.equals("NEW")) {
                    userInput = ui.askInput("Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы");
                    if (!userInput.equals("BACK")) taskList.add(new Task(taskList.size() + 1, userInput, new Date(), "Wait"));
                }
                else {
                    int taskIndex = getIndex(userInput, taskList);
                    if (taskIndex != -1) do {
                        userInput = ui.askInput("DEL - удалить, DONE - пометить как выполненное, WAIT - возобновить задачу," +
                                " BACK - к списку заметок или EXIT - выйти из программы ");
                        if (userInput.equals("DONE")) taskList.get(taskIndex - 1).setStatus("Done");
                        if (userInput.equals("WAIT")) taskList.get(taskIndex - 1).setStatus("Wait");
                        if (userInput.equals("DEL")) taskList.remove(taskIndex - 1);
                    } while (!Main.commandInList(userInput, new String[]{"DEL", "DONE", "WAIT", "BACK", "EXIT"}));
                }
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



    static int getIndex (String userInput, List<Task> taskList) {
        try {
            int taskIndex = Integer.parseInt(userInput);
            if (ValueRange.of(1, taskList.size()).isValidIntValue(taskIndex)) return taskIndex;
            else return -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    boolean isEmpty () { return taskList.size()==0; }

}