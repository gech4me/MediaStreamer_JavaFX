package controller;

import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.channel.ChannelResolver;
import model.channel.ChannelsLoader;
import model.database.Search;
import model.language.LanguageResolver;
import model.theme.SVGProvider;
import model.theme.ThemeResolver;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Created by Getachew Mulat on 5/5/2016.
 * Controller the MainWindowView.fxml
 * @author Getachew Mulat
 */
public class MainWindowController implements Initializable {

    private MainClass mainClass;
    private LanguageResolver languageResolver;
    private ThemeResolver themeResolver;
    private SVGProvider svgProvider;
    private Stage primaryStage;
    private HomepageController homepageController;

    private final URL fontUrl = MainWindowController.this.getClass().getClassLoader().getResource("resource/font/nyala.ttf");
    private  final Font nyalaFont = Font.loadFont(fontUrl != null ? fontUrl.toExternalForm() : null, 12);

    private SVGPath play = new SVGPath();
    private SVGPath fastRewind = new SVGPath();
    private SVGPath fastForward = new SVGPath();
    private SVGPath stop = new SVGPath();
    private SVGPath repeat = new SVGPath();
    private SVGPath fullscreen = new SVGPath();

    private StackPane playPane;
    private StackPane fastRewindPane;
    private StackPane fastForwardPane;
    private StackPane stopPane;
    private StackPane repeatPane;
    private StackPane fullscreenPane;

    private SVGPath volumeSVG = new SVGPath();
    private Slider volumeSlider = new Slider();
    private Label volumeName = new Label();
    private Label volumeStatus = new Label(": " +  String.format("%.0f", volumeSlider.getValue()) + "%");
    private Label currentTimeStatus = new Label();
    private Label totalTimeStatus = new Label();
    private Slider durationSlider = new Slider(0, 100, 1);
    private double RATE = 1.0;
    private int REPEAT_COUNT = 1;

    @FXML private BorderPane root;
    @FXML protected AnchorPane topPane;
    @FXML private StackPane homepagePane;
    @FXML private Pane tvPane;
    @FXML private Pane moviesPane;
    @FXML private Pane videosPane;
    @FXML private Pane radiosPane;
    @FXML private Pane helpPane;
    @FXML private Pane aboutPane;
    @FXML private Pane menuPane;
    @FXML private AnchorPane leftPane;
    @FXML private TextField searchText;
    @FXML private SVGPath searchSVG;
    @FXML private Pane settingPane;
    @FXML private Pane minimizePane;
    @FXML private Pane maximizePane;
    @FXML private SVGPath maximizeSVG;
    @FXML private Pane closePane;
    @FXML private Pane backPane;
    @FXML private StackPane homePane;
    @FXML private Label titleText;
    @FXML private Label moviesText;
    @FXML private Label tvText;
    @FXML private Label radiosText;
    @FXML private Label videosText;

    private MediaPlayer mediaPlayer;
    private String CHANNEL_TYPE = "";
    private String TYPE = "";
    private StringProperty mediaNameString = new SimpleStringProperty(this, null, "Buffering....");
    private static String LANGUAGE;
    private static String THEME;

    /* Related with Setting method */
    private Label settingTitle = new Label();
    private Label language = new Label();
    private Label amharicLanguage = new Label();
    private Label englishLanguage = new Label();
    private Label afaanOromooLanguage = new Label();
    private Label theme = new Label();
    private ComboBox<Label> languageChoice = new ComboBox<>();
    ComboBox<String> themeChoice = new ComboBox<>();

    private SVGPath addSVG = new SVGPath();
    private Label channelTitle = new Label();

    private Label category = new Label();
    private Label tvWord = new Label();
    private Label videosWord = new Label();
    private Label moviesWord = new Label();
    private Label radiosWord = new Label();

    private Label addNewChannelTitle = new Label();
    private Label newChannelName = new Label();
    private Label newImageUrl = new Label();
    private Label newChannelUrl = new Label();
    private Label newMediaStatus = new Label();
    private Button cancelBtn = new Button();
    private Button addBtn = new Button();


    private TextField newChannelNameField = new TextField();
    private TextField newImageField = new TextField();
    private TextField newChannelUrlField = new TextField();

    ComboBox<Label> listOfCategory;

