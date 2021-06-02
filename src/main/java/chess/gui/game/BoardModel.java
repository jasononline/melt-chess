package chess.gui.game;

import chess.gui.util.GraphicsManager;
import chess.model.Board;
import chess.model.Move;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Storage for information on the currently visible board position,
 * like positions of image objects, which piece is selected, etc.
 */
public class BoardModel {

    /**
     * Stores the ImageView objects that represent the pieces on the field.
     */
    private static List<ImageView> piecesGraphics = new ArrayList<>();

    /**
     * Stores the ImageView objects that represent the beaten pieces.
     */
    private static List<ImageView> beatenPiecesGraphics = new ArrayList<>();

    /**
     * Stores the ImageView objects that represent the possible moves on the field.
     */
    private static List<ImageView> possibleMovesGraphics = new ArrayList<>();

    /**
     * Stored the ImageView object that represents the selection of a piece.
     */
    private static ImageView selectedFieldGraphic = null;


    /**
     * Stores the index of the selected field according to model.Board.squares. -1 means "no field selected".
     * "a8" is 0 and "h1" is 63.
     */
    private static int selectedIndex = -1;


    /**
     * Gives information about whether a piece has been selected or not
     * @return true if a piece has been selected
     */
    public static boolean isSelected() {
        return selectedIndex == -1;
    }

    /**
     * Setter for the selectedIndex
     * @param index the new selectedIndex, should be -1 smaller than or equal index smaller than or equal 63.
     */
    public static void setSelectedIndex(int index) {
        if (-1 <= index && index <= 63) {
            BoardModel.selectedIndex = index;
        }
    }

    /**
     * Getter for the piecesGraphics-List
     * @return the piecesGraphics-List
     */
    public static List<ImageView> getPiecesGraphics() {
        return piecesGraphics;
    }


    /**
     * Getter for the BeatenPiecesGraphics-List
     * @return the BeatenPiecesGraphics-List
     */
    public static List<ImageView> getBeatenPiecesGraphics() {
        return beatenPiecesGraphics;
    }


    /**
     * Getter for the possibleMovesGraphics-List
     * @return the possibleMovesGraphics-List
     */
    public static List<ImageView> getPossibleMovesGraphics() {
        return possibleMovesGraphics;
    }


    /**
     * Getter for the selected index
     * @return the selected index
     */
    public static int getSelectedIndex() {
        return selectedIndex;
    }


    /**
     * Getter for the selectedFieldGraphic
     * @return the selectedFieldGraphic
     */
    public static ImageView getSelectedFieldGraphic() {
        return selectedFieldGraphic;
    }


    /**
     * Setter for the piecesGraphics-List
     * @param piecesGraphics the new piecesGraphics-List
     */
    public static void setPiecesGraphics(List<ImageView> piecesGraphics) {
        BoardModel.piecesGraphics = piecesGraphics;
    }


    /**
     * Setter for the beatenPiecesGraphics-List
     * @param beatenPiecesGraphics the new beatenPiecesGraphics-List
     */
    public static void setBeatenPiecesGraphics(List<ImageView> beatenPiecesGraphics) {
        BoardModel.beatenPiecesGraphics = beatenPiecesGraphics;
    }


    /**
     * Setter for the possibleMovesGraphics-List
     * @param possibleMovesGraphics the new possibleMovesGraphics-List
     */
    public static void setPossibleMovesGraphics(List<ImageView> possibleMovesGraphics) {
        BoardModel.possibleMovesGraphics = possibleMovesGraphics;
    }


    /**
     * Setter for the selectedFieldGraphic-List
     * @param selectedFieldGraphic the new selectedFieldGraphic-List
     */
    public static void setSelectedFieldGraphic(ImageView selectedFieldGraphic) {
        BoardModel.selectedFieldGraphic = selectedFieldGraphic;
    }
}
