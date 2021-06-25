package chess.gui.game;

import java.io.File;

import chess.gui.Gui;
import chess.gui.settings.SettingsModel;
import chess.gui.util.GraphicsManager;
import chess.gui.util.ResizeManager;
import chess.model.Board;
import chess.model.Coordinate;
import chess.model.Game;
import chess.model.Move;
import chess.model.Piece;
import chess.util.SavingManager;
import chess.util.Saving;
import chess.util.TextManager;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Controls behaviour of GUI elements except the chessboard (see
 * BoardController). The chess.model.Game class should be used as GameModel for
 * this scene.
 */
@SuppressWarnings({ "PMD.UnusedPrivateMethod", "PMD.TooManyFields" })
// Some methods in this class seem unused but they are used by FXML
// since there are many elements to be controlled in the game view, many fields
// are required
public class GameController {

	@FXML
	public AnchorPane rootPane;
	@FXML
	public Label historyLabel;
	@FXML
	public ScrollPane historyScrollPane;
	@FXML
	public AnchorPane historyPane;
	@FXML
	public GridPane historyGrid;
	@FXML
	public GridPane lineNumbersPane;
	@FXML
	public GridPane columnLettersPane;
	@FXML
	public GridPane boardGrid;
	@FXML
	public Label timeLabel;
	@FXML
	public FlowPane whiteBeatenFlowPane;
	@FXML
	public FlowPane blackBeatenFlowPane;
	@FXML
	public Label currentMoveLabel;

	@FXML
	public ProgressIndicator activityIndicator;

	@FXML
	public Label checkLabel;
	@FXML
	public Button resignButton;
	@FXML
	public Button restartButton;
	@FXML
	public Button settingsButton;
	@FXML
	public Button menuButton;
	@FXML
	public Button saveButton;

	@FXML
	public AnchorPane promotionPopup;
	@FXML
	public Label promotionPopupLabel;
	@FXML
	public Button promotionPopupQueenButton;
	@FXML
	public Button promotionPopupRookButton;
	@FXML
	public Button promotionPopupBishopButton;
	@FXML
	public Button promotionPopupKnightButton;
	@FXML
	public AnchorPane surePopup;
	@FXML
	public Label surePopupLabel;
	@FXML
	public Button surePopupCancelButton;
	@FXML
	public Button surePopupYesButton;
	@FXML
	public AnchorPane gameOverPopup;
	@FXML
	public Label gameOverPopupTitle;
	@FXML
	public Label gameOverPopupLabel;
	@FXML
	public Button gameOverPopupMenuButton;
	@FXML
	public Button gameOverPopupRestartButton;
	@FXML
	public Button gameOverPopupStayButton;

	private static String focused = "focused";

	private ResizeManager resizeManager = new ResizeManager(this);
	protected GamePopup gamePopup = new GamePopup(this);
	protected BoardController boardController = new BoardController(this);

	/**
	 * Initializes the elements in the Game view
	 */
	@FXML
	protected void initialize() {
		TextManager.computeText(historyLabel, "game.history");
		TextManager.computeText(currentMoveLabel, "game.whiteMove");
		TextManager.computeText(checkLabel, "game.whiteInCheck");
		TextManager.computeText(settingsButton, "game.settings");
		TextManager.computeText(resignButton, "game.resign");
		TextManager.computeText(restartButton, "game.restart");
		TextManager.computeText(menuButton, "game.menu");
		TextManager.computeText(saveButton, "game.save");

		ChangeListener<Number> rootPaneSizeListener = (observable, oldValue, newValue) -> {
			resizeManager.resizeGame(rootPane.getWidth(), rootPane.getHeight());
		};
		rootPane.widthProperty().addListener(rootPaneSizeListener);
		rootPane.heightProperty().addListener(rootPaneSizeListener);

		checkLabel.setVisible(false);
		if (!resignButton.disableProperty().isBound()) {
			resignButton.setDisable(false);
		}
		restartButton.setDisable(false);
		if (!settingsButton.disableProperty().isBound()) {
			settingsButton.setDisable(false);
		}
		menuButton.setDisable(false);
		if (!saveButton.disableProperty().isBound()) {
			saveButton.setDisable(false);
		}

		activityIndicator.visibleProperty().unbind();
		activityIndicator.setVisible(false);

		gamePopup.initialize();
		boardController.initialize();

		updateUI();

		boardController.continueAccordingToGameMode();

	}

