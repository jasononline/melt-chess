/**
 * The main module of the chess application.
 */
module chess {
    requires javafx.fxml;
    requires javafx.controls;
    requires transitive javafx.graphics;
    
    exports chess.gui;
    exports chess.gui.settings;
    exports chess.cli;
    exports chess.model;
    exports chess.engine;

    opens chess.gui.settings;
}
