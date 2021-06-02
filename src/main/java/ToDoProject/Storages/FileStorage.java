package ToDoProject.Storages;

import ToDoProject.Abstractions.IStorage;
import ToDoProject.Main;
import ToDoProject.Models.Task;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class FileStorage implements IStorage {
    private final String path;

    public FileStorage (String path) {
        this.path = path;
        try {
            if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
        } catch (IOException e) { Main.storageErrorPrint(); }
    }

    @Override
    public Task[] getAll () {
        var result = new ArrayList<Task>();
        try {
            for (String str:Files.readAllLines(Paths.get(this.path))) {
                if (str.isEmpty()) continue;
                Task task = new Task(str);
                if (!task.getStatus().equals("ARCHIVED")) result.add(new Task(str));
            }
        } catch (IOException e) { Main.storageErrorPrint(); }
        if (result.size()==0) return new Task[0];
        else {
            Task[] resArr = new Task[result.size()];
            for (int i = 0; i < result.size(); i++) { resArr[i]= result.get(i); }
            return resArr;
        }
    }

    @Override
    public void add (Task task) {
        try {
            Files.write(Paths.get(path), (task.getId() + " " + task.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) { Main.storageErrorPrint(); }
    }

    @Override
    public void remove (long index) {
        var tasks = getAll();
        try (PrintStream printStream = new PrintStream(new FileOutputStream(path))) {
            for (var task : tasks) {
                if (task.getId() == index) continue;
                printStream.println(task.getId() + " " + task);
            }
        } catch (IOException e) { Main.storageErrorPrint(); }
    }

    @Override
    public void setStatus (long id, String status) {
        var tasks = getAll();
        try (PrintStream printStream = new PrintStream(new FileOutputStream(path))) {
            for (var task : tasks) {
                if (task.getId() == id) task.setStatus(status);
                printStream.println(task.getId() + " " + task);
            }
        } catch (IOException e) { Main.storageErrorPrint(); }
    }
}
