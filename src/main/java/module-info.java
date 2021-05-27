/**
 * The main module of the chess application.
 */
module chess {
    requires javafx.controls;
    requires transitive javafx.graphics;
    
    exports chess.gui;
    exports chess.cli;
    exports chess.model;
    exports chess.engine;

}
