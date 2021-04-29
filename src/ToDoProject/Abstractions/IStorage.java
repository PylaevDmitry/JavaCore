package ToDoProject.Abstractions;

import ToDoProject.Models.Task_;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStorage {
    Task_[] getAll () throws IOException;
    void add (Task_ data) throws FileNotFoundException;
    void delete (int id) throws IOException;
}
