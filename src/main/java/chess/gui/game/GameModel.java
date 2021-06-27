package chess.gui.game;

import chess.engine.Engine;
import chess.gui.Gui;
import chess.gui.settings.SettingsModel;
import chess.gui.util.GraphicsManager;
import chess.model.Game;
import chess.util.Server;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import chess.model.Move;
import chess.model.Piece;
import chess.util.Saving;
import chess.model.MoveGenerator;
import chess.model.MoveValidator;

/**
 * Storage for information on the currently visible board position, like
 * positions of image objects, which piece is selected, etc.
 */
@SuppressWarnings("PMD.TooManyFields")
// One of the main features of this class is to store states of variables.
// That's why many fields are necessary here.
public class GameModel {

	/**
	 * Enumeration of available game modes
	 */
	public enum ChessMode {
		None, Player, Computer, Network
	}

	/**
	 * Enumeration of available sounds
	 */
	public enum ChessSound {
		Move("move.mp3"), Capture("capture.mp3"), Check("check.wav"), Failure("failure.mp3"), GameOver("gameover.mp3"),
		Win("win.mp3"), Lose("lose.mp3");

		public final AudioClip audio;

		ChessSound(String filename) {
			this.audio = new AudioClip(Gui.class.getResource("audio/" + filename).toExternalForm());
		}
	}

	/**
	 * The current Game
	 */
	private static Game currentGame;

	private static boolean stopTask = false;

	/**
	 * The engine to get best moves from
	 */
	private static Engine engine = new Engine();

	/**
	 * Stores whether the user is allowed do make a move. This variable is used to
	 * prevent the user from making moves while the Engine computes a move.
	 */
	private static boolean allowedToMove = true;

	/**
	 * Stores the chosen game mode
	 */
	private static ChessMode gameMode = ChessMode.None;

	/**
	 * Stores the chosen color (not to confuse with currentColor)
	 */
	private static int choosenColor = Piece.None;

	/**
	 * Stores the Move objects that represent the history of the game.
	 */
	private static List<Move> movesHistory = new ArrayList<>();

	/**
	 * Stores the Move objects that represent the possible moves for current
	 * position.
	 */
	private static List<Move> possibleMoves = new ArrayList<>();

	/**
	 * Stores the ImageView objects that represent the beaten white pieces.
	 */
	private static List<ImageView> beatenWhitePiecesGraphics = new ArrayList<>();

	/**
	 * Stores the ImageView objects that represent the beaten black pieces.
	 */
	private static List<ImageView> beatenBlackPiecesGraphics = new ArrayList<>();

	/**
	 * Stores the index of the selected field according to model.Board.squares. -1
	 * means "no field selected". "a8" is 0 and "h1" is 63.
	 */
	private static int selectedIndex = -1;

	/**
	 * Begins a new Game
	 */
	public static void beginNewGame() {
		// start the new game
		currentGame = new Game();

		// clear outdated lists and do some reset
		beatenWhitePiecesGraphics.clear();
		beatenBlackPiecesGraphics.clear();
		movesHistory.clear();
		selectedIndex = -1;

		allowedToMove = true;
		stopTask = false;
	}

	/**
	 * Begins a saved game
	 *
	 * @param saving saving object
	 */
	public static void beginSavedGame(Saving saving) {
		// start saved game
		currentGame = saving.getGame();

		updateBeatenPiecesLists();
		movesHistory = saving.getMovesHistory();
		selectedIndex = -1;

		allowedToMove = true;
		stopTask = false;
	}

	/**
	 * Updates the Lists of the beaten pieces
	 */
	public static void updateBeatenPiecesLists() {
		List<Integer> capturedPieces = currentGame.getCurrentPosition().getCapturedPieces();
		beatenBlackPiecesGraphics.clear();
		beatenWhitePiecesGraphics.clear();
		ImageView pieceView;
		for (int piece : capturedPieces) {
			pieceView = GraphicsManager.getGraphicAsImageView(Piece.toName(piece));
			if (Piece.isColor(piece, Piece.White)) {
				beatenWhitePiecesGraphics.add(pieceView);
			} else {
				beatenBlackPiecesGraphics.add(pieceView);
			}
		}
	}

	/**
	 * Uses the MoveGenerator to get possible moves from a starting position
	 * 
	 * @param startPosition the starting position
	 * @return the possible moves
	 */
	public static List<Move> getPossibleMoves(int startPosition) {
		MoveGenerator generator = new MoveGenerator(currentGame.getCurrentPosition());
		possibleMoves = MoveValidator.filter(currentGame.getCurrentPosition(),
				generator.generateMovesStartingAt(startPosition));
		return possibleMoves;
	}

