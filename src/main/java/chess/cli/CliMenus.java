package chess.cli;

import chess.util.Saving;
import chess.util.SavingManager;
import chess.util.TextManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Controls behaviour of Cli menus.
 */
public class CliMenus {

	/**
	 * Display the main menu and process the selected option
	 */
	public static void runMainMenu() {
		String title = TextManager.get("cli.menu.title");
		List<String> options = Arrays.asList(TextManager.get("cli.menu.newGame"), TextManager.get("cli.menu.loadGame"),
				TextManager.get("cli.menu.showHelp"), TextManager.get("cli.menu.changeLanguage"),
				TextManager.get("cli.menu.quit"));

		Menu mainMenu = new Menu(title, options);
		int selectedIndex = mainMenu.run();

		switch (selectedIndex) {
			case 0:
				runGameModeMenu(true, "");
				break;
			case 1:
				// loadGame();
				runLoadMenu(".");
				break;
			case 2:
				Help.print();
				runMainMenu();
				break;
			case 3:
				runLanguageMenu();
				break;
			case 4:
				Cli.exitGame();
				break;
			default:
				handleStandardMenuCommands(selectedIndex, "mainMenu");
				break;
		}
	}

	/**
	 * Print the game mode menu and process the selected game mode
	 * 
	 * @param newgame  boolean whether start a new game or a loaded one
	 * @param loadPath the path by which saving was chosen
	 */
	private static void runGameModeMenu(boolean newgame, String loadPath) {
		String title = TextManager.get("cli.mode.title");
		List<String> options;
		if (newgame) {
			options = Arrays.asList(TextManager.get("cli.mode.pvpMode"), TextManager.get("cli.mode.aiMode"),
					TextManager.get("cli.mode.netMode"), TextManager.get("cli.mode.backToMain"));
		} else {
			options = Arrays.asList(TextManager.get("cli.mode.pvpMode"), TextManager.get("cli.mode.aiMode"),
					TextManager.get("cli.mode.backToMain"), TextManager.get("cli.mode.backToLoad"));
		}
		Menu gameModeMenu = new Menu(title, options);
		int selectedIndex = gameModeMenu.run();

		switch (selectedIndex) {
			case 0: // pvp mode
				Cli.runningPVP = true;
				Cli.runningPVPC = false;
				Cli.runGame(newgame);
				break;
			case 1: // ai mode
				Cli.runningPVP = false;
				Cli.runningPVPC = true;
				Cli.runGame(newgame);
				break;
			case 2: // network mode when newgame is true or back to main menu when false
				if (newgame) {
					// TODO: Start network game
				} else {
					runMainMenu();
				}
				break;
			case 3: // back to main menu when newgame is true or back to selection when false
				if (newgame) {
					runMainMenu();
				} else {
					runLoadMenu(loadPath);
				}
				break;
			default:
				handleStandardMenuCommands(selectedIndex, newgame ? "gameModeMenuNewGame" : "gameModeMenuLoadGame");
				break;
		}
	}

	/**
	 * Print the language menu and process the change of the current language to the
	 * selected one
	 */
	private static void runLanguageMenu() {
		String title = TextManager.get("cli.lang.title");
		List<String> options = Arrays.asList(TextManager.get("cli.lang.en"), TextManager.get("cli.lang.de"),
				TextManager.get("cli.lang.backToMain"));

		Menu languageMenu = new Menu(title, options);
		int selectedIndex = languageMenu.run();

		switch (selectedIndex) {
			case 0: // English language
				TextManager.setLocale(Locale.ENGLISH);
				runMainMenu();
				break;
			case 1: // German language
				TextManager.setLocale(Locale.GERMAN);
				runMainMenu();
				break;
			case 2: // Back to main menu
				runMainMenu();
				break;
			default:
				handleStandardMenuCommands(selectedIndex, "languageMenu");
				break;
		}
	}

	/**
	 * Handles standard commands, like help, english, german, quit
	 * 
	 * @param code       command code
	 * @param calledFrom the place from which this function was called
	 */
	private static void handleStandardMenuCommands(int code, String calledFrom) {
		switch (code) {
			case 101: // help code
				Help.print();
				break;
			case 102: // english language code
				TextManager.setLocale(Locale.ENGLISH);
				break;
			case 103: // german language code
				TextManager.setLocale(Locale.GERMAN);
				break;
			case 104: // back to main menu
				runMainMenu();
				break;
			case 105: // quit code
				Cli.exitGame();
				break;
		}
		returnToMenu(calledFrom);
	}

