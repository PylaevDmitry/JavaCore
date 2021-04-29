package ToDoProject.Abstractions;

import ToDoProject.Models.Task;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStorage {
    Task[] getAll () throws IOException;
    void add (Task data) throws FileNotFoundException;
    void delete (int id) throws IOException;
}
