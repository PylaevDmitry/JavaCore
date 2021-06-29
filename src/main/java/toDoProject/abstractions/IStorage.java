package toDoProject.abstractions;

import toDoProject.models.Task;

public interface IStorage {
    Task[] getAll ();
    void add (Task data);
    void remove (long id);
    void setStatus (long id, String status);
}
