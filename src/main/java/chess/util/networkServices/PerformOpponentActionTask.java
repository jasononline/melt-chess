package chess.util.networkServices;

import chess.gui.game.GameModel;
import chess.model.Move;
import chess.util.Server;
import javafx.concurrent.Task;

import java.io.IOException;

public class PerformOpponentActionTask extends Task {
    @Override
    protected Object call() throws IOException {
        String opponentInput = Server.getOpponentInput();
        if (opponentInput == "resign") {
            // TODO resign
            System.out.println("Opponent resigned");
        } else {
            GameModel.performOpponentMove();
        }
        return null;
    }
}
