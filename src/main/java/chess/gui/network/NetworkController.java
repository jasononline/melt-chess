package chess.gui.network;

import chess.gui.Gui;
import chess.gui.util.TextManager;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Controls the behaviour and actions of the UI elements in the network scene.
 * In this scene the user can enter the IP, port and start to establish a
 * connection.
 */
public class NetworkController {

	@FXML
	private AnchorPane rootPane;
	@FXML
	private Label titleLabel;
	@FXML
	private Label ipLabel;
	@FXML
	private TextField ipTextField;
	@FXML
	private Label portLabel;
	@FXML
	private TextField portTextField;
	@FXML
	private Button cancelButton;
	@FXML
	private Button connectButton;
	private boolean[] connectButtonDisabled = { true, true };

	@FXML
	private void initialize() {
		TextManager.computeText(titleLabel, "network.title");
		TextManager.computeText(ipLabel, "network.ip");
		TextManager.computeText(portLabel, "network.port");
		TextManager.computeText(cancelButton, "network.cancel");
		TextManager.computeText(connectButton, "network.connect");
		connectButton.setDisable(true);

		ChangeListener<Number> rootPaneSizeListener = (observable, oldValue, newValue) -> {
			resize();
		};
		rootPane.widthProperty().addListener(rootPaneSizeListener);
		rootPane.heightProperty().addListener(rootPaneSizeListener);

		// Check if input matched ip pattern
		ipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			ipTextField.getStyleClass().add("error");
			connectButtonDisabled[0] = true;
			connectButton.setDisable(true);
			if (newValue.matches("^(([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])(.(?!$)|$)){4}$")) {
				ipTextField.getStyleClass().removeAll("error");
				connectButtonDisabled[0] = false;
				if (!connectButtonDisabled[0] && !connectButtonDisabled[1])
					connectButton.setDisable(false);
			}
			if (newValue == "") {
				ipTextField.getStyleClass().removeAll("error");
			}
		});

		// Check if port is in range of [1-65535]
		portTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			portTextField.getStyleClass().add("error");
			connectButtonDisabled[1] = true;
			connectButton.setDisable(true);
			if (newValue
					.matches("^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$")) {
				portTextField.getStyleClass().removeAll("error");
				connectButtonDisabled[1] = false;
				if (!connectButtonDisabled[0] && !connectButtonDisabled[1])
					connectButton.setDisable(false);
			}
			if (newValue == "") {
				portTextField.getStyleClass().removeAll("error");
			}
		});
	}

	@FXML
	private void handleConnectButtonOnAction() {
		String IPAddress = ipTextField.getText();
		String PortAddress = portTextField.getText();
		System.out.println("Connect to");
		System.out.println("IP Address: " + IPAddress);
		System.out.println("Port: " + PortAddress);

		// TODO: Establish connection
		// TODO: Start new game
		Gui.switchTo(Gui.ChessScene.Game);
	}

	@FXML
	private void handleCancelButtonOnAction() {
		Gui.switchTo(Gui.ChessScene.Menu);
	}

	@FXML
	private void handleTextFieldKeyPress(KeyEvent event) {
		TextField field = (TextField) event.getSource();
		if (field == ipTextField) {
			if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
				portTextField.requestFocus();
			}
		}

		if (field == portTextField) {
			if (event.getCode().equals(KeyCode.ENTER)) {
				if (!connectButtonDisabled[0] && !connectButtonDisabled[1])
					handleConnectButtonOnAction();
			}
		}
	}

	private void resize() {
		double rootHeight = rootPane.getHeight();
		double rootWidth = rootPane.getWidth();
		double titleFontSize = Math.min(rootHeight / 12, rootWidth / 21.33 * 1.5);
		double fontSize = Math.min(rootHeight / 28.8, rootWidth / 51.2 * 1.5);
		double iconSize = Math.min(rootHeight / 20.57, rootWidth / 36.57 * 1.5);
		double borderRadius = Math.min(rootHeight / 72, rootWidth / 128 * 1.2);
		double borderWidth = Math.min(rootHeight / 240, rootWidth / 426.66);

		titleLabel.setStyle("-fx-font-size: " + titleFontSize);
		ipLabel.setStyle("-fx-font-size: " + fontSize);
		portLabel.setStyle("-fx-font-size: " + fontSize);
		ipTextField.setStyle("-fx-font-size: " + fontSize + "; -fx-background-radius: " + borderRadius
				+ "; -fx-border-radius: " + borderRadius + "; -fx-border-width: " + borderWidth / 1.5);
		portTextField.setStyle("-fx-font-size: " + fontSize + "; -fx-background-radius: " + borderRadius
				+ "; -fx-border-radius: " + borderRadius + "; -fx-border-width: " + borderWidth / 1.5);
		cancelButton.setStyle(
				"-fx-font-size: " + fontSize + "; -fx-graphic-text-gap: " + rootWidth / 42.66 + "; -fx-background-radius: "
						+ borderRadius + "; -fx-border-radius: " + borderRadius + "; -fx-border-width: " + borderWidth);
		connectButton.setStyle(
				"-fx-font-size: " + fontSize + "; -fx-graphic-text-gap: " + rootWidth / 42.66 + "; -fx-background-radius: "
						+ borderRadius + "; -fx-border-radius: " + borderRadius + "; -fx-border-width: " + borderWidth);
		ImageView cancelButtonIcon = (ImageView) cancelButton.getGraphic();
		cancelButtonIcon.setFitHeight(iconSize);
		ImageView connectButtonIcon = (ImageView) connectButton.getGraphic();
		connectButtonIcon.setFitHeight(iconSize);
		connectButton.getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius + "; -fx-border-radius: " + borderRadius);
		ipTextField.getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius + "; -fx-border-radius: " + borderRadius);
	}
}
