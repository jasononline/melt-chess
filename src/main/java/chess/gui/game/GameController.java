package chess.gui.game;

import java.util.function.Predicate;

import chess.gui.Gui;
import chess.gui.game.GameModel.ChessColor;
import chess.gui.settings.SettingsModel;
import chess.gui.util.GraphicsManager;
import chess.gui.util.TextManager;
import chess.model.Board;
import chess.model.Coordinate;
import chess.model.Game;
import chess.model.Move;
import chess.model.MoveValidator;
import chess.model.Piece;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Controls behaviour of GUI elements except the chessboard (see
 * BoardController). The chess.model.Game class should be used as GameModel for
 * this scene.
 */
public class GameController {

	@FXML
	private AnchorPane rootPane;
	@FXML
	private Label historyLabel;
	@FXML
	private ScrollPane historyScrollPane;
	@FXML
	private AnchorPane historyPane;
	@FXML
	private GridPane historyGrid;
	@FXML
	private GridPane lineNumbersPane;
	@FXML
	private GridPane columnLettersPane;
	@FXML
	private GridPane boardGrid;
	@FXML
	private Label timeLabel;
	@FXML
	private FlowPane whiteBeatenFlowPane;
	@FXML
	private FlowPane blackBeatenFlowPane;
	@FXML
	private Label currentMoveLabel;

	@FXML
	private ProgressIndicator activityIndicator;

	@FXML
	private Label checkLabel;
	@FXML
	private Button resignButton;
	@FXML
	private Button restartButton;
	@FXML
	private Button settingsButton;
	@FXML
	private Button menuButton;

	@FXML
	private AnchorPane promotionPopup;
	@FXML
	private Label promotionPopupLabel;
	@FXML
	private Button promotionPopupQueenButton;
	@FXML
	private Button promotionPopupRookButton;
	@FXML
	private Button promotionPopupBishopButton;
	@FXML
	private Button promotionPopupKnightButton;
	@FXML
	private AnchorPane surePopup;
	@FXML
	private Label surePopupLabel;
	@FXML
	private Button surePopupCancelButton;
	@FXML
	private Button surePopupYesButton;

	private boolean isRotated = false;
	private boolean endGame = false;
	private boolean restartGame = false;
	private boolean resignGame = false;

	@FXML
	private void initialize() {
		TextManager.computeText(historyLabel, "game.history");
		TextManager.computeText(currentMoveLabel, "game.whiteMove");
		TextManager.computeText(checkLabel, "game.whiteInCheck");
		TextManager.computeText(settingsButton, "game.settings");
		TextManager.computeText(resignButton, "game.resign");
		TextManager.computeText(restartButton, "game.restart");
		TextManager.computeText(menuButton, "game.menu");
		TextManager.computeText(promotionPopupLabel, "game.promotionTitle");
		TextManager.computeText(promotionPopupQueenButton, "game.promotionQueen");
		TextManager.computeText(promotionPopupRookButton, "game.promotionRook");
		TextManager.computeText(promotionPopupBishopButton, "game.promotionBishop");
		TextManager.computeText(promotionPopupKnightButton, "game.promotionKnight");

		ChangeListener<Number> rootPaneSizeListener = (observable, oldValue, newValue) -> {
			resize();
		};
		rootPane.widthProperty().addListener(rootPaneSizeListener);
		rootPane.heightProperty().addListener(rootPaneSizeListener);

		boardGrid.prefHeightProperty()
				.bind(Bindings.min(rootPane.widthProperty().divide(1.94), rootPane.heightProperty().divide(1.09)));
		boardGrid.prefWidthProperty()
				.bind(Bindings.min(rootPane.widthProperty().divide(1.94), rootPane.heightProperty().divide(1.09)));
		boardGrid.maxHeightProperty().bind(boardGrid.prefHeightProperty());
		boardGrid.maxWidthProperty().bind(boardGrid.prefWidthProperty());

		promotionPopup.prefHeightProperty().bind(boardGrid.heightProperty().divide(4.4));
		promotionPopup.prefWidthProperty().bind(boardGrid.widthProperty().divide(1.32));
		promotionPopup.maxHeightProperty().bind(promotionPopup.prefHeightProperty());
		promotionPopup.maxWidthProperty().bind(promotionPopup.prefWidthProperty());
		surePopup.prefHeightProperty().bind(boardGrid.heightProperty().divide(4.4));
		surePopup.prefWidthProperty().bind(boardGrid.widthProperty().divide(1.32));
		surePopup.maxHeightProperty().bind(promotionPopup.prefHeightProperty());
		surePopup.maxWidthProperty().bind(promotionPopup.prefWidthProperty());

		lineNumbersPane.prefHeightProperty().bind(boardGrid.heightProperty());
		lineNumbersPane.prefWidthProperty()
				.bind(Bindings.min(rootPane.widthProperty().divide(42.66), rootPane.heightProperty().divide(24)));

		columnLettersPane.prefHeightProperty().bind(lineNumbersPane.widthProperty());
		columnLettersPane.prefWidthProperty().bind(boardGrid.widthProperty());

		checkLabel.setVisible(false);
		promotionPopup.setVisible(false);
		surePopup.setVisible(false);
		activityIndicator.setVisible(false);

		boardGrid.getChildren().forEach(s -> {
			s.getStyleClass().removeAll("focused", "possibleMove", "checkMove", "captureMove");
		});

		if (GameModel.getCurrentGame().getCurrentPosition().getTurnColor() == Piece.Black && !isRotated
				&& SettingsModel.isFlipBoard()) {
			flipBoard();
		}
		updateUI();
	}

	@FXML
	private void handleButtonOnAction(ActionEvent event) {
		Button button = (Button) event.getSource();

		if (button != settingsButton && !resignGame && !restartGame && !endGame)
			showSurePopup(event);

		if (button == resignButton && resignGame) {
			// TODO: End current game (leave game scene open)

			System.out.println("Resign");
			resignGame = false;
		}

		if (button == restartButton && restartGame) {
			restartGame = false;
			System.out.println("Restart");
			if (SettingsModel.isFlipBoard() && isRotated)
				flipBoard();
			GameModel.beginNewGame();
			initialize();
		}

		if (button == settingsButton) {
			SettingsModel.setLastScene(Gui.ChessScene.Game);
			Gui.switchTo(Gui.ChessScene.Settings);
		}

		if (button == menuButton && endGame) {
			endGame = false;
			Gui.switchTo(Gui.ChessScene.Menu);
		}
	}

	/**
	 * Moves the selected Piece to the selected destination if allowed
	 * 
	 * @param startIndex  the index of the selected piece
	 * @param targetIndex the selected destination
	 */
	private void movePieceOnBoard(int startIndex, int targetIndex) {
		Move move = new Move(startIndex, targetIndex);
		Board board = GameModel.getCurrentGame().getCurrentPosition();
		Move testMove = new Move(startIndex, targetIndex);
		Game testGame = GameModel.getCurrentGame();
		testGame.addFlag(testMove);

		if (Piece.isColor(board.getPieceAt(startIndex), board.getTurnColor())
				&& MoveValidator.validateMove(testGame.getCurrentPosition(), testMove)) {
			// if promotion is possible
			if ((Coordinate.isOnUpperBorder(targetIndex) || Coordinate.isOnLowerBorder(targetIndex))
					&& Piece.isType(board.getPieceAt(startIndex), Piece.Pawn)) {
				ChessColor currentColor;
				if (GameModel.getCurrentGame().getCurrentPosition().getTurnColor() == Piece.White) {
					currentColor = ChessColor.White;
				} else {
					currentColor = ChessColor.Black;
				}
				// open the PopupMenu to choose promotion
				showPromotionPopup(currentColor, move);
				return;
			}
		}
		finishMove(move);
	}

	/**
	 * Calls all the functions to update the UI
	 */
	private void updateUI() {
		updateMovesHistoryUI();
		updateBeatenPiecesUI();
		updatePiecesUI();
		updateLabelsUI();
	}

	private void showPossibleMoves(int startPosition) {

		Board board = GameModel.getCurrentGame().getCurrentPosition();
		if (!Piece.isColor(board.getPieceAt(startPosition), board.getTurnColor()))
			return;

		for (Move move : GameModel.getPossibleMoves(startPosition)) {
			Node squareNode = boardGrid.getChildren().get(move.getTargetSquare());

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
		resize();
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
		String pieceName;

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
				centerPiecePosition(pieceView);
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
	 * Finishes the current move after flags for promotion are set.
	 * 
	 * @param move the current move containing flags for promotion
	 */
	private void finishMove(Move move) {
		GameModel.getCurrentGame().addFlag(move);
		System.out.println("Called: movePieceOnBorad(" + move.toString() + ")");
		if (GameModel.getCurrentGame().attemptMove(move)) {
			System.out.println("Move happened: " + move.toString());
			GameModel.getMovesHistory().add(0, move);
			if (SettingsModel.isFlipBoard())
				flipBoard();
		} else {
			System.out.println("Game.attemptMove() did not allow " + move.toString());
			return;
		}

		updateUI();
		if (checkForGameOver())
			return;

		if (GameModel.getGameMode() == GameModel.ChessMode.Computer) {
			activityIndicator.setVisible(true);
			GameModel.setAllowedToMove(false);

			// TODO rest of this function should happen in different thread
			GameModel.performEngineMove();
			activityIndicator.setVisible(false);
			checkForGameOver();
			updateUI();
			GameModel.setAllowedToMove(true);
		}

	}

	private void activateIndikator(boolean status) {
		System.out.println("activateIndikator was called.\n");
		activityIndicator.setVisible(status);
	}

	/**
	 * Checks whether the current game is over if that is the case the endGame()
	 * function will be called
	 */
	private boolean checkForGameOver() {
		checkLabel.setVisible(false);
		Game game = GameModel.getCurrentGame();
		if (game.checkCheck()) {
			System.out.println("checkForGameOver was called");
			String key;
			if (game.getCurrentPosition().getTurnColor() == Piece.White) {
				key = "game.whiteInCheck";
			} else {
				key = "game.blackInCheck";
			}
			checkLabel.setVisible(true);
			TextManager.computeText(checkLabel, key);
		}
		if (game.checkWinCondition() != 0) {
			endGame();
			return true;
		}
		return false;
	}

	/**
	 * Ends the current game
	 */
	private void endGame() {
		Game game = GameModel.getCurrentGame();
		int winCondition = game.checkWinCondition();

		// only end the game when it should be ended
		if (winCondition == 0)
			return;

		String key = "";
		if (winCondition == 1) {
			if (game.getCurrentPosition().getTurnColor() == Piece.White) {
				key = "game.whiteInCheckmate";
			} else {
				key = "game.blackInCheckmate";
			}
		} else if (winCondition == 2) {
			if (game.getCurrentPosition().getTurnColor() == Piece.White) {
				key = "game.whiteInRemis";
			} else {
				key = "game.blackInRemis";
			}
		}
		TextManager.computeText(checkLabel, key);
		boardGrid.setDisable(true);
		historyPane.setDisable(true);
		resignButton.setDisable(true);
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
				square.getStyleClass().add("focused");
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
				square.getStyleClass().removeAll("focused");
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

		if (!GameModel.isSelected()) { // if a piece has not yet been selected (first press)

			if (!square.getChildren().isEmpty()) {

				GameModel.setSelectedIndex(index);
				square.getStyleClass().add("focused");
				settingsButton.setDisable(true);

				if (SettingsModel.isShowPossibleMoves())
					showPossibleMoves(index);
			}
			return;
		}

		// if a piece has already been selected (second press)

		// if one touch rule is disabled and press on the same square
		if (!SettingsModel.isOneTouchRule() && GameModel.getSelectedIndex() == index) {
			settingsButton.setDisable(false);
			GameModel.setSelectedIndex(-1);
			boardGrid.getChildren().forEach(s -> {
				s.getStyleClass().removeAll("focused", "possibleMove", "checkMove", "captureMove");
			});

			// if one touch rule is disabled and press not on the same square
		} else if (!SettingsModel.isOneTouchRule() || GameModel.getSelectedIndex() != index) {

			if (SettingsModel.isOneTouchRule() && !GameModel.getPossibleMoves(GameModel.getSelectedIndex())
					.contains(new Move(GameModel.getSelectedIndex(), index))) {
				return;
			}

			movePieceOnBoard(GameModel.getSelectedIndex(), Coordinate.toIndex(square.getId()));

			boardGrid.getChildren().forEach(s -> {
				s.getStyleClass().removeAll("focused", "possibleMove", "checkMove", "captureMove");
			});
			settingsButton.setDisable(promotionPopup.isVisible());

			GameModel.setSelectedIndex(-1);
		}

	}

	/**
	 * Opens a popup menu to choose promotion Should only be called if promotion is
	 * possible
	 * 
	 * @param forColor the current color on the board
	 * @param move     the move that brings a pawn to the last rank
	 */
	public void showPromotionPopup(ChessColor forColor, Move move) {
		ImageView queenIcon = GraphicsManager
				.getGraphicAsImageView(forColor == ChessColor.White ? "queen_white" : "queen_black");
		ImageView rookIcon = GraphicsManager
				.getGraphicAsImageView(forColor == ChessColor.White ? "rook_white" : "rook_black");
		ImageView bishopIcon = GraphicsManager
				.getGraphicAsImageView(forColor == ChessColor.White ? "bishop_white" : "bishop_black");
		ImageView knightIcon = GraphicsManager
				.getGraphicAsImageView(forColor == ChessColor.White ? "knight_white" : "knight_black");
		ImageView[] icons = { queenIcon, rookIcon, bishopIcon, knightIcon };
		for (ImageView icon : icons) {
			icon.setPreserveRatio(true);
			icon.setFitHeight(20);
		}
		promotionPopupQueenButton.setGraphic(queenIcon);
		promotionPopupRookButton.setGraphic(rookIcon);
		promotionPopupBishopButton.setGraphic(bishopIcon);
		promotionPopupKnightButton.setGraphic(knightIcon);
		promotionPopup.setVisible(true);
		boardGrid.setDisable(true);
		historyPane.setDisable(true);
		resignButton.setDisable(true);
		restartButton.setDisable(true);
		settingsButton.setDisable(true);
		menuButton.setDisable(true);

		EventHandler<ActionEvent> buttonActionHandler = (event) -> {

			if (event.getSource() == promotionPopupQueenButton) {
				move.setFlag(Move.PromoteToQueen);
				finishMove(move);
			}
			if (event.getSource() == promotionPopupRookButton) {
				move.setFlag(Move.PromoteToRook);
				finishMove(move);
			}
			if (event.getSource() == promotionPopupBishopButton) {
				move.setFlag(Move.PromoteToBishop);
				finishMove(move);
			}
			if (event.getSource() == promotionPopupKnightButton) {
				move.setFlag(Move.PromoteToKnight);
				finishMove(move);
			}

			promotionPopup.setVisible(false);
			boardGrid.setDisable(false);
			historyPane.setDisable(false);
			resignButton.setDisable(false);
			restartButton.setDisable(false);
			if (!GameModel.isSelected())
				settingsButton.setDisable(false);
			menuButton.setDisable(false);
		};

		promotionPopupQueenButton.setOnAction(buttonActionHandler);
		promotionPopupRookButton.setOnAction(buttonActionHandler);
		promotionPopupBishopButton.setOnAction(buttonActionHandler);
		promotionPopupKnightButton.setOnAction(buttonActionHandler);
	}

	private void showSurePopup(ActionEvent sourceEvent) {
		surePopup.setVisible(true);
		boardGrid.setDisable(true);
		historyPane.setDisable(true);
		resignButton.setDisable(true);
		restartButton.setDisable(true);
		settingsButton.setDisable(true);
		menuButton.setDisable(true);

		EventHandler<ActionEvent> buttonActionHandler = (event) -> {
			if (sourceEvent.getSource() == resignButton && event.getSource() == surePopupYesButton)
				resignGame = true;
			if (sourceEvent.getSource() == restartButton && event.getSource() == surePopupYesButton)
				restartGame = true;
			if (sourceEvent.getSource() == menuButton && event.getSource() == surePopupYesButton)
				endGame = true;

			if (event.getSource() == surePopupYesButton)
				handleButtonOnAction(sourceEvent);

			surePopup.setVisible(false);
			boardGrid.setDisable(false);
			historyPane.setDisable(false);
			resignButton.setDisable(false);
			restartButton.setDisable(false);
			if (!GameModel.isSelected())
				settingsButton.setDisable(false);
			menuButton.setDisable(false);
		};

		surePopupCancelButton.setOnAction(buttonActionHandler);
		surePopupYesButton.setOnAction(buttonActionHandler);

	}

	/**
	 * Flips the board
	 */
	private void flipBoard() {
		String lines = isRotated ? "87654321" : "12345678";
		String columns = isRotated ? "abcdefgh" : "hgfedcba";

		for (int i = 0; i < 8; i++) {
			((Label) lineNumbersPane.getChildren().get(i)).setText(lines.charAt(i) + "");
			((Label) columnLettersPane.getChildren().get(i)).setText(columns.charAt(i) + "");
		}

		// -------- rotate with animation --------
		RotateTransition transition = new RotateTransition(Duration.seconds(1.2), boardGrid);
		transition.setToAngle(isRotated ? 0 : 180);
		// transition.setDelay(Duration.seconds(0.5));
		transition.play();

		// -------- rotate without animation --------
		// boardGrid.setRotate(isRotated ? 0 : 180);

		for (Node squareNode : boardGrid.getChildren()) {
			squareNode.setRotate(isRotated ? 0 : 180);
		}
		isRotated = !isRotated;
	}

	/**
	 * Resizes the GameView
	 */
	private void resize() {
		double rootHeight = rootPane.getHeight();
		double rootWidth = rootPane.getWidth();
		double fontSize = Math.min(rootHeight / 28.8, rootWidth / 51.2);
		double borderRadius = Math.min(rootHeight / 72, rootWidth / 128 * 1.2);
		double historyBorderWidth = Math.min(rootHeight / 360, rootWidth / 640);
		double timeFontSize = Math.min(rootHeight / 18, rootWidth / 32 * 1.2);
		double historyFontSize = Math.min(rootHeight / 36, rootWidth / 64);
		double historyButtonFontSize = Math.min(rootHeight / 40, rootWidth / 71.11 * 1.2);

		setButtonStyle(resignButton, new double[] { rootWidth, rootHeight }, rootWidth / 128);
		setButtonStyle(restartButton, new double[] { rootWidth, rootHeight }, rootWidth / 128);
		setButtonStyle(settingsButton, new double[] { rootWidth, rootHeight }, rootWidth / 128);
		setButtonStyle(menuButton, new double[] { rootWidth, rootHeight }, rootWidth / 128);

		currentMoveLabel.setStyle("-fx-font-size: " + fontSize);
		checkLabel.setStyle("-fx-font-size: " + historyFontSize);
		timeLabel.setStyle("-fx-font-size: " + timeFontSize);
		historyLabel.setStyle("-fx-font-size: " + historyFontSize);
		promotionPopupLabel.setStyle("-fx-font-size: " + fontSize);
		surePopupLabel.setStyle("-fx-font-size: " + fontSize);

		historyLabel.getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius + "; -fx-border-radius: " + borderRadius);
		timeLabel.getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius + "; -fx-border-radius: " + borderRadius);

		historyGrid.setVgap(rootHeight / 72);
		for (Node historyNode : historyGrid.getChildren()) {
			AnchorPane historyButtonPane = (AnchorPane) historyNode;
			Button historyButton = (Button) historyButtonPane.getChildren().get(0);
			historyButton.setStyle("-fx-font-size: " + historyButtonFontSize + "; -fx-background-radius: " + borderRadius / 2
					+ "; -fx-border-radius: " + borderRadius / 2 + "; -fx-border-width: " + historyBorderWidth);
			// historyButton.setMaxHeight(rootHeight / 20.57);
		}

		promotionPopupQueenButton.setStyle("-fx-font-size: " + fontSize / 1.66 + "; -fx-graphic-text-gap: " + fontSize / 5
				+ "; -fx-background-radius: " + borderRadius / 2 + "; -fx-border-radius: " + borderRadius / 2
				+ "; -fx-border-width: " + historyBorderWidth);
		promotionPopupRookButton.setStyle("-fx-font-size: " + fontSize / 1.66 + "; -fx-graphic-text-gap: " + fontSize / 5
				+ "; -fx-background-radius: " + borderRadius / 2 + "; -fx-border-radius: " + borderRadius / 2
				+ "; -fx-border-width: " + historyBorderWidth);
		promotionPopupBishopButton.setStyle("-fx-font-size: " + fontSize / 1.66 + "; -fx-graphic-text-gap: " + fontSize / 5
				+ "; -fx-background-radius: " + borderRadius / 2 + "; -fx-border-radius: " + borderRadius / 2
				+ "; -fx-border-width: " + historyBorderWidth);
		promotionPopupKnightButton.setStyle("-fx-font-size: " + fontSize / 1.66 + "; -fx-graphic-text-gap: " + fontSize / 5
				+ "; -fx-background-radius: " + borderRadius / 2 + "; -fx-border-radius: " + borderRadius / 2
				+ "; -fx-border-width: " + historyBorderWidth);
		surePopupCancelButton.setStyle("-fx-font-size: " + historyButtonFontSize + "; -fx-graphic-text-gap: "
				+ historyButtonFontSize + "; -fx-background-radius: " + borderRadius / 2 + "; -fx-border-radius: "
				+ borderRadius / 2 + "; -fx-border-width: " + historyBorderWidth);
		surePopupYesButton.setStyle("-fx-font-size: " + historyButtonFontSize + "; -fx-graphic-text-gap: "
				+ historyButtonFontSize + "; -fx-background-radius: " + borderRadius / 2 + "; -fx-border-radius: "
				+ borderRadius / 2 + "; -fx-border-width: " + historyBorderWidth);

		ImageView[] promotionIcons = { (ImageView) promotionPopupQueenButton.getGraphic(),
				(ImageView) promotionPopupRookButton.getGraphic(), (ImageView) promotionPopupBishopButton.getGraphic(),
				(ImageView) promotionPopupKnightButton.getGraphic() };
		for (ImageView icon : promotionIcons) {
			icon.setFitHeight(historyFontSize);
		}

		ImageView[] icons = { (ImageView) resignButton.getGraphic(), (ImageView) restartButton.getGraphic(),
				(ImageView) settingsButton.getGraphic(), (ImageView) menuButton.getGraphic(),
				(ImageView) surePopupCancelButton.getGraphic(), (ImageView) surePopupYesButton.getGraphic() };
		for (ImageView icon : icons) {
			icon.setFitHeight(fontSize);
		}

		// columnLettersPane.setLayoutY(-(rootHeight - rootHeight / 24 - (rootHeight /
		// 24 + boardGrid.getHeight())));
		for (Node number : lineNumbersPane.getChildren()) {
			number.setStyle("-fx-font-size: " + Math.min(rootHeight / 60, rootWidth / 106.66));
		}
		for (Node letter : columnLettersPane.getChildren()) {
			letter.setStyle("-fx-font-size: " + Math.min(rootHeight / 60, rootWidth / 106.66));
		}

		Double clipSize = Math.min(rootPane.getWidth() / 1.94, rootPane.getHeight() / 1.09);
		Rectangle clip = new Rectangle(clipSize, clipSize);
		clip.setArcWidth(borderRadius * 2);
		clip.setArcHeight(borderRadius * 2);
		boardGrid.setClip(clip);

		for (Node squareNode : boardGrid.getChildren()) {
			AnchorPane square = (AnchorPane) squareNode;
			square.setStyle("-fx-border-width: " + historyBorderWidth * 1.25);

			Predicate<Node> pieces = image -> image.getId().equals("piece");
			// Predicate<Node> possibleMoves = image -> image.getId().equals("possibleMove");

			for (Node node : square.getChildren().filtered(pieces)) {
				ImageView piece = (ImageView) node;
				centerPiecePosition(piece);
			}

			// for (Node node : square.getChildren().filtered(possibleMoves)) {
			// 	ImageView icon = (ImageView) node;
			// 	stylePossibleMoveIcon(icon);
			// }
		}

	}

