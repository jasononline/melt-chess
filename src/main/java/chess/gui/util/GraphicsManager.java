package chess.gui.util;

import chess.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Loads image resources and serves as graphical element object factory.
 */
public class GraphicsManager {

    /**
     * Holds all graphics that were loaded by the loadGraphic()-function.
     */
    private static Map<String, Image> imgs = new HashMap<String, Image>();


    /**
     * Adds a graphic (must be .png format) to the imgs-map so that it can be used using the getGraphic()-function.
     *
     * @param name the name of the graphic.
     */
    private static void loadGraphic(String name) {
        if(!(name == null) && imgs.get(name) == null){
            Image image = new Image(Main.class.getResourceAsStream("/chess/gui/util/graphics/" + name + ".png"));
            imgs.put(name, image);
        }
    }


    /**
     * Returns the specified graphic as an Image object.
     *
     * @param name the name of the wanted graphic.
     * @return the graphic as an Image object according to name from the imgs-HashMap.
     */
    public static Image getGraphicAsImage(String name) {
        if (imgs.get(name) == null) {
            loadGraphic(name);
        }
        return imgs.get(name);
    }


    /**
     * Returns the specified graphic as an ImgaeView object.
     *
     * @param name the name of the wanted graphic.
     * @return the graphic as an ImameView object according to name.
     */
    public static ImageView getGraphicAsImageView(String name) {
        return new ImageView(getGraphicAsImage(name));
    }


    /**
     * Returns the specified graphic as an ImgaeView object with specified coordinates.
     *
     * @param name the name of the wanted graphic.
     * @param x the wanted x coordinate.
     * @param y the wanted y coordinate.
     * @return the graphic as an ImageView object with non-default coordinate values.
     */
    public static ImageView getGraphicAsImageView(String name, double x, double y) {
        ImageView result = getGraphicAsImageView(name);
        result.setX(x);
        result.setY(y);
        return result;
    }

    /**
     * Returns the specified graphic as an ImgaeView object with specified coordinates and size
     *
     * @param name the name of the wanted graphic.
     * @param x the wanted x coordinate.
     * @param y the wanted y coordinate.
     * @param size the wanted size.
     * @return the graphic as an ImageView object with non-default coordinate values and specific size.
     */
    public static ImageView getGraphicAsImageView(String name, double x, double y, int size) {
        ImageView result = getGraphicAsImageView(name, x, y);
        result.setFitHeight(size);
        result.setFitWidth(size);
        return result;
    }
}
