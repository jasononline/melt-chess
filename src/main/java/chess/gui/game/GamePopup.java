package chess.gui.game;

import chess.gui.Gui;
import chess.gui.game.GameModel.ChessMode;
import chess.gui.settings.SettingsModel;
import chess.gui.util.GraphicsManager;
import chess.model.Game.WinCondition;
import chess.model.Move;
import chess.model.Piece;
import chess.util.Client;
import chess.util.TextManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Controls behaviour of GUI game popup menus.
 */
public class GamePopup {

	private GameController gameController;

	/**
	 * Create new GamePopup instance of
	 * 
	 * @param gameController the current game controller
	 */
	public GamePopup(GameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * Initialize popup menus in GameView
	 */
	protected void initialize() {
		TextManager.computeText(gameController.promotionPopupLabel, "game.promotionTitle");
		TextManager.computeText(gameController.promotionPopupQueenButton, "game.promotionQueen");
		TextManager.computeText(gameController.promotionPopupRookButton, "game.promotionRook");
		TextManager.computeText(gameController.promotionPopupBishopButton, "game.promotionBishop");
		TextManager.computeText(gameController.promotionPopupKnightButton, "game.promotionKnight");

		TextManager.computeText(gameController.surePopupYesButton, "game.surePopupYes");
		TextManager.computeText(gameController.surePopupCancelButton, "game.surePopupCancel");

		TextManager.computeText(gameController.gameOverPopupTitle, "game.gameOverPopupTitle");
		TextManager.computeText(gameController.gameOverPopupMenuButton, "game.gameOverPopupMenu");
		TextManager.computeText(gameController.gameOverPopupStayButton, "game.gameOverPopupStay");
		TextManager.computeText(gameController.gameOverPopupRestartButton, "game.gameOverPopupRestartWin");

		gameController.promotionPopup.prefHeightProperty().bind(gameController.boardGrid.heightProperty().divide(4.4));
		gameController.promotionPopup.prefWidthProperty().bind(gameController.boardGrid.widthProperty().divide(1.32));
		gameController.promotionPopup.maxHeightProperty().bind(gameController.promotionPopup.prefHeightProperty());
		gameController.promotionPopup.maxWidthProperty().bind(gameController.promotionPopup.prefWidthProperty());

		gameController.surePopup.prefHeightProperty().bind(gameController.boardGrid.heightProperty().divide(4.4));
		gameController.surePopup.prefWidthProperty().bind(gameController.boardGrid.widthProperty().divide(1.32));
		gameController.surePopup.maxHeightProperty().bind(gameController.promotionPopup.prefHeightProperty());
		gameController.surePopup.maxWidthProperty().bind(gameController.promotionPopup.prefWidthProperty());

		gameController.gameOverPopup.prefHeightProperty().bind(gameController.boardGrid.heightProperty().divide(3.3));
		gameController.gameOverPopup.prefWidthProperty().bind(gameController.boardGrid.widthProperty().divide(1.32));
		gameController.gameOverPopup.maxHeightProperty().bind(gameController.gameOverPopup.prefHeightProperty());
		gameController.gameOverPopup.maxWidthProperty().bind(gameController.gameOverPopup.prefWidthProperty());

		gameController.promotionPopup.setVisible(false);
		gameController.surePopup.setVisible(false);
		gameController.gameOverPopup.setVisible(false);
	}

	/**
	 * Disables all UI elements when popup menus appear
	 */
	private void disableUI() {
		gameController.boardGrid.setDisable(true);
		gameController.historyGrid.setDisable(true);
		if (!gameController.resignButton.disableProperty().isBound()) {
			gameController.resignButton.setDisable(true);
		}
		gameController.restartButton.setDisable(true);
		if (!gameController.settingsButton.disableProperty().isBound()) {
			gameController.settingsButton.setDisable(true);
		}
		if (!gameController.saveButton.disableProperty().isBound()) {
			gameController.saveButton.setDisable(true);
		}
		gameController.menuButton.setDisable(true);
	}

	/**
	 * Opens a popup menu to choose promotion Should only be called if promotion is
	 * possible
	 * 
	 * @param forColor the current color on the board
	 * @param move     the move that brings a pawn to the last rank
	 */
	protected void showPromotionPopup(int forColor, Move move) {
		ImageView queenIcon = GraphicsManager.getGraphicAsImageView(forColor == Piece.White ? "queen_white" : "queen_black",
				20);

		ImageView rookIcon = GraphicsManager.getGraphicAsImageView(forColor == Piece.White ? "rook_white" : "rook_black",
				20);

		ImageView bishopIcon = GraphicsManager
				.getGraphicAsImageView(forColor == Piece.White ? "bishop_white" : "bishop_black", 20);

		ImageView knightIcon = GraphicsManager
				.getGraphicAsImageView(forColor == Piece.White ? "knight_white" : "knight_black", 20);

		ImageView[] icons = { queenIcon, rookIcon, bishopIcon, knightIcon };

		gameController.promotionPopupQueenButton.setGraphic(queenIcon);
		gameController.promotionPopupRookButton.setGraphic(rookIcon);
		gameController.promotionPopupBishopButton.setGraphic(bishopIcon);
		gameController.promotionPopupKnightButton.setGraphic(knightIcon);
		gameController.promotionPopup.setVisible(true);
		disableUI();

		EventHandler<ActionEvent> buttonActionHandler = (event) -> {

			if (event.getSource() == gameController.promotionPopupQueenButton) {
				move.setFlag(Move.PromoteToQueen);
			} else if (event.getSource() == gameController.promotionPopupRookButton) {
				move.setFlag(Move.PromoteToRook);
			} else if (event.getSource() == gameController.promotionPopupBishopButton) {
				move.setFlag(Move.PromoteToBishop);
			} else if (event.getSource() == gameController.promotionPopupKnightButton) {
				move.setFlag(Move.PromoteToKnight);
			}

			gameController.boardController.finishMove(move);

			gameController.promotionPopup.setVisible(false);
			gameController.boardGrid.setDisable(false);
			gameController.historyGrid.setDisable(false);
			if (!gameController.resignButton.disableProperty().isBound()) {
				gameController.resignButton.setDisable(false);
			}
			gameController.restartButton.setDisable(false);
			if (!GameModel.isSelected() && !gameController.settingsButton.disableProperty().isBound())
				gameController.settingsButton.setDisable(false);
			if (!GameModel.isSelected() && !gameController.saveButton.disableProperty().isBound())
				gameController.saveButton.setDisable(false);
			gameController.menuButton.setDisable(false);
		};

		gameController.promotionPopupQueenButton.setOnAction(buttonActionHandler);
		gameController.promotionPopupRookButton.setOnAction(buttonActionHandler);
		gameController.promotionPopupBishopButton.setOnAction(buttonActionHandler);
		gameController.promotionPopupKnightButton.setOnAction(buttonActionHandler);
	}

	/**
	 * Opens a popup menu to ask player if he sure to do an action
	 * 
	 * @param button button which was pressed to call this function
	 */
	protected void showSurePopup(Button button) {
		gameController.surePopup.setVisible(true);
		disableUI();

		if (button == gameController.resignButton)
			TextManager.computeText(gameController.surePopupLabel, "game.surePopupResign");
		if (button == gameController.restartButton)
			TextManager.computeText(gameController.surePopupLabel, "game.surePopupRestart");
		if (button == gameController.menuButton)
			TextManager.computeText(gameController.surePopupLabel, "game.surePopupQuit");

		EventHandler<ActionEvent> buttonActionHandler = (event) -> {
			if (button == gameController.resignButton && event.getSource() == gameController.surePopupYesButton) {
				// Resign
				if (GameModel.getGameMode() == ChessMode.Network) {
					try {
						Client.send("resign");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				resign();
				return;
			}
			if (button == gameController.restartButton && event.getSource() == gameController.surePopupYesButton) {
				// Restart
				if (SettingsModel.isFlipBoard() && gameController.boardController.isRotated
						&& GameModel.getChoosenColor() != Piece.Black)
					gameController.boardController.flipBoard(true);
				GameModel.beginNewGame();
				gameController.initialize();
			}
			if (button == gameController.menuButton && event.getSource() == gameController.surePopupYesButton) {
				// Main menu
				GameModel.stopTask();
				Gui.switchTo(Gui.ChessScene.Menu);
			}

			gameController.surePopup.setVisible(false);
			gameController.boardGrid.setDisable(false);
			gameController.historyGrid.setDisable(false);
			enableButtons();
		};

		gameController.surePopupCancelButton.setOnAction(buttonActionHandler);
		gameController.surePopupYesButton.setOnAction(buttonActionHandler);
	}

	/**
	 * Takes care of actions that have to happen when resigning
	 */
	protected void resign() {
		System.out.println("resign() was called in GamePopup.java!");
		gameController.activityIndicator.visibleProperty().unbind();
		gameController.activityIndicator.setVisible(false);

		gameController.surePopup.setVisible(false);
		gameController.restartButton.setDisable(false);
		gameController.menuButton.setDisable(false);
		gameController.settingsButton.disableProperty().unbind();
		gameController.settingsButton.setDisable(false);

		if (!gameController.saveButton.disableProperty().isBound()) {
			gameController.saveButton.setDisable(false);
		}

		String key = "";
		if (GameModel.getCurrentGame().getCurrentPosition().getTurnColor() == Piece.White) {
			key = "game.whiteResigned";
		} else {
			key = "game.blackResigned";
		}
		gameController.boardGrid.getChildren().forEach(s -> {
			s.getStyleClass().removeAll("focused", "possibleMove", "checkMove", "captureMove");
		});
		TextManager.computeText(gameController.checkLabel, key);
		gameController.checkLabel.setVisible(true);
		gameController.gamePopup.showGameOverPopup(WinCondition.RESIGN);
	}

	private void enableButtons() {
		gameController.restartButton.setDisable(false);
		gameController.menuButton.setDisable(false);
		if (!gameController.resignButton.disableProperty().isBound()) {
			gameController.resignButton.setDisable(false);
		}
		if (!GameModel.isSelected() && !gameController.settingsButton.disableProperty().isBound())
			gameController.settingsButton.setDisable(false);
		if (!GameModel.isSelected() && !gameController.saveButton.disableProperty().isBound())
			gameController.saveButton.setDisable(false);
	}

	/**
	 * Opens a popup menu at the end of the game for the player to choose the next
	 * action
	 * 
	 * @param winCondition type of win for current player
	 */
	protected void showGameOverPopup(WinCondition winCondition) {

		gameController.gameOverPopup.setVisible(true);
		disableUI();

		if (winCondition == WinCondition.CHECKMATE) {
			// Checkmate
			handleCheckmate();
		} else if (winCondition == WinCondition.REMIS) {
			// Remis
			if (GameModel.getCurrentGame().getCurrentPosition().getTurnColor() == Piece.White) {
				gameController.gameOverPopupLabel.setText(TextManager.get("game.whiteInRemis"));
			} else {
				gameController.gameOverPopupLabel.setText(TextManager.get("game.blackInRemis"));
			}
			GameModel.playSound(GameModel.ChessSound.GameOver, true);
		} else if (winCondition == WinCondition.RESIGN) {
			// Resign
			if (GameModel.getCurrentGame().getCurrentPosition().getTurnColor() == Piece.White) {
				gameController.gameOverPopupLabel.setText(TextManager.get("game.gameOverPopupWhiteResign"));
			} else {
				gameController.gameOverPopupLabel.setText(TextManager.get("game.gameOverPopupBlackResign"));
			}
			GameModel.playSound(GameModel.ChessSound.GameOver, true);
		}

		EventHandler<ActionEvent> buttonActionHandler = (event) -> {
			if (event.getSource() == gameController.gameOverPopupMenuButton) { // Main menu button pressed
				Gui.switchTo(Gui.ChessScene.Menu);
			}
			if (event.getSource() == gameController.gameOverPopupRestartButton) { // Restart button pressed
				if (SettingsModel.isFlipBoard() && gameController.boardController.isRotated)
					gameController.boardController.flipBoard(true);
				GameModel.beginNewGame();
				gameController.initialize();
			}

			GameModel.playSound(GameModel.ChessSound.Win, false);
			GameModel.playSound(GameModel.ChessSound.Lose, false);
			GameModel.playSound(GameModel.ChessSound.GameOver, false);
			gameController.gameOverPopup.setVisible(false);
			gameController.restartButton.setDisable(false);
			gameController.menuButton.setDisable(false);
		};

		gameController.gameOverPopupMenuButton.setOnAction(buttonActionHandler);
		gameController.gameOverPopupRestartButton.setOnAction(buttonActionHandler);
		gameController.gameOverPopupStayButton.setOnAction(buttonActionHandler);
	}

	private void handleCheckmate() {
		if (GameModel.getGameMode() == ChessMode.Player) {
			if (GameModel.getCurrentGame().getCurrentPosition().getTurnColor() == Piece.White) {
				gameController.gameOverPopupLabel.setText(TextManager.get("game.gameOverPopupBlackWin"));
			} else {
				gameController.gameOverPopupLabel.setText(TextManager.get("game.gameOverPopupWhiteWin"));
			}
			GameModel.playSound(GameModel.ChessSound.GameOver, true);
		} else {

			if (GameModel.getChoosenColor() == Piece.White) {
				if (GameModel.getCurrentGame().getCurrentPosition().getTurnColor() == Piece.White) {
					gameController.gameOverPopupLabel.setText(TextManager.get("game.gameOverPopupLose"));
					TextManager.computeText(gameController.gameOverPopupRestartButton, "game.gameOverPopupRestartLose");
					GameModel.playSound(GameModel.ChessSound.Lose, true);
				} else {
					gameController.gameOverPopupLabel.setText(TextManager.get("game.gameOverPopupWin"));
					TextManager.computeText(gameController.gameOverPopupRestartButton, "game.gameOverPopupRestartWin");
					GameModel.playSound(GameModel.ChessSound.Win, true);
				}
			} else {
				if (GameModel.getCurrentGame().getCurrentPosition().getTurnColor() == Piece.White) {
					gameController.gameOverPopupLabel.setText(TextManager.get("game.gameOverPopupWin"));
					TextManager.computeText(gameController.gameOverPopupRestartButton, "game.gameOverPopupRestartWin");
					GameModel.playSound(GameModel.ChessSound.Win, true);
				} else {
					gameController.gameOverPopupLabel.setText(TextManager.get("game.gameOverPopupLose"));
					TextManager.computeText(gameController.gameOverPopupRestartButton, "game.gameOverPopupRestartLose");
					GameModel.playSound(GameModel.ChessSound.Lose, true);
				}
			}
		}
	}
}
