package ToDoProject;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import java.time.temporal.ValueRange;

enum Mods {ITERATE, EXIT, NEW, EDIT}//Состояния программы
enum EditCommands {DEL, DONE, WAIT, BACK}//Варианты действий с заметкой (одной)

public class Main {
    public static void main (String[] args) {
        String path = "D:\\ToDoList.txt";
        List<Task> taskList = new ArrayList<>();
        Mods mode=Mods.ITERATE;
        try {
            if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
            else for (String str:Files.readAllLines(Paths.get(path))) {
                taskList.add(new Task (str));
            }
        } catch (IOException|InvalidPathException e) {
            System.out.println("Нет доступа к диску, попробуйте задать другое место на диске или другой диск");
            mode=Mods.EXIT;
        }

        while (mode.equals(Mods.ITERATE)) {
            for (int i = 0; i < taskList.size(); i++) {
                taskList.get(i).setCorrectIndex(i+1);
                System.out.println(taskList.get(i).toString());
            }

            Scanner scanner = new Scanner((System.in)).useDelimiter("\n");
            String userInput;

            do {
                System.out.println("Выберете номер пункта, создайте новую задачу (NEW) или EXIT - выйти из программы");
                userInput = scanner.next();
            } while (!(taskList.size()==0)
                    &&!(userInput.equals("EXIT"))
                    &&!(userInput.equals("NEW"))
                    &&!(ValueRange.of(1, taskList.size()+1).isValidIntValue(Integer.parseInt(userInput))));

            if (userInput.equals("EXIT")) mode = Mods.EXIT;
            else if (userInput.equals("NEW")||(taskList.size()==0)) mode = Mods.NEW;
            else mode=Mods.EDIT;

            if (mode.equals(Mods.EDIT)) {

                do {
                    System.out.println("DEL - удалить, DONE - пометить как выполненное, WAIT - возобновить задачу, BACK - назад или EXIT - выйти из программы ");
                    userInput = scanner.next();
                } while (!Arrays.asList(EditCommands.values()).toString().contains(userInput));

                if (EditCommands.valueOf(userInput)==EditCommands.DEL) taskList.remove(Integer.parseInt(userInput)-1);
                else if (EditCommands.valueOf(userInput)==EditCommands.DONE) taskList.get(Integer.parseInt(userInput)-1).setStatusDone();
                else if (EditCommands.valueOf(userInput)==EditCommands.WAIT) taskList.get(Integer.parseInt(userInput)-1).setStatusWait();

                mode = Mods.ITERATE;
            }

            if (mode.equals(Mods.NEW)) {
                System.out.println("Введите новую заметку или BACK - назад");
                userInput = scanner.next();

                if (!userInput.equals("BACK")) taskList.add(new Task(taskList.size()+1, userInput, new Date(), "Wait"));

                if (taskList.size()==0) mode=Mods.EXIT;
                else mode = Mods.ITERATE;
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

    static class Task {
        private String index;
        private final String text;
        private final String date;
        private String status;

        public Task (int index, String text, Date date, String status) {
            this.index = String.valueOf(index);
            this.text = text;
            this.date = String.valueOf(date);
            this.status = status;
        }

        public Task (String content) {
            this.index = content.substring(0, content.indexOf(" "));
            this.text = content.substring(content.indexOf(" ")+1, content.length()-14);
            this.date = content.substring(content.length()-34, content.length()-5);
            this.status = content.substring(content.length()-4);
        }

        public void setCorrectIndex (int index) {
            this.index = String.valueOf(index);
        }

        public void setStatusDone () {
            this.status = "DONE";
        }

        public void setStatusWait () {
            this.status = "WAIT";
        }

        @Override
        public String toString () {
            return  index + ' ' + text + ' ' + date + ' ' + status;
        }
    }
}
