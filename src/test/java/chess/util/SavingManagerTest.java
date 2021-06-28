package chess.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import chess.model.Game;
import chess.model.Move;

/**
 * Contains methods to test the methods of the SavingManager class
 */
public class SavingManagerTest {

	private Game game = new Game();
	private List<Move> movesHistory = new ArrayList<>();

	/**
	 * Method to test the default file naming
	 */
	@Test
	public void getDefaultFileName() {
		LocalDateTime myDateObj = LocalDateTime.now();
		String formattedDate = myDateObj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss"));
		String fileName = "MELT-CHESS_Saving_" + formattedDate + ".melt";
		assertEquals(fileName, SavingManager.getDefaultFileName());
	}

	/**
	 * Method to test the game loading
	 */
	@Test
	public void loadGame() {
		Saving loadedSaving = SavingManager.loadGame(new File("./src/test/java/chess/util/TestSaving.melt"));

		assertEquals(this.game.toString(), loadedSaving.getGame().toString());
		assertEquals(this.movesHistory, loadedSaving.getMovesHistory());
		assertEquals(this.game.getCurrentPosition().getCapturedPieces(),
				loadedSaving.getGame().getCurrentPosition().getCapturedPieces());
		assertEquals(this.game.getCurrentPosition().getLastMove(),
				loadedSaving.getGame().getCurrentPosition().getLastMove());
		assertEquals(this.game.getCurrentPosition().getTurnColor(),
				loadedSaving.getGame().getCurrentPosition().getTurnColor());
	}

	/**
	 * Method to test the game saving
	 */
	@Test
	public void saveGame() {
		Saving saving = new Saving(this.game, this.movesHistory);

		File file = new File("./src/test/java/chess/util/TestSaving.melt");
		SavingManager.saveGame(saving, file);

		assertTrue(Files.exists(Paths.get("./src/test/java/chess/util/TestSaving.melt")));
	}

}
