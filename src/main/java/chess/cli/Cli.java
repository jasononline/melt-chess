package chess.cli;

import chess.engine.Engine;
import chess.model.Game;
import chess.model.Move;
import chess.model.Piece;
import chess.util.TextManager;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * Starting point of the command line interface
 */
public class Cli {
	private static Game game = new Game();
	private static final Engine engine = new Engine();
	private static boolean runningPVP = false;
	private static boolean runningPVPC = false;
	private static final Scanner scan = new Scanner(System.in);

	/**
	 * The entry point of the CLI application.
	 *
	 * @param args The command line arguments passed to the application
	 */
	public static void main(String[] args) {

		boolean simpleRun = Arrays.asList(args).contains("--simple");

		if (simpleRun) {
			// Start game person vs person
			runningPVP = true;
			runningPVPC = false;

		} else {

			System.out.println();
			System.out.println("███╗   ███╗███████╗██╗  ████████╗     ██████╗██╗  ██╗███████╗███████╗███████╗");
			System.out.println("████╗ ████║██╔════╝██║  ╚══██╔══╝    ██╔════╝██║  ██║██╔════╝██╔════╝██╔════╝");
			System.out.println("██╔████╔██║█████╗  ██║     ██║       ██║     ███████║█████╗  ███████╗███████╗");
			System.out.println("██║╚██╔╝██║██╔══╝  ██║     ██║       ██║     ██╔══██║██╔══╝  ╚════██║╚════██║");
			System.out.println("██║ ╚═╝ ██║███████╗███████╗██║       ╚██████╗██║  ██║███████╗███████║███████║");
			System.out.println("╚═╝     ╚═╝╚══════╝╚══════╝╚═╝        ╚═════╝╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝");

			runMainMenu();

		}
		runGame();
		scan.close();
	}

	/**
	 * Display the main menu and process the selected option
	 */
	private static void runMainMenu() {
		String title = TextManager.get("cli.menu.title");
		String[] options = { TextManager.get("cli.menu.newGame"), TextManager.get("cli.menu.loadGame"),
				TextManager.get("cli.menu.showHelp"), TextManager.get("cli.menu.changeLanguage"),
				TextManager.get("cli.menu.quit") };

		Menu mainMenu = new Menu(title, options);
		int selectedIndex = mainMenu.run();

		switch (selectedIndex) {
			case 0:
				runGameModeMenu();
				break;
			case 1:
				loadGame();
				break;
			case 2:
				printHelp();
				runMainMenu();
				break;
			case 3:
				runLanguageMenu();
				break;
			case 4:
				exitGame();
				break;
			default:
				handleStandardCommands(selectedIndex, "mainMenu");
				break;
		}
	}

	/**
	 * Print the game mode menu and process the selected game mode
	 */
	private static void runGameModeMenu() {
		String title = TextManager.get("cli.mode.title");
		String[] options = { TextManager.get("cli.mode.pvpMode"), TextManager.get("cli.mode.aiMode"),
				TextManager.get("cli.mode.netMode"), TextManager.get("cli.mode.backToMain") };
		Menu gameModeMenu = new Menu(title, options);
		int selectedIndex = gameModeMenu.run();

		switch (selectedIndex) {
			case 0: // pvp mode
				// Start game against another player
				runningPVP = true;
				runningPVPC = false;
				break;
			case 1: // ai mode
				// // Start game against computer
				runningPVP = false;
				runningPVPC = true;
				break;
			case 2: // network mode
				// TODO: Start network game
				break;
			case 3: // back to main menu
				runMainMenu();
				break;
			default:
				handleStandardCommands(selectedIndex, "gameModeMenu");
				break;
		}
	}