	/**
	 * Returns to the menu from which the user entered one of the commands
	 * 
	 * @param toMenu which menu to return to
	 */
	private static void returnToMenu(String toMenu) {
		if (toMenu.equals("mainMenu"))
			runMainMenu();
		if (toMenu.equals("gameModeMenuNewGame"))
			runGameModeMenu(true, "");
		if (toMenu.matches("^gameModeMenuLoadGame/.+$"))
			runGameModeMenu(false, toMenu.split("/")[1]);
		if (toMenu.equals("languageMenu"))
			runLanguageMenu();
		if (toMenu.matches("^savingsMenu/.+$"))
			runLoadMenu(toMenu.split("/")[1]);
	}

	/**
	 * Print the savings menu and process the selected savings, searches for savings
	 * in the entered path
	 * 
	 * @param path the path in which savings should be found
	 */
	private static void runLoadMenu(String path) {
		String fullPath = "";
		try {
			fullPath = new File(path).getCanonicalPath();
		} catch (IOException e) {
			fullPath = path;
		}
		String title = TextManager.get("cli.load.youIn") + " " + fullPath + "\n" + TextManager.get("cli.load.foundSavings");
		List<String> options = new ArrayList<>();
		File[] savingFiles = SavingManager.findSavings(path);

		for (File file : savingFiles) {
			options.add(file.getName());
		}

		int filesCount = options.size();

		if (options.isEmpty()) {
			title = TextManager.get("cli.load.youIn") + " " + fullPath + "\n" + TextManager.get("cli.load.noSavings");
		}

		options.add(TextManager.get("cli.load.changeDir"));
		options.add(TextManager.get("cli.load.manualPath"));
		options.add(TextManager.get("cli.load.backToMain"));

		Menu savingsMenu = new Menu(title, options);
		int selectedIndex = savingsMenu.run();

		if (selectedIndex == filesCount) {
			checkManualDirPath(path);
		} else if (selectedIndex == filesCount + 1) {
			checkManualFilePath(path);
		} else if (selectedIndex == filesCount + 2) {
			runMainMenu();
		} else if (selectedIndex >= 0 && selectedIndex < filesCount) {
			loadGame(SavingManager.loadGame(savingFiles[selectedIndex]), path);
		} else {
			handleStandardMenuCommands(selectedIndex, "savingsMenu/" + path);
		}
	}

	/**
	 * Checks if the entered path is a directory, and if so, runs the savings menu
	 * once again
	 * 
	 * @param entryPath the path to return in case of cancellation
	 */
	private static void checkManualDirPath(String entryPath) {
		String path = Cli.getUserInput("\n" + TextManager.get("cli.load.enterDir"));

		while (!Files.isDirectory(Paths.get(path))) {
			if (path.matches("^" + Help.CliCommand.CANCEL_EN.raw + "$|^" + Help.CliCommand.CANCEL_DE.raw + "$")) {
				runLoadMenu(entryPath);
				return;
			}
			if (!(new File(path).canRead())) {
				path = Cli.getUserInput("\n" + TextManager.get("cli.load.dirAccessError"));
			} else {
				path = Cli.getUserInput("\n" + TextManager.get("cli.load.notDirError"));
			}
		}
		runLoadMenu(path);
	}

	/**
	 * Checks if the entered path is a valid saving, and if so, loads this
	 * 
	 * @param entryPath the path to return in case of cancellation or failure
	 *                  loading saving
	 */
	private static void checkManualFilePath(String entryPath) {

		String path = Cli.getUserInput("\n" + TextManager.get("cli.load.enterSaving"));

		while (!path.endsWith(".melt") || !(new File(path).isFile())) {
			if (path.matches("^" + Help.CliCommand.CANCEL_EN.raw + "$|^" + Help.CliCommand.CANCEL_DE.raw + "$")) {
				runLoadMenu(entryPath);
				return;
			}
			path = Cli.getUserInput("\n" + TextManager.get("cli.load.notSavingError"));
		}

		Saving saving = SavingManager.loadGame(path);

		if (saving == null) {
			System.out.println("\n" + TextManager.get("cli.load.loadingError"));
			runLoadMenu(entryPath);
			return;
		}

		loadGame(saving, path);
	}

	/**
	 * Loads saved game
	 * 
	 * @param saving saving to load
	 * @param path   path to return to if user choose "return" option
	 */
	private static void loadGame(Saving saving, String path) {
		Cli.game = saving.getGame();
		Cli.movesHistory = saving.getMovesHistory();
		runGameModeMenu(false, path);
	}

}
