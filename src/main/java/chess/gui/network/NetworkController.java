package chess.gui.network;

import chess.gui.Gui;
import chess.gui.game.GameModel;
import chess.gui.util.ResizeManager;
import chess.util.TextManager;
import chess.util.Client;
import chess.util.networkservices.ConnectClientService;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controls the behaviour and actions of the UI elements in the network scene.
 * In this scene the user can enter the IP, port and start to establish a
 * connection.
 */
@SuppressWarnings({ "PMD.UnusedPrivateMethod", "PMD.TooManyFields" })
// Some methods in this class seem unused but they are used by FXML
// This class controls many elements of the gui, hence many fields are needed
// here.
public class NetworkController {

	@FXML
	public AnchorPane rootPane;
	@FXML
	public Label titleLabel;
	@FXML
	public Label ipLabel;
	@FXML
	public TextField ipTextField;
	@FXML
	public Label portLabel;
	@FXML
	public TextField portTextField;
	@FXML
	public AnchorPane errorPane;
	@FXML
	public Label errorLabel;
	@FXML
	public Button cancelButton;
	@FXML
	public Button connectButton;

	private ResizeManager resizeManager = new ResizeManager(this);

	private static String error = "error";
	private boolean isIpValid = false;
	private boolean isPortValid = false;

	private Service colorSelector;

	@FXML
	private void initialize() throws IOException {
		TextManager.computeText(titleLabel, "network.title");
		TextManager.computeText(ipLabel, "network.ip");
		TextManager.computeText(portLabel, "network.port");
		TextManager.computeText(errorLabel, "network.error");
		TextManager.computeText(cancelButton, "network.cancel");
		TextManager.computeText(connectButton, "network.connect");
		errorPane.setVisible(false);
		connectButton.setDisable(true);

		if (colorSelector == null) {
			colorSelector = new ConnectClientService();
		} else {
			// cancel al running service
			colorSelector.cancel();
		}

		ChangeListener<Number> rootPaneSizeListener = (observable, oldValue, newValue) -> {
			resizeManager.resizeNetwork(rootPane.getWidth(), rootPane.getHeight());
		};
		rootPane.widthProperty().addListener(rootPaneSizeListener);
		rootPane.heightProperty().addListener(rootPaneSizeListener);

		// Add Listener to check if input matched ip pattern
		ipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			ipTextField.getStyleClass().add(error);
			isIpValid = false;
			connectButton.setDisable(true);
			if (newValue.matches("^(([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])(.(?!$)|$)){4}$")) {
				ipTextField.getStyleClass().removeAll(error);
				isIpValid = true;
				if (isIpValid && isPortValid)
					connectButton.setDisable(false);
			}
			if (newValue == "") {
				ipTextField.getStyleClass().removeAll(error);
			}
		});

		// Add Listener to check if port is in range of [1-65535]
		portTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			portTextField.getStyleClass().add(error);
			isPortValid = false;
			connectButton.setDisable(true);
			if (newValue
					.matches("^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$")) {
				portTextField.getStyleClass().removeAll(error);
				isPortValid = true;
				if (isIpValid && isPortValid)
					connectButton.setDisable(false);
			}
			if (newValue == "") {
				portTextField.getStyleClass().removeAll(error);
			}
		});
	}

	@FXML
	private void handleConnectButtonOnAction() throws IOException {
		String iPAddress = ipTextField.getText();
		String portAddress = portTextField.getText();
		/*
		 * System.out.println("Connect to"); System.out.println("IP Address: " +
		 * iPAddress); System.out.println("Port: " + portAddress);
		 */
		errorPane.setVisible(false);

		// Establish connection
		try {
			Client.initialize(iPAddress, Integer.parseInt(portAddress));
			System.out.println("Restarting the NetworkConnectService...");
			colorSelector.restart();
			System.out.println("NetworkConnectService is done.");
		} catch (Exception exception) {
			errorPane.setVisible(true);
		}

		// else
		// TODO: Start new game
		// Gui.switchTo(Gui.ChessScene.Game);
	}

	@FXML
	private void handleCancelButtonOnAction() {
		GameModel.stopTask();
		Gui.switchTo(Gui.ChessScene.Menu);
	}

	@FXML
	private void handleTextFieldKeyPress(KeyEvent event) throws IOException {
		TextField field = (TextField) event.getSource();
		if (field.equals(ipTextField) && event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
			portTextField.requestFocus();
		}

		if (field.equals(portTextField) && event.getCode().equals(KeyCode.ENTER) && isIpValid && isPortValid) {
			handleConnectButtonOnAction();
		}
	}
}
