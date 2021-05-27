package ToDoProject.Storages;

import ToDoProject.Abstractions.IStorage;
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

    public FileStorage (String path) throws IOException {
        this.path=path;
        if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
    }

    @Override
    public Task[] getAll () throws IOException {
        var result = new ArrayList<Task>();
        for (String task:Files.readAllLines(Paths.get(this.path))) {
            if (task.isEmpty()) continue;
            result.add(new Task(task));
        }
        if (result.size()==0) return new Task[0];
        else {
            Task[] resArr = new Task[result.size()];
            for (int i = 0; i < result.size(); i++) { resArr[i]= result.get(i); }
            return resArr;
        }
    }

    @Override
    public void add (Task task) throws IOException {
        Files.write(Paths.get(path), (task.toString()+task.getId()+"\n").getBytes(), StandardOpenOption.APPEND);
    }

    @Override
    public void delete (long index) throws IOException {
        var tasks = getAll();
        PrintStream printStream = new PrintStream(new FileOutputStream(path));
        for (var task : tasks) {
            if(task.getId() == index) continue;
            printStream.println(task);
        }
    }

    @Override
    public void setStatus (long id, String status) throws IOException {
        var tasks = getAll();
        PrintStream printStream = new PrintStream(new FileOutputStream(path));
        for (var task : tasks) {
            if(task.getId() == id) task.setStatus(status);
            printStream.println(task);
        }
    }
}
