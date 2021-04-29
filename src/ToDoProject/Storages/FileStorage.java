package ToDoProject.Storages;

import ToDoProject.Abstractions.IStorage;
import ToDoProject.Models.Task_;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileStorage implements IStorage {
    private final String path;

    public FileStorage (String path) throws IOException {
        if (!Files.exists(Paths.get(path)))
            Files.createFile(Paths.get(path));
        this.path=path;
    }

    @Override
    public Task_[] getAll () throws IOException {
        var result = new ArrayList<Task_>();
        for (String task:Files.readAllLines(Paths.get(this.path))) {
            if (task.isEmpty()) continue;
            result.add(new Task_(task));
        }
        return (Task_[]) result.toArray();
    }

    @Override
    public void add (Task_ data) throws FileNotFoundException {
        var printStream = new PrintStream(new FileOutputStream(path));
        printStream.println(data);
        printStream.close();
    }

    @Override
    public void delete (int id) throws IOException {
        var tasks = getAll();
        for (var task : tasks) {
            if(task.getId() == id)
                continue;

            add(task);
        }
    }


}
