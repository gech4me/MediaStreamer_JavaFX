package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import model.language.LanguageResolver;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Getachew on 6/9/2016.
 *
 * @author Getachew Mulat
 */
public class HomepageController implements Initializable {

    private MainClass mainClass;
    private MainWindowController mainWindowController;
    private LanguageResolver languageResolver;

    private final URL fontUrl = HomepageController.this.getClass().getClassLoader().getResource("resource/font/nyala.ttf");
    private final Font nyalaFont = Font.loadFont(fontUrl != null ? fontUrl.toExternalForm() : null, 12);

    @FXML private AnchorPane welcomeTitlePane;
    @FXML private Label welcomeText;
    @FXML private TextField searchField;
    @FXML private Label moviesText;
    @FXML private Label videosText;
    @FXML private Label radiosText;
    @FXML private Label tvText;
    @FXML private Label settingText;

    @FXML private StackPane moviesPane;
    @FXML private StackPane videosPane;
    @FXML private StackPane tvPane;
    @FXML private StackPane radiosPane;
    @FXML private StackPane settingPane;

    /**
     *  Set the mainClass, connect MainClass and homepageController
     * @param mainClass object of MainClass
     */
    public void setMainClass(MainClass mainClass) {
        this.mainClass = mainClass;
    }

    /**
     *  set the mainWindowController, connect mainWindowController and homepageController
     * @param mainWindowController object of MainWindowController
     */
    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    /**
     *  This method call before stage shown
     * @param location url object
     * @param resources resourceBundle object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        moviesText.setFont(Font.font(nyalaFont.getFamily(), 15));
        welcomeText.setFont(Font.font(nyalaFont.getFamily(), 28));
        settingText.setFont(Font.font(nyalaFont.getFamily(), 15));
        tvText.setFont(Font.font(nyalaFont.getFamily(), 15));
        radiosText.setFont(Font.font(nyalaFont.getFamily(), 15));
        videosText.setFont(Font.font(nyalaFont.getFamily(), 15));
        searchField.setFont(Font.font(nyalaFont.getFamily(), 16));
        search();
        updateLanguage();
        action();
    }

    /**
     *  Actions performed on homepage window(HomepageController)
     */
    private void action() {
        moviesPane.setOnMouseClicked(event -> mainWindowController.externalAction("Movies"));
        tvPane.setOnMouseClicked(event -> mainWindowController.externalAction("Tv"));
        radiosPane.setOnMouseClicked(event -> mainWindowController.externalAction("Radios"));
        videosPane.setOnMouseClicked(event -> mainWindowController.externalAction("Videos"));
        settingPane.setOnMouseClicked(event -> mainWindowController.externalSettingAccess());
    }

    /**
     *  methods for searchField, and action performed.
     */
    public void search() {

        if (searchField != null) {
            searchField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!searchField.isFocused()) {
                    searchField.clear();
                }
            });
            searchField.setOnMouseClicked(event -> searchField.clear());

            searchField.setOnAction(event -> mainWindowController.search(searchField.getText()));
        }
    }

    /**
     *  automatically update the language of HomepageView.
     *  can be called on other class.
     */
    public void updateLanguage() {
        languageResolver = new LanguageResolver();
        welcomeText.textProperty().bind(languageResolver.welcomeWordProperty());
        searchField.promptTextProperty().bind(languageResolver.searchWordProperty());
        moviesText.textProperty().bind(languageResolver.moviesWordProperty());
        tvText.textProperty().bind(languageResolver.tvWordProperty());
        videosText.textProperty().bind(languageResolver.videosWordProperty());
        radiosText.textProperty().bind(languageResolver.radiosWordProperty());
        settingText.textProperty().bind(languageResolver.settingsWordProperty());
    }
}
