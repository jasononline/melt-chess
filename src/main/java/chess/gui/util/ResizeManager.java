package chess.gui.util;

import java.util.function.Predicate;

import chess.gui.game.GameController;
import chess.gui.menu.MenuController;
import chess.gui.network.NetworkController;
import chess.gui.settings.SettingsController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

/**
 * Resizes GUI elements, depending on the current view.
 */
public class ResizeManager {

	private MenuController menuController;
	private SettingsController settingsController;
	private NetworkController networkController;
	private GameController gameController;

	private double rootWidth;
	private double rootHeight;
	private double fontSize60;
	private double fontSize40;
	private double fontSize25;
	private double fontSize20;
	private double fontSize18;
	private double fontSize15;
	private double fontSize12;
	private double borderRadius10;
	private double borderRadius5;
	private double borderWidth3;
	private double borderWidth2;
	private double iconHeight35;
	private double graphicTextGapH30;
	private double graphicTextGapV20;
	private double graphicTextGapV5;

	/**
	 * Create new ResizeManager instance of
	 * 
	 * @param menuController the current menu controller
	 */
	public ResizeManager(MenuController menuController) {
		this.menuController = menuController;
	}

	/**
	 * Create new ResizeManager instance of
	 * 
	 * @param settingsController the current settings controller
	 */
	public ResizeManager(SettingsController settingsController) {
		this.settingsController = settingsController;
	}

	/**
	 * Create new ResizeManager instance of
	 * 
	 * @param networkController the current network controller
	 */
	public ResizeManager(NetworkController networkController) {
		this.networkController = networkController;
	}