    /**
     *  This method call before stage shown
     * @param location url object
     * @param resource resourceBundle object
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        volumeName.setFont(Font.font(nyalaFont.getName(), 25));
        volumeName.setStyle("-fx-text-fill: whitesmoke");
        volumeStatus.setGraphic(volumeName);

        play.setId("playSVG");
        stop.setId("stopSVG");
        fastForward.setId("fastForwardSVG");
        fastRewind.setId("fastRewindSVG");
        repeat.setId("repeatSVG");
        fullscreen.setId("fullscreenSVG");
        volumeSVG.setId("volumeSVG");

        titleText.setFont(Font.font(nyalaFont.getFamily(), 15));
        moviesText.setFont(Font.font(nyalaFont.getFamily(), 15));
        tvText.setFont(Font.font(nyalaFont.getFamily(), 15));
        radiosText.setFont(Font.font(nyalaFont.getFamily(), 15));
        videosText.setFont(Font.font(nyalaFont.getFamily(), 15));
        channelTitle.setFont(Font.font(nyalaFont.getFamily(), 25));

        svgProvider = new SVGProvider();
        play.contentProperty().bind(svgProvider.playSvgProperty());
        stop.contentProperty().bind(svgProvider.stopSvgProperty());
        fastRewind.contentProperty().bind(svgProvider.fastRewindProperty());
        fastForward.contentProperty().bind(svgProvider.fastForwardProperty());
        repeat.contentProperty().bind(svgProvider.repeatOneSvgProperty());
        fullscreen.contentProperty().bind(svgProvider.fullscreenSvgProperty());
        volumeSVG.contentProperty().bind(svgProvider.volumeUpProperty());
        addSVG.contentProperty().bind(svgProvider.addSvgProperty());


        searchText.setFont(Font.font(nyalaFont.getFamily(), 14));

        actions();
        search(null);
    }

    /**
     *  Connect MainClass and MainWindowController, set the MainClass Object on field
     * @param mainClass Object of MainClass
     */
    void setMainClass(MainClass mainClass) {
        this.mainClass = mainClass;
    }
    /**
     *  Load homepage from HomepageController, by execute the MainClass homepageWindow, accept the return value,
     *  and set Center on root.
     */
    protected void loadHomepage() {
        try {
            homepagePane.getChildren().add(mainClass.homepageWindow());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  remove the previous HomepageController from root center
     *  and set to again to refresh the language.
     */
    protected void home() {
        homepagePane.getChildren().remove(0);
        homepagePane.getChildren().add(mainClass.homepageWindow());
        root.setLeft(leftPane);
        root.setCenter(homepagePane);
        if(mediaPlayer != null) {
            mediaPlayer.dispose();
        }
    }

    /**
     *  update theme of the scene, by execute MainClass theme method.
     *  First, compare the Theme name, if match
     *  second, execute the mainClass them method.
     */
    private void updateTheme() {
        themeResolver = new ThemeResolver();

        THEME = themeResolver.getMainTheme();
        if(Objects.equals(THEME, "Darcula")) {
            mainClass.setTheme(THEME);
        } else if(Objects.equals(THEME, "Zerone")) {
            mainClass.setTheme(THEME);
        }
    }

    /**
     *  automatically update language, of the scene
     *  First, create object of the LanguageResolver class
     *  second, bind the textProperty of the nodes.
     *  and update Setting Window language naming.
     */
    private void updateLanguage() {

        languageResolver = new LanguageResolver();

        searchText.promptTextProperty().bind(languageResolver.searchWordProperty());

        settingTitle.textProperty().bind(languageResolver.settingsWordProperty());
        titleText.textProperty().bind(languageResolver.titleWordProperty());
        moviesText.textProperty().bind(languageResolver.moviesWordProperty());
        tvText.textProperty().bind(languageResolver.tvWordProperty());
        radiosText.textProperty().bind(languageResolver.radiosWordProperty());
        videosText.textProperty().bind(languageResolver.videosWordProperty());
        volumeName.textProperty().bind(languageResolver.volumeWordProperty());
        amharicLanguage.textProperty().bind(languageResolver.amharicWordProperty());
        englishLanguage.textProperty().bind(languageResolver.englishWordProperty());
        afaanOromooLanguage.textProperty().bind(languageResolver.oromicWordProperty());
        language.textProperty().bind(languageResolver.languageWordProperty());
        theme.textProperty().bind(languageResolver.themeWordProperty());

        tvWord.textProperty().bind(languageResolver.tvWordProperty());
        videosWord.textProperty().bind(languageResolver.videosWordProperty());
        moviesWord.textProperty().bind(languageResolver.moviesWordProperty());
        radiosWord.textProperty().bind(languageResolver.radiosWordProperty());

        addNewChannelTitle.textProperty().bind(languageResolver.addNewChannelWordProperty());
        category.textProperty().bind(languageResolver.categoryWordProperty());
        newChannelName.textProperty().bind(languageResolver.newChannelNameWordProperty());
        newImageUrl.textProperty().bind(languageResolver.newImageUrlWordProperty());
        newChannelUrl.textProperty().bind(languageResolver.newStreamUrlWordProperty());
        cancelBtn.textProperty().bind(languageResolver.newCancelWordProperty());
        addBtn.textProperty().bind(languageResolver.newAddWordProperty());



        if(Objects.equals(languageResolver.getMainLanguage(), "English")) {
            languageChoice.getSelectionModel().select(0);
        } else if(Objects.equals(languageResolver.getMainLanguage(), "Amharic")) {
            languageChoice.getSelectionModel().select(1);
        } else if(Objects.equals(languageResolver.getMainLanguage(), "AfaanOromoo")) {
            languageChoice.getSelectionModel().select(2);
        }

        LANGUAGE = languageResolver.getMainLanguage();


    }

    /**
     *  update language of the ChannelWindows,
     *  first, compare if Channel is click by channelTitle
     *  then if the are not click, return
     *  second, get the type of the channel then update the Channel titles.
     */
    private void updateChannelLanguage() {
        if(Objects.equals(channelTitle, null) || Objects.equals(languageResolver, null)) {
            return;
        }
        switch (TYPE) {
            case "Movies":
                channelTitle.setText(languageResolver.getMoviesWord());
                break;
            case "Tv":
                channelTitle.setText(languageResolver.getTvWord());
                break;
            case "Videos":
                channelTitle.setText(languageResolver.getVideosWord());
                break;
            case "Radios":
                channelTitle.setText(languageResolver.getRadiosWord());
                break;
        }
    }

    /**
     *  All pane action performed here.
     *  list: tvPane, moviesPane..., settingPane...
     *  and detect the size of root and update the leftSide
     *  Alert Window also defined here.
     */
    private void actions() {

        tvPane.setOnMouseClicked(event -> root.setCenter(createBoard("Tv", "")));
        moviesPane.setOnMouseClicked(event -> root.setCenter(createBoard("Movies", "")));
        videosPane.setOnMouseClicked(event -> root.setCenter(createBoard("Videos", "")));
        radiosPane.setOnMouseClicked(event -> root.setCenter(createBoard("Radios", "")));

        settingPane.setOnMouseClicked(event -> root.setCenter(createSetting()));
        homePane.setOnMouseClicked(event -> home());

        menuPane.setOnMouseClicked(event -> {
            if(leftPane.getWidth() > 60) {
                updateSideToLess();
            } else {
                updateSideToHigh();
            }
        });

        root.widthProperty().addListener(observable -> {
            if(root.getWidth() < 850) {
               updateSideToLess();
            } else {
                updateSideToHigh();
            }
        });

        helpPane.setOnMouseClicked(event -> {

            try {

                File helpFile = new File("ZeroneHelp.url");

                Desktop.getDesktop().open(helpFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        aboutPane.setOnMouseClicked(event -> {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.GRAY);
            dropShadow.setOffsetY(3.0);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            StackPane stackPane = new StackPane();

            for(int i = 0; i < 16; i++) {
                Ellipse e1 = new Ellipse(150, 100, 100, 50);
                e1.setEffect(dropShadow);
                e1.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
                e1.setFill(Color.WHITE);
                e1.setRotate(i * 180 / 16);
                stackPane.getChildren().add(e1);

            }

            Text text = new Text("Created by 01");
            text.setFont(Font.font(20));
            text.setFill(Color.DARKSLATEGRAY);
            text.setEffect(dropShadow);
            text.setRotate(-14);
            stackPane.getChildren().add(text);

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), text);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.2);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.setAutoReverse(true);
            fadeTransition.play();

            Text message = new Text("Zerone Media Streamer 0.1 beta version" +
                    "\n© 2016 Zerone  Technology. All rights reserved." + "\n@author Getachew Mulat "+
                    "\ncontact: gech2me@gmail.com " +
                    "\nThis software are protected by trademark and other pending or " +
                    "existing intellectual property rights in Wolkite University " +
                    "and also in Ethiopia. " +
                    "\nFurther more this software is intellectual property of Zerone Technology.");

            stackPane.setPrefSize(200, 200);

            alert.setTitle("About Zereone Media Streamer");
            alert.setGraphic(stackPane);
            alert.setContentText(message.getText());
            alert.setHeight(400);
            alert.setWidth(400);
            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }

    /**
     *  External action, actions that are performed by Homepage controller
     *  accept String object and compare, set to root Center.
     * @param str Object of String.
     */
    public void externalAction(String str) {
        if(Objects.equals(str, "Tv")) {
            root.setCenter(createBoard(str, ""));
        } else if(Objects.equals(str, "Movies")) {
            root.setCenter(createBoard(str, ""));
        } else if(Objects.equals(str, "Videos")) {
            root.setCenter(createBoard(str, ""));
        } else if(Objects.equals(str, "Radios")) {
            root.setCenter(createBoard(str, ""));
        }
    }

    /**
     *  Size the leftPane, and disable searchText Object
     *  change the color of the searchSVG, depend on THEME.
     */
    private void updateSideToLess() {
        KeyValue toWidth = new KeyValue(leftPane.prefWidthProperty(), 40, Interpolator.LINEAR);
        KeyFrame myInitFrame = new KeyFrame(Duration.millis(40), toWidth);
        Timeline timeline = new Timeline(myInitFrame);
        timeline.setCycleCount(1);
        timeline.play();
        searchText.setVisible(false);
        if(Objects.equals(THEME, "Darcula")) {
            searchSVG.setStyle("-fx-fill: whitesmoke");
        } else if(Objects.equals(THEME, "Darcula")) {
            searchSVG.setStyle("-fx-fill: black");
        }
    }

    /**
     *  Size the leftPane, and enable SearchText Object
     *  change the color of the searchSVG to black.
     */
    private void updateSideToHigh() {
            KeyValue toWidth = new KeyValue(leftPane.prefWidthProperty(), 210, Interpolator.LINEAR);
            KeyFrame myInitFrame = new KeyFrame(Duration.millis(40), toWidth);
            Timeline timeline = new Timeline(myInitFrame);
            timeline.setCycleCount(1);
            timeline.play();
            searchText.setVisible(true);
            searchSVG.setStyle("-fx-fill: black");
    }

    /**
     *  Accept channel Type and load the Channel from createChannels method.
     * @param type
     * @param searchText if want to search channel on the Database, accept and transfer to createChannel method.
     * @return Object of BorderPane
     */
    protected BorderPane createBoard(String type, String searchText) {
        TYPE = type;
        channelTitle.setText(type);
        updateChannelLanguage();
        channelTitle.setId("channelTitle");
        channelTitle.setPrefWidth(100.0);
        AnchorPane.setTopAnchor(channelTitle, 0.0);
        AnchorPane.setLeftAnchor(channelTitle, 20.0);
        AnchorPane.setBottomAnchor(channelTitle, 0.0);

        addSVG.setScaleX(0.5);
        addSVG.setScaleY(0.5);
        addSVG.setId("addSVG");

        StackPane addPane = new StackPane(addSVG);
        addPane.setPrefSize(40.0, 15.0);
        AnchorPane.setTopAnchor(addPane, 0.0);
        AnchorPane.setRightAnchor(addPane, 20.0);
        AnchorPane.setBottomAnchor(addPane, 0.0);
        addPane.setId("addPane");
        addPane.setOnMouseClicked(event -> createNewContentBoard());

        AnchorPane titlePane = new AnchorPane(channelTitle, addPane);
        titlePane.setId("channelTitlePane");

        FlowPane contentPane = new FlowPane();
        contentPane.setId("channelContentPane");

        try {
            contentPane.getChildren().addAll(createChannels(type, searchText));
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }

        contentPane.setHgap(10);
        contentPane.setVgap(10);

        ScrollPane scrollPane = new ScrollPane(contentPane);
        contentPane.setPrefWidth(scrollPane.getWidth());
        contentPane.setPrefHeight(scrollPane.getHeight());
        scrollPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            contentPane.setPrefWidth(scrollPane.getWidth());
        });
        scrollPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            contentPane.setPrefHeight(scrollPane.getHeight());
        });
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        BorderPane board = new BorderPane();
        board.setTop(titlePane);
        board.setCenter(scrollPane);

        return board;
    }

