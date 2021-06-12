package chess.gui.game;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PerformEngineMoveService extends Service {

    @Override
    protected Task createTask() {
        return new PerformEngineMoveTask();
    }
}
