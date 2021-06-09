package chess.gui.game;

import chess.gui.game.GameModel.ChessColor;
import chess.gui.util.GraphicsManager;
import chess.model.Piece;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class GamePopup {

	public static boolean showSurePopup(GridPane boardGrid, String titleKey) {
		return true;
	}

	public static void showPromotionPopup(GridPane boardGrid, ChessColor forColor) {

		Popup popup = new Popup();

		Button queenButton = new Button();
		Button rookButton = new Button();
		Button bishopButton = new Button();
		Button knightButton = new Button();

		String buttonStyle = "-fx-background-color: rgba(131, 131, 131, 0.7); -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-width: 3px; -fx-font-family: Montserrat; -fx-font-weight: 600; -fx-font-size: 15px; -fx-text-fill: #fff; -fx-cursor: hand;";

		ImageView whiteQueen = GraphicsManager.getGraphicAsImageView("queen_white");
		whiteQueen.setPreserveRatio(true);
		whiteQueen.setFitHeight(20);
		ImageView blackQueen = GraphicsManager.getGraphicAsImageView("queen_black");
		blackQueen.setPreserveRatio(true);
		blackQueen.setFitHeight(20);
		ImageView whiteRook = GraphicsManager.getGraphicAsImageView("rook_white");
		whiteRook.setPreserveRatio(true);
		whiteRook.setFitHeight(20);
		ImageView blackRook = GraphicsManager.getGraphicAsImageView("rook_black");
		blackRook.setPreserveRatio(true);
		blackRook.setFitHeight(20);
		ImageView whiteBishop = GraphicsManager.getGraphicAsImageView("bishop_white");
		whiteBishop.setPreserveRatio(true);
		whiteBishop.setFitHeight(20);
		ImageView blackBishop = GraphicsManager.getGraphicAsImageView("bishop_black");
		blackBishop.setPreserveRatio(true);
		blackBishop.setFitHeight(20);
		ImageView whiteKnight = GraphicsManager.getGraphicAsImageView("knight_white");
		whiteKnight.setPreserveRatio(true);
		whiteKnight.setFitHeight(20);
		ImageView blackKnight = GraphicsManager.getGraphicAsImageView("knight_black");
		blackKnight.setPreserveRatio(true);
		blackKnight.setFitHeight(20);

		queenButton.setText("Queen");
		queenButton.setPrefHeight(50);
		queenButton.setPrefWidth(75);
		queenButton.setGraphic(forColor == ChessColor.White ? whiteQueen : blackQueen);
		queenButton.setContentDisplay(ContentDisplay.TOP);
		queenButton.setStyle(buttonStyle);
		anchorNode(queenButton, 0., 0., 0., 0.);

		rookButton.setText("Rook");
		rookButton.setPrefHeight(50);
		rookButton.setPrefWidth(75);
		rookButton.setGraphic(forColor == ChessColor.White ? whiteRook : blackRook);
		rookButton.setContentDisplay(ContentDisplay.TOP);
		rookButton.setStyle(buttonStyle);
		anchorNode(rookButton, 0., 0., 0., 0.);

		bishopButton.setText("Bishop");
		bishopButton.setPrefHeight(50);
		bishopButton.setPrefWidth(75);
		bishopButton.setGraphic(forColor == ChessColor.White ? whiteBishop : blackBishop);
		bishopButton.setContentDisplay(ContentDisplay.TOP);
		bishopButton.setStyle(buttonStyle);
		anchorNode(bishopButton, 0., 0., 0., 0.);

		knightButton.setText("Knight");
		knightButton.setPrefHeight(50);
		knightButton.setPrefWidth(75);
		knightButton.setGraphic(forColor == ChessColor.White ? whiteKnight : blackKnight);
		knightButton.setContentDisplay(ContentDisplay.TOP);
		knightButton.setStyle(buttonStyle);
		anchorNode(knightButton, 0., 0., 0., 0.);

		Label popupLabel = new Label("Promoute your pawn into");

		popupLabel.setStyle(
				"-fx-font-family: Montserrat; -fx-font-weight: 600; -fx-font-size: 20px; -fx-text-fill: white; -fx-alignment: center;");

		EventHandler<ActionEvent> buttonActionHandler = (event) -> {
			if (event.getSource() == queenButton) {
				if (forColor == ChessColor.White) {
					GameModel.setPieceForPromotion(Piece.Queen + Piece.White);
				} else {
					GameModel.setPieceForPromotion(Piece.Queen + Piece.Black);
				}
			}
			if (event.getSource() == rookButton) {
				if (forColor == ChessColor.White) {
					GameModel.setPieceForPromotion(Piece.Rook + Piece.White);
				} else {
					GameModel.setPieceForPromotion(Piece.Rook + Piece.Black);
				}
			}
			if (event.getSource() == bishopButton) {
				if (forColor == ChessColor.White) {
					GameModel.setPieceForPromotion(Piece.Bishop + Piece.White);
				} else {
					GameModel.setPieceForPromotion(Piece.Bishop + Piece.Black);
				}
			}
			if (event.getSource() == knightButton) {
				if (forColor == ChessColor.White) {
					GameModel.setPieceForPromotion(Piece.Knight + Piece.White);
				} else {
					GameModel.setPieceForPromotion(Piece.Knight + Piece.Black);
				}
			}
			boardGrid.setDisable(false);
			popup.hide();
		};

		queenButton.setOnAction(buttonActionHandler);
		rookButton.setOnAction(buttonActionHandler);
		bishopButton.setOnAction(buttonActionHandler);
		knightButton.setOnAction(buttonActionHandler);

		HBox hBox = new HBox(20, new AnchorPane(queenButton), new AnchorPane(rookButton), new AnchorPane(bishopButton),
				new AnchorPane(knightButton));
		VBox vBox = new VBox(20, popupLabel, hBox);
		AnchorPane popupRoot = new AnchorPane(vBox);
		popupRoot.setStyle("-fx-background-radius: 10; -fx-background-color: rgba(92, 92, 92, .9);");

		anchorNode(vBox, 20., 20., 20., 20.);

		boardGrid.setDisable(true);

		popup.getContent().addAll(popupRoot);

		popup.show(boardGrid.getScene().getWindow());

		if (forColor == ChessColor.White) {
		} else {

		}
		popup.centerOnScreen();
	}

	private static void anchorNode(Node node, double left, double top, double right, double bottom) {
		AnchorPane.setLeftAnchor(node, left);
		AnchorPane.setTopAnchor(node, top);
		AnchorPane.setRightAnchor(node, right);
		AnchorPane.setBottomAnchor(node, bottom);
	}

}