	/**
	 * Create new ResizeManager instance of
	 * 
	 * @param gameController the current game controller
	 */
	public ResizeManager(GameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * Updates style values
	 * 
	 * @param rootWidth  current width of the root element
	 * @param rootHeight current height of the root element
	 */
	private void updateValues(double rootWidth, double rootHeight) {
		this.rootWidth = rootWidth;
		this.rootHeight = rootHeight;

		this.fontSize60 = Math.min(rootHeight / 12, rootWidth / 21.33);
		this.fontSize40 = this.fontSize60 / 1.5;
		this.fontSize25 = Math.min(rootHeight / 28.8, rootWidth / 51.2);
		this.fontSize20 = this.fontSize40 / 2;
		this.fontSize18 = Math.min(rootHeight / 40, rootWidth / 71.11);
		this.fontSize15 = Math.min(rootHeight / 48, rootWidth / 85.33);
		this.fontSize12 = Math.min(rootHeight / 60, rootWidth / 106.66);
		this.borderRadius10 = Math.min(rootHeight / 72, rootWidth / 128);
		this.borderRadius5 = this.borderRadius10 / 2;
		this.borderWidth3 = Math.min(rootHeight / 240, rootWidth / 426.66);
		this.borderWidth2 = this.borderWidth3 / 1.5;
		this.iconHeight35 = Math.min(rootHeight / 20.57, rootWidth / 36.57);
		this.graphicTextGapH30 = rootWidth / 42.66;
		this.graphicTextGapV20 = rootHeight / 36;
		this.graphicTextGapV5 = this.graphicTextGapV20 / 4;
	}

	/**
	 * Resizes the menu view
	 * 
	 * @param rootWidth  the current width of the root element
	 * @param rootHeight the current height of the root element
	 */
	public void resizeMenu(double rootWidth, double rootHeight) {
		updateValues(rootWidth, rootHeight);

		String buttonStyleV = "-fx-font-size: " + fontSize25 + "; -fx-graphic-text-gap: " + graphicTextGapV20
				+ "; -fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10
				+ "; -fx-border-width: " + borderWidth3;
		String buttonStyleH = "-fx-font-size: " + fontSize25 + "; -fx-graphic-text-gap: " + graphicTextGapH30
				+ "; -fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10
				+ "; -fx-border-width: " + borderWidth3;

		menuController.titleLabel.setStyle("-fx-font-size: " + fontSize60);
		menuController.modeLabel.setStyle("-fx-font-size: " + fontSize40);
		menuController.colorLabel.setStyle("-fx-font-size: " + fontSize40);

		menuController.playerModeButton.setStyle(buttonStyleV);
		menuController.aiModeButton.setStyle(buttonStyleV);
		menuController.networkModeButton.setStyle(buttonStyleV);
		menuController.whiteColorButton.setStyle(buttonStyleV);
		menuController.blackColorButton.setStyle(buttonStyleV);
		menuController.startButton.setStyle(buttonStyleH);
		menuController.settingsButton.setStyle(buttonStyleH);
		menuController.quitButton.setStyle(buttonStyleH);

		ImageView[] icons = { (ImageView) menuController.playerModeButton.getGraphic(),
				(ImageView) menuController.aiModeButton.getGraphic(), (ImageView) menuController.networkModeButton.getGraphic(),
				(ImageView) menuController.whiteColorButton.getGraphic(),
				(ImageView) menuController.blackColorButton.getGraphic(), (ImageView) menuController.startButton.getGraphic(),
				(ImageView) menuController.settingsButton.getGraphic(), (ImageView) menuController.quitButton.getGraphic() };

		for (ImageView icon : icons) {
			icon.setFitHeight(iconHeight35);
		}

		menuController.playerModeButton.getParent().getParent().getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10);
		menuController.whiteColorButton.getParent().getParent().getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10);
		menuController.startButton.getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10);
	}

	/**
	 * Resizes the settings view
	 * 
	 * @param rootWidth  the current width of the root element
	 * @param rootHeight the current height of the root element
	 */
	public void resizeSettings(double rootWidth, double rootHeight) {
		updateValues(rootWidth, rootHeight);

		settingsController.titleLabel.setStyle("-fx-font-size: " + fontSize60);
		settingsController.languageLabel.setStyle("-fx-font-size: " + fontSize25);
		settingsController.flipBoardLabel.setStyle("-fx-font-size: " + fontSize25);
		settingsController.oneTouchRuleLabel.setStyle("-fx-font-size: " + fontSize25);
		settingsController.showInCheckLabel.setStyle("-fx-font-size: " + fontSize25);
		settingsController.showPossibleMovesLabel.setStyle("-fx-font-size: " + fontSize25);
		settingsController.soundEffectsLabel.setStyle("-fx-font-size: " + fontSize25);

		String langButtonStyle = "-fx-font-size: " + fontSize18 + "; -fx-background-radius: " + borderRadius5
				+ "; -fx-border-radius: " + borderRadius5 + "; -fx-border-width: " + borderWidth2;
		String controlButtonStyle = "-fx-font-size: " + fontSize25 + "; -fx-graphic-text-gap: " + graphicTextGapH30
				+ "; -fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10
				+ "; -fx-border-width: " + borderWidth3;

		settingsController.englishButton.setStyle(langButtonStyle);
		settingsController.germanButton.setStyle(langButtonStyle);
		settingsController.cancelButton.setStyle(controlButtonStyle);
		settingsController.saveButton.setStyle(controlButtonStyle);

		settingsController.saveButton.getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10);
		settingsController.englishButton.getParent().getParent().getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10);

		ImageView[] icons = { (ImageView) settingsController.cancelButton.getGraphic(),
				(ImageView) settingsController.saveButton.getGraphic() };
		for (ImageView icon : icons) {
			icon.setFitHeight(iconHeight35);
		}

		setCheckboxStyle(settingsController.flipBoardCheckbox);
		setCheckboxStyle(settingsController.oneTouchRuleCheckbox);
		setCheckboxStyle(settingsController.showInCheckCheckbox);
		setCheckboxStyle(settingsController.showPossibleMovesCheckbox);
		setCheckboxStyle(settingsController.soundEffectsCheckbox);
	}

	/**
	 * Styles checkboxes in the settings view
	 * 
	 * @param checkbox checkbox to be styled
	 */
	private void setCheckboxStyle(CheckBox checkbox) {
		double width = checkbox.getHeight() != 0 ? checkbox.getHeight() * 2 : rootHeight / 20.57 * 2;
		checkbox.setPrefWidth(width);
		checkbox.setMaxWidth(width);
		checkbox.setStyle(
				"-fx-padding: 0 0 0 " + (width / 3.04) + "; -fx-border-width: " + borderWidth2 + "; -fx-background-radius: "
						+ borderRadius5 + "; -fx-border-radius: " + borderRadius5 + "; -fx-font-size: " + fontSize15);
	}

	/**
	 * Resizes the network view
	 * 
	 * @param rootWidth  the current width of the root element
	 * @param rootHeight the current height of the root element
	 */
	public void resizeNetwork(double rootWidth, double rootHeight) {
		updateValues(rootWidth, rootHeight);

		networkController.titleLabel.setStyle("-fx-font-size: " + fontSize60);
		networkController.ipLabel.setStyle("-fx-font-size: " + fontSize25);
		networkController.portLabel.setStyle("-fx-font-size: " + fontSize25);
		networkController.errorLabel.setStyle("-fx-font-size: " + fontSize20);

		String textFieldStyle = "-fx-font-size: " + fontSize25 + "; -fx-background-radius: " + borderRadius10
				+ "; -fx-border-radius: " + borderRadius10 + "; -fx-border-width: " + borderWidth2;
		String controlButtonStyle = "-fx-font-size: " + fontSize25 + "; -fx-graphic-text-gap: " + graphicTextGapH30
				+ "; -fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10
				+ "; -fx-border-width: " + borderWidth3;

		networkController.ipTextField.setStyle(textFieldStyle);
		networkController.portTextField.setStyle(textFieldStyle);

		networkController.cancelButton.setStyle(controlButtonStyle);
		networkController.connectButton.setStyle(controlButtonStyle);

		ImageView[] icons = { (ImageView) networkController.cancelButton.getGraphic(),
				(ImageView) networkController.connectButton.getGraphic() };
		for (ImageView icon : icons) {
			icon.setFitHeight(iconHeight35);
		}

		networkController.connectButton.getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10);
		networkController.ipTextField.getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10);
	}

	/**
	 * Resizes the game view
	 * 
	 * @param rootWidth  the current width of the root element
	 * @param rootHeight the current height of the root element
	 */
	public void resizeGame(double rootWidth, double rootHeight) {
		updateValues(rootWidth, rootHeight);

		String buttonStyleH = "-fx-font-size: " + fontSize25 + "; -fx-graphic-text-gap: " + graphicTextGapH30 / 3
				+ "; -fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10
				+ "; -fx-border-width: " + borderWidth3;

		gameController.resignButton.setStyle(buttonStyleH);
		gameController.restartButton.setStyle(buttonStyleH);
		gameController.settingsButton.setStyle(buttonStyleH);
		gameController.menuButton.setStyle(buttonStyleH);

		gameController.currentMoveLabel.setStyle("-fx-font-size: " + fontSize25);
		gameController.checkLabel.setStyle("-fx-font-size: " + fontSize20);
		gameController.timeLabel.setStyle("-fx-font-size: " + fontSize40);
		gameController.historyLabel.setStyle("-fx-font-size: " + fontSize20);
		gameController.promotionPopupLabel.setStyle("-fx-font-size: " + fontSize25);
		gameController.surePopupLabel.setStyle("-fx-font-size: " + fontSize20);
		gameController.gameOverPopupTitle.setStyle("-fx-font-size: " + fontSize25);
		gameController.gameOverPopupLabel.setStyle("-fx-font-size: " + fontSize20);

		gameController.historyLabel.getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10);
		gameController.timeLabel.getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius10 + "; -fx-border-radius: " + borderRadius10);

		gameController.historyGrid.setVgap(graphicTextGapV20 / 2);

		for (Node historyNode : gameController.historyGrid.getChildren()) {
			Button historyButton = (Button) ((AnchorPane) historyNode).getChildren().get(0);
			historyButton.setStyle("-fx-font-size: " + fontSize18 + "; -fx-background-radius: " + borderRadius5
					+ "; -fx-border-radius: " + borderRadius5 + "; -fx-border-width: " + borderWidth2);
		}

		String popupButtonStyleV = "-fx-font-size: " + fontSize15 + "; -fx-graphic-text-gap: " + graphicTextGapV5
				+ "; -fx-background-radius: " + borderRadius5 + "; -fx-border-radius: " + borderRadius5 + "; -fx-border-width: "
				+ borderWidth2;
		String popupButtonStyleH = "-fx-font-size: " + fontSize18 + "; -fx-graphic-text-gap: " + graphicTextGapV20
				+ "; -fx-background-radius: " + borderRadius5 + "; -fx-border-radius: " + borderRadius5 + "; -fx-border-width: "
				+ borderWidth2;

		gameController.promotionPopupQueenButton.setStyle(popupButtonStyleV);
		gameController.promotionPopupRookButton.setStyle(popupButtonStyleV);
		gameController.promotionPopupBishopButton.setStyle(popupButtonStyleV);
		gameController.promotionPopupKnightButton.setStyle(popupButtonStyleV);

		gameController.surePopupCancelButton.setStyle(popupButtonStyleH);
		gameController.surePopupYesButton.setStyle(popupButtonStyleH);

		gameController.gameOverPopupMenuButton.setStyle(popupButtonStyleV);
		gameController.gameOverPopupRestartButton.setStyle(popupButtonStyleV);
		gameController.gameOverPopupStayButton.setStyle(popupButtonStyleV);

		ImageView[] promotionIcons = { (ImageView) gameController.promotionPopupQueenButton.getGraphic(),
				(ImageView) gameController.promotionPopupRookButton.getGraphic(),
				(ImageView) gameController.promotionPopupBishopButton.getGraphic(),
				(ImageView) gameController.promotionPopupKnightButton.getGraphic(),
				(ImageView) gameController.gameOverPopupMenuButton.getGraphic(),
				(ImageView) gameController.gameOverPopupRestartButton.getGraphic(),
				(ImageView) gameController.gameOverPopupStayButton.getGraphic() };
		for (ImageView icon : promotionIcons) {
			icon.setFitHeight(iconHeight35 / 1.75);
		}

		ImageView[] icons = { (ImageView) gameController.resignButton.getGraphic(),
				(ImageView) gameController.restartButton.getGraphic(), (ImageView) gameController.settingsButton.getGraphic(),
				(ImageView) gameController.menuButton.getGraphic(),
				(ImageView) gameController.surePopupCancelButton.getGraphic(),
				(ImageView) gameController.surePopupYesButton.getGraphic() };
		for (ImageView icon : icons) {
			icon.setFitHeight(iconHeight35 / 1.4);
		}

		for (Node number : gameController.lineNumbersPane.getChildren()) {
			number.setStyle("-fx-font-size: " + fontSize12);
		}
		for (Node letter : gameController.columnLettersPane.getChildren()) {
			letter.setStyle("-fx-font-size: " + fontSize12);
		}

		Double clipSize = Math.min(rootWidth / 1.94, rootHeight / 1.09);
		Rectangle clip = new Rectangle(clipSize, clipSize);
		clip.setArcWidth(borderRadius10 * 2);
		clip.setArcHeight(borderRadius10 * 2);
		gameController.boardGrid.setClip(clip);

		for (Node squareNode : gameController.boardGrid.getChildren()) {
			AnchorPane square = (AnchorPane) squareNode;
			square.setStyle("-fx-border-width: " + borderWidth2 * 1.25);

			Predicate<Node> pieces = image -> image.getId().equals("piece");

			for (Node node : square.getChildren().filtered(pieces)) {
				ImageView piece = (ImageView) node;
				centerPiecePosition(piece);
			}
		}
	}

	/**
	 * Sets piece position in the center of the square
	 * 
	 * @param pieceView piece image view
	 */
	public void centerPiecePosition(ImageView pieceView) {
		pieceView.setFitHeight(Math.min(rootHeight / 14.4, rootWidth / 25.6));
		pieceView.setY((Math.min(rootHeight / 8.72, rootWidth / 15.51) - pieceView.getFitHeight()) / 2);
		pieceView.setX(
				(Math.min(rootHeight / 8.72, rootWidth / 15.51) - pieceView.boundsInParentProperty().get().getWidth()) / 2);
	}
}
