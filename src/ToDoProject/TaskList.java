package ToDoProject;

import ToDoProject.Models.Task;
import java.nio.file.InvalidPathException;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskList {
    private final List<Task> taskList = new ArrayList<>();

    public TaskList (Task[] tasks) throws InvalidPathException {
        for (Task task: tasks) {
            if (task.toString().isEmpty()) continue;
            taskList.add(task);
        }
    }

    void editTask (String userInput) {
        int taskIndex = this.getIndex(userInput);
        if (taskIndex != -1) do {
            userInput = Main.getUserInput("DEL - удалить, DONE - пометить как выполненное, WAIT - возобновить задачу," +
                    " BACK - к списку заметок или EXIT - выйти из программы ");
            if (userInput.equals("DONE")) taskList.get(taskIndex - 1).setStatus("Done");
            if (userInput.equals("WAIT")) taskList.get(taskIndex - 1).setStatus("Wait");
            if (userInput.equals("DEL")) taskList.remove(taskIndex - 1);
        } while (!Main.commandInList(userInput, new String[]{"DEL", "DONE", "WAIT", "BACK", "EXIT"}));
    }

    void newTask () {
        String userInput = Main.getUserInput("Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы");
        if (!userInput.equals("BACK")) taskList.add(new Task(taskList.size() + 1, userInput, new Date(), "Wait"));
    }

    int getIndex (String userInput) {
        try {
            int taskIndex = Integer.parseInt(userInput);
            if (ValueRange.of(1, taskList.size()).isValidIntValue(taskIndex)) return taskIndex;
            else return -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    boolean isEmpty () { return taskList.size()==0; }
}
