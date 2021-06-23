package chess.gui.network;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class NetworkServerService extends Service {
    @Override
    protected Task createTask() {return new InitServerTask();}
}
