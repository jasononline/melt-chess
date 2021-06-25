package chess.util.networkServices;

import chess.util.Server;
import javafx.concurrent.Task;

import java.io.IOException;

public class GetOpponentInputTask extends Task {
    @Override
    protected Object call() throws IOException {
        return Server.getOpponentInput();
    }

}
