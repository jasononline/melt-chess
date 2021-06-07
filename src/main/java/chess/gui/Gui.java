package chess.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Starting point of the JavaFX GUI
 */
public class Gui extends Application {

	/*
	 * Enumeration of available scenes
	 */
	public enum ChessScene {
		Menu, Settings, NetworkConnection, Game;
	}

	private static Scene scene;

	/**
	 * This method is called by the Application to start the GUI.
	 *
	 * @param primaryStage The initial root stage of the application.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MenuView.fxml"));
			scene = new Scene(root);

			// activate the menuScene (MenuView) as default entry scene
			primaryStage.setScene(scene);
			primaryStage.show();
			double titleBarHeight = primaryStage.getHeight() - primaryStage.getScene().getHeight();
			primaryStage.setMinWidth(640);
			primaryStage.setMinHeight(360 + titleBarHeight);
			primaryStage.setTitle("Chess");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Switches the current scene to the new scene
	 * 
	 * @param newScene new scene to switch to
	 */
	public static void switchTo(ChessScene toScene) {
		try {
			Parent root;
			switch (toScene) {
				case Menu:
					root = FXMLLoader.load(Gui.class.getResource("MenuView.fxml"));
					break;
				case Settings:
					root = FXMLLoader.load(Gui.class.getResource("SettingsView.fxml"));
					break;
				case NetworkConnection:
					root = FXMLLoader.load(Gui.class.getResource("NetworkConnectionView.fxml"));
					break;
				case Game:
					root = FXMLLoader.load(Gui.class.getResource("GameView.fxml"));
					break;
				default:
					root = FXMLLoader.load(Gui.class.getResource("MenuView.fxml"));
					break;
			}

			scene.setRoot(root);

		} catch (Exception e) {
			e.printStackTrace();
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
