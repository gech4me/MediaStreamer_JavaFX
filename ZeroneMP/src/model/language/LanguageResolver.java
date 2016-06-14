package model.language;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.database.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 *  Resolve the mainLanguage.
 *  Rule: this class load the preference mainLanguage from database.
 */
public class LanguageResolver {

    private StringProperty mainLanguage = new SimpleStringProperty(this, null, null);
    private AmharicLanguage amharicLanguage;
    private EnglishLanguage englishLanguage;
    private AfaanOromoLanguage afaanOromoLanguage;

    private StringProperty settingsWord = new SimpleStringProperty(this, null, null);
    private StringProperty themeWord = new SimpleStringProperty(this, null, null);
    private StringProperty languageWord = new SimpleStringProperty(this, null, null);
    private StringProperty englishWord = new SimpleStringProperty(this, null, null);
    private StringProperty amharicWord = new SimpleStringProperty(this, null, null);

    private StringProperty oromicWord = new SimpleStringProperty(this, null, null);
    private StringProperty moviesWord = new SimpleStringProperty(this, null, null);
    private StringProperty tvWord = new SimpleStringProperty(this, null, null);
    private StringProperty radiosWord = new SimpleStringProperty(this, null, null);
    private StringProperty videosWord = new SimpleStringProperty(this, null, null);
    private StringProperty titleWord = new SimpleStringProperty(this, null, null);
    private StringProperty welcomeWord = new SimpleStringProperty(this, null, null);
    private StringProperty volumeWord = new SimpleStringProperty(this, null, null);

    private StringProperty addNewChannelWord = new SimpleStringProperty(this, null, null);
    private StringProperty categoryWord = new SimpleStringProperty(this, null, null);
    private StringProperty newChannelNameWord = new SimpleStringProperty(this, null, null);
    private StringProperty newImageUrlWord = new SimpleStringProperty(this, null, null);
    private StringProperty newStreamUrlWord = new SimpleStringProperty(this, null, null);
    private StringProperty newCancelWord = new SimpleStringProperty(this, null, null);
    private StringProperty newAddWord = new SimpleStringProperty(this, null, null);

    private StringProperty searchWord = new SimpleStringProperty(this, null, null);

    /**
     *  Automatically load selected mainLanguage and initialize the field.
     */
    public LanguageResolver() {
        amharicLanguage = new AmharicLanguage();
        englishLanguage = new EnglishLanguage();
        afaanOromoLanguage = new AfaanOromoLanguage();
        
        if(Objects.equals(getSelectedLanguage(), amharicLanguage)) {
            setSettingsWord(AmharicLanguage.getAmSettings());
            setThemeWord(AmharicLanguage.getAmTheme());
            setLanguageWord(AmharicLanguage.getAmLanguage());
            setEnglishWord(AmharicLanguage.getAmEnglish());
            setAmharicWord(AmharicLanguage.getAmAmharic());
            setOromicWord(AmharicLanguage.getAmAfaanoromoo());
            setMoviesWord(AmharicLanguage.getAmMovies());
            setTvWord(AmharicLanguage.getAmTv());
            setRadiosWord(AmharicLanguage.getAmRadios());
            setVideosWord(AmharicLanguage.getAmVideos());
            setTitleWord(AmharicLanguage.getAmTitle());
            setWelcomeWord(AmharicLanguage.getAmWelcome());
            setVolumeWord(AmharicLanguage.getAmVolume());

            setAddNewChannelWord(AmharicLanguage.getAmAddNewChannel());
            setCategoryWord(AmharicLanguage.getAmCategory());
            setNewChannelNameWord(AmharicLanguage.getAmNewChannelName());
            setNewImageUrlWord(AmharicLanguage.getAmNewImageUrl());
            setNewStreamUrlWord(AmharicLanguage.getAmNewStreamUrl());
            setNewCancelWord(AmharicLanguage.getAmCancel());
            setNewAddWord(AmharicLanguage.getAmAdd());

            setSearchWord(AmharicLanguage.getAmSearch());
        } else if(Objects.equals(getSelectedLanguage(), englishLanguage)) {
            setSettingsWord(EnglishLanguage.getEnSettings());
            setThemeWord(EnglishLanguage.getEnTheme());
            setLanguageWord(EnglishLanguage.getEnLanguage());
            setEnglishWord(EnglishLanguage.getEnEnglish());
            setAmharicWord(EnglishLanguage.getEnAmharic());
            setOromicWord(EnglishLanguage.getEnAfaanoromoo());
            setMoviesWord(EnglishLanguage.getEnMovies());
            setTvWord(EnglishLanguage.getEnTv());
            setRadiosWord(EnglishLanguage.getEnRadios());
            setVideosWord(EnglishLanguage.getEnVideos());
            setTitleWord(EnglishLanguage.getEnTitle());
            setWelcomeWord(EnglishLanguage.getEnWelcome());
            setVolumeWord(EnglishLanguage.getEnVolume());

            setAddNewChannelWord(EnglishLanguage.getEnAddNewChannel());
            setCategoryWord(EnglishLanguage.getEnCategory());
            setNewChannelNameWord(EnglishLanguage.getEnNewChannelName());
            setNewImageUrlWord(EnglishLanguage.getEnNewImageUrl());
            setNewStreamUrlWord(EnglishLanguage.getEnNewStreamUrl());
            setNewCancelWord(EnglishLanguage.getEnCancel());
            setNewAddWord(EnglishLanguage.getEnAdd());

            setSearchWord(EnglishLanguage.getEnSearch());
        } else if(Objects.equals(getSelectedLanguage(), afaanOromoLanguage) ) {
            setSettingsWord(AfaanOromoLanguage.getOrSettings());
            setThemeWord(AfaanOromoLanguage.getOrTheme());
            setLanguageWord(AfaanOromoLanguage.getOrLanguage());
            setEnglishWord(AfaanOromoLanguage.getOrEnglish());
            setAmharicWord(AfaanOromoLanguage.getOrAmharic());
            setOromicWord(AfaanOromoLanguage.getOrAfaanoromoo());
            setMoviesWord(AfaanOromoLanguage.getOrMovies());
            setTvWord(AfaanOromoLanguage.getOrTv());
            setRadiosWord(AfaanOromoLanguage.getOrRadios());
            setVideosWord(AfaanOromoLanguage.getOrVideos());
            setTitleWord(AfaanOromoLanguage.getOrTitle());
            setWelcomeWord(AfaanOromoLanguage.getOrWelcome());
            setVolumeWord(AfaanOromoLanguage.getOrVolume());

            setAddNewChannelWord(AfaanOromoLanguage.getOrAddNewChannel());
            setCategoryWord(AfaanOromoLanguage.getOrCategory());
            setNewChannelNameWord(AfaanOromoLanguage.getOrNewChannelName());
            setNewImageUrlWord(AfaanOromoLanguage.getOrNewImageUrl());
            setNewStreamUrlWord(AfaanOromoLanguage.getOrNewStreamUrl());
            setNewCancelWord(AfaanOromoLanguage.getOrCancel());
            setNewAddWord(AfaanOromoLanguage.getOrAdd());

            setSearchWord(AfaanOromoLanguage.getOrSearch());
        }
    }

