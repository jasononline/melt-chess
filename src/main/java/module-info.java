/**
 * The main module of the chess application.
 */
module chess {
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.media;
	requires transitive javafx.graphics;

	exports chess.gui;
	exports chess.gui.settings;
	exports chess.gui.menu;
	exports chess.gui.game;
	exports chess.gui.network;
	exports chess.cli;
	exports chess.model;
	exports chess.engine;

	opens chess.gui.settings;
	opens chess.gui.game;
	opens chess.gui.menu;
	opens chess.gui.network;
}
