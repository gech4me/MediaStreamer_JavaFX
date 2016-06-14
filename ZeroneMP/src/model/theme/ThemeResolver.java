package model.theme;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.database.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Getachew on 5/28/2016.
 *
 * @author Getachew Mulat
 */
public class ThemeResolver {

    private StringProperty mainTheme = new SimpleStringProperty(this, null, null);

    public ThemeResolver() {
        loadTheme();
    }

    public StringProperty mainThemeProperty() {
        return mainTheme;
    }

    public void setMainTheme(String mainTheme) {
        this.mainTheme.set(mainTheme);
    }

    public String getMainTheme() {
        return mainTheme.get();
    }

    public void loadTheme() {

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Settings");

            while (resultSet.next()) {
                setMainTheme(resultSet.getString("theme"));

                if(resultSet.isAfterLast()) {
                    break;
                }
            }

            connection.close();
            if(connection.isClosed()) {
                System.out.println("Connection with Settings closed.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void updateTheme() {
        Connection connection;
        Statement statement;

        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();

            statement.executeUpdate("UPDATE Settings " +
                    "SET theme='" + mainTheme.get() + "' WHERE rowid=1;");

            System.out.println("Theme updated to " + mainTheme.get());

            connection.close();
            if(connection.isClosed()) {
                System.out.println("Connection with Settings closed.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
