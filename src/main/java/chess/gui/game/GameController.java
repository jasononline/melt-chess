package chess.gui.game;

import java.util.ListIterator;
import java.util.Stack;

import chess.gui.Gui;
import chess.gui.settings.SettingsModel;
import chess.gui.util.TextManager;
import chess.model.Coordinate;
import chess.model.Move;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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
	private GridPane boardGrid;
	@FXML
	private Label timeLabel;
	@FXML
	private Button resignButton;
	@FXML
	private Button restartButton;
	@FXML
	private Button settingsButton;
	@FXML
	private Button menuButton;

	private Point2D startPoint;
	private Point2D dragDistance;
	private boolean isDragStarted = false;

	@FXML
	private void initialize() {
		TextManager.computeText(settingsButton, "game.settings");
		TextManager.computeText(resignButton, "game.resign");
		TextManager.computeText(restartButton, "game.restart");
		TextManager.computeText(menuButton, "game.menu");

		ChangeListener<Number> rootPaneSizeListener = (observable, oldValue, newValue) -> {
			resize();
		};
		rootPane.widthProperty().addListener(rootPaneSizeListener);
		rootPane.heightProperty().addListener(rootPaneSizeListener);

		boardGrid.prefHeightProperty()
				.bind(Bindings.min(rootPane.widthProperty().divide(1.94), rootPane.heightProperty().divide(1.09)));
		boardGrid.prefWidthProperty()
				.bind(Bindings.min(rootPane.widthProperty().divide(1.94), rootPane.heightProperty().divide(1.09)));

		updateMovesHistoryUI();
	}

	@FXML
	private void handleButtonOnAction(ActionEvent event) {
		Button button = (Button) event.getSource();

		if (button == resignButton) {
			System.out.println("Resign");
			// TODO: End current game (leave game scene open)
		}

		if (button == restartButton) {
			System.out.println("Restart");
			// TODO: End current game
			// TODO: Start new game
		}

		if (button == settingsButton) {
			SettingsModel.setLastScene(Gui.ChessScene.Game);
			Gui.switchTo(Gui.ChessScene.Settings);
		}

		if (button == menuButton) {
			// TODO: End current game

			GameModel.reset();
			Gui.switchTo(Gui.ChessScene.Menu);
		}
	}

	private void updateMovesHistoryUI() {

		historyGrid.getChildren().clear();

		ListIterator<Move> movesHistoryIterator = GameModel.getMovesHistory()
				.listIterator(GameModel.getMovesHistory().size());
		while (movesHistoryIterator.hasPrevious()) {
			Move move = movesHistoryIterator.previous();

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
	private void handleSquareMouseClick(MouseEvent event) {

		Node source = (Node) event.getSource();

		// TODO: Implement "click&click" play

		if (source instanceof AnchorPane) {
			AnchorPane square = (AnchorPane) source;

			if (GameModel.isSelected()) { // if it is first player's click

				System.out.println("Target square: " + square.getId());
				GameModel.setSelectedIndex(-1);

			} else { // if player already choosed the figure

				if (!square.getChildren().isEmpty()) {
					GameModel.setSelectedIndex(Coordinate.toIndex(square.getId()));
					System.out.println("Start square: " + square.getId());
				}

			}

		}

	}

	@FXML
	private void handleSquareMouseDrag(MouseEvent event) {
		Node source = (Node) event.getSource();
		Node target = event.getPickResult().getIntersectedNode();

		if (source instanceof AnchorPane) {
			AnchorPane square = (AnchorPane) source;
			if (!square.getChildren().isEmpty()) {
				ImageView figure = (ImageView) square.getChildren().get(0);

				if (!isDragStarted) {

					startPoint = new Point2D(event.getSceneX(), event.getSceneY());
					dragDistance = startPoint.subtract(square.localToScene(new Point2D(figure.getX(), figure.getY())));

					isDragStarted = true;
				}

				Point2D px = new Point2D(event.getSceneX(), event.getSceneY());
				px = square.sceneToLocal(px.subtract(dragDistance));

				figure.toFront();
				figure.setTranslateX(px.getX());
				figure.setTranslateY(px.getY());

				boardGrid.getChildren().forEach(s -> {
					if (s != square)
						s.getStyleClass().removeAll("focused");
				});

				if (target instanceof AnchorPane) {
					((AnchorPane) target).getStyleClass().add("focused");
				}

				if (target instanceof ImageView) {
					((ImageView) target).getParent().getStyleClass().add("focused");
				}

			}
		}
	}

	@FXML
	private void handleSquareMouseRelease(MouseEvent event) {
		Node source = (Node) event.getSource();
		Node target = event.getPickResult().getIntersectedNode();

		if (source instanceof AnchorPane) {
			AnchorPane startSquare = (AnchorPane) source;
			AnchorPane targetSquare;
			if (!startSquare.getChildren().isEmpty()) {
				ImageView figure = (ImageView) startSquare.getChildren().get(0);

				if (target instanceof AnchorPane) {
					targetSquare = (AnchorPane) target;

					if (targetSquare != startSquare) {
						targetSquare.getChildren().clear();
						targetSquare.getChildren().add(figure);
						GameModel.addMoveToHistory(
								new Move(Coordinate.toIndex(startSquare.getId()), Coordinate.toIndex(targetSquare.getId())));
						updateMovesHistoryUI();
					}
				}

				if (target instanceof ImageView) {
					ImageView existingFigure = (ImageView) target;
					targetSquare = (AnchorPane) ((ImageView) target).getParent();
					if (targetSquare != startSquare) {
						targetSquare.getChildren().remove(existingFigure);
						targetSquare.getChildren().add(figure);
						GameModel.addMoveToHistory(
								new Move(Coordinate.toIndex(startSquare.getId()), Coordinate.toIndex(targetSquare.getId())));
						updateMovesHistoryUI();
					}
				}

				boardGrid.getChildren().forEach(s -> {
					s.getStyleClass().removeAll("focused");
				});
				figure.setTranslateX(0);
				figure.setTranslateY(0);
			}
		}
		isDragStarted = false;
	}

	private void resize() {
		double rootHeight = rootPane.getHeight();
		double rootWidth = rootPane.getWidth();
		double fontSize = Math.min(rootHeight / 28.8, rootWidth / 51.2);
		double borderRadius = Math.min(rootHeight / 72, rootWidth / 128 * 1.2);
		double historyBorderWidth = Math.min(rootHeight / 360, rootWidth / 640);
		double timeFontSize = Math.min(rootHeight / 18, rootWidth / 32 * 1.2);
		double historyFontsize = Math.min(rootHeight / 36, rootWidth / 64);
		double historyButtonFontSize = Math.min(rootHeight / 40, rootWidth / 71.11 * 1.2);

		setButtonStyle(resignButton, new double[] { rootWidth, rootHeight }, rootWidth / 128);
		setButtonStyle(restartButton, new double[] { rootWidth, rootHeight }, rootWidth / 128);
		setButtonStyle(settingsButton, new double[] { rootWidth, rootHeight }, rootWidth / 128);
		setButtonStyle(menuButton, new double[] { rootWidth, rootHeight }, rootWidth / 128);

		timeLabel.setStyle("-fx-font-size: " + timeFontSize);
		historyLabel.setStyle("-fx-font-size: " + historyFontsize);

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

		ImageView[] icons = { (ImageView) resignButton.getGraphic(), (ImageView) restartButton.getGraphic(),
				(ImageView) settingsButton.getGraphic(), (ImageView) menuButton.getGraphic() };
		for (ImageView icon : icons) {
			icon.setFitHeight(fontSize);
		}

		boardGrid.getChildren().get(0)
				.setStyle("-fx-background-radius: " + borderRadius + " 0 0 0; -fx-border-radius: " + borderRadius + " 0 0 0;");
		boardGrid.getChildren().get(7)
				.setStyle("-fx-background-radius: 0 " + borderRadius + " 0 0; -fx-border-radius: 0 " + borderRadius + " 0 0;");
		boardGrid.getChildren().get(63)
				.setStyle("-fx-background-radius: 0 0 " + borderRadius + " 0; -fx-border-radius: 0 0 " + borderRadius + " 0;");
		boardGrid.getChildren().get(56)
				.setStyle("-fx-background-radius: 0 0 0 " + borderRadius + "; -fx-border-radius: 0 0 0 " + borderRadius + ";");

		for (Node squareNode : boardGrid.getChildren()) {
			AnchorPane square = (AnchorPane) squareNode;
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
