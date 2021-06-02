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
    private static Scene gameScene;
    private static Scene networkConnectScene;
    private static Scene menuScene;

    private static Stage stage;

    /**
     * This method is called by the Application to start the GUI.
     *
     * @param primaryStage The initial root stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        try{
            stage = primaryStage;
            Parent root;
            // Generate Scenes

            // GameView - Scene
            /*
            root = FXMLLoader.load(getClass().getResource("GameView.fxml"));
            gameViewScene = new Scene(root);
             */

            // NetworkConnect - Scene
            /*
            root = FXMLLoader.load(getClass().getResource("NetworkConnectView.fxml"));
            gameViewScene = new Scene(root);
             */
            // SettingsView - Scene
            root = FXMLLoader.load(getClass().getResource("SettingsView.fxml"));
            settingsScene = new Scene(root);
            // MenuView - Scene
            root = FXMLLoader.load(getClass().getResource("MenuView.fxml"));
            menuScene = new Scene(root);

            // activate the menuScene (MenuView) as default entry scene
            primaryStage.setScene(menuScene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchToSettings() {
        System.out.println("The switchToSettings function was called.");
        if (!(settingsScene == null)) {
            stage.setScene(settingsScene);
            stage.show();
        } else {
            System.out.println("setingsScene not set yet.");
        }
    }

    public static void switchToGame() {
        System.out.println("The switchToGame function was called.");
        if(!(gameScene == null)) {
            stage.setScene(gameScene);
            stage.show();
        } else {
            System.out.println("gameScene not set yet.");
        }
    }

    public static void switchToNetworkConnect() {
        System.out.println("The switchToNetworkConnect function was called.");
        if(!(networkConnectScene == null)) {
            stage.setScene(networkConnectScene);
            stage.show();
        } else {
            System.out.println("networkConnectScene not set yet.");
        }
    }

    public static void switchToMenu() {
        System.out.println("The switchToMenu function was called.");
        if(!(menuScene == null)) {
            stage.setScene(menuScene);
            stage.show();
        } else {
            System.out.println("menuScene not set yet.");
        }
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
