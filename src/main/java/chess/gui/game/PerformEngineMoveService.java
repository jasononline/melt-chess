package chess.gui.game;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * A Service class to offer Tasks in the Background
 */
public class PerformEngineMoveService extends Service {

	/**
	 * Creates a new Task for performing an engine move
	 * 
	 * @return the engine move taskg
	 */
	@Override
	protected Task createTask() {
		return new PerformEngineMoveTask();
	}
}
