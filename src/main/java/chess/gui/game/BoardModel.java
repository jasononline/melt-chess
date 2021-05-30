package chess.gui.game;

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
    private static List<ImageView> pieces = new ArrayList<>();

    /**
     * Stores the ImageView objects that represent the beaten pieces.
     */
    private static List<ImageView> beatenPieces = new ArrayList<>();
}