	/**
	 * Handles what to do when User clicks a button in the game view
	 * 
	 * @param event the Action event to handle
	 */
	@FXML
	protected void handleButtonOnAction(ActionEvent event) {
		Button button = (Button) event.getSource();

		if (button.equals(settingsButton)) {
			SettingsModel.setLastScene(Gui.ChessScene.Game);
			Gui.switchTo(Gui.ChessScene.Settings);
		} else if (button.equals(saveButton)) {
			saveGame();
		} else {
			gamePopup.showSurePopup(button);
		}
	}

	/**
	 * Calls all the functions to update the UI
	 */
	public void updateUI() {
		updateMovesHistoryUI();
		updateBeatenPiecesUI();
		updatePiecesUI();
		updateLabelsUI();
	}

	/**
	 * Updates the beaten pieces on the UI
	 */
	private void updateBeatenPiecesUI() {

		GameModel.updateBeatenPiecesLists();

		whiteBeatenFlowPane.getChildren().clear();
		blackBeatenFlowPane.getChildren().clear();
		for (ImageView piece : GameModel.getBeatenWhitePiecesGraphics()) {
			piece.setFitHeight(25);
			whiteBeatenFlowPane.getChildren().add(piece);
		}
		for (ImageView piece : GameModel.getBeatenBlackPiecesGraphics()) {
			piece.setFitHeight(25);
			blackBeatenFlowPane.getChildren().add(piece);
		}
	}

	/**
	 * Updates the move history on the UI
	 */
	private void updateMovesHistoryUI() {

		historyGrid.getChildren().clear();

		for (Move move : GameModel.getMovesHistory()) {

			Button moveButton = new Button();
			moveButton.setText(move.toString());
			moveButton.setId(move.toString());
			moveButton.getStyleClass().addAll("button", "historyButton");
			AnchorPane.setBottomAnchor(moveButton, 0.);
			AnchorPane.setTopAnchor(moveButton, 0.);
			AnchorPane.setLeftAnchor(moveButton, 0.);
			AnchorPane.setRightAnchor(moveButton, 0.);
			AnchorPane pane = new AnchorPane();
			pane.getChildren().add(moveButton);

			int rowIndex = historyGrid.getRowCount();
			historyGrid.add(pane, 0, rowIndex);
		}

		historyScrollPane.setContent(historyGrid);
		resizeManager.resizeGame(rootPane.getWidth(), rootPane.getHeight());
	}

	/**
	 * Updates the pieces on the UI
	 */
	private void updatePiecesUI() {
		// clear the board
		AnchorPane pane;
		Board board = GameModel.getCurrentGame().getCurrentPosition();
		ImageView pieceView;
		int piece;

		for (int i = 0; i <= 63; i++) {
			// clear the pane
			pane = (AnchorPane) boardGrid.getChildren().get(i);
			pane.getChildren().clear();

			piece = board.getPieceAt(i);

			// if there is a piece here
			if (piece != 0) {
				// get the graphic of the piece
				pieceView = GraphicsManager.getGraphicAsImageView(Piece.toName(piece));
				pieceView.setFitHeight(50);
				pieceView.setId("piece");
				// put the graphic in th pane
				pane.getChildren().add(pieceView);
				resizeManager.centerPiecePosition(pieceView);
			}
		}
	}

	/**
	 * Updates the labels on the UI
	 */
	private void updateLabelsUI() {
		String key;
		// CurrentMoveLabel
		if (GameModel.getCurrentGame().getCurrentPosition().getTurnColor() == Piece.White) {
			key = "game.whiteMove";
		} else {
			key = "game.blackMove";
		}
		TextManager.computeText(currentMoveLabel, key);
	}

