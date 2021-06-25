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
@SuppressWarnings("PMD.TooManyFields")
// since resizing means to modify many elements, many fields are needed.
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

	private static String fxFontSize = "-fx-font-size: ";
	private static String fxBorderRadius = "; -fx-border-radius: ";
	private static String fxBackgroundRadius = "; -fx-background-radius: ";
	private static String fxBackgroundRadiusNoSemicolon = "-fx-background-radius: ";
	private static String fxGraphicTextGap = "; -fx-graphic-text-gap: ";
	private static String fxBorderWidth = "; -fx-border-width: ";

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

		String buttonStyleV = fxFontSize + fontSize25 + fxGraphicTextGap + graphicTextGapV20 + fxBackgroundRadius
				+ borderRadius10 + fxBorderRadius + borderRadius10 + fxBorderWidth + borderWidth3;
		String buttonStyleH = fxFontSize + fontSize25 + fxGraphicTextGap + graphicTextGapH30 + fxBackgroundRadius
				+ borderRadius10 + fxBorderRadius + borderRadius10 + fxBorderWidth + borderWidth3;

		menuController.titleLabel.setStyle(fxFontSize + fontSize60);
		menuController.modeLabel.setStyle(fxFontSize + fontSize40);
		menuController.colorLabel.setStyle(fxFontSize + fontSize40);

		menuController.playerModeButton.setStyle(buttonStyleV);
		menuController.aiModeButton.setStyle(buttonStyleV);
		menuController.networkModeButton.setStyle(buttonStyleV);
		menuController.whiteColorButton.setStyle(buttonStyleV);
		menuController.blackColorButton.setStyle(buttonStyleV);
		menuController.startButton.setStyle(buttonStyleH);
		menuController.loadButton.setStyle(buttonStyleH);
		menuController.settingsButton.setStyle(buttonStyleH);
		menuController.quitButton.setStyle(buttonStyleH);

		ImageView[] icons = { (ImageView) menuController.playerModeButton.getGraphic(),
				(ImageView) menuController.aiModeButton.getGraphic(), (ImageView) menuController.networkModeButton.getGraphic(),
				(ImageView) menuController.whiteColorButton.getGraphic(),
				(ImageView) menuController.blackColorButton.getGraphic(), (ImageView) menuController.startButton.getGraphic(),
				(ImageView) menuController.loadButton.getGraphic(), (ImageView) menuController.settingsButton.getGraphic(),
				(ImageView) menuController.quitButton.getGraphic() };

		for (ImageView icon : icons) {
			icon.setFitHeight(iconHeight35);
		}

		menuController.playerModeButton.getParent().getParent().getParent().getParent().getParent()
				.setStyle(fxBackgroundRadiusNoSemicolon + borderRadius10 + fxBorderRadius + borderRadius10);
		menuController.whiteColorButton.getParent().getParent().getParent().getParent().getParent()
				.setStyle(fxBackgroundRadiusNoSemicolon + borderRadius10 + fxBorderRadius + borderRadius10);
		menuController.startButton.getParent().getParent().getParent()
				.setStyle(fxBackgroundRadiusNoSemicolon + borderRadius10 + fxBorderRadius + borderRadius10);
	}

	/**
	 * Resizes the settings view
	 * 
	 * @param rootWidth  the current width of the root element
	 * @param rootHeight the current height of the root element
	 */
	public void resizeSettings(double rootWidth, double rootHeight) {
		updateValues(rootWidth, rootHeight);

		settingsController.titleLabel.setStyle(fxFontSize + fontSize60);
		settingsController.languageLabel.setStyle(fxFontSize + fontSize25);
		settingsController.flipBoardLabel.setStyle(fxFontSize + fontSize25);
		settingsController.oneTouchRuleLabel.setStyle(fxFontSize + fontSize25);
		settingsController.showInCheckLabel.setStyle(fxFontSize + fontSize25);
		settingsController.showPossibleMovesLabel.setStyle(fxFontSize + fontSize25);
		settingsController.soundEffectsLabel.setStyle(fxFontSize + fontSize25);

		String langButtonStyle = fxFontSize + fontSize18 + fxBackgroundRadius + borderRadius5 + fxBorderRadius
				+ borderRadius5 + fxBorderWidth + borderWidth2;
		String controlButtonStyle = fxFontSize + fontSize25 + fxGraphicTextGap + graphicTextGapH30 + fxBackgroundRadius
				+ borderRadius10 + fxBorderRadius + borderRadius10 + fxBorderWidth + borderWidth3;

		settingsController.englishButton.setStyle(langButtonStyle);
		settingsController.germanButton.setStyle(langButtonStyle);
		settingsController.cancelButton.setStyle(controlButtonStyle);
		settingsController.saveButton.setStyle(controlButtonStyle);

		settingsController.saveButton.getParent().getParent().getParent()
				.setStyle(fxBackgroundRadiusNoSemicolon + borderRadius10 + fxBorderRadius + borderRadius10);
		settingsController.englishButton.getParent().getParent().getParent().getParent().getParent()
				.setStyle(fxBackgroundRadiusNoSemicolon + borderRadius10 + fxBorderRadius + borderRadius10);

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
		checkbox.setStyle("-fx-padding: 0 0 0 " + (width / 3.04) + fxBorderWidth + borderWidth2 + fxBackgroundRadius
				+ borderRadius5 + fxBorderRadius + borderRadius5 + "; -fx-font-size: " + fontSize15);
	}

	/**
	 * Resizes the network view
	 * 
	 * @param rootWidth  the current width of the root element
	 * @param rootHeight the current height of the root element
	 */
	public void resizeNetwork(double rootWidth, double rootHeight) {
		updateValues(rootWidth, rootHeight);

		networkController.titleLabel.setStyle(fxFontSize + fontSize60);
		networkController.ipLabel.setStyle(fxFontSize + fontSize25);
		networkController.portLabel.setStyle(fxFontSize + fontSize25);
		networkController.errorLabel.setStyle(fxFontSize + fontSize20);

		String textFieldStyle = fxFontSize + fontSize25 + fxBackgroundRadius + borderRadius10 + fxBorderRadius
				+ borderRadius10 + fxBorderWidth + borderWidth2;
		String controlButtonStyle = fxFontSize + fontSize25 + fxGraphicTextGap + graphicTextGapH30 + fxBackgroundRadius
				+ borderRadius10 + fxBorderRadius + borderRadius10 + fxBorderWidth + borderWidth3;

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
				.setStyle(fxBackgroundRadiusNoSemicolon + borderRadius10 + fxBorderRadius + borderRadius10);
		networkController.ipTextField.getParent().getParent().getParent()
				.setStyle(fxBackgroundRadiusNoSemicolon + borderRadius10 + fxBorderRadius + borderRadius10);
	}

	/**
	 * Resizes the game view
	 * 
	 * @param rootWidth  the current width of the root element
	 * @param rootHeight the current height of the root element
	 */
	public void resizeGame(double rootWidth, double rootHeight) {
		updateValues(rootWidth, rootHeight);

		String buttonStyleH = fxFontSize + fontSize25 + fxGraphicTextGap + graphicTextGapH30 / 3 + fxBackgroundRadius
				+ borderRadius10 + fxBorderRadius + borderRadius10 + fxBorderWidth + borderWidth3;

		gameController.resignButton.setStyle(buttonStyleH);
		gameController.restartButton.setStyle(buttonStyleH);
		gameController.settingsButton.setStyle(buttonStyleH);
		gameController.menuButton.setStyle(buttonStyleH);

		setLabelStyles();
		setHistoryStyles();
		setPopupStyles();

		ImageView[] promotionIcons = getPromotionIcons();
		for (ImageView icon : promotionIcons) {
			icon.setFitHeight(iconHeight35 / 1.75);
		}

		ImageView[] icons = getButtonIcons();
		for (ImageView icon : icons) {
			icon.setFitHeight(iconHeight35 / 1.4);
		}

		for (Node number : gameController.lineNumbersPane.getChildren()) {
			number.setStyle(fxFontSize + fontSize12);
		}
		for (Node letter : gameController.columnLettersPane.getChildren()) {
			letter.setStyle(fxFontSize + fontSize12);
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

	private void setHistoryStyles() {
		gameController.historyGrid.setVgap(graphicTextGapV20 / 2);
		String historyButtonStyle = fxFontSize + fontSize18 + fxGraphicTextGap + graphicTextGapH30 / 3 + fxBackgroundRadius
				+ borderRadius5 + fxBorderRadius + borderRadius5 + fxBorderWidth + borderWidth2;
		gameController.saveButton.setStyle(historyButtonStyle);
		for (Node historyNode : gameController.historyGrid.getChildren()) {
			Button historyButton = (Button) ((AnchorPane) historyNode).getChildren().get(0);
			historyButton.setStyle(historyButtonStyle);
		}
	}

	private void setPopupStyles() {
		gameController.gameOverPopupTitle.setStyle(fxFontSize + fontSize25);

		String popupButtonStyleV = fxFontSize + fontSize15 + fxGraphicTextGap + graphicTextGapV5 + fxBackgroundRadius
				+ borderRadius5 + fxBorderRadius + borderRadius5 + fxBorderWidth + borderWidth2;
		String popupButtonStyleH = fxFontSize + fontSize18 + fxGraphicTextGap + graphicTextGapV20 + fxBackgroundRadius
				+ borderRadius5 + fxBorderRadius + borderRadius5 + fxBorderWidth + borderWidth2;

		gameController.promotionPopupQueenButton.setStyle(popupButtonStyleV);
		gameController.promotionPopupRookButton.setStyle(popupButtonStyleV);
		gameController.promotionPopupBishopButton.setStyle(popupButtonStyleV);
		gameController.promotionPopupKnightButton.setStyle(popupButtonStyleV);

		gameController.surePopupCancelButton.setStyle(popupButtonStyleH);
		gameController.surePopupYesButton.setStyle(popupButtonStyleH);

		gameController.gameOverPopupMenuButton.setStyle(popupButtonStyleV);
		gameController.gameOverPopupRestartButton.setStyle(popupButtonStyleV);
		gameController.gameOverPopupStayButton.setStyle(popupButtonStyleV);
	}

	private void setLabelStyles() {
		gameController.currentMoveLabel.setStyle(fxFontSize + fontSize25);
		gameController.checkLabel.setStyle(fxFontSize + fontSize20);
		gameController.timeLabel.setStyle(fxFontSize + fontSize40);
		gameController.historyLabel.setStyle(fxFontSize + fontSize20);
		gameController.promotionPopupLabel.setStyle(fxFontSize + fontSize25);
		gameController.surePopupLabel.setStyle(fxFontSize + fontSize20);
		gameController.gameOverPopupLabel.setStyle(fxFontSize + fontSize20);

		gameController.historyLabel.getParent().getParent().getParent()
				.setStyle(fxBackgroundRadiusNoSemicolon + borderRadius10 + fxBorderRadius + borderRadius10);
		gameController.timeLabel.getParent().getParent().getParent()
				.setStyle(fxBackgroundRadiusNoSemicolon + borderRadius10 + fxBorderRadius + borderRadius10);
	}

	private ImageView[] getPromotionIcons() {
		return new ImageView[] { (ImageView) gameController.promotionPopupQueenButton.getGraphic(),
				(ImageView) gameController.promotionPopupRookButton.getGraphic(),
				(ImageView) gameController.promotionPopupBishopButton.getGraphic(),
				(ImageView) gameController.promotionPopupKnightButton.getGraphic(),
				(ImageView) gameController.gameOverPopupMenuButton.getGraphic(),
				(ImageView) gameController.gameOverPopupRestartButton.getGraphic(),
				(ImageView) gameController.gameOverPopupStayButton.getGraphic() };
	}

	private ImageView[] getButtonIcons() {
		return new ImageView[] { (ImageView) gameController.resignButton.getGraphic(),
				(ImageView) gameController.restartButton.getGraphic(), (ImageView) gameController.settingsButton.getGraphic(),
				(ImageView) gameController.menuButton.getGraphic(), (ImageView) gameController.saveButton.getGraphic(),
				(ImageView) gameController.surePopupCancelButton.getGraphic(),
				(ImageView) gameController.surePopupYesButton.getGraphic() };
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
