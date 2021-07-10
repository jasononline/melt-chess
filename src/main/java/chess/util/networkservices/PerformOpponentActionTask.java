package chess.util.networkservices;

import chess.gui.game.GameModel;
import javafx.concurrent.Task;

import java.io.IOException;

/**
 * Task to receive and perform the next Opponent action
 */
public class PerformOpponentActionTask extends Task {
    @Override
    protected Object call() throws IOException {
        // System.out.println("#Debug: PerformOpponentActionTask started.");
        GameModel.performOpponentMove();
        // System.out.println("#Debug: PerformOpponentActionTask finished.");
        return null;
    }
}
