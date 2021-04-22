package ToDoProject;

import java.io.*;
import java.nio.file.*;
import java.util.*;

enum Mods {EXIT, NEW, EDIT, ITERATE}//Флаги режимов
enum EditCommands {DEL, DONE, WAIT, BACK}//Варианты действий с заметкой (одной)

public class Main {
    public static void main (String[] args) {
        String path = "D:\\ToDoList.txt";
        List<String> taskList = new ArrayList<>();
        Mods mode=Mods.ITERATE;
        try {
            if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
            taskList = Files.readAllLines(Paths.get(path));
        } catch (IOException|InvalidPathException e) {
            System.out.println("Нет доступа к диску, попробуйте задать другое место на диске или другой диск");
            mode=Mods.EXIT;
        }

        Scanner scanner = new Scanner((System.in)).useDelimiter("\n");

        while (mode.equals(Mods.ITERATE)) {
            for (int i = 0; i < taskList.size(); i++) {
                String str = taskList.get(i);
                taskList.set(i, (i + 1) + str.substring(str.indexOf(" ")));
                System.out.println(taskList.get(i));
            }

            String userInput;
            int noteIndex=-1;

            do {
                if (taskList.size()==0) {
                    mode=Mods.NEW;
                    break;
                }
                System.out.println("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");

                userInput = scanner.next();

                if (userInput.equals("EXIT")) {
                    mode = Mods.EXIT;
                    break;
                }
                else if (userInput.equals("NEW")) {
                    mode = Mods.NEW;
                    break;
                }
                else {
                    try {
                        noteIndex = Integer.parseInt(userInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Некорректный ввод числа");
                    }
                    mode=Mods.EDIT;
                }
            } while (!(noteIndex>0)&&(noteIndex<=taskList.size() + 1));

            if (mode.equals(Mods.EDIT)) {

                do {
                    System.out.println("DEL - удалить, DONE - пометить как выполненное, WAIT - возобновить задачу, BACK - назад");
                    userInput = scanner.next();

                } while (!Arrays.asList(EditCommands.values()).toString().contains(userInput));

                switch (EditCommands.valueOf(userInput)) {

                    case DEL -> {
                        taskList.remove(noteIndex-1);
                    }

                    case DONE -> {
                        String str = taskList.get(noteIndex-1);
                        taskList.set(noteIndex-1, str.substring(0, str.length() - 4) + "Done");
                    }

                    case WAIT -> {
                        String str = taskList.get(noteIndex-1);
                        taskList.set(noteIndex-1, str.substring(0, str.length() - 4) + "Wait");
                    }
                }

                mode = Mods.ITERATE;
            }

            if (mode.equals(Mods.NEW)) {
                System.out.println("Введите новую заметку или BACK - назад");
                userInput = scanner.next();

                if (!userInput.equals("BACK")) {
                    String date = String.valueOf(new Date());
                    taskList.add((taskList.size()+1)+ " " + userInput + " " + date.substring(0, 20) + "Wait");
                }

                if (taskList.size()==0) mode=Mods.EXIT;
                else mode = Mods.ITERATE;
            }

            try {
                PrintStream printStream = new PrintStream(new FileOutputStream(path));
                for (String str : taskList) {
                    printStream.println(str);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
            }
        }

        System.out.println("Спасибо за использование моей программы");
    }
}
