package chess.util.networkServices;

import chess.gui.game.GameModel;
import chess.model.Move;
import chess.util.Server;
import javafx.concurrent.Task;

import java.io.IOException;

public class PerformOpponentActionTask extends Task {
    @Override
    protected Object call() throws IOException {
        System.out.println("PerformOpponentActionTask started.");
        GameModel.performOpponentMove();
        System.out.println("PerformOpponentActionTask finished.");
        return null;
    }
}
