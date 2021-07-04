package chess.util.networkservices;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service to be able to use the ConnectClientTask
 */
public class ConnectClientService extends Service {
    @Override
    protected Task createTask() {
        return new ConnectClientTask();
    }
}