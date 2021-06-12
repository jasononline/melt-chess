package chess.gui.game;

import javafx.concurrent.Task;

public class PerformEngineMoveTask extends Task {


    @Override
    protected Object call() throws Exception {
        GameModel.performEngineMove();
        return null;
    }


}
