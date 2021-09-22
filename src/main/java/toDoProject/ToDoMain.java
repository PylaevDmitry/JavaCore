package toDoProject;

import toDoProject.dal.FileTasksDao;
import toDoProject.businessLayer.ToDoHandler;
import toDoProject.userInterfaces.ConsoleUserInterface;
import toDoProject.userInterfaces.WindowUserInterface;
import util.CustomProperties;
import java.util.*;

public class ToDoMain {

    public static final Map<String, String> environmentVars = System.getenv();
    public static final CustomProperties properties = new CustomProperties("toDoProject.customConfig");

    public static void main (String[] args) {

        var consoleUserInterface = new ConsoleUserInterface();
        var windowUserInterface = new WindowUserInterface();
//        var telegramBotUserInterface = new TelegramBotUserInterface(environmentVars.get("botToken"));

        var fileTasksDao = new FileTasksDao(environmentVars.get("filePath"));
//        var dbTasksDao = new DbTasksDao(environmentVars.get("dbUserName"), environmentVars.get("dbUserPass"));

        ToDoHandler toDoHandler1 = new ToDoHandler(consoleUserInterface, fileTasksDao);
        Thread t1 = new Thread(toDoHandler1);
        t1.start();

        ToDoHandler toDoHandler = new ToDoHandler(windowUserInterface, fileTasksDao);
        toDoHandler.run();
    }
}

