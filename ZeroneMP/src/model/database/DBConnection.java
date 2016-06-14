package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Getachew on 5/21/2016.
 * @author Getachew Mulat
 */
public class DBConnection {
    private static Connection connection;

    /**
     * Load driver and establish connection to database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection doConnect() throws SQLException, ClassNotFoundException{

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:src/DB/ZeroneMediaPlayer.sqlite");

        return connection;
    }
}
