package ToDoProject;

import ToDoProject.Models.Task;

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

public class IOStorage {
    private final List<Task> taskList = new ArrayList<>();

    public IOStorage (String path) throws IOException, InvalidPathException {
       for (String str:Files.readAllLines(Paths.get(path))) {
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

//    void actualizeList () {
//       for (int i = 0; i < taskList.size(); i++) {
//           taskList.get(i).setId(i+1);
//       }
//    }

    void editTask (String userInput) {
        int taskIndex = this.getIndex(userInput);
        if (taskIndex != -1) do {
                userInput = Main.getUserInput("DEL - удалить, DONE - пометить как выполненное, WAIT - возобновить задачу," +
                        " BACK - к списку заметок или EXIT - выйти из программы ");
                if (userInput.equals("DONE")) taskList.get(taskIndex - 1).setStatus("Done");
                if (userInput.equals("WAIT")) taskList.get(taskIndex - 1).setStatus("Wait");
                if (userInput.equals("DEL")) taskList.remove(taskIndex - 1);
        } while (!Main.commandInList(userInput, new String[]{"DEL", "DONE", "WAIT", "BACK", "EXIT"}));
    }

    void newTask () {
        String userInput = Main.getUserInput("Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы");
        if (!userInput.equals("BACK")) taskList.add(new Task(taskList.size() + 1, userInput, new Date(), "Wait"));
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
