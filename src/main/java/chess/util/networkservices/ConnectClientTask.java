package chess.util.networkservices;

import chess.gui.Gui;
import chess.gui.game.GameModel;
import chess.model.Piece;
import chess.util.Client;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Task to connect the Client to a server socket and decide what color one will play
 */
public class ConnectClientTask extends Task {
	@Override
	protected Object call() throws IOException {
		if (InetAddress.getByName(Client.getIpAddress()).isReachable(10000)) {
			Client.initialize();
		} else {
			return null;
		}
		int color = 0;
		try {
			color = Client.decideColor();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (color == Piece.White) {
			GameModel.setChoosenColor(Piece.White);
			System.out.println("My color is WHITE.");
		} else if (color == Piece.Black) {
			GameModel.setChoosenColor(Piece.Black);
			System.out.println("My color is BLACK.");
		} else {
			System.out.println("Connection timeout (treat this in ConnectClientTask.java----------------------");
			System.exit(0);
		}
		GameModel.beginNewGame();
		Gui.switchTo(Gui.ChessScene.Game);
		return null;
	}
}