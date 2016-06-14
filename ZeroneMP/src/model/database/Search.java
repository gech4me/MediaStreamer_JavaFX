package model.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Created by Getachew on 5/28/2016.
 *
 * @author Getachew Mulat
 */
public class Search {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static boolean searchOnTv(String searchText) {

        boolean found = false;
        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();

            String query = "SELECT * FROM Tv " +
                    "WHERE tvName='" + searchText + "';";
            System.out.println(query);
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if(Objects.equals(resultSet.getString("tvName"), searchText)) {
                    found = true;
                    break;
                }

                if(resultSet.isAfterLast()) {
                    break;
                }
            }

            connection.close();
            if(connection.isClosed()) {
                System.out.println("Connection with TV table closed.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return found;
    }

    public static boolean searchOnRadios(String searchText) {

        boolean found = false;
        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();

            String query = "SELECT * FROM Radios " +
                    "WHERE radioName='" + searchText + "';";
            System.out.println(query);
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if(Objects.equals(resultSet.getString("radioName"), searchText)) {
                    found = true;
                    break;
                }

                if(resultSet.isAfterLast()) {
                    break;
                }
            }

            connection.close();
            if(connection.isClosed()) {
                System.out.println("Connection with Radios table closed.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return found;
    }

    public static boolean searchOnMovies(String searchText) {

        boolean found = false;
        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();

            String query = "SELECT * FROM Movies " +
                    "WHERE movieName='" + searchText + "';";
            System.out.println(query);
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if(Objects.equals(resultSet.getString("movieName"), searchText)) {
                    found = true;
                    break;
                }

                if(resultSet.isAfterLast()) {
                    break;
                }
            }

            connection.close();
            if(connection.isClosed()) {
                System.out.println("Connection with Movies table closed.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return found;
    }

    public static boolean searchOnVideos(String searchText) {

        boolean found = false;
        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();

            String query = "SELECT * FROM Videos " +
                    "WHERE videoName='" + searchText + "';";
            System.out.println(query);
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if(Objects.equals(resultSet.getString("videoName"), searchText)) {
                    found = true;
                    break;
                }

                if(resultSet.isAfterLast()) {
                    break;
                }
            }

            connection.close();
            if(connection.isClosed()) {
                System.out.println("Connection with Videos table closed.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return found;
    }
}
