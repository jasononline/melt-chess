package chess.gui.network;

import chess.util.Server;
import javafx.concurrent.Task;

public class InitServerTask extends Task {

    @Override
    protected Object call() throws Exception {
        Server.initialize();
        return null;
    }
}
