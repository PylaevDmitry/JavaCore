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
import java.util.stream.Collectors;

enum ProgramStatus {MAIN_MENU, EXIT_PROGRAM, NEW_TASK, EDIT_TASK}
enum UserEditCommands {DEL, DONE, WAIT, BACK, EXIT}

public class Main {
    public static void main (String[] args) {
        String path = "D:\\ToDoList.txt";
        ProgramStatus mode= ProgramStatus.MAIN_MENU;
        List<Task> taskList = new ArrayList<>();

        try {
            if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
            else for (String str:Files.readAllLines(Paths.get(path))) {
                if (str.isEmpty()) continue;
                taskList.add(new Task (str));
            }
        } catch (IOException | InvalidPathException e) {
            System.out.println("Нет доступа к диску, попробуйте задать другое место на диске или другой диск");
            mode= ProgramStatus.EXIT_PROGRAM;
        }

        while (!mode.equals(ProgramStatus.EXIT_PROGRAM)) {
            Scanner scanner = new Scanner((System.in)).useDelimiter("\n");
            String userInput = "";
            int taskIndex = Integer.MIN_VALUE;

            if (!(taskList.size() == 0)) do {
                System.out.println("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");
                userInput = scanner.next();
                try {
                    taskIndex = Integer.parseInt(userInput);
                } catch (NumberFormatException ignored) {
                }
            } while (!(userInput.equals("EXIT"))
                    && !(userInput.equals("NEW"))
                    && !(ValueRange.of(1, taskList.size() + 1).isValidIntValue(taskIndex)));

            if (userInput.equals("EXIT")) mode = ProgramStatus.EXIT_PROGRAM;
            else if (userInput.equals("NEW") || (taskList.size() == 0)) mode = ProgramStatus.NEW_TASK;
            else mode = ProgramStatus.EDIT_TASK;

            if (mode.equals(ProgramStatus.EDIT_TASK)) {
                do {
                    System.out.println("DEL - удалить, DONE - пометить как выполненное, WAIT - возобновить задачу," +
                            " BACK - к списку заметок или EXIT - выйти из программы ");
                    userInput = scanner.next();
                } while (!Arrays.stream(UserEditCommands.values()).map(String::valueOf).collect(Collectors.toList()).contains(userInput) || userInput.isBlank());

                switch (userInput) {
                    case "EXIT" -> mode = ProgramStatus.EXIT_PROGRAM;
                    case "DEL" -> {
                        taskList.remove(taskIndex - 1);
                        for (int i = 0; i < taskList.size(); i++) {
                            taskList.get(i).setCorrectIndex(i+1);
                            System.out.println(taskList.get(i).toString());
                        }
                    }
                    case "DONE" -> taskList.get(taskIndex - 1).setStatus("Done");
                    case "WAIT" -> taskList.get(taskIndex - 1).setStatus("Wait");
                }
            }

            if (mode.equals(ProgramStatus.NEW_TASK)) {
                System.out.println("Введите новую заметку, BACK - к списку заметок или EXIT - выйти из программы");
                userInput = scanner.next();
                if (!(userInput.equals("BACK")) && !(userInput.equals("EXIT")))
                    taskList.add(new Task(taskList.size() + 1, userInput, new Date(), "Wait"));
                if (userInput.equals("EXIT")) mode = ProgramStatus.EXIT_PROGRAM;
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