    /**
     *  Accept ChannelType and retrieve the date from ChannelLoader model class
     * @param channelType Object of String.
     * @param searchText Object of String
     * @return ObservableList of Pane object.
     * @throws URISyntaxException
     * @throws MalformedURLException
     */
    private ObservableList<Pane> createChannels(String channelType, String searchText) throws URISyntaxException, MalformedURLException {
        int size;
        List<String> imagePath;
        List<String> videoPath;
        List<String> videoTitle;
        ObservableList<ImageView> imageViews = FXCollections.observableArrayList();
        ObservableList<Label> labels = FXCollections.observableArrayList();
        ObservableList<Pane> panes = FXCollections.observableArrayList();

        ChannelsLoader channelsLoader = new ChannelsLoader();
        channelsLoader.getChannel(channelType, searchText);

        size = channelsLoader.getSize();
        imagePath = channelsLoader.getImagePath();
        videoPath = channelsLoader.getVideoPath();
        videoTitle = channelsLoader.getVideoTitle();

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(20, 20, 75, .688));
        dropShadow.setOffsetX(4);
        dropShadow.setOffsetY(6);

        for(int i = 0; i < size; i++) {

            URI imageUri = new URI(imagePath.get(i));
            URL imageUrl = imageUri.toURL();

            ImageView imageView  = new ImageView(imageUrl.toExternalForm());
            imageView.setFitWidth(120);
            imageView.setFitHeight(70);
            imageViews.add(i, imageView);

            Label label = new Label();
            label.setGraphic(imageViews.get(i));
            label.setAlignment(Pos.CENTER);
            label.setPrefSize(130, 80);
            labels.add(i , label);

            Label title = new Label(videoTitle.get(i));
            title.setId("contentTitle");

            title.setWrapText(true);

            VBox contentBox = new VBox(labels.get(i), title);

            contentBox.setAlignment(Pos.CENTER);
            contentBox.setId("contentBox");
            contentBox.setEffect(dropShadow);

            final int finalI = i;
            final List<String> finalVideoPath = videoPath;
            final List<String> finalVideoTitle = videoTitle;
            contentBox.setOnMouseClicked(event -> {
                    backPane.setDisable(false);
                    backPane.setOpacity(1.0);
                    root.setCenter(createMediaPlayerBoard(finalVideoPath.get(finalI)));
                    root.setLeft(null);
                CHANNEL_TYPE = channelType;
                mediaNameString.setValue(finalVideoTitle.get(finalI));
            });

            panes.add(contentBox);
        }

