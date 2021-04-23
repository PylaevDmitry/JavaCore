package ToDoProject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.time.temporal.ValueRange;
import java.util.*;

enum Mods {MAIN_MENU, EXIT_PROGRAM, NEW_TASK, EDIT_TASK}
enum EditCommands {DEL, DONE, WAIT, BACK, EXIT}

public class ToDoList {
    public static void main (String path) {
        Mods mode=Mods.MAIN_MENU;
        List<Task> taskList = new ArrayList<>();

        try {
            if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
            else for (String str:Files.readAllLines(Paths.get(path))) {
                if (str.isEmpty()) continue;
                taskList.add(new Task (str));
            }
        } catch (IOException | InvalidPathException e) {
            System.out.println("Нет доступа к диску, попробуйте задать другое место на диске или другой диск");
            mode=Mods.EXIT_PROGRAM;
        }

        while (!mode.equals(Mods.EXIT_PROGRAM)) {
            for (int i = 0; i < taskList.size(); i++) {
                taskList.get(i).setCorrectIndex(i+1);
                System.out.println(taskList.get(i).toString());
            }

            Scanner scanner = new Scanner((System.in)).useDelimiter("\n");
            String userInput;
            int taskIndex=Integer.MIN_VALUE;

            do {
                System.out.println("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");
                userInput = scanner.next();
                try {
                    taskIndex=Integer.parseInt(userInput);
                } catch (NumberFormatException ignored) {
                }
            } while (!(taskList.size()==0)
                    &&!(userInput.equals("EXIT"))
                    &&!(userInput.equals("NEW"))
                    &&!(ValueRange.of(1, taskList.size()+1).isValidIntValue(taskIndex)));

            if (userInput.equals("EXIT")) mode = Mods.EXIT_PROGRAM;
            else if (userInput.equals("NEW")||(taskList.size()==0)) mode = Mods.NEW_TASK;
            else mode=Mods.EDIT_TASK;

            if (mode.equals(Mods.EDIT_TASK)) {

                do {
                    System.out.println("DEL - удалить, DONE - пометить как выполненное, WAIT - возобновить задачу, BACK - назад или EXIT - выйти из программы ");
                    userInput = scanner.next();
                } while (!(Arrays.asList(EditCommands.values()).toString().contains(userInput))||userInput.isBlank());

                if (EditCommands.valueOf(userInput)==EditCommands.EXIT) mode=Mods.EXIT_PROGRAM;
                else if (EditCommands.valueOf(userInput)==EditCommands.DEL) taskList.remove(Integer.parseInt(userInput)-1);
                else if (EditCommands.valueOf(userInput)==EditCommands.DONE) taskList.get(Integer.parseInt(userInput)-1).setStatus("Done");
                else if (EditCommands.valueOf(userInput)==EditCommands.WAIT) taskList.get(Integer.parseInt(userInput)-1).setStatus("Wait");
            }

            if (mode.equals(Mods.NEW_TASK)) {
                System.out.println("Введите новую заметку, BACK - назад или EXIT - выйти из программы");
                userInput = scanner.next();

                if (!userInput.equals("BACK")) taskList.add(new Task(taskList.size()+1, userInput, new Date(), "Wait"));
                if (userInput.equals("EXIT")) mode=Mods.EXIT_PROGRAM;
            }

            try {
                PrintStream printStream = new PrintStream(new FileOutputStream(path));
                for (Task str : taskList) {
                    printStream.println(str);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
            }
        }

        System.out.println("Спасибо за использование моей программы");
    }
}
