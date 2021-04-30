package ToDoProject.Abstractions;

import ToDoProject.Models.Task;

import java.io.IOException;

public interface IStorage {
    Task[] getAll () throws IOException;
    void add (Task data) throws IOException;
    void delete (int id) throws IOException;
    void setStatus (int id, String status) throws IOException;
}
