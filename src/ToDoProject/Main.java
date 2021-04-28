package ToDoProject;

import ToDoProject.Models.Task;
import ToDoProject.UserInterfaces.ConsoleUserInterface;

import java.io.IOException;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws IOException {
        var storage = new Storage("D:\\ToDoList.txt");
        var ui = new ConsoleUserInterface();

        try {
            Storage storage = new Storage("D:\\ToDoList.txt");
            while (true) {
                storage.showActualList();
                userEdit();
                storage.writeToFile();
            }
        } catch (IOException e) { System.out.println("Файл не найден"); }
    }

    public void userEdit() {
        if (this.isEmpty()) this.newTask();
        else {
            String userInput = getUserInput("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");
            if (userInput.equals("NEW")) this.newTask();
            else this.editTask(userInput);
        }
    }

    private void newTask () {
        var userInput = getUserInput("Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы");
        if (!(userInput.equals("BACK"))) taskList.add(new Task(taskList.size() + 1, userInput, new Date(), "Wait"));
    }

    private void editTask (String userInput) {
        var taskIndex = this.getIndex(userInput);
        if (taskIndex != -1) do {
            userInput = getUserInput("DEL - удалить, DONE - пометить как выполненное, WAIT - возобновить задачу," +
                    " BACK - к списку заметок или EXIT - выйти из программы ");
            if (userInput.equals("DONE")) taskList.get(taskIndex - 1).setStatus("Done");
            if (userInput.equals("WAIT")) taskList.get(taskIndex - 1).setStatus("Wait");
            if (userInput.equals("DEL")) taskList.remove(taskIndex - 1);
        } while (!commandInList(userInput, new String[]{"DEL", "DONE", "WAIT", "BACK"}));
    }

    private boolean commandInList (String userInput, String[] list) {
        return Arrays.asList(list).contains(userInput);
    }

    private void exit() {
        System.out.println("Спасибо за использование программы");
        System.exit(0);
    }

    private String getUserInput (String message) {
        var scanner = new Scanner((System.in)).useDelimiter("\n");
        System.out.println(message);
        String userInput = scanner.next();
        if (userInput.equals("EXIT")) exit();
        return userInput;
    }

    private int getIndex (String userInput) {
        try {
            int taskIndex = Integer.parseInt(userInput);
            if (ValueRange.of(1, taskList.size()).isValidIntValue(taskIndex)) return taskIndex;
            else return -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private boolean isEmpty () { return taskList.size()==0; }
}