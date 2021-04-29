package ToDoProject;

import ToDoProject.Models.Task_;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;

public class Storage {
    String path;
    private final List<Task_> taskList = new ArrayList<>();

    public Storage (String path) throws InvalidPathException, IOException {
        this.path=path;
        if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
        else for (String task:Files.readAllLines(Paths.get(path))) {
            if (task.isEmpty()) continue;
            taskList.add(new Task_(task));
        }
    }

    public void showActualList () {
        for (int i = 0; i < taskList.size(); i++) {
            taskList.get(i).setIndex(i+1);
            System.out.println(taskList.get(i).toString());
        }
    }

    public void writeToFile () throws FileNotFoundException {
        var printStream = new PrintStream(new FileOutputStream(path));
        for (Task_ str : taskList) {
            printStream.println(str);
        }
        printStream.close();
    }
}
