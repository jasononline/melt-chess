package chess.util.networkservices;

import chess.util.Server;
import javafx.concurrent.Task;

/**
 * Task to initialize a Server socket and wait for a Client to connect to this socket
 */
public class InitServerTask extends Task {

    @Override
    protected Object call() throws Exception {
        Server.initialize();
        return null;
    }
}