        return panes;
    }

    /**
     *  Accept video url, then create MediaPlayer window and transfer the video url to MediaView on
     *  createMediaView method.
     *  and initialize the mediaView action.
     * @param videoPath Object of String
     * @return object of AnchorPane
     */
    private AnchorPane createMediaPlayerBoard(String videoPath) {

        StackPane mediaViewPane = new StackPane(createMediaView(videoPath));
        AnchorPane.setTopAnchor(mediaViewPane, 0.0);
        AnchorPane.setRightAnchor(mediaViewPane, 0.0);
        AnchorPane.setBottomAnchor(mediaViewPane, 0.0);
        AnchorPane.setLeftAnchor(mediaViewPane, 0.0);

        AnchorPane mediaControlPane = new AnchorPane(createMediaControlBoard());
        mediaControlPane.setPrefHeight(50);
        AnchorPane.setBottomAnchor(mediaControlPane, 10.0);
        AnchorPane.setLeftAnchor(volumeStatus, 10.0);

        volumeStatus.setFont(Font.font(nyalaFont.getFamily(), 15));
        AnchorPane mediaPlayerBoard = new AnchorPane(mediaViewPane, mediaControlPane, volumeStatus);

        FadeTransition fadeTransitionMediaControl = new FadeTransition(Duration.seconds(2), mediaControlPane);

        mediaPlayerBoard.setOnMouseEntered(event -> {
            if(primaryStage.isFullScreen()) {
                event.consume();
            } else if(fadeTransitionMediaControl.getNode().getOpacity() <= 0.87) {
                fadeTransitionMediaControl.setToValue(0.87);
                fadeTransitionMediaControl.playFromStart();
            }
        });

        mediaPlayerBoard.setOnMouseExited(event -> {
            if(fadeTransitionMediaControl.getNode().getOpacity() >= 0.0) {
                fadeTransitionMediaControl.setToValue(0.0);
                fadeTransitionMediaControl.playFromStart();
            }
        });

        createMediaAction();
        return mediaPlayerBoard;
    }

    /**
     *  all mediaActions performed here.
     *  and also backPane actions performed here.
     */
    private void createMediaAction() {

        backPane.setOnMouseClicked(event -> {
            mediaPlayer.dispose();
            backPane.setDisable(true);
            backPane.setOpacity(0.0);
            root.setLeft(leftPane);
            if(Objects.equals(CHANNEL_TYPE, "")) {
                home();
            } else {
                root.setCenter(createBoard(CHANNEL_TYPE, ""));
            }

        });

        volumeStatus.setOpacity(0.0);
        volumeStatus.setDisable(true);
        volumeStatus.setId("volumeStatus");

        FadeTransition fadeTransitionVolumeStatus = new FadeTransition(Duration.seconds(3), volumeStatus);
        mediaPlayer.volumeProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            volumeProperty(fadeTransitionVolumeStatus);
        });
        volumeSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            volumeProperty(fadeTransitionVolumeStatus);
        });
        volumeSlider.setOnMouseReleased(event -> {
            fadeTransitionVolumeStatus.setToValue(0.0);
            fadeTransitionVolumeStatus.playFromStart();
        });
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);

        playPane.setOnMouseClicked(event -> {
            if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
        });

        stopPane.setOnMouseClicked(event -> {
            if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING || mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                mediaPlayer.stop();
                mediaPlayer.seek(Duration.ZERO);
                double totalDuration = mediaPlayer.getTotalDuration().toMillis();
                double currentTime = mediaPlayer.getCurrentTime().toMillis();
                durationSlider.setMax(totalDuration);
                durationSlider.setValue(currentTime);
            }
        });

        mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) -> {
            if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                play.contentProperty().unbind();
                play.contentProperty().bind(svgProvider.pauseSvgProperty());
            } else if(mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED ||
                    mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                play.contentProperty().unbind();
                play.contentProperty().bind(svgProvider.playSvgProperty());
            }
        });

        mediaPlayer.currentTimeProperty().addListener((observable) -> {
            if (mediaPlayer != null) {
                double totalDuration = mediaPlayer.getTotalDuration().toMillis();
                double currentTime = mediaPlayer.getCurrentTime().toMillis();
                durationSlider.setMax(totalDuration);
                durationSlider.setValue(currentTime);

                currentTimeStatus.setText(formatDuration(mediaPlayer.getCurrentTime()));
                totalTimeStatus.setText(formatDuration(mediaPlayer.getTotalDuration()));
            }
        });

        durationSlider.valueProperty().addListener((observable) -> {
            if(mediaPlayer != null && durationSlider.isValueChanging()) {
                double currentValue = durationSlider.getValue();
                mediaPlayer.seek(Duration.millis(currentValue));
            }
        });

        fastForwardPane.setOnMouseClicked(event -> {
            if(mediaPlayer != null && RATE <= 8.0) {
                RATE += 0.5;
                mediaPlayer.setRate(RATE);
            }
        });

        fastRewindPane.setOnMouseClicked(event -> {
            if(mediaPlayer != null && RATE > 0.5) {

                RATE -= 0.5;
                mediaPlayer.setRate(RATE);
            }
        });

        repeatPane.setOnMouseClicked(event -> {
            System.out.println(event.getEventType().getName());
            System.out.println(REPEAT_COUNT);

            if(REPEAT_COUNT == 1 ) {
                REPEAT_COUNT = 2;
                repeat.setFill(Color.CYAN);
                if (event.isAltDown()) {
                    repeat.contentProperty().unbind();
                    repeat.contentProperty().bind(svgProvider.repeatAllSvgProperty());
                    mediaPlayer.setCycleCount(Integer.MAX_VALUE);
                } else {
                    repeat.contentProperty().unbind();
                    repeat.contentProperty().bind(svgProvider.repeatOneSvgProperty());
                    mediaPlayer.setCycleCount(2);
                }
            } else {
                repeat.setFill(Color.WHITESMOKE);
                mediaPlayer.setCycleCount(1);
                REPEAT_COUNT = 1;
            }
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            System.out.println("Current Count: " + mediaPlayer.getCurrentCount());
            System.out.println("Cycle Count: " + mediaPlayer.getCycleCount());

            if(mediaPlayer.getCycleCount() == mediaPlayer.getCurrentCount() || mediaPlayer.getCurrentCount() > 2) {
                repeat.setFill(Color.WHITESMOKE);
                repeat.contentProperty().unbind();
                repeat.contentProperty().bind(svgProvider.repeatOneSvgProperty());
                mediaPlayer.setCycleCount(1);
                REPEAT_COUNT = 1;
                mediaPlayer.stop();
                mediaPlayer.seek(Duration.ZERO);
            }
        });

        fullscreenPane.setOnMouseClicked(event -> {
            root.setTop(null);
            if(primaryStage.isFullScreen()) {
                primaryStage.setFullScreen(false);
                root.setMouseTransparent(false);
                root.setTop(topPane);
            } else {
                primaryStage.setFullScreen(true);
                root.setMouseTransparent(true);
                root.setTop(null);
            }

        });

        primaryStage.fullScreenProperty().addListener(observable -> {
            if(!primaryStage.isFullScreen()) {
                root.setTop(topPane);
                root.setMouseTransparent(false);
            }
        });

        durationSlider.setOnMousePressed(event -> mediaPlayer.seek(Duration.millis(durationSlider.getValue())));
        volumeSlider.setOnMousePressed(event -> mediaPlayer.setVolume(volumeSlider.getValue() / 100.0));

    }

    /**
     *  update the volumeSVG content propery and set volumeStatus Text propetry.
     * @param fadeTransition
     */
    private void volumeProperty(FadeTransition fadeTransition) {
        mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
        if(volumeSlider.getValue() == 0.0) {
            volumeSVG.contentProperty().unbind();
            volumeSVG.contentProperty().bind(svgProvider.volumeOffProperty());
            fadeTransition.setToValue(0.0);
            fadeTransition.playFromStart();
        } else if(volumeSlider.getValue() < 50) {
            volumeSVG.contentProperty().unbind();
            volumeSVG.contentProperty().bind(svgProvider.volumeDownProperty());
            fadeTransition.setToValue(0.0);
            fadeTransition.playFromStart();
        } else {
            volumeSVG.contentProperty().unbind();
            volumeSVG.contentProperty().bind(svgProvider.volumeUpProperty());
            fadeTransition.setToValue(0.0);
            fadeTransition.playFromStart();
        }
        volumeStatus.setDisable(false);
        volumeStatus.setOpacity(1.0);
        volumeStatus.setFont(Font.font(nyalaFont.getFamily(), 25));
        volumeStatus.setText(": " +  String.format("%.0f", volumeSlider.getValue()) + "%");
    }

    /**
     *  Accept the media url, verify if the url is valid, then
     *  create a mediaPlayer with mediaView
     * @param mediaPath Object of String
     * @return Object of MediaView
     */
    private MediaView createMediaView(String mediaPath) {
        URI mediaUri = null;
        URL mediaUrl = null;

        try {
            mediaUri = new URI(mediaPath);
            mediaUrl= mediaUri.toURL();

        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("URL: " + (mediaUrl != null ? mediaUrl.toString() : null));
        System.out.println("URI: " + mediaUri.toString());

        String mediaStringUrl = null;
        if (mediaUrl != null) {
            mediaStringUrl = mediaUrl.toExternalForm();
        }

        System.out.println(mediaStringUrl);
        Media media = new Media(mediaStringUrl);

        mediaPlayer = new MediaPlayer(media);

        MediaView mediaView = new MediaView(mediaPlayer);

        final DoubleProperty width = mediaView.fitWidthProperty();
        final DoubleProperty height = mediaView.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        mediaView.setPreserveRatio(true);
        mediaView.setManaged(true);

        AnchorPane.setTopAnchor(mediaView, 0.0);
        AnchorPane.setRightAnchor(mediaView, 0.0);
        AnchorPane.setBottomAnchor(mediaView, 0.0);
        AnchorPane.setLeftAnchor(mediaView, 0.0);

        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.67);
        return mediaView;
    }

    /**
     *  Create the mediaControllerBoard, loading createDurationBox and createControlBox method.
     * @return Object of VBox
     */
    private VBox createMediaControlBoard() {
        VBox mediaControlBoard = new VBox(createDurationBox(), createControlBox());
        AnchorPane.setLeftAnchor(mediaControlBoard, 10.0);
        AnchorPane.setRightAnchor(mediaControlBoard, 10.0);

        mediaControlBoard.setPrefWidth(root.getWidth() - 20);
        root.widthProperty().addListener(observable -> {
            mediaControlBoard.setPrefWidth(root.getWidth() - 20);
        });

        mediaControlBoard.setPrefHeight(100);
        mediaControlBoard.setId("mediaControlBoard");

        return mediaControlBoard;
    }

    /**
     *  create control box, which contains playing name, createMediaBox, createVolumeBox.
     * @return Object of AnchorPane
     */
    private AnchorPane createControlBox() {

        Label playingName = new Label();
        playingName.textProperty().bind(mediaNameStringProperty());
        playingName.setPrefWidth(150.0);
        playingName.setWrapText(true);
        AnchorPane.setLeftAnchor(playingName, 10.0);
        AnchorPane.setTopAnchor(playingName, 0.0);
        AnchorPane.setBottomAnchor(playingName, 0.0);

        AnchorPane controlBox = new AnchorPane( playingName, createMediaBox(), createVolumeBox());
        controlBox.setPrefHeight(20);

        return controlBox;
    }

    /**
     *  Duration slider Box which contains duration slider.
     *  listen root width then update durationSlider size.
     * @return Object of HBox
     */
    private HBox createDurationBox() {

        durationSlider.setPrefWidth(root.getWidth() - 100);
        root.widthProperty().addListener(observable -> {
            durationSlider.setPrefWidth(root.getWidth() - 100);
        });

        HBox durationBox = new HBox(createCurrentTimeBox(), durationSlider, createTotalTimeBox());
        durationBox.setAlignment(Pos.CENTER);

        durationBox.setPrefHeight(20);
        return durationBox;
    }

    /**
     *  create CurrentTimeBox, initialize the text to "--:--"
     * @return object of HBox
     */
    private HBox createCurrentTimeBox() {
        currentTimeStatus.setText("--:--");
        currentTimeStatus.setLayoutX(2);

        return new HBox(currentTimeStatus);
    }

    /**
     *  create totalTimeBox, initialize the text to "--:--"
     * @return object of HBox
     */
    private HBox createTotalTimeBox() {
        totalTimeStatus.setText("--:--");
        totalTimeStatus.setAlignment(Pos.CENTER_RIGHT);
        totalTimeStatus.setLayoutX(-2);

        return new HBox(totalTimeStatus);
    }

    /**
     *  create MediaBox set the property
     * @return object of HBox
     */
    private HBox createMediaBox() {

        fastRewind.setScaleX(0.75);
        fastRewind.setScaleY(0.75);

        fastForward.setScaleX(0.75);
        fastForward.setScaleY(0.75);

        stop.setScaleX(0.75);
        stop.setScaleY(0.75);

        repeat.setScaleX(0.5);
        repeat.setScaleY(0.5);

        fullscreen.setScaleX(0.5);
        fullscreen.setScaleY(0.5);

        play.setFill(Color.WHITE);
        fastRewind.setFill(Color.WHITE);
        fastForward.setFill(Color.WHITE);
        stop.setFill(Color.WHITE);
        repeat.setFill(Color.WHITE);
        fullscreen.setFill(Color.WHITE);

        playPane = new StackPane(play);
        playPane.setPrefWidth(30);
        playPane.setId("playPane");

        fastRewindPane = new StackPane(fastRewind);
        fastRewindPane.setPrefWidth(30);
        fastRewindPane.setId("fastRewindPane");

        fastForwardPane = new StackPane(fastForward);
        fastForwardPane.setPrefWidth(30);
        fastForwardPane.setId("fastForwardPane");

        stopPane = new StackPane(stop);
        stopPane.setPrefWidth(30);
        stopPane.setId("stopPane");

        repeatPane = new StackPane(repeat);
        repeatPane.setPrefWidth(30);
        repeatPane.setId("repeatPane");

        fullscreenPane = new StackPane(fullscreen);
        fullscreenPane.setPrefWidth(30);
        fullscreenPane.setId("fullscreenPane");

        HBox mediaControls = new HBox(20);
        HBox tempControl = new HBox(5, fastRewindPane, stopPane, fastForwardPane, repeatPane, fullscreenPane);
        mediaControls.getChildren().addAll( playPane, tempControl);
        mediaControls.setAlignment(Pos.CENTER);

        mediaControls.setPrefHeight(20);
        AnchorPane.setRightAnchor(mediaControls, 0.0);
        AnchorPane.setLeftAnchor(mediaControls, 0.0);

        return mediaControls;
    }

    /**
     *  Volume control box, which contains volume icon and volume slider
     * @return volumeBox
     */
    private HBox createVolumeBox() {

        volumeSVG.setFill(Color.WHITE);
        volumeSVG.setStrokeWidth(30.0);
        volumeSVG.setScaleX(0.5);
        volumeSVG.setScaleY(0.5);
        StackPane volumePane = new StackPane(volumeSVG);
        volumePane.setPrefWidth(30);
        volumeSlider.setPrefWidth(160);
        HBox volumeSliderBox = new HBox(7, volumePane, volumeSlider);
        volumeSliderBox.setPrefHeight(20);

        volumeSliderBox.setAlignment(Pos.CENTER);
        AnchorPane.setRightAnchor(volumeSliderBox, 10.0);

        return volumeSliderBox;
    }

    private String formatDuration(Duration duration) {
        double millis = duration.toMillis();
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) (millis / (1000 * 60));
        return String.format("%02d:%02d", minutes, seconds);
    }

    void initFileDragNDrop(Stage stage) {

        Scene scene = stage.getScene();
        scene.setOnDragOver(event -> {
            Dragboard dragboard = event.getDragboard();
            if(dragboard.hasFiles() || dragboard.hasUrl()) {
                event.acceptTransferModes(TransferMode.LINK);
            } else {
                event.consume();
            }
        });

        scene.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            String droppedFile = null;

            if(dragboard.hasFiles() ) {
                success = true;
                if (dragboard.getFiles().size() > 0) {
                    System.out.println("Dropped");
                    droppedFile = dragboard.getFiles().get(0).getPath();
                    mediaNameString.setValue(dragboard.getFiles().get(0).getName());

                    URI pathUri = null;
                    File filePath = new File(droppedFile);
                    pathUri = filePath.toURI();

                    System.out.println(filePath);
                    if(mediaPlayer != null) {
                        mediaPlayer.stop();
                    }
                    backPane.setDisable(false);
                    backPane.setOpacity(1.0);
                    root.setCenter(createMediaPlayerBoard(pathUri.toString()));
                    root.setLeft(null);
                } else {
                    createMediaPlayerBoard(dragboard.getUrl());
                    success = true;
                }

                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;

        updateLanguage();
        updateTheme();

        primaryStage.iconifiedProperty().addListener(observable -> {
            if(mediaPlayer != null) {
                if(primaryStage.isIconified()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.play();
                }
            }
        });
    }

    protected void search(String str) {

        if(searchText != null) {
        searchText.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!searchText.isFocused()) {
                searchText.clear();
                searchText.promptTextProperty().bind(languageResolver.searchWordProperty());
            }
        });

        searchText.setOnMouseClicked(event -> {
            searchText.clear();
        });

        searchText.setOnAction(event -> {
            String searchableText = searchText.getText();
            searchAccess(searchableText);

        });

        }
        if(!Objects.equals(str, null)) {
            searchAccess(str);
        }
    }

    public void searchAccess(String searchableText) {
        boolean success = false;

        if(Objects.equals(searchableText, "")) {
            return;
        }

        if(Search.searchOnTv(searchableText)) {
            TYPE = "Tv";
            success = true;
        } else if(Search.searchOnRadios(searchableText)) {
            TYPE = "Radios";
            success = true;
        } else if(Search.searchOnMovies(searchableText)) {
            TYPE = "Movies";
            success = true;
        } else if(Search.searchOnVideos(searchableText)) {
            TYPE = "Videos";
            success = true;
        }

        if(success) {
            root.setCenter(createBoard(TYPE, searchableText));
        } else {
            Label label = new Label(" No match found for " + searchableText + "! please try again.");
            label.setStyle("-fx-text-fill: red;" +
                    "-fx-font-size: 25px;" +
                    "-fx-wrap-text: true");
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), label);
            fadeTransition.setAutoReverse(true);
            fadeTransition.setToValue(0.2);
            fadeTransition.setCycleCount(Timeline.INDEFINITE);
            StackPane stackPane = new StackPane(label);
            stackPane.setStyle("-fx-background-color: #424242");
            root.setCenter(stackPane);
            fadeTransition.play();
        }
    }

    private void createNewContentBoard() {
        newChannelNameField.clear();
        newImageField.clear();
        newChannelUrlField.clear();
        newMediaStatus.setText("");

        addNewChannelTitle.setFont(Font.font(nyalaFont.getFamily(), 25));
        addNewChannelTitle.setPrefHeight(50.0);
        addNewChannelTitle.setId("addNewChannelTitle");
        AnchorPane.setLeftAnchor(addNewChannelTitle, 20.0);
        AnchorPane.setTopAnchor(addNewChannelTitle, 0.0);
        AnchorPane.setBottomAnchor(addNewChannelTitle, 0.0);

        AnchorPane addNewChannelTitlePane = new AnchorPane(addNewChannelTitle);
        addNewChannelTitlePane.setId("addNewChannelTitlePane");

        category.setFont(Font.font(nyalaFont.getFamily(), 15));
        category.setId("category");
        category.setPrefHeight(30.0);
        newChannelName.setFont(Font.font(nyalaFont.getFamily(), 15));
        newChannelName.setId("newChannelName");
        newChannelName.setPrefHeight(30.0);
        newImageUrl.setFont(Font.font(nyalaFont.getFamily(), 15));
        newImageUrl.setId("newImageUrl");
        newImageUrl.setPrefHeight(30.0);
        newChannelUrl.setFont(Font.font(nyalaFont.getFamily(), 15));
        newChannelUrl.setId("newChannelUrl");
        newChannelUrl.setPrefHeight(30.0);

        VBox labelsBox = new VBox(20, category, newChannelName, newImageUrl, newChannelUrl);
        AnchorPane.setTopAnchor(labelsBox, 40.0);
        AnchorPane.setLeftAnchor(labelsBox, 40.0);


        newImageField.setPrefHeight(30.0);
        newImageField.setFont(Font.font(nyalaFont.getFamily(), 15));
        Button newImageBtn = new Button("...");
        newImageBtn.setPrefHeight(30.0);

        listOfCategory = new ComboBox<>();
        listOfCategory.setPrefHeight(30.0);
        tvWord.setFont(Font.font(nyalaFont.getFamily(), 15));
        videosWord.setFont(Font.font(nyalaFont.getFamily(), 15));
        moviesWord.setFont(Font.font(nyalaFont.getFamily(), 15));
        radiosWord.setFont(Font.font(nyalaFont.getFamily(), 15));

        listOfCategory.getItems().addAll(tvWord, radiosWord, moviesWord, videosWord);

        newChannelNameField.setPrefHeight(30.0);
        newChannelNameField.setFont(Font.font(nyalaFont.getFamily(), 15));
        HBox imageBox = new HBox(5, newImageField, newImageBtn);
        imageBox.setPrefHeight(30.0);

        newChannelUrlField.setPrefHeight(30.0);
        newChannelUrlField.setFont(Font.font(nyalaFont.getFamily(), 15));

        VBox textFieldBox = new VBox(20, listOfCategory, newChannelNameField, imageBox, newChannelUrlField);
        AnchorPane.setTopAnchor(textFieldBox, 40.0);
        AnchorPane.setLeftAnchor(textFieldBox, 200.0);

        cancelBtn.setFont(Font.font(nyalaFont.getFamily(), 14));
        cancelBtn.setPrefSize(60.0, 25.0);
        addBtn.setFont(Font.font(nyalaFont.getFamily(), 14));
        addBtn.setPrefSize(60.0, 25.0);


        HBox cancelAddBox = new HBox(20, cancelBtn, addBtn);
        AnchorPane.setTopAnchor(cancelAddBox, 250.0);
        AnchorPane.setRightAnchor(cancelAddBox, 0.0);


        newMediaStatus.setWrapText(true);
        newMediaStatus.setPrefWidth(300.0);
        AnchorPane.setTopAnchor(newMediaStatus, 300.0);
        AnchorPane.setLeftAnchor(newMediaStatus, 40.0);

        AnchorPane contentAnchorPane = new AnchorPane(labelsBox, textFieldBox, cancelAddBox, newMediaStatus);
        contentAnchorPane.setId("contentAnchorPane");


        BorderPane addContentBorderPane = new BorderPane();
        addContentBorderPane.setTop(addNewChannelTitlePane);
        addContentBorderPane.setCenter(new Pane(contentAnchorPane));
        addContentBorderPane.setId("addContentBorderPane");

        newChannelNameField.setPromptText("EBC");
        newImageField.setPromptText("http://www.ebc.gov.et/logo.jpg");
        newChannelUrlField.setPromptText("http://www.ebc.gov.et/live/ebc1.mu3");

        root.setCenter(addContentBorderPane);

        addBtn.setOnAction(event -> addContent());
        cancelBtn.setOnAction(event -> root.setCenter(createBoard(TYPE, "")));
    }

    private void addContent() {

        boolean valid = true;

            if(Objects.equals(newChannelNameField.getText(), "") || Objects.equals(newImageField.getText(), "") || Objects.equals(newChannelUrlField.getText(), "")) {

            } else {

                String errorText = "";
                int selectedChannel = listOfCategory.getSelectionModel().getSelectedIndex();

                if(selectedChannel == 0) {
                    ChannelResolver.setType("Tv");
                } else if(selectedChannel == 1) {
                    ChannelResolver.setType("Radios");
                } else if(selectedChannel == 2) {
                    ChannelResolver.setType("Movies");
                } else if(selectedChannel == 3) {
                    ChannelResolver.setType("Videos");
                } else {
                    errorText += "Select category...  \n";
                    System.out.println("Select category...");
                    valid = false;
                }

                ChannelResolver.setChannelName(newChannelNameField.getText());
                ChannelResolver.setChannelImage(newImageField.getText());
                ChannelResolver.setChannelUrl(newChannelUrlField.getText());
                ChannelResolver.CheckValidation();


                if(!ChannelResolver.isValidImage()) {
                    errorText += "Invalid Image url...  \n";
                    System.out.println("Invalid Image url...");
                    valid = false;
                }
                if(!ChannelResolver.isValidUrl()) {
                    errorText += "Invalid Channel url...  \n";
                    System.out.println("Invalid Channel url...");
                    valid = false;
                }

                if(valid) {
                    ChannelResolver.updateChannel();
                    newMediaStatus.setText("Successfully updated....  ");
                } else {
                    newMediaStatus.setText(errorText);
                }
            }

    }

    protected void externalSettingAccess() {
        root.setCenter(createSetting());
    }
    private BorderPane createSetting() {

        settingTitle.setFont(Font.font(nyalaFont.getFamily(), 25));
        settingTitle.setPrefSize(100.0, 50.0);
        settingTitle.setId("settingTitle");
        AnchorPane.setLeftAnchor(settingTitle, 20.0);
        AnchorPane.setTopAnchor(settingTitle, 0.0);
        AnchorPane.setBottomAnchor(settingTitle, 0.0);

        AnchorPane settingTitlePane = new AnchorPane(settingTitle);
        settingTitlePane.setId("settingTitlePane");

        language.setFont(Font.font(nyalaFont.getFamily(), 12));
        language.setPrefWidth(100);
        language.setId("languageTitle");


        amharicLanguage.setFont(Font.font(nyalaFont.getFamily(), 12));
        amharicLanguage.setId("amharicLanguage");

        englishLanguage.setFont(Font.font(nyalaFont.getFamily(), 12));
        englishLanguage.setId("englishLanguage");

        afaanOromooLanguage.setFont(Font.font(nyalaFont.getFamily(), 12));
        afaanOromooLanguage.setId("afaanOromooLanguage");

        languageChoice = new ComboBox<>();
        languageChoice.getItems().addAll(englishLanguage, amharicLanguage, afaanOromooLanguage);
        languageChoice.setId("languageChoice");
        languageChoice.setPrefWidth(100);

        HBox languageBox = new HBox(10, language, languageChoice);
        languageBox.setAlignment(Pos.CENTER_RIGHT);
        languageBox.setId("languageBox");

        theme.setFont(Font.font(nyalaFont.getFamily(), 18));
        theme.setPrefWidth(100);
        theme.setId("theme");

        themeChoice = new ComboBox<>();

        themeChoice.getItems().addAll("Zerone", "Darcula");
        themeChoice.setPrefWidth(100);
        themeChoice.getSelectionModel().select(THEME);

        HBox themeBox = new HBox(10, theme, themeChoice);
        themeBox.setAlignment(Pos.CENTER_RIGHT);
        themeBox.setId("themeBox");

        VBox settingContentBox = new VBox(10, themeBox, languageBox);
        settingContentBox.setAlignment(Pos.CENTER_RIGHT);

        AnchorPane.setTopAnchor(settingContentBox, 50.0);
        AnchorPane.setLeftAnchor(settingContentBox, 50.0);

        AnchorPane anchorPane = new AnchorPane(settingContentBox);


        BorderPane settingPane = new BorderPane();
        settingPane.setId("settingBorderPane");
        settingPane.setCenter(anchorPane);
        settingPane.setTop(settingTitlePane);

        themeChoice.setOnAction(event -> {
            String chosenTheme = themeChoice.getSelectionModel().getSelectedItem();

            themeResolver.setMainTheme(chosenTheme);
            themeResolver.updateTheme();

            updateTheme();
        });

        languageChoice.setOnAction(event -> {
            int chosenLanguage = languageChoice.getSelectionModel().getSelectedIndex();

            System.out.println("Choosen Language: " + chosenLanguage);
            if(chosenLanguage == 0) {
                languageResolver.setMainLanguage("English");
                languageResolver.updateLanguage();
            } else if(chosenLanguage == 1) {
                languageResolver.setMainLanguage("Amharic");
                languageResolver.updateLanguage();
            } else if(chosenLanguage == 2) {
                languageResolver.setMainLanguage("AfaanOromoo");
                languageResolver.updateLanguage();
            }

            updateLanguage();
        });

        updateLanguage();
        updateTheme();
        return settingPane;
    }

    Pane getMinimizePane() {
        return minimizePane;
    }
    Pane getMaximizePane() {
        return maximizePane;
    }
    Pane getClosePane() {
        return closePane;
    }
    SVGPath getMaximizeSVG() {
        return maximizeSVG;
    }


    private StringProperty mediaNameStringProperty() {
        return mediaNameString;
    }
}
