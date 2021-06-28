package chess.util;

import chess.model.Game;
import chess.model.Move;

import java.io.Serializable;
import java.util.List;

/**
 * Container for a game saving. Contains game object and list of moves
 */
public class Saving implements Serializable {

	private Game game;
	private List<Move> movesHistory;

	/**
	 * Create new Saving instance of
	 * 
	 * @param game         game object to save
	 * @param movesHistory list of moves to save
	 */
	public Saving(Game game, List<Move> movesHistory) {
		this.game = game;
		this.movesHistory = movesHistory;
	}

	/**
	 * Getter for a saved game object
	 * 
	 * @return saved game object
	 */
	public Game getGame() {
		return this.game;
	}

	/**
	 * Getter for a saved history of moves
	 * 
	 * @return list of move objects
	 */
	public List<Move> getMovesHistory() {
		return this.movesHistory;
	}

}
