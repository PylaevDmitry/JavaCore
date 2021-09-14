package toDoProject;

import toDoProject.dal.DbTasksDao;
import toDoProject.dal.FileTasksDao;
import toDoProject.handlers.ToDoHandler;
import toDoProject.userInterfaces.ConsoleUserInterface;
import toDoProject.userInterfaces.TelegramBotUserInterface;
import toDoProject.userInterfaces.WindowUserInterface;
import java.util.*;

public class Main {
    public static void main (String[] args) {
        Map<String, String> environmentVars = System.getenv();

        var ui1 = new ConsoleUserInterface();
        var ui2 = new WindowUserInterface();
        var ui3 = new TelegramBotUserInterface(environmentVars.get("botToken"));
        var storage1 = new DbTasksDao(environmentVars.get("dbUserName"), environmentVars.get("dbUserPass"));
        var storage2 = new FileTasksDao(environmentVars.get("filePath"));

        ToDoHandler toDoHandler = new ToDoHandler(ui3, storage2);
        toDoHandler.manage();
    }

    public static String getPropertyContent (String property ) {
        PropertyResourceBundle properties = (PropertyResourceBundle) PropertyResourceBundle.getBundle("toDoProject.customConfig");
        return properties.getString(property);
    }
}