	/**
	 * Saves current game
	 */
	private void saveGame() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("MELT-CHESS Files", "*.melt"));
		fc.setInitialFileName(SavingManager.getDefaultFileName());
		File file = fc.showSaveDialog(rootPane.getScene().getWindow());
		if (file != null) {
			Saving saving = new Saving(GameModel.getCurrentGame(), GameModel.getMovesHistory());
			SavingManager.saveGame(saving, file);
		}
	}

	@FXML
	private void handleSquareMouseEnter(MouseEvent event) {
		Node source = (Node) event.getSource();

		if (source instanceof AnchorPane) {
			AnchorPane square = (AnchorPane) source;

			if (!square.getChildren().isEmpty()) {
				square.getStyleClass().add("cursor");
			}

			if (GameModel.isSelected()) {
				square.getStyleClass().add(focused);
			}
		}
	}

	@FXML
	private void handleSquareMouseExit(MouseEvent event) {
		Node source = (Node) event.getSource();

		if (source instanceof AnchorPane) {
			AnchorPane square = (AnchorPane) source;

			if (!square.getChildren().isEmpty()) {
				square.getStyleClass().removeAll("cursor");
			}

			if (GameModel.isSelected() && square != boardGrid.getChildren().get(GameModel.getSelectedIndex())) {
				square.getStyleClass().removeAll(focused);
			}
		}

	}

	@FXML
	private void handleSquareMousePress(MouseEvent event) {

		Node source = (Node) event.getSource();

		if (!(source instanceof AnchorPane || !(GameModel.isAllowedToMove())))
			return;

		AnchorPane square = (AnchorPane) event.getSource();
		int index = Coordinate.toIndex(square.getId());

		if (!GameModel.isSelected()) {
			// if no piece has yet been selected (first press)
			handleFirstClick(square, index);
			return;
		} else {
			// if a piece has already been selected (second press)
			handleSecondClick(index);
		}
	}

	/**
	 * Has ability to control what should happen when there was a click on the board
	 * and no Piece has been selected yet
	 * 
	 * @param square the AnchorPane of the clicked on square
	 * @param index  the index of the clicked on square
	 */
	private void handleFirstClick(AnchorPane square, int index) {
		if (!square.getChildren().isEmpty()) {

			int piece = GameModel.getCurrentGame().getCurrentPosition().getPieceAt(index);
			boolean colorMatch = Piece.isColor(piece, GameModel.getCurrentGame().getCurrentPosition().getTurnColor());
			if (SettingsModel.isOneTouchRule() && (!colorMatch || GameModel.getPossibleMoves(index).isEmpty())) {
				GameModel.playSound(GameModel.ChessSound.Failure, true);
				return;
			}

			GameModel.setSelectedIndex(index);
			square.getStyleClass().add(focused);
			if (!settingsButton.disableProperty().isBound()) {
				settingsButton.setDisable(true);
			}
			if (!saveButton.disableProperty().isBound())
				saveButton.setDisable(true);
			if (SettingsModel.isShowPossibleMoves())
				boardController.showPossibleMoves(index);
		}
	}

	/**
	 * Has ability to control what should happen when there was a click on the board
	 * and a Piece has been selected yet
	 * 
	 * @param index the index of the clicked on square
	 */
	private void handleSecondClick(int index) {
		Move currentMove = new Move(GameModel.getSelectedIndex(), index);
		Game testGame = GameModel.getCurrentGame();
		testGame.addFlag(currentMove);
		int piece = GameModel.getCurrentGame().getCurrentPosition().getPieceAt(GameModel.getSelectedIndex());
		boolean colorMatch = Piece.isColor(piece, GameModel.getCurrentGame().getCurrentPosition().getTurnColor());

		if (SettingsModel.isOneTouchRule()
				&& (!GameModel.getPossibleMoves(GameModel.getSelectedIndex()).contains(currentMove) || !colorMatch)) {
			GameModel.playSound(GameModel.ChessSound.Failure, true);
		} else {
			if (GameModel.getSelectedIndex() == index) {
				// if clicked on selected piece
				if (!settingsButton.disableProperty().isBound()) {
					settingsButton.setDisable(false);
				}
				if (!saveButton.disableProperty().isBound())
					saveButton.setDisable(false);
				GameModel.setSelectedIndex(-1);
				boardGrid.getChildren().forEach(s -> {
					s.getStyleClass().removeAll(focused, "possibleMove", "checkMove", "captureMove");
				});

			} else {
				// if not clicked on selected piece
				if (!settingsButton.disableProperty().isBound()) {
					settingsButton.setDisable(promotionPopup.isVisible());
				}
				if (!saveButton.disableProperty().isBound()) {
					saveButton.setDisable(promotionPopup.isVisible());
				}
				boardController.movePieceOnBoard(GameModel.getSelectedIndex(), index);

				boardGrid.getChildren().forEach(s -> {
					s.getStyleClass().removeAll(focused, "possibleMove", "checkMove", "captureMove");
				});
				GameModel.setSelectedIndex(-1);
			}
		}
	}
}