	/**
	 * Sets piece position in the center of the square
	 * 
	 * @param pieceView piece image view
	 */
	private void centerPiecePosition(ImageView pieceView) {
		pieceView.setFitHeight(Math.min(rootPane.getHeight() / 14.4, rootPane.getWidth() / 25.6));
		pieceView.setY((Math.min(rootPane.getHeight() / 8.72, rootPane.getWidth() / 15.51) - pieceView.getFitHeight()) / 2);
		pieceView.setX((Math.min(rootPane.getHeight() / 8.72, rootPane.getWidth() / 15.51)
				- pieceView.boundsInParentProperty().get().getWidth()) / 2);
	}

	/**
	 * Styles possible move icon
	 * 
	 * @param icon icon image view
	 */
	private void stylePossibleMoveIcon(ImageView icon) {
		icon.setFitHeight(Math.min(rootPane.getHeight() / 36, rootPane.getWidth() / 64));
		icon.setFitWidth(Math.min(rootPane.getHeight() / 28.8, rootPane.getWidth() / 51.2));
		icon.setX((Math.min(rootPane.getHeight() / 8.72, rootPane.getWidth() / 15.51) - icon.getFitHeight()) / 2);
		icon.setY((Math.min(rootPane.getHeight() / 8.72, rootPane.getWidth() / 15.51) - icon.getFitWidth()) / 2);
	}

	/**
	 * Sets the button style
	 * 
	 * @param button         the button
	 * @param rootSize       the root size
	 * @param graphicTextGap the graphic text gap
	 */
	private void setButtonStyle(Button button, double[] rootSize, double graphicTextGap) {
		double fontSize = Math.min(rootSize[1] / 28.8, rootSize[0] / 51.2);
		double borderRadius = Math.min(rootSize[1] / 72, rootSize[0] / 128 * 1.2);
		double borderWidth = Math.min(rootSize[1] / 240, rootSize[0] / 426.66);
		button.setStyle(
				"-fx-font-size: " + fontSize + "; -fx-graphic-text-gap: " + graphicTextGap + "; -fx-background-radius: "
						+ borderRadius + "; -fx-border-radius: " + borderRadius + "; -fx-border-width: " + borderWidth);
	}
}
