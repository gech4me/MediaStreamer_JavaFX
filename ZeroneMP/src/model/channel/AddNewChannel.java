package model.channel;

import model.database.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Getachew on 5/29/2016.
 *
 * @author Getachew Mulat
 */
public class AddNewChannel {

    private static Connection connection;
    private static Statement statement;

    /**
     *  Insert values to Movies table.
     * @param movieName
     * @param image
     * @param url
     */
    public static void insertMoviesValue(String movieName, String image, String url) {

        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Movies VALUES('"+ movieName + "','" + image + "','" + url + "')");
            connection.close();
            if(connection.isClosed()) {
                System.out.println("Data successfully inserted.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     *  Insert values to Tv table.
     * @param tvName
     * @param image
     * @param url
     */
    public static void insertTvValue(String tvName, String image, String url) {

        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Tv VALUES('"+ tvName + "','" + image + "','" + url + "')");

            connection.close();
            if(connection.isClosed()) {
                System.out.println("Data successfully inserted.");
            }
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
    }

    /**
     *  Insert values to Radios table.
     * @param radioName
     * @param image
     * @param url
     */
    public static void insertRadiosValue(String radioName, String image, String url) {

        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Radios VALUES('"+ radioName + "','" + image + "','" + url + "')");

            connection.close();
            if(connection.isClosed()) {
                System.out.println("Data successfully inserted.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     *  Insert values to Videos table.
     * @param videoName
     * @param image
     * @param url
     */
    public static void insertVideosValue(String videoName, String image, String url) {

        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Videos VALUES('"+ videoName + "','" + image + "','" + url + "')");

            connection.close();
            if(connection.isClosed()) {
                System.out.println("Data successfully inserted.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
