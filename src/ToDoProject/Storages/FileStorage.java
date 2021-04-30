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
        if (!Files.exists(Paths.get(path)))
            Files.createFile(Paths.get(path));
    }

    @Override
    public Task[] getAll () throws IOException {
        var result = new ArrayList<Task>();
        for (String task:Files.readAllLines(Paths.get(this.path))) {
            if (task.isEmpty()) continue;
            result.add(new Task(task));
        }
        return (result.size()!=0)?(Task[]) result.toArray():new Task[0];
    }

    @Override
    public void add (Task task) throws IOException {
        Files.write(Paths.get(path), (task.toString()+"\n").getBytes(), StandardOpenOption.APPEND);
    }

    @Override
    public void delete (int id) throws IOException {
        var tasks = getAll();
        int actualId=0;
        PrintStream printStream = new PrintStream(new FileOutputStream(path));
        for (var task : tasks) {
            if(task.getId() == id)
                continue;
            actualId++;
            task.setId(actualId);
            printStream.println(task);
        }
    }

    @Override
    public void setStatus (int id, String status) throws IOException {
        var tasks = getAll();
        int actualId=0;
        PrintStream printStream = new PrintStream(new FileOutputStream(path));
        for (var task : tasks) {
            if(task.getId() == id)
                task.setStatus(status);
            actualId++;
            task.setId(actualId);
            printStream.println(task);
        }
    }
}
