package ToDoProject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class IOStorage {
    String path;
    private final List<Task> taskList = new ArrayList<>();
    Scanner scanner = new Scanner((System.in)).useDelimiter("\n");

    public IOStorage (String path) throws IOException, InvalidPathException {
        this.path=path;
        if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
        else for (String str:Files.readAllLines(Paths.get(path))) {
            if (str.isEmpty()) continue;
            taskList.add(new Task(str));
        }
    }

    public void writeFile() throws FileNotFoundException {
        PrintStream printStream = new PrintStream(new FileOutputStream(path));
        for (Task str : taskList) {
            printStream.println(str);
        }
    }

    void showActualList () {
       for (int i = 0; i < taskList.size(); i++) {
           taskList.get(i).setIndex(i+1);
           System.out.println(taskList.get(i).toString());
       }
    }

    void editTask (String userInput) {
        int taskIndex = this.getIndex(userInput);
        if (taskIndex != -1) do {
                System.out.println("DEL - удалить, DONE - пометить как выполненное, WAIT - возобновить задачу," +
                        " BACK - к списку заметок или EXIT - выйти из программы ");
                userInput = scanner.next();
                if (userInput.equals("EXIT")) Main.exit();
                if (ValueRange.of(1, taskList.size() + 1).isValidIntValue(taskIndex)) {
                    if (userInput.equals("DONE")) taskList.get(taskIndex - 1).setStatus("Done");
                    if (userInput.equals("WAIT")) taskList.get(taskIndex - 1).setStatus("Wait");
                    if (userInput.equals("DEL")) taskList.remove(taskIndex - 1);
                }
        } while (!Main.commandInList(userInput, new String[]{"DEL", "DONE", "WAIT", "BACK", "EXIT"}));
    }

    void newTask () {
        System.out.println("Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы");
        String userInput = scanner.next();
        if (userInput.equals("EXIT")) Main.exit();
        if (!(userInput.equals("BACK")) && !(userInput.equals("EXIT"))) taskList.add(new Task(taskList.size() + 1, userInput, new Date(), "Wait"));
    }

    int getIndex (String userInput) {
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