	/**
	 * Uses the engine to generate the next PC-move, or Network-Opponent-move and
	 * executes that move. Should only be used for PvPC or Network game
	 *
	 */
	public static void performOpponentMove() throws IOException {
		List<Integer> capturedPieces = currentGame.getCurrentPosition().getCapturedPieces();
		Move next;
		if (gameMode == ChessMode.Computer) {
			// PvPC
			next = engineMove();
		} else {
			// Network
			next = networkMove();
		}

		if (next == null || !currentGame.attemptMove(next) || stopTask) {
			stopTask = false;
			return;
		}
		if (currentGame.checkWinCondition() == Game.WinCondition.NONE) {
			if (currentGame.checkCheck() && SettingsModel.isShowInCheck()) {
				GameModel.playSound(GameModel.ChessSound.Check, true);
			} else if (currentGame.getCurrentPosition().getCapturedPieces().equals(capturedPieces)) {
				GameModel.playSound(GameModel.ChessSound.Move, true);
			} else {
				GameModel.playSound(GameModel.ChessSound.Capture, true);
			}
		}
		movesHistory.add(0, next);
	}

	private static Move engineMove() {
		Move next = engine.generateBestMove(currentGame.getCurrentPosition());
		return next;
	}

	private static Move networkMove() throws IOException {
		String opponentInput = Server.getOpponentInput();
		if (opponentInput == "resign") {
			// TODO resign
			System.out.println("Opponent resigned.");
			return null;
		}
		return Move.parseUserMoveInput(opponentInput, currentGame);
	}

	/**
	 * Gives information about whether a piece has been selected or not
	 * 
	 * @return true if a piece has been selected
	 */
	public static boolean isSelected() {
		return selectedIndex != -1;
	}

	/**
	 * Setter for the allowedToMove variable
	 * 
	 * @param status the wanted status
	 */
	public static void setAllowedToMove(boolean status) {
		allowedToMove = status;
	}

	/**
	 * Getter for the allowedToMove variable
	 * 
	 * @return whether allowedToMove or not
	 */
	public static boolean isAllowedToMove() {
		return allowedToMove;
	}

	/**
	 * Setter for the gameMode
	 * 
	 * @param mode the new game mode
	 */
	public static void setGameMode(ChessMode mode) {
		GameModel.gameMode = mode;
	}

	/**
	 * Setter for the color
	 * 
	 * @param color the new color
	 */
	public static void setChoosenColor(int color) {
		GameModel.choosenColor = color;
	}

	/**
	 * Setter for the selectedIndex
	 * 
	 * @param index the new selectedIndex, should be -1 smaller than or equal index
	 *              smaller than or equal 63.
	 */
	public static void setSelectedIndex(int index) {
		if (-1 <= index && index <= 63) {
			GameModel.selectedIndex = index;
		}
	}

	/**
	 * Getter for the currentGame variable
	 *
	 * @return the current game
	 */
	public static Game getCurrentGame() {
		return currentGame;
	}

	/**
	 * Getter for the current game mode
	 * 
	 * @return current game mode
	 */
	public static ChessMode getGameMode() {
		return gameMode;
	}

	/**
	 * Getter for the current color
	 * 
	 * @return current color
	 */
	public static int getChoosenColor() {
		return choosenColor;
	}

	/**
	 * Getter for the movesHistory-List
	 * 
	 * @return the movesHistory-List
	 */
	public static List<Move> getMovesHistory() {
		return movesHistory;
	}

	/**
	 * Getter for the BeatenPiecesGraphics-List
	 * 
	 * @return the BeatenPiecesGraphics-List
	 */
	public static List<ImageView> getBeatenWhitePiecesGraphics() {
		return beatenWhitePiecesGraphics;
	}

	/**
	 * Getter for the BeatenPiecesGraphics-List
	 * 
	 * @return the BeatenPiecesGraphics-List
	 */
	public static List<ImageView> getBeatenBlackPiecesGraphics() {
		return beatenBlackPiecesGraphics;
	}

	/**
	 * Getter for the selected index
	 * 
	 * @return the selected index
	 */
	public static int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * Plays AudioClips object that represents the specific sound if setting is
	 * enabled.
	 * 
	 * @param sound which sound is to be played or stopped
	 * @param play  whether the sound should play or stop
	 */
	public static void playSound(ChessSound sound, boolean play) {
		if (SettingsModel.isSoundEffects() && play) {
			sound.audio.play();
		} else {
			sound.audio.stop();
		}
	}

	/**
	 * This can be called to prevent the engine from finishing its move when leaving
	 * Game view
	 */
	public static void stopTask() {
		stopTask = true;
	}
}
