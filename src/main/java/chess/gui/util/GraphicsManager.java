package chess.gui.util;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads image resources and serves as graphical element object factory.
 */
public class GraphicsManager {

    private static GraphicsManager instance;

    private Map<String, Image> imgs = new HashMap<String, Image>();

    /**
     * Constructor of the GraphicsManager. Can only be called using the getInstance()-function.
     */
    private GraphicsManager() {

    }

    /**
     * Ensures that only one instance of GraphicsManager exist.
     * @return the one and only instance of GraphicsManager.
     */
    public static GraphicsManager getInstance() {
        if (instance == null) {
            instance = new GraphicsManager();
        }
        return instance;
    }

    /**
     * Adds a graphic (must be .png format) to the imgs-map so that it can be used using the getGraphic()-function.
     * @param name the name of the graphic.
     */
    private void loadGraphic(String name) {
        Image image = new Image(GraphicsManager.class.getResource(name + ".png").toExternalForm(), true);
        imgs.put(name, image);
    }

    /**
     * Returns the specified graphic as an Image object.
     * @param name the name of the wanted graphic.
     * @return the graphic according to the parameter name from the imgs-HashMap.
     */
    public Image getGraphic(String name) {
        if (imgs.get(name) == null) {
            loadGraphic(name);
        }
        return imgs.get(name);
    }

}
