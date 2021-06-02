package chess.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Starting point of the JavaFX GUI
 */
public class Gui extends Application {

    /**
     * The SettingsView - Scene
     */
    private static Scene settingsScene;

    private static Stage stage;

    /**
     * This method is called by the Application to start the GUI.
     *
     * @param primaryStage The initial root stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        /*
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> System.out.println("Hello World!"));

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();

         */
        /*
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuView.fxml"));

        Parent root = null;
        try {
            root = (Parent)fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

        try{
            // Generate Scenes
            Parent root;

            // SettingsView - Scene
            root = FXMLLoader.load(getClass().getResource("SettingsView.fxml"));
            settingsScene = new Scene(root);

            // GameView - Scene
            // root = FXMLLoader.load(getClass().getResource("GameView.fxml"));

            root = FXMLLoader.load(getClass().getResource("MenuView.fxml"));
            Scene scene = new Scene(root, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static void switchToSettings() {
        stage.setScene(settingsScene);
    }


    /**
     * The entry point of the GUI application.
     *
     * @param args The command line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
