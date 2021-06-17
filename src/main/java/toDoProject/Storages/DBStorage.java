package toDoProject.Storages;

import toDoProject.Abstractions.IStorage;
import toDoProject.Main;
import toDoProject.Models.Task;
import java.sql.*;
import java.util.ArrayList;

public class DBStorage implements IStorage {
    private final String dbUser;
    private final String dbPass;
    private static final String TABLE_CURRENT = "tasks";
    private static final String ID  = "id";
    private static final String TEXT  = "text";
    private static final String DATE  = "date";
    private static final String STATUS  = "status";

    public DBStorage (String dbUser, String dbPass) {
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    private Connection getDbConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) { e.printStackTrace(); }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/todo", dbUser, dbPass);
    }

    @Override
    public Task[] getAll ( ) {
        String request = "SELECT * FROM " + TABLE_CURRENT + ";";
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
        String request = "INSERT INTO " + TABLE_CURRENT + "(" + ID + ", " + TEXT + ", " + DATE + ", " + STATUS + ")" + "VALUES (?,?,?,?)" + ";";
        try (Connection dbConnection = getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setString(1, String.valueOf(data.getId()));
            preparedStatement.setString(2, data.getText());
            preparedStatement.setString(3, data.getDate());
            preparedStatement.setString(4, data.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) { Main.storageErrorPrint(); }
    }

    @Override
    public void remove (long id) {
        String request = "DELETE FROM  " + TABLE_CURRENT + " WHERE " + ID + "=" + "'" + id + "'" + ";";
        try (Connection dbConnection = getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.executeUpdate();
        } catch (SQLException e) { Main.storageErrorPrint(); }
    }

    @Override
    public void setStatus (long id, String status) {
        String request = "UPDATE " + TABLE_CURRENT + " SET " + STATUS + "=" + "'" + status + "'" + " WHERE " + ID + "=" + "'" + id +"'" + ";";
        try (Connection dbConnection = getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.executeUpdate();
        } catch (SQLException e) { Main.storageErrorPrint(); }
    }
}
