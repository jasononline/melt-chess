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
     * @return the graphic as an ImageView object according to name.
     */
    public static ImageView getGraphicAsImageView(String name) {
        ImageView imageView = new ImageView(getGraphicAsImage(name));
        imageView.setPreserveRatio(true);
        return imageView;
    }


    /**
     * Returns the specified graphic as an ImgaeView object with specified height
     *
     * @param name the name of the wanted graphic.
     * @param height the wanted height of the graphic.
     * @return the graphic as an ImageView object according to name.
     */
    public static ImageView getGraphicAsImageView(String name, int height) {
        ImageView imageView = new ImageView(getGraphicAsImage(name));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
        return imageView;
    }

}
