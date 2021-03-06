package chess.gui.game;

import chess.gui.settings.SettingsModel;
import chess.model.*;
import chess.util.Client;
import chess.util.TextManager;
import chess.util.networkservices.PerformOpponentActionService;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

/**
 * Controls behaviour of GUI chessboard.
 */
public class BoardController {

	private GameController gameController;

	private static Service performEngineMoveService;
	private static Service performOpponentActionService;
	protected boolean isRotated = false;

	/**
	 * Create new BoardController instance of
	 * 
	 * @param gameController the current game controller
	 */
	public BoardController(GameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * Initialize chessboard in GameView
	 */
	protected void initialize() {

		performEngineMoveService = new PerformEngineMoveService();

		performOpponentActionService = new PerformOpponentActionService();

		gameController.boardGrid.setDisable(false);
		gameController.boardGrid.prefHeightProperty().bind(Bindings.min(
				gameController.rootPane.widthProperty().divide(1.94), gameController.rootPane.heightProperty().divide(1.09)));
		gameController.boardGrid.prefWidthProperty().bind(Bindings.min(gameController.rootPane.widthProperty().divide(1.94),
				gameController.rootPane.heightProperty().divide(1.09)));

		gameController.lineNumbersPane.prefHeightProperty().bind(gameController.boardGrid.heightProperty());
		gameController.lineNumbersPane.prefWidthProperty().bind(Bindings.min(
				gameController.rootPane.widthProperty().divide(42.66), gameController.rootPane.heightProperty().divide(24)));
		gameController.lineNumbersPane.translateXProperty().bind(gameController.boardGrid.widthProperty().divide(2)
				.add(gameController.lineNumbersPane.widthProperty().divide(2)).negate());

		gameController.columnLettersPane.prefHeightProperty().bind(gameController.lineNumbersPane.widthProperty());
		gameController.columnLettersPane.prefWidthProperty().bind(gameController.boardGrid.widthProperty());
		gameController.columnLettersPane.translateYProperty().bind(gameController.boardGrid.heightProperty().divide(2)
				.add(gameController.columnLettersPane.heightProperty().divide(2)));

		gameController.boardGrid.getChildren().forEach(s -> {
			s.getStyleClass().removeAll("focused", "possibleMove", "checkMove", "captureMove");
		});

		if (GameModel.getGameMode() != GameModel.ChessMode.Player && GameModel.getChoosenColor() == Piece.Black) {
			isRotated = false;
			flipBoard(false);
		}

		checkForGameOver();
	}

	/**
	 * Moves the selected Piece to the selected destination if allowed
	 * 
	 * @param startIndex  the index of the selected piece
	 * @param targetIndex the selected destination
	 */
	protected void movePieceOnBoard(int startIndex, int targetIndex) {
		Move move = new Move(startIndex, targetIndex);
		Board board = GameModel.getCurrentGame().getCurrentPosition();
		Move testMove = new Move(startIndex, targetIndex);
		Game testGame = GameModel.getCurrentGame();
		testGame.addFlag(testMove);

		// if promotion is possible
		if (Piece.isColor(board.getPieceAt(startIndex), board.getTurnColor())
				&& MoveValidator.validateMove(testGame.getCurrentPosition(), testMove)
				&& (Coordinate.isOnUpperBorder(targetIndex) || Coordinate.isOnLowerBorder(targetIndex))
				&& Piece.isType(board.getPieceAt(startIndex), Piece.Pawn)) {
			// open the PopupMenu to choose promotion
			gameController.gamePopup.showPromotionPopup(GameModel.getCurrentGame().getCurrentPosition().getTurnColor(), move);
			return;
		}
		finishMove(move);
	}

	/**
	 * Displays the possible moves on the View
	 * 
	 * @param startPosition from where to look for moves
	 */
	protected void showPossibleMoves(int startPosition) {

		Board board = GameModel.getCurrentGame().getCurrentPosition();
		if (!Piece.isColor(board.getPieceAt(startPosition), board.getTurnColor()))
			return;

		for (Move move : GameModel.getPossibleMoves(startPosition)) {
			Node squareNode = gameController.boardGrid.getChildren().get(move.getTargetSquare());

			if (!(squareNode instanceof AnchorPane))
				return;

			AnchorPane square = (AnchorPane) squareNode;
			String styleClass = square.getChildren().isEmpty() ? "possibleMove" : "captureMove";
			if (MoveValidator.getPossibleCheckMoves(board, board.getTurnColor(), move).contains(move)) {
				styleClass = "checkMove";
			}
			square.getStyleClass().addAll(styleClass);
		}
	}

	/**
	 * Finishes the current move after flags for promotion are set.
	 * 
	 * @param move the current move containing flags for promotion
	 */
	protected void finishMove(Move move) {
		GameModel.getCurrentGame().addFlag(move);

		List<Integer> capturedPieces = GameModel.getCurrentGame().getCurrentPosition().getCapturedPieces();
		if (!GameModel.getCurrentGame().attemptMove(move)) {
			GameModel.playSound(GameModel.ChessSound.Failure, true);
			return;
		}

		playMoveSounds(capturedPieces);

		GameModel.getMovesHistory().add(0, move);
		if (SettingsModel.isFlipBoard() && GameModel.getGameMode() != GameModel.ChessMode.Computer)
			flipBoard(true);

		try {
			Client.send(move.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		gameController.updateUI();
		if (checkForGameOver())
			return;

		continueAccordingToGameMode();
	}

	/**
	 * Play sounds according to a happened Move
	 */
	private void playMoveSounds(List<Integer> capturedPieces) {

		if (GameModel.getCurrentGame().checkWinCondition() != Game.WinCondition.NONE)
			return;

		if (GameModel.getCurrentGame().checkCheck() && SettingsModel.isShowInCheck()) {
			GameModel.playSound(GameModel.ChessSound.Check, true);
		} else if (!GameModel.getCurrentGame().getCurrentPosition().getCapturedPieces().equals(capturedPieces)) {
			GameModel.playSound(GameModel.ChessSound.Capture, true);
		} else {
			GameModel.playSound(GameModel.ChessSound.Move, true);
		}
	}

	/**
	 * Continues the actions depending on the gamemode
	 */
	protected void continueAccordingToGameMode() {
		if (GameModel.getChoosenColor() == GameModel.getCurrentGame().getCurrentPosition().getTurnColor()) {
			return;
		}
		// PvP
		if (GameModel.getGameMode() == GameModel.ChessMode.Player) {
			return;
		}
		// PvPC
		if (GameModel.getGameMode() == GameModel.ChessMode.Computer) {

			gameController.activityIndicator.visibleProperty().bind(performEngineMoveService.runningProperty());
			gameController.boardGrid.setDisable(true);
			gameController.resignButton.disableProperty().bind(performEngineMoveService.runningProperty());
			gameController.settingsButton.disableProperty().bind(performEngineMoveService.runningProperty());
			gameController.saveButton.disableProperty().bind(performEngineMoveService.runningProperty());

			performEngineMoveService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent workerStateEvent) {
					finishOpponentAction();
				}
			});

			performEngineMoveService.restart();
		}
		// Network
		if (GameModel.getGameMode() == GameModel.ChessMode.Network) {
			gameController.activityIndicator.visibleProperty().bind(performOpponentActionService.runningProperty());
			gameController.boardGrid.setDisable(true);
			gameController.settingsButton.disableProperty().bind(performOpponentActionService.runningProperty());
			gameController.saveButton.disableProperty().bind(performOpponentActionService.runningProperty());

			performOpponentActionService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent workerStateEvent) {
					// System.out.println("#Debug: OpponentMove Worker is done");
					// System.out.println("#Debug: GameModel.surrendered = " + GameModel.surrendered);
					if (GameModel.surrendered) {
						// System.out.println("#Debug: resign found after performOpponentActionServiece succeeded.");
						gameController.gamePopup.resign();
					} else {
						finishOpponentAction();
					}
				}
			});

			performOpponentActionService.restart();
		}
	}

	/**
	 * Updates a performed move of the Engine to the view
	 * 
	 * @return null
	 */
	private EventHandler<WorkerStateEvent> finishOpponentAction() {
		GameModel.setSelectedIndex(-1);
		gameController.boardGrid.getChildren().forEach(s -> {
			s.getStyleClass().removeAll("focused", "possibleMove", "checkMove", "captureMove");
		});
		GameModel.setAllowedToMove(true);
		gameController.boardGrid.setDisable(false);
		if (!gameController.settingsButton.disableProperty().isBound()) {
			gameController.settingsButton.setDisable(false);
		}
		if (!gameController.saveButton.disableProperty().isBound()) {
			gameController.saveButton.setDisable(false);
		}
		if (!gameController.resignButton.disableProperty().isBound()) {
			gameController.resignButton.setDisable(false);
		}
		checkForGameOver();
		gameController.updateUI();
		return null;
	}

	/**
	 * Checks whether the current game is over if that is the case the endGame()
	 * function will be called
	 * 
	 * @return boolean whether the current game is over
	 */
	protected boolean checkForGameOver() {
		gameController.checkLabel.setVisible(false);
		Game game = GameModel.getCurrentGame();
		if (game.checkCheck()) {
			String key;
			if (game.getCurrentPosition().getTurnColor() == Piece.White) {
				key = "game.whiteInCheck";
			} else {
				key = "game.blackInCheck";
			}
			if (SettingsModel.isShowInCheck())
				gameController.checkLabel.setVisible(true);
			TextManager.computeText(gameController.checkLabel, key);
		}
		if (game.checkWinCondition() != Game.WinCondition.NONE) {
			endGame();
			return true;
		}
		return false;
	}

	/**
	 * Ends the current game
	 */
	public void endGame() {
		Game game = GameModel.getCurrentGame();
		Game.WinCondition winCondition = game.checkWinCondition();

		// only end the game when it should be ended
		if (winCondition == Game.WinCondition.NONE)
			return;

		String key = "";
		if (winCondition == Game.WinCondition.CHECKMATE) {
			if (game.getCurrentPosition().getTurnColor() == Piece.White) {
				key = "game.whiteInCheckmate";
			} else {
				key = "game.blackInCheckmate";
			}
		} else if (winCondition == Game.WinCondition.REMIS) {
			if (game.getCurrentPosition().getTurnColor() == Piece.White) {
				key = "game.whiteInRemis";
			} else {
				key = "game.blackInRemis";
			}
		}
		TextManager.computeText(gameController.checkLabel, key);
		gameController.checkLabel.setVisible(true);
		gameController.gamePopup.showGameOverPopup(winCondition);
	}

	/**
	 * Flips the board
	 * 
	 * @param animate whether should animate rotate
	 */
	protected void flipBoard(boolean animate) {
		String lines = isRotated ? "87654321" : "12345678";
		String columns = isRotated ? "abcdefgh" : "hgfedcba";

		for (int i = 0; i < 8; i++) {
			((Label) gameController.lineNumbersPane.getChildren().get(i)).setText(lines.charAt(i) + "");
			((Label) gameController.columnLettersPane.getChildren().get(i)).setText(columns.charAt(i) + "");
		}

		if (animate) {
			// -------- rotate with animation --------
			RotateTransition transition = new RotateTransition(Duration.seconds(1.2), gameController.boardGrid);
			transition.setToAngle(isRotated ? 0 : 180);
			// transition.setDelay(Duration.seconds(0.5));
			transition.play();
		} else {
			// -------- rotate without animation --------
			gameController.boardGrid.setRotate(isRotated ? 0 : 180);
		}

		for (Node squareNode : gameController.boardGrid.getChildren()) {
			squareNode.setRotate(isRotated ? 0 : 180);
		}
		isRotated = !isRotated;
	}

	/**
	 * Getter for the performEngineMoveService
	 * 
	 * @return the performEngineMoveService
	 */
	public static Service getPerformEngineMoveService() {
		return performEngineMoveService;
	}

	/**
	 * Getter for the performOpponentActionService
	 * 
	 * @return the performOpponentActionService
	 */
	public static Service getPerformOpponentActionService() {
		return performOpponentActionService;
	}

}
