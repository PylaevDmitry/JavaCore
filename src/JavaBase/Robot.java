package JavaBase;

import java.util.logging.*;

public class Robot {
    public static void moveRobot (RobotConnectionManager robotConnectionManager, int toX, int toY) {
        try {
            for (int i = 0; i <= 2;i++) {
                try (RobotConnection rc = robotConnectionManager.getConnection()) {
                    rc.moveRobotTo(toX, toY);
                    i=3;
                } catch (RobotConnectionException e) {
                    if (i >= 2) throw e;
                }
            }
        }
        catch (RuntimeException e) {
            if (!e.getStackTrace()[0].getMethodName().equals("close")) throw e;
        }
    }


//Из задания
    public interface RobotConnectionManager {
        RobotConnection getConnection();
    }

    public class RobotConnectionException extends RuntimeException {
        public RobotConnectionException(String message) {
            super(message);
        }
        public RobotConnectionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public interface RobotConnection extends AutoCloseable {
        void moveRobotTo (int x, int y);

        @Override
        void close();
    }

    private static void configureLogging() {
        Logger ClassA = Logger.getLogger("ClassA");
        Logger ClassB = Logger.getLogger("ClassB");

        ClassA.setLevel(Level.ALL);
        ClassB.setLevel(Level.WARNING);

        Logger C = Logger.getLogger(Robot.class.getName());
        Handler h = new ConsoleHandler();
        h.setLevel(Level.OFF);
        h.setFormatter(new XMLFormatter());
        C.addHandler(h);
        C.setUseParentHandlers(false);

    }
}
