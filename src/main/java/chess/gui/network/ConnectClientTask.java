package chess.gui.network;

import chess.gui.Gui;
import chess.gui.game.GameModel;
import chess.model.Piece;
import chess.util.Client;
import chess.util.Server;
import javafx.concurrent.Task;

public class ConnectClientTask extends Task {
	@Override
	protected Object call() throws Exception {
		int color = Client.decideColor();
		if (color == Piece.White) {
			GameModel.setChoosenColor(Piece.White);
			System.out.println("My color is WHITE.");
		} else if (color == Piece.Black) {
			GameModel.setChoosenColor((Piece.Black));
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
