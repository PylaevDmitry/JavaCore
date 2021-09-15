package toDoProject.dal;

import toDoProject.abstractions.IStorage;
import toDoProject.ToDoMain;
import toDoProject.models.Task;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

// TODO: Синхронизировать операции редактирования файла
public class FileTasksDao implements IStorage {
    private final String path;
    private String owner;
    private long lastIndex = 0;

    public void setOwner (String owner) {
        this.owner = owner;
    }

    public FileTasksDao (String path) {
        this.path = path;
        try {
            if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
        } catch (IOException e) {
            System.out.println(ToDoMain.properties.getPropertyContent("storageError"));
        }
    }

    @Override
    public synchronized Task[] getAll () {
        var result = new ArrayList<Task>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(path)))) {
            while (true) {
                var task = (Task) objectInputStream.readObject();
                if (task.getOwner().equals(owner)) {
                    result.add(task);
                }
            }
        } catch (EOFException e) {
            lastIndex = result.size();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println(ToDoMain.properties.getPropertyContent("storageError"));
        }
        return (result.size()==0)?new Task[0]:result.toArray(Task[]::new);
    }

    @Override
    public synchronized void add (Task task) {
        task.setId(lastIndex+1);
        var tasks = getAll();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)))) {
            for (Task item:tasks) {objectOutputStream.writeObject(item);}
            objectOutputStream.writeObject(task);
        } catch (IOException e) {
            System.out.println(ToDoMain.properties.getPropertyContent("storageError"));
        }
    }

    @Override
    public synchronized void setStatus (long id, String status) {
        var tasks = getAll();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)))) {
            for (var task : tasks) {
                if (task.getId() == id) task.setStatus(status);
                objectOutputStream.writeObject(task);
            }
        } catch (IOException e) {
            System.out.println(ToDoMain.properties.getPropertyContent("storageError"));
        }
    }
}
