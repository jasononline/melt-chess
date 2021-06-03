package chess.gui.network;

import chess.gui.util.TextManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controls the behaviour and actions of the UI elements in the network scene.
 * In this scene the user can enter the IP, port and start to establish a
 * connection.
 */

public class NetworkController {

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

	@FXML
	private void initialize() {
		TextManager.computeText(titleLabel, "network.title");
		TextManager.computeText(ipLabel, "network.ip");
		TextManager.computeText(portLabel, "network.port");
		TextManager.computeText(cancelButton, "network.cancel");
		TextManager.computeText(connectButton, "network.connect");
	}

	@FXML
	void handleButtonCancelOnAction(ActionEvent event) {
		String IPAddress = ipTextField.getText();
		String PortAddress = portTextField.getText();
		System.out.println("User clicked on connection in the Main Menu");
		System.out.println("The IP Address: " + IPAddress);
		System.out.println("The Port Address: " + PortAddress);

		// TODO Establish connection
	}

	@FXML
	private void handleConnectSaveOnAction() {
		System.out.println("User clicked on cancel in the Main Menu");
	}

}
