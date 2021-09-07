package toDoProject.abstractions;

import toDoProject.models.Task;

public interface IStorage {
    Task[] getAll ();
    void add (Task data);
    void setStatus (long id, String status);
}
