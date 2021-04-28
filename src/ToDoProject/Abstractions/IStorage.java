package ToDoProject.Abstractions;

import ToDoProject.Models.Task;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStorage {
    Task[] GetAll() throws IOException;
    void Add(Task data) throws FileNotFoundException;
    void Delete(int id) throws IOException;
}
