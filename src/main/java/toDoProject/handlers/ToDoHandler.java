package toDoProject.handlers;

import toDoProject.Main;
import toDoProject.abstractions.IStorage;
import toDoProject.abstractions.IUserInterface;
import toDoProject.models.Task;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ToDoHandler {
    private final IStorage storage;
    private final IUserInterface ui;

    public ToDoHandler (IUserInterface ui, IStorage storage) {
        this.storage = storage;
        this.ui = ui;
    }

    public void manage () {
        String[] invalidNameSymbols = new String[]{" ", "\\", "|", "/", ":", "?", "\"", "<", ">"};
        String[] commands = new String[] {"ARCH", "DONE", "WAIT", "BACK", "EXIT"};
        String[] tasksStates = new String[] {"ARCH", "DONE", "WAIT"};
        String userInput = "";
        String owner = "";

        while (inputCheck(invalidNameSymbols, owner)>=0) { owner = ui.askInput(Main.getPropertyContent("askOwner")); }
        storage.setOwner(owner);

        while (true) {
            List<Task> list = Arrays.stream(storage.getAll()).filter(task -> !task.getStatus().equals("ARCH")).collect(Collectors.toList());
            IntStream.range(0, list.size()).forEach(i -> ui.show(i + 1 + " " + list.get(i)));

            if (list.size()==0 || userInput.equals("NEW")) {
                userInput = ui.askInput(Main.getPropertyContent("askNew"));
                if (!userInput.equals("BACK")) storage.add(new Task(owner, userInput, new Date(), "WAIT"));
            }
            else {
                userInput = ui.askInput(Main.getPropertyContent("askNumber"));
                int taskIndex = getIndex(userInput, list);
                if (taskIndex != -1) do {
                    userInput = ui.askInput(Main.getPropertyContent("askStatus"));
                    if (inputCheck(tasksStates, userInput)>0) storage.setStatus(list.get(taskIndex-1).getId(),userInput);
                } while (inputCheck(commands, userInput)<=0);
            }
        }
    }

    private int getIndex (String userInput, List <Task> list) {
        try {
            int taskIndex = Integer.parseInt(userInput);
            return  (ValueRange.of(1, list.size()).isValidIntValue(taskIndex))?taskIndex:-1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int inputCheck (String[] arrString, String userInput) {
        boolean anyMatch = Arrays.stream(arrString).anyMatch(userInput::contains);
        if (userInput.length() > 0) {
            return (anyMatch)?1:-1;
        }
        else return 0;
    }
}
