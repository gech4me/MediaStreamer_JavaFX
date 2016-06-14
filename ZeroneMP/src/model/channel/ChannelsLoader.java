package model.channel;

import model.database.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Getachew Mulat on 5/5/2016.
 * @author Getachew Mulat
 */
public class ChannelsLoader {

    private int size;
    private List<String> imagePath = new ArrayList<>();
    private List<String> videoPath = new ArrayList<>();
    private List<String> videoTitle = new ArrayList<>();
    private Connection connection;
    private Statement statement;

    public  void getChannel(String type, String searchText) {

        switch (type) {
            case "Tv":
                   tvChannel(searchText);
                break;
            case "Movies":
                moviesChannel(searchText);
                break;
            case "Videos":
                videosChannel(searchText);
                break;
            case "Radios":
                radiosChannel(searchText);
                break;
        }
    }

    private void tvChannel(String searchText) {

        ResultSet resultSet;

        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();
            if(Objects.equals(searchText, "")) {
                resultSet = statement.executeQuery("SELECT * FROM Tv");
            } else {
                String query = "SELECT * FROM Tv " +
                        "WHERE tvName='" + searchText + "';";
                System.out.println(query);
                resultSet = statement.executeQuery(query);
            }

            while(resultSet.next()) {
                videoTitle.add(resultSet.getString("tvName"));
                imagePath.add(resultSet.getString("image"));
                videoPath.add(resultSet.getString("url"));

                if(resultSet.isAfterLast()) {
                    return;
                }
            }

            this.size = videoPath.size();
            connection.close();
            if(connection.isClosed()) {
                System.out.println("Connection with Tv table closed.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void moviesChannel(String searchText) {

        ResultSet resultSet;

        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();

            if(Objects.equals(searchText, "")) {
                resultSet = statement.executeQuery("SELECT * FROM Movies");
            } else {
                String query = "SELECT * FROM Movies " +
                        "WHERE movieName='" + searchText + "';";

                System.out.println(query);
                resultSet = statement.executeQuery(query);
            }

            while(resultSet.next()) {
                videoTitle.add(resultSet.getString("movieName"));
                imagePath.add(resultSet.getString("image"));
                videoPath.add(resultSet.getString("url"));

                if(resultSet.isAfterLast()) {
                    return;
                }
            }

            this.size = videoPath.size();
            connection.close();
            if(connection.isClosed()) {
                System.out.println("Connection with Movies table closed.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void videosChannel(String searchText) {

        ResultSet resultSet;

        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();

            if(Objects.equals(searchText, "")) {
                resultSet = statement.executeQuery("SELECT * FROM Videos");
            } else {
                String query = "SELECT * FROM Videos " +
                        "WHERE videoName='" + searchText + "';";

                System.out.println(query);
                resultSet = statement.executeQuery(query);
            }

            while(resultSet.next()) {
                videoTitle.add(resultSet.getString("videoName"));
                imagePath.add(resultSet.getString("image"));
                videoPath.add(resultSet.getString("url"));

                if(resultSet.isAfterLast()) {
                    return;
                }
            }

            this.size = videoPath.size();
            connection.close();
            if(connection.isClosed()) {
                System.out.println("Connection with Videos table closed.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void radiosChannel(String searchText) {

        ResultSet resultSet;

        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();

            if(Objects.equals(searchText, "")) {
                resultSet = statement.executeQuery("SELECT * FROM Radios");
            } else {
                String query = "SELECT * FROM Radios " +
                        "WHERE radioName='" + searchText + "';";

                System.out.println(query);
                resultSet = statement.executeQuery(query);
            }

            while(resultSet.next()) {
                videoTitle.add(resultSet.getString("radioName"));
                imagePath.add(resultSet.getString("image"));
                videoPath.add(resultSet.getString("url"));

                if(resultSet.isAfterLast()) {
                    return;
                }
            }

            this.size = videoPath.size();
            connection.close();
            if(connection.isClosed()) {
                System.out.println("Connection with Radios table closed.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getSize() {
        return size;
    }
    public List<String> getImagePath() {
        return imagePath;
    }
    public List<String> getVideoPath() {
        return videoPath;
    }
    public List<String> getVideoTitle() {
        return videoTitle;
    }
}
