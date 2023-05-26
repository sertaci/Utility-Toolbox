package kozmikoda.utilitytoolbox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GUIRunner extends Application {

    Stage window;
    Scene mainScene;

    FXMLLoader fxml;

    /**
     * Starts the main application window
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        fxml = new FXMLLoader(getClass().getResource("GUISkeleton.fxml"));
        window = fxml.load();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        window.setX(screenBounds.getWidth()/3.5);
        window.setY(screenBounds.getHeight()/4);



        window.initStyle(StageStyle.TRANSPARENT);

        mainScene = window.getScene();
        mainScene.setFill(Color.TRANSPARENT);

        window.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));

        window.setScene(mainScene);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
}