package chess.util.networkservices;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service to be able to use the InitServerTask
 */
public class InitServerService extends Service {
    @Override
    protected Task createTask() {return new InitServerTask();}
}