	/**
	 * Print the language menu and process the change of the current language to the
	 * selected one
	 */
	private static void runLanguageMenu() {
		String title = TextManager.get("cli.lang.title");
		String[] options = { TextManager.get("cli.lang.en"), TextManager.get("cli.lang.de"),
				TextManager.get("cli.lang.backToMain") };

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
				handleStandardCommands(selectedIndex, "languageMenu");
				break;
		}
	}

	/**
	 * Handles standard commands, like help, english, german, quit
	 * 
	 * @param code       command code
	 * @param calledFrom the place from which this function was called
	 */
	private static void handleStandardCommands(int code, String calledFrom) {
		switch (code) {
			case 101: // help code
				printHelp();
				break;
			case 102: // english language code
				TextManager.setLocale(Locale.ENGLISH);
				break;
			case 103: // german language code
				TextManager.setLocale(Locale.GERMAN);
				break;
			case 104: // quit code
				exitGame();
				break;
		}
		if (calledFrom.equals("mainMenu"))
			runMainMenu();
		if (calledFrom.equals("gameModeMenu"))
			runGameModeMenu();
		if (calledFrom.equals("languageMenu"))
			runLanguageMenu();
	}

	/**
	 * Loads a saved game
	 */
	private static void loadGame() {
		// TODO: Load saved game
	}

	/**
	 * Saves the current game
	 */
	private static void saveGame() {
		// TODO: Save the current game
	}

	/**
	 * Exits the game with a message
	 */
	private static void exitGame() {
		System.out.println("\n" + TextManager.get("cli.exit") + "\n");
		System.exit(0);
	}

	/**
	 * Start the correct new game loop
	 */
	public static void runGame() {
		if (game.getCurrentPosition().getTurnColor() == Piece.White)
			System.out.println(ConsoleColors.BOLD + "\n" + TextManager.get("cli.whiteMove") + ConsoleColors.RESET);
		System.out.println("\n" + game.getCurrentPosition().toString());
		if (runningPVP) {
			gameLoopPVP();
		} else if (runningPVPC) {
			gameLoopPVPC();
		}
	}

	/**
	 * Start a Player versus Player game
	 */
	public static void gameLoopPVP() {
		while (runningPVP) {
			performAction(getValidUserInput());
		}
	}

	/**
	 * Start a Player versus Computer game
	 */
	public static void gameLoopPVPC() {
		while (runningPVPC) {
			if (game.getCurrentPosition().getTurnColor() == Piece.White) {
				performAction(getValidUserInput());
			} else {
				// let the Engine make a move
				System.out.println(TextManager.get("cli.aiThink"));
				performEngineMove();
			}
		}
	}

	/**
	 * Perform the Action according to the user input
	 * 
	 * @param userInput The user input
	 */
	public static void performAction(String userInput) {

		switch (userInput) {

			case "help":
			case "hilfe":
				printHelp();
				printTurnColor();
				System.out.println("\n" + game.getCurrentPosition().toString());
				break;

			case "english":
			case "englisch":
				TextManager.setLocale(Locale.ENGLISH);
				printTurnColor();
				System.out.println("\n" + game.getCurrentPosition().toString());
				break;

			case "german":
			case "deutsch":
				TextManager.setLocale(Locale.GERMAN);
				printTurnColor();
				System.out.println("\n" + game.getCurrentPosition().toString());
				break;

			case "beaten":
			case "geschlagen":
				printBeatenPieces();
				System.out.println("\n" + TextManager.get("cli.help.back"));
				scan.nextLine();
				printTurnColor();
				System.out.println("\n" + game.getCurrentPosition().toString());
				break;

			case "reset":
			case "neustart":
				game = new Game();
				printTurnColor();
				System.out.println("\n" + game.getCurrentPosition().toString());
				break;

			case "save":
			case "speichern":
				saveGame();
				break;

			case "quit":
			case "beenden":
				exitGame();
				break;

			default:
				if (!game.attemptMove(Move.parseUserMoveInput(userInput, game))) {
					System.out.println(ConsoleColors.RED_BOLD + "\n!Move not allowed\n" + ConsoleColors.RESET);
				} else {
					ConsoleColors.green();
					System.out.println("\n!" + userInput);
					ConsoleColors.reset();
					printTurnColor();
					System.out.println("\n" + game.getCurrentPosition().toString());
					if (game.checkCheck()) {
						System.out
								.println(ConsoleColors.PURPLE_BOLD + "\n" + TextManager.get("cli.youInCheck") + ConsoleColors.RESET);
					}
					if (game.checkWinCondition() != Game.WinCondition.NONE) {
						endGame();
					}
				}
				break;
		}
	}

	/**
	 * Prints whose turn it is.
	 */
	private static void printTurnColor() {
		if (game.getCurrentPosition().getTurnColor() == Piece.White) {
			System.out.println(ConsoleColors.BOLD + "\n" + TextManager.get("cli.whiteMove") + ConsoleColors.RESET);
		} else {
			System.out.println(ConsoleColors.BOLD + "\n" + TextManager.get("cli.blackMove") + ConsoleColors.RESET);
		}
	}

	/**
	 * Test if the user input will have a defined effect
	 * 
	 * @param userInput The user Input
	 * @return whether the user input will have a defined effect
	 */
	public static boolean testUserInputSyntax(String userInput) {
		// Checks if input matches one of valid inputs: move(e7-e8[Q]), beaten, help,
		// quit, reset
		return userInput.matches(
				"^[a-h]{1}[1-8]{1}-[a-h]{1}[1-8]{1}[qrbn]?$|^help$|^hilfe$|^english$|^englisch$|^german$|^deutsch$|^beaten$|^geschlagen$|^reset$|^neustart$|^save$|^speichern$|^quit$|^beenden$");
	}

	/**
	 * Uses the engine to generate the next PC-move and executes that move.
	 */
	public static void performEngineMove() {
		Move next = engine.generateBestMove(game.getCurrentPosition());
		System.out
				.println(ConsoleColors.GREEN + "\n" + TextManager.get("cli.aiMove") + next.toString() + ConsoleColors.RESET);
		game.attemptMove(next);
		// print the new Position after the PCs move
		printTurnColor();
		System.out.println("\n" + game.getCurrentPosition().toString());
		// find out if the game is over
		if (game.checkCheck()) {
			System.out.println(ConsoleColors.PURPLE_BOLD + "\n" + TextManager.get("cli.youInCheck") + ConsoleColors.RESET);
		}
		if (game.checkWinCondition() != Game.WinCondition.NONE) {
			endGame();
		}
	}

	/**
	 * Outputs valid commands and their descriptions
	 */
	public static void printHelp() {

		StringBuilder help;
		System.out.print(ConsoleColors.GREEN);
		System.out.println();
		System.out.println("██╗  ██╗███████╗██╗     ██████╗ ");
		System.out.println("██║  ██║██╔════╝██║     ██╔══██╗");
		System.out.println("███████║█████╗  ██║     ██████╔╝");
		System.out.println("██╔══██║██╔══╝  ██║     ██╔═══╝ ");
		System.out.println("██║  ██║███████╗███████╗██║     ");
		System.out.println("╚═╝  ╚═╝╚══════╝╚══════╝╚═╝     ");

		help = new StringBuilder("\n" + TextManager.get("cli.help.title") + "\n\n");
		help.append(TextManager.get("cli.help.fromAnywhere")).append("\n\n");

		String[] commands = { "cli.helpCommand", "cli.enCommand", "cli.deCommand", "cli.quitCommand", "cli.beatenCommand",
				"cli.resetCommand", "cli.saveCommand" };
		String[] descriptions = { "cli.help.help", "cli.help.english", "cli.help.german", "cli.help.quit",
				"cli.help.beaten", "cli.help.reset", "cli.help.save" };

		for (int i = 0; i < commands.length; i++) {
			help.append(TextManager.get(commands[i] + "En")).append("	| ").append(TextManager.get(commands[i] + "De"))
					.append("	").append(TextManager.get(descriptions[i])).append("\n");
			if (i == 3) {
				help.append("\n").append(TextManager.get("cli.help.fromGame")).append("\n\n");
			}
		}

		help.append("\n").append(TextManager.get("cli.help.back"));

		System.out.println(help);
		System.out.print(ConsoleColors.RESET);
		scan.nextLine();
	}

	/**
	 * Request input from the User
	 * 
	 * @param messageToUser The Message to print to ask for Input
	 * @return The user input as a String
	 */
	public static String getUserInput(String messageToUser) {
		System.out.println(messageToUser);
		return getUserInput();
	}

	/**
	 * Request input from the User
	 * 
	 * @return The user input as a String (lowercase)
	 */
	public static String getUserInput() {
		return scan.nextLine().toLowerCase();
	}

	/**
	 * Request valid input from the user until there ist valid input
	 * 
	 * @return user input that has a defined meaning
	 */
	private static String getValidUserInput() {
		String input = getUserInput();
		while (!testUserInputSyntax(input)) {
			input = getUserInput(ConsoleColors.RED_BOLD + "\n!Invalid move\n" + ConsoleColors.RESET);
		}
		return input;
	}

	/**
	 * Print the list of beaten pieces
	 */
	private static void printBeatenPieces() {
		System.out.println("\n" + TextManager.get("cli.beatenPieces") + "\n");
		if (!game.getCurrentPosition().getCapturedPieces().isEmpty()) {
			for (int i : game.getCurrentPosition().getCapturedPieces()) {
				System.out.print(Piece.toString(i));
			}
			System.out.println();
		} else {
			System.out.println("> No beaten pieces");
		}
	}

	/**
	 * End the game according to win conditions
	 */
	private static void endGame() {
		Game.WinCondition winCondition = game.checkWinCondition();
		if (winCondition == Game.WinCondition.CHECKMATE) {
			if (!runningPVP) {
				if (game.getCurrentPosition().getTurnColor() == Piece.White) {
					System.out.println(ConsoleColors.YELLOW_BOLD + TextManager.get("cli.lose") + ConsoleColors.RESET);
				} else {
					System.out.println(ConsoleColors.YELLOW_BOLD + TextManager.get("cli.win") + ConsoleColors.RESET);
				}
			} else {
				if (game.getCurrentPosition().getTurnColor() == Piece.White) {
					System.out.println(ConsoleColors.YELLOW_BOLD + TextManager.get("cli.blackWin") + ConsoleColors.RESET);
				} else {
					System.out.println(ConsoleColors.YELLOW_BOLD + TextManager.get("cli.whiteWin") + ConsoleColors.RESET);
				}
			}
		} else if (winCondition == Game.WinCondition.REMIS) {
			System.out.println(ConsoleColors.YELLOW_BOLD + TextManager.get("cli.remis") + ConsoleColors.RESET);
		}
		if (winCondition != Game.WinCondition.NONE) {
			printBeatenPieces();
			exitGame();
		}
	}
}
