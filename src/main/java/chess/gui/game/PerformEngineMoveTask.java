package chess.gui.game;

import javafx.concurrent.Task;

/**
 * A Task to perform an engine - move. This Task is offered by the
 * PerformEngineMoveService service
 */
public class PerformEngineMoveTask extends Task {

	/**
	 * Lets the engine come up with a good move in the background and perfoms that
	 * move
	 * 
	 * @return null
	 */
	@Override
	protected Object call() throws Exception {
		GameModel.performOpponentMove();
		return null;
	}

}