    /**
     * get the current mainLanguage, which is loaded from database.
     * @return currentLanguage
     */
    public String getMainLanguage() {
        return mainLanguage.get();
    }

    /**
     * set the current mainLanguage.
     * @param mainLanguage
     */
    public void setMainLanguage(String mainLanguage) {
        this.mainLanguage.set(mainLanguage);
    }

    /**
     * get the current selected mainLanguage.
     * @return Object
     */
    public Object getSelectedLanguage() {
        loadLanguage();
        switch (mainLanguage.get()) {
            case "Amharic":
                amharicLanguage = new AmharicLanguage();
                return amharicLanguage;
            case "English":
                englishLanguage = new EnglishLanguage();
                return englishLanguage;
            case "AfaanOromoo":
                afaanOromoLanguage = new AfaanOromoLanguage();
                return afaanOromoLanguage;
            default:
                return null;
        }
    }

    /**
     *  load the current mainLanguage from database.
     * @throws SQLException
     */
    public void loadLanguage() {
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Settings");

            while (resultSet.next()) {
                setMainLanguage(resultSet.getString("language"));
    
                if(resultSet.isAfterLast()) {
                    break;
                }
            }

            connection.close();
            if(connection.isClosed()) {
                System.out.println("Connection with Settings table closed.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *  update mainLanguage to the database.
     */
    public void updateLanguage() {
        Connection connection;
        Statement statement;

        try {
            connection = DBConnection.doConnect();
            statement = connection.createStatement();

            statement.executeUpdate("UPDATE Settings " +
                    "SET language='" + mainLanguage.get() + "' WHERE rowid=1;");
            System.out.println("Language updated to " + mainLanguage.get());


            connection.close();
            if(connection.isClosed()) {
                System.out.println("Connection with Settings closed.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public StringProperty amharicWordProperty() {
        return amharicWord;
    }

    public StringProperty englishWordProperty() {
        return englishWord;
    }

    public StringProperty languageWordProperty() {
        return languageWord;
    }

    public StringProperty radiosWordProperty() {
        return radiosWord;
    }

    public StringProperty moviesWordProperty() {
        return moviesWord;
    }

    public StringProperty settingsWordProperty() {
        return settingsWord;
    }

    public StringProperty themeWordProperty() {
        return themeWord;
    }

    public StringProperty titleWordProperty() {
        return titleWord;
    }

    public StringProperty tvWordProperty() {
        return tvWord;
    }

    public StringProperty videosWordProperty() {
        return videosWord;
    }

    public StringProperty volumeWordProperty() {
        return volumeWord;
    }

    public StringProperty welcomeWordProperty() {
        return welcomeWord;
    }

    public  String getWelcomeWord() {
        return welcomeWord.get();
    }

    public  String getVolumeWord() {
        return volumeWord.get();
    }

    public  String getVideosWord() {
        return videosWord.get();
    }

    public  String getTvWord() {
        return tvWord.get();
    }

    public  String getTitleWord() {
        return titleWord.get();
    }

    public  String getThemeWord() {
        return themeWord.get();
    }

    public  String getSettingsWord() {
        return settingsWord.get();
    }

    public  String getRadiosWord() {
        return radiosWord.get();
    }

    public String getMoviesWord() {
        return moviesWord.get();
    }

    public  String getLanguageWord() {
        return languageWord.get();
    }

    public  String getEnglishWord() {
        return englishWord.get();
    }

    public  String getAmharicWord() {
        return amharicWord.get();
    }

    public void setAmharicWord(String amharicWord) {
        this.amharicWord.set(amharicWord);
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord.set(englishWord);
    }

    public void setLanguageWord(String languageWord) {
        this.languageWord.set(languageWord);
    }

    public StringProperty mainLanguageProperty() {
        return mainLanguage;
    }

    public void setMoviesWord(String moviesWord) {
        this.moviesWord.set(moviesWord);
    }

    public void setRadiosWord(String radiosWord) {
        this.radiosWord.set(radiosWord);
    }

    public void setSettingsWord(String settingsWord) {
        this.settingsWord.set(settingsWord);
    }

    public void setThemeWord(String themeWord) {
        this.themeWord.set(themeWord);
    }

    public void setTitleWord(String titleWord) {
        this.titleWord.set(titleWord);
    }

    public void setTvWord(String tvWord) {
        this.tvWord.set(tvWord);
    }

    public void setVideosWord(String videosWord) {
        this.videosWord.set(videosWord);
    }

    public void setVolumeWord(String volumeWord) {
        this.volumeWord.set(volumeWord);
    }

    public void setWelcomeWord(String welcomeWord) {
        this.welcomeWord.set(welcomeWord);
    }

    public String getAddNewChannelWord() {
        return addNewChannelWord.get();
    }

    public StringProperty addNewChannelWordProperty() {
        return addNewChannelWord;
    }

    public String getNewStreamUrlWord() {
        return newStreamUrlWord.get();
    }

    public StringProperty newStreamUrlWordProperty() {
        return newStreamUrlWord;
    }

    public String getNewImageUrlWord() {
        return newImageUrlWord.get();
    }

    public StringProperty newImageUrlWordProperty() {
        return newImageUrlWord;
    }

    public String getNewChannelNameWord() {
        return newChannelNameWord.get();
    }

    public StringProperty newChannelNameWordProperty() {
        return newChannelNameWord;
    }

    public String getNewCancelWord() {
        return newCancelWord.get();
    }

    public StringProperty newCancelWordProperty() {
        return newCancelWord;
    }

    public String getNewAddWord() {
        return newAddWord.get();
    }

    public StringProperty newAddWordProperty() {
        return newAddWord;
    }

    public String getCategoryWord() {
        return categoryWord.get();
    }

    public StringProperty categoryWordProperty() {
        return categoryWord;
    }

    public void setAddNewChannelWord(String addNewChannelWord) {
        this.addNewChannelWord.set(addNewChannelWord);
    }

    public void setCategoryWord(String categoryWord) {
        this.categoryWord.set(categoryWord);
    }

    public void setNewAddWord(String newAddWord) {
        this.newAddWord.set(newAddWord);
    }

    public void setNewCancelWord(String newCancelWord) {
        this.newCancelWord.set(newCancelWord);
    }

    public void setNewChannelNameWord(String newChannelNameWord) {
        this.newChannelNameWord.set(newChannelNameWord);
    }

    public void setNewImageUrlWord(String newImageUrlWord) {
        this.newImageUrlWord.set(newImageUrlWord);
    }

    public void setNewStreamUrlWord(String newStreamUrlWord) {
        this.newStreamUrlWord.set(newStreamUrlWord);
    }

    public String getOromicWord() {
        return oromicWord.get();
    }

    public StringProperty oromicWordProperty() {
        return oromicWord;
    }

    public void setOromicWord(String oromicWord) {
        this.oromicWord.set(oromicWord);
    }


    public String getSearchWord() {
        return searchWord.get();
    }

    public StringProperty searchWordProperty() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord.set(searchWord);
    }


}
