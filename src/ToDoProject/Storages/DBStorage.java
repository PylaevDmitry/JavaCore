package ToDoProject.Storages;

import ToDoProject.Abstractions.IStorage;
import ToDoProject.Models.Task;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBStorage implements IStorage {
    private final String dbHost;
    private final String dbPort;
    private final String dbUser;
    private final String dbPass;
    private final String dbName;
    private final String jdbcDriver;

    private static final String TABLE  = "tasks";

    private static final String ID  = "id";
    private static final String TEXT  = "text";
    private static final String DATE  = "date";
    private static final String STATUS  = "status";

    public DBStorage (String dbHost, String dbPort, String dbUser, String dbPass, String dbName, String jdbcDriver) {
        this.dbHost = dbHost;
        this.dbPort = dbPort;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
        this.dbName = dbName;
        this.jdbcDriver = jdbcDriver;
    }

    private Connection getDbConnection() {
        Connection dbConnection=null;
        String dbType = jdbcDriver.substring(jdbcDriver.indexOf(".")+1, jdbcDriver.lastIndexOf("."));
        try {
            String connectionString = "jdbc:" +dbType+"://" + dbHost + ":" + dbPort + "/" + dbName;
            Class.forName(jdbcDriver);
            dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        } catch (ClassNotFoundException|SQLException e) { e.printStackTrace(); }
        return dbConnection;
    }

    @Override
    public Task[] getAll ( ) throws IOException {
        String select = "SELECT * FROM " + TABLE + ";";
        var result = new ArrayList<Task>();
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(new Task(resultSet.getString(ID) + " "
                    + resultSet.getString(TEXT) + " "
                    + resultSet.getString(DATE) + " "
                    + resultSet.getString(STATUS)));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        if (result.size()==0) return new Task[0];
        else {
            Task[] resArr = new Task[result.size()];
            for (int i = 0; i < result.size(); i++) { resArr[i]= result.get(i); }
            return resArr;
        }
    }

    @Override
    public void add (Task data) throws IOException {
        String request = "INSERT INTO " + TABLE + "(" + ID + ", " + TEXT + ", " + DATE + ", " + STATUS + ")" + "VALUES (?,?,?,?)" + ";";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(request);

            preparedStatement.setString(1, String.valueOf(data.getId()));
            preparedStatement.setString(2, data.getText());
            preparedStatement.setString(3, data.getDate());
            preparedStatement.setString(4, data.getStatus());

            preparedStatement.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void delete (int id) throws IOException {
        String request = "DELETE FROM  " + TABLE + " WHERE " + ID + "=" + "'" + id + "'" + ";";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(request);
            preparedStatement.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void setStatus (int id, String status) throws IOException {
        String request = "UPDATE " + TABLE + " SET " + STATUS + "=" + "'" + status + "'" + " WHERE " + ID + "=" + "'" + id +"'" + ";";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(request);
            preparedStatement.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
