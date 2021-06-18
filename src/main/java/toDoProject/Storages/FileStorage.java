package toDoProject.Storages;

import toDoProject.Abstractions.IStorage;
import toDoProject.Main;
import toDoProject.Models.Task;
import java.io.IOException;
import java.io.PrintWriter;
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
                if (!task.getStatus().equals("ARCH")) result.add(new Task(str));
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
        try (PrintWriter printWriter = new PrintWriter(path)) {
            for (var task : tasks) {
                if (task.getId() == index) continue;
                printWriter.println(task.getId() + " " + task);
            }
        } catch (IOException e) { Main.storageErrorPrint(); }
    }

    @Override
    public void setStatus (long id, String status) {
        var tasks = getAll();
        try (PrintWriter printWriter = new PrintWriter(path)) {
            for (var task : tasks) {
                if (task.getId() == id) task.setStatus(status);
                printWriter.println(task.getId() + " " + task);
            }
        } catch (IOException e) { Main.storageErrorPrint(); }
    }
}
