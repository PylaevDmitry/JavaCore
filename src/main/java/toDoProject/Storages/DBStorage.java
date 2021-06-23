package toDoProject.Storages;

import toDoProject.Abstractions.IStorage;
import toDoProject.Main;
import toDoProject.Models.Task;
import java.sql.*;
import java.util.ArrayList;

public class DBStorage implements IStorage {
    private final String dbUser;
    private final String dbPass;
    private final String listName;
    private static final String ID  = "id";
    private static final String TEXT  = "text";
    private static final String DATE  = "date";
    private static final String STATUS  = "status";

    public DBStorage (String dbUser, String dbPass, String listName) {
        this.dbUser = dbUser;
        this.dbPass = dbPass;
        this.listName = listName;
    }

    private Connection getDbConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) { e.printStackTrace(); }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/todo", dbUser, dbPass);
    }

    @Override
    public Task[] getAll ( ) {
        String firstRequest = "CREATE TABLE IF NOT EXISTS " + listName + " (\n" +
                ID + " SERIAL,\n" +
                TEXT + " VARCHAR(50),\n" +
                DATE + " VARCHAR(50),\n" +
                STATUS + " VARCHAR(50),\n" +
                "PRIMARY KEY (" + ID + ")\n" +
                ");";
        try (Connection dbConnection = getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(firstRequest);
            preparedStatement.executeUpdate();
        } catch (SQLException e) { Main.storageErrorPrint(); }

        String request = "SELECT * FROM " + listName + ";";
        var result = new ArrayList<Task>();
        try (Connection dbConnection = getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (!resultSet.getString(STATUS).equals("ARCH")) {
                    result.add(new Task(resultSet.getString(ID) + " "
                            + resultSet.getString(TEXT) + " "
                            + resultSet.getString(DATE) + " "
                            + resultSet.getString(STATUS)));
                }
            }
        } catch (SQLException e) { Main.storageErrorPrint(); }

        if (result.size()==0) return new Task[0];
        else {
            Task[] resArr = new Task[result.size()];
            for (int i = 0; i < result.size(); i++) { resArr[i]= result.get(i); }
            return resArr;
        }
    }

    @Override
    public void add (Task data) {
        String request = "INSERT INTO " + listName + "("  + TEXT + ", " + DATE + ", " + STATUS + ")" + "VALUES (?,?,?)" + ";";
        try (Connection dbConnection = getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request, new String[] {ID});
            preparedStatement.setString(1, data.getText());
            preparedStatement.setString(2, data.getDate());
            preparedStatement.setString(3, data.getStatus());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) data.setId(generatedKeys.getLong(ID));

        } catch (SQLException e) { Main.storageErrorPrint(); }
    }

    @Override
    public void remove (long id) {
        String request = "DELETE FROM  " + listName + " WHERE " + ID + "=" + "'" + id + "'" + ";";
        try (Connection dbConnection = getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.executeUpdate();
        } catch (SQLException e) { Main.storageErrorPrint(); }
    }

    @Override
    public void setStatus (long id, String status) {
        String request = "UPDATE " + listName + " SET " + STATUS + "=" + "'" + status + "'" + " WHERE " + ID + "=" + "'" + id +"'" + ";";
        try (Connection dbConnection = getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.executeUpdate();
        } catch (SQLException e) { Main.storageErrorPrint(); }
    }
}


