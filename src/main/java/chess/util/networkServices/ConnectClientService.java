package chess.util.networkServices;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ConnectClientService extends Service {
    @Override
    protected Task createTask() {
        return new ConnectClientTask();
    }
}