package controller;

import xSupport.BorderlessScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 *  MainClass, load the fxml file and connect controllers and views.
 * @author Getachew Mulat
 */
public class MainClass extends Application {

    private MainWindowController mainWindowController;
    private Stage primaryStage;
    private BorderlessScene scene;

    /**
     * execute before JavaFX thread.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *  Set mainPage, override the method on Application.
     *  load after main method.
     *  sequence: init, start, stop
     * @param primaryStage stage object
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        mainWindow();
    }

    /**
     *  Load MainWindowView.fxml and connect to MainWindowController.
     * @throws URISyntaxException
     */
    public void mainWindow() throws URISyntaxException {
        try {

            FXMLLoader loader = new FXMLLoader(MainClass.class.getResource("/controller/MainWindowView.fxml"));
            BorderPane root = loader.load();

            mainWindowController = loader.getController();
            mainWindowController.setMainClass(this);

            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(500);
            primaryStage.setWidth(900);
            primaryStage.setHeight(600);

            scene = new BorderlessScene(this.primaryStage, root);
            scene.setMoveControl(mainWindowController.topPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Zerone Media Streamer");
            primaryStage.centerOnScreen();
            primaryStage.show();

            final BorderlessScene finalScene = scene;
            mainWindowController.getMinimizePane().setOnMouseClicked(event -> finalScene.minimise());
            mainWindowController.getMaximizePane().setOnMouseClicked(event -> {
                finalScene.maximise();
                if(!finalScene.isMaximised()) {
                    mainWindowController.getMaximizeSVG().setRotate(0.0);
                } else {
                    mainWindowController.getMaximizeSVG().setRotate(180.0);
                }
            });
            finalScene.setOnMouseReleased(event -> {
                if(!finalScene.isMaximised()) {
                    mainWindowController.getMaximizeSVG().setRotate(0.0);
                } else {
                    mainWindowController.getMaximizeSVG().setRotate(180.0);
                }
            });

            mainWindowController.getClosePane().setOnMouseClicked(event -> Platform.exit());
            mainWindowController.setPrimaryStage(primaryStage);
            mainWindowController.initFileDragNDrop(primaryStage);
            mainWindowController.loadHomepage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load Homepage.fxml, and connect HomepageController .
     * @return object of BorderPane
     */
    public BorderPane homepageWindow() {
        FXMLLoader loader = new FXMLLoader(MainClass.class.getResource("/controller/Homepage.fxml"));
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HomepageController controller = loader.getController();
        controller.setMainClass(this);
        controller.setMainWindowController(mainWindowController);


        return root;
    }

    /**
     *  update the theme of Scene, can be execute on the same package.
     * @param theme object of String, name of the theme.
     */
    void setTheme(String theme) {

        if(scene.getStylesheets().size() != 0) {
            scene.getStylesheets().remove(0);
        }
        if(Objects.equals(theme, "Zerone")) {
            scene.getStylesheets().add("resource/stylesheet/zerone.css");
            homepageWindow().getStylesheets().add("resource/stylesheet/zerone.css");
        } else if(Objects.equals(theme, "Darcula")) {
            scene.getStylesheets().add("resource/stylesheet/darcula.css");
            homepageWindow().getStylesheets().add("resource/stylesheet/darcula.css");
        }

    }
}
