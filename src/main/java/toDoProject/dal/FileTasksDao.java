package toDoProject.dal;

import toDoProject.abstractions.IStorage;
import toDoProject.Main;
import toDoProject.models.Task;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

// TODO: Синхронизировать операции редактирования файла
public class FileTasksDao implements IStorage {
    private final String path;
    long lastIndex = 0;

    public FileTasksDao (String path) {
        this.path = path;
        try {
            if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
        } catch (IOException e) { Main.storageErrorPrint(); }
    }

//    @Override
//    public Task[] getAll () {
//        var result = new ArrayList<Task>();
//        try {
//            for (String str:Files.readAllLines(Paths.get(this.path))) {
//                if (str.isEmpty()) continue;
//                result.add(new Task(str));
//            }
//            lastIndex = result.size();
//        } catch (IOException e) { Main.storageErrorPrint(); }
//        return (result.size()==0)?new Task[0]:result.toArray(Task[]::new);
//    }
//
//    @Override
//    public void add (Task task) {
//        try {
//            Files.write(Paths.get(path), (lastIndex+1 + " " + task.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
//        } catch (IOException e) { Main.storageErrorPrint(); }
//    }
//    @Override
//    public void setStatus (long id, String status) {
//        var tasks = getAll();
//        try (PrintWriter printWriter = new PrintWriter(path)) {
//            for (var task : tasks) {
//                if (task.getId() == id) task.setStatus(status);
//                printWriter.println(task.getId() + " " + task);
//            }
//        } catch (IOException e) { Main.storageErrorPrint(); }
//    }

    @Override
    public Task[] getAll () {
        var result = new ArrayList<Task>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(path)))) {
            while (true) {result.add((Task) objectInputStream.readObject());}
        } catch (EOFException e) {lastIndex = result.size();}
        catch (IOException | ClassNotFoundException e) { Main.storageErrorPrint(); }
        return (result.size()==0)?new Task[0]:result.toArray(Task[]::new);
    }

    @Override
    public void add (Task task) {
        task.setId(lastIndex+1);
        var tasks = getAll();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)))) {
            for (Task item:tasks) {objectOutputStream.writeObject(item);}
            objectOutputStream.writeObject(task);
        } catch (IOException e) { Main.storageErrorPrint(); }
    }

    @Override
    public void setStatus (long id, String status) {
        var tasks = getAll();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)))) {
            for (var task : tasks) {
                if (task.getId() == id) task.setStatus(status);
                objectOutputStream.writeObject(task);
            }
        } catch (IOException e) { Main.storageErrorPrint(); }
    }
}
