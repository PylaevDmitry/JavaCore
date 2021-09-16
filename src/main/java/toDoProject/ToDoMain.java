package toDoProject;

import toDoProject.dal.DbTasksDao;
import toDoProject.dal.FileTasksDao;
import toDoProject.businessLayer.ToDoHandler;
import toDoProject.userInterfaces.ConsoleUserInterface;
import toDoProject.userInterfaces.WindowUserInterface;
import util.CustomProperties;
import java.util.*;

public class ToDoMain {

    public static Map<String, String> environmentVars;
    public static CustomProperties properties;

    public static void main (String[] args) {

        environmentVars = System.getenv();
        properties = new CustomProperties("toDoProject.customConfig");

        var ui1 = new ConsoleUserInterface();
        var ui2 = new WindowUserInterface();
//        var ui3 = new TelegramBotUserInterface(environmentVars.get("botToken"));

        var storage1 = new DbTasksDao(environmentVars.get("dbUserName"), environmentVars.get("dbUserPass"));
        var storage2 = new FileTasksDao(environmentVars.get("filePath"));

        ToDoHandler toDoHandler2 = new ToDoHandler(ui2, storage2);
        Thread t1 = new Thread(toDoHandler2);
        t1.start();

        ToDoHandler toDoHandler = new ToDoHandler(ui1, storage2);
        toDoHandler.run();
    }
}

