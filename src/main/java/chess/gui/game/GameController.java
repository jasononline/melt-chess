package chess.gui.game;

import chess.gui.Gui;
import chess.gui.game.GameModel.ChessColor;
import chess.gui.settings.SettingsModel;
import chess.gui.util.GraphicsManager;
import chess.gui.util.TextManager;
import chess.model.Coordinate;
import chess.model.Move;
import chess.model.Piece;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;

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

		updateMovesHistoryUI();
		updateBeatenPiecesUI();
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
			// TODO: End current game
			// TODO: Start new game

			restartGame = false;
			System.out.println("Restart");
			GameModel.reset();
			initialize();

		}

		if (button == settingsButton) {
			SettingsModel.setLastScene(Gui.ChessScene.Game);
			Gui.switchTo(Gui.ChessScene.Settings);
		}

		if (button == menuButton && endGame) {
			// TODO: End current game

			GameModel.reset();
			endGame = false;
			Gui.switchTo(Gui.ChessScene.Menu);
		}
	}

	private boolean movePieceOnBoard(int startIndex, int targetIndex) {
		AnchorPane startSquare = (AnchorPane) boardGrid.getChildren().get(startIndex);
		AnchorPane targetSquare = (AnchorPane) boardGrid.getChildren().get(targetIndex);

		if (!startSquare.getChildren().isEmpty()) {

			ImageView piece = (ImageView) startSquare.getChildren().get(0);

			if (startSquare != targetSquare) {

				// Check if the move is valid or allowed
				if (true) {
					// check if target square has piece
					if (!targetSquare.getChildren().isEmpty()) {
						ImageView targetPiece = (ImageView) targetSquare.getChildren().get(0);
						String targetPieceName = targetPiece.getImage().getUrl()
								.substring(targetPiece.getImage().getUrl().lastIndexOf("/") + 1);
						String targetPieceType = targetPieceName.split("_")[0];
						String targetPieceColor = targetPieceName.split("_")[1];
						targetPieceColor = targetPieceColor.substring(0, targetPieceColor.lastIndexOf("."));

						if (targetPieceColor.equals("white"))
							GameModel.getBeatenWhitePiecesGraphics().add(targetPiece);
						if (targetPieceColor.equals("black"))
							GameModel.getBeatenBlackPiecesGraphics().add(targetPiece);

						updateBeatenPiecesUI();
					}

					targetSquare.getChildren().clear();
					targetSquare.getChildren().add(piece);
					GameModel.getMovesHistory().add(0, new Move(startIndex, targetIndex));
					updateMovesHistoryUI();

					return true;

				}

			}

		}

		return false;
	}

	private void updateBeatenPiecesUI() {

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

		if (source instanceof AnchorPane) {
			AnchorPane square = (AnchorPane) source;

			if (GameModel.isSelected()) { // if a piece has already been selected (second press)

				if (!SettingsModel.isOneTouchRule() || GameModel.getSelectedIndex() != Coordinate.toIndex(square.getId())) {
					movePieceOnBoard(GameModel.getSelectedIndex(), Coordinate.toIndex(square.getId()));

					boardGrid.getChildren().forEach(s -> {
						s.getStyleClass().removeAll("focused");
					});

					if (SettingsModel.isFlipBoard() && GameModel.getSelectedIndex() != Coordinate.toIndex(square.getId())) {
						flipBoard();
					}
					settingsButton.setDisable(false);
					GameModel.setSelectedIndex(-1);
				}

			} else { // if a piece has not yet been selected (first press)

				if (!square.getChildren().isEmpty()) {

					GameModel.setSelectedIndex(Coordinate.toIndex(square.getId()));

					square.getStyleClass().add("focused");
					settingsButton.setDisable(true);
				}

			}

		}

	}

	public void showPromotionPopup(ChessColor forColor) {
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
			if (event.getSource() == promotionPopupQueenButton)
				GameModel.setPieceForPromotion(Piece.Queen + (forColor == ChessColor.White ? Piece.White : Piece.Black));
			if (event.getSource() == promotionPopupRookButton)
				GameModel.setPieceForPromotion(Piece.Rook + (forColor == ChessColor.White ? Piece.White : Piece.Black));
			if (event.getSource() == promotionPopupBishopButton)
				GameModel.setPieceForPromotion(Piece.Bishop + (forColor == ChessColor.White ? Piece.White : Piece.Black));
			if (event.getSource() == promotionPopupKnightButton)
				GameModel.setPieceForPromotion(Piece.Knight + (forColor == ChessColor.White ? Piece.White : Piece.Black));

			promotionPopup.setVisible(false);
			boardGrid.setDisable(false);
			historyPane.setDisable(false);
			resignButton.setDisable(false);
			restartButton.setDisable(false);
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
			settingsButton.setDisable(false);
			menuButton.setDisable(false);
		};

		surePopupCancelButton.setOnAction(buttonActionHandler);
		surePopupYesButton.setOnAction(buttonActionHandler);

	}

	private void flipBoard() {
		String lines = "12345678";
		String columns = "hgfedcba";
		if (isRotated) { // rotate back
			lines = "87654321";
			columns = "abcdefgh";
		}

		for (int i = 0; i < 8; i++) {
			((Label) lineNumbersPane.getChildren().get(i)).setText("" + lines.charAt(i));
		}
		for (int i = 0; i < 8; i++) {
			((Label) columnLettersPane.getChildren().get(i)).setText("" + columns.charAt(i));
		}

		boardGrid.getTransforms().add(new Rotate(180, boardGrid.getWidth() / 2, boardGrid.getHeight() / 2));
		for (Node squareNode : boardGrid.getChildren()) {
			squareNode.getTransforms()
					.add(new Rotate(180, squareNode.getBoundsInLocal().getCenterX(), squareNode.getBoundsInLocal().getCenterY()));
		}
		isRotated = !isRotated;
		resize();
	}

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

		if (isRotated) {
			boardGrid.getChildren().get(0).setStyle("-fx-background-radius: 0 0 " + borderRadius
					+ " 0; -fx-border-radius: 0 0 " + borderRadius + " 0; -fx-border-width: " + historyBorderWidth * 2);
			boardGrid.getChildren().get(7).setStyle("-fx-background-radius: " + borderRadius + " 0 0 0; -fx-border-radius: 0 "
					+ borderRadius + " 0 0; -fx-border-width: " + historyBorderWidth * 2);
			boardGrid.getChildren().get(63).setStyle("-fx-background-radius: 0 0 " + borderRadius
					+ " 0; -fx-border-radius: 0 0 " + borderRadius + " 0; -fx-border-width: " + historyBorderWidth * 2);
			boardGrid.getChildren().get(56).setStyle("-fx-background-radius: 0 " + borderRadius
					+ " 0 0; -fx-border-radius: 0 " + borderRadius + " 0 0; -fx-border-width: " + historyBorderWidth * 2);
		} else {
			boardGrid.getChildren().get(0).setStyle("-fx-background-radius: " + borderRadius + " 0 0 0; -fx-border-radius: "
					+ borderRadius + " 0 0 0; -fx-border-width: " + historyBorderWidth * 2);
			boardGrid.getChildren().get(7).setStyle("-fx-background-radius: 0 " + borderRadius + " 0 0; -fx-border-radius: 0 "
					+ borderRadius + " 0 0; -fx-border-width: " + historyBorderWidth * 2);
			boardGrid.getChildren().get(63).setStyle("-fx-background-radius: 0 0 " + borderRadius
					+ " 0; -fx-border-radius: 0 0 " + borderRadius + " 0; -fx-border-width: " + historyBorderWidth * 2);
			boardGrid.getChildren().get(56).setStyle("-fx-background-radius: 0 0 0 " + borderRadius
					+ "; -fx-border-radius: 0 0 0 " + borderRadius + "; -fx-border-width: " + historyBorderWidth * 2);
		}

		for (Node squareNode : boardGrid.getChildren()) {
			AnchorPane square = (AnchorPane) squareNode;
			if (!square.getId().equals("a8") && !square.getId().equals("h8") && !square.getId().equals("a1")
					&& !square.getId().equals("h1")) {
				square.setStyle("-fx-border-width: " + historyBorderWidth * 2);
			}
			ImageView figure = !square.getChildren().isEmpty() ? (ImageView) square.getChildren().get(0) : null;
			if (figure != null) {
				figure.setFitHeight(Math.min(rootHeight / 14.4, rootWidth / 25.6));
				figure.setY((Math.min(rootHeight / 8.72, rootWidth / 15.51) - figure.getFitHeight()) / 2);
				figure.setX(
						(Math.min(rootHeight / 8.72, rootWidth / 15.51) - figure.boundsInParentProperty().get().getWidth()) / 2);
			}
		}

	}

	private void setButtonStyle(Button button, double[] rootSize, double graphicTextGap) {
		double fontSize = Math.min(rootSize[1] / 28.8, rootSize[0] / 51.2);
		double borderRadius = Math.min(rootSize[1] / 72, rootSize[0] / 128 * 1.2);
		double borderWidth = Math.min(rootSize[1] / 240, rootSize[0] / 426.66);
		button.setStyle(
				"-fx-font-size: " + fontSize + "; -fx-graphic-text-gap: " + graphicTextGap + "; -fx-background-radius: "
						+ borderRadius + "; -fx-border-radius: " + borderRadius + "; -fx-border-width: " + borderWidth);
	}
}
