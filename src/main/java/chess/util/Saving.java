package chess.util;

import java.io.Serializable;
import java.util.List;

import chess.model.Game;
import chess.model.Move;

public class Saving implements Serializable {

	private Game game;
	private List<Move> movesHistory;

	public Saving(Game game, List<Move> movesHistory) {
		this.game = game;
		this.movesHistory = movesHistory;
	}

	public Game getGame() {
		return this.game;
	}

	public List<Move> getMovesHistory() {
		return this.movesHistory;
	}

}
