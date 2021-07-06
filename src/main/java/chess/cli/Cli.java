package chess.cli;

import chess.engine.Engine;
import chess.model.Game;
import chess.model.Move;
import chess.model.Piece;
import chess.util.Client;
import chess.util.Server;
import chess.util.TextManager;

import java.io.IOException;
import java.util.*;

/**
 * Starting point of the command line interface
 */
public class Cli {
	static Game game = new Game();
	private static final Engine engine = new Engine();
	protected static boolean runningPVP = false;
	protected static boolean runningPVPC = false;
	protected static boolean runningNetwork = false;
	static final Scanner scan = new Scanner(System.in);
	static List<Move> movesHistory = new ArrayList<>();
	private static int usersColor = Piece.White;

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

			CliMenus.runMainMenu();

		}
		scan.close();
	}

	/**
	 * Starts the correct new game loop
	 * 
	 * @param newgame boolean whether run a new game or a loaded one
	 */
	public static void runGame(boolean newgame) {
		if (newgame)
			game = new Game();
		printGameBoard();
		if (runningPVP) {
			gameLoopPVP();
		} else if (runningPVPC) {
			gameLoopPVPC();
		} else if (runningNetwork) {
			gameLoopNetwork();
		}
	}

	/**
	 * Starts and runs a Player versus Player game
	 */
	public static void gameLoopPVP() {
		while (runningPVP) {
			performAction(getValidUserInput());
		}
	}

	/**
	 * Starts and runs a Player versus Computer game
	 */
	public static void gameLoopPVPC() {
		while (runningPVPC) {
			if (game.getCurrentPosition().getTurnColor() == Piece.White) {
				performAction(getValidUserInput());
			} else {
				// let the Engine make a move
				performMove(null, false);
			}
		}
	}

	/**
	 * Starts and runs Player versus Computer game
	 */
	public static void gameLoopNetwork() {
		while (runningNetwork) {
			if (game.getCurrentPosition().getTurnColor() == usersColor) {
				// Let User make a Move
				System.out.println(TextManager.get("cli.network.yourTurn"));
				performAction(getValidUserInput());
			} else {
				// Let the Opponent make a Move
				String opponentInput = null;
				try {
					System.out.println(TextManager.get("cli.network.waitForOpponent"));
					opponentInput = Server.getOpponentInput();
					// System.out.println("#Debug: Opponent input was: " + opponentInput);
					performAction(opponentInput);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Performs the action according to the user input
	 * 
	 * @param userInput The user input
	 */
	public static void performAction(String userInput) {

		int code = Help.detectGameCommands(userInput);

		switch (code) {
			case -1: // one of standard commands was entered and applied
				return;
			case 106: // beaten code
				printBeatenPieces();
				System.out.println("\n" + TextManager.get("cli.help.back"));
				scan.nextLine();
				printGameBoard();
				break;
			case 107: // restart code
				game = new Game();
				printGameBoard();
				break;
			case 108: // resign code
				printGameOverMessage(Game.WinCondition.RESIGN);
				networkResign();
				CliMenus.runMainMenu();
				break;
			case 109: // save code
				CliMenus.runSaveMenu(".");
				printGameBoard();
				break;
			default: // make a move
				Move move = Move.parseUserMoveInput(userInput, game);
				performMove(move, true);
				if (runningNetwork && game.getCurrentPosition().getTurnColor() != usersColor) {
					try {
						Client.send(move.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				break;
		}
	}

	/**
	 * Performs a move for both the user and the engine
	 * 
	 * @param move     move to perform
	 * @param userMove boolean whether the move is a user move
	 */
	public static void performMove(Move move, boolean userMove) {

		Move next = move;
		if (!userMove) {
			System.out.println(TextManager.get("cli.aiThink"));
			next = engine.generateBestMove(game.getCurrentPosition());
			ConsoleColors.greenBoldColor();
			System.out.println("\n" + TextManager.get("cli.aiMove") + next.toString());
			ConsoleColors.resetColor();
		}

		if (!game.attemptMove(next)) {
			System.out.println(ConsoleColors.RED_BOLD + "\n!Move not allowed\n" + ConsoleColors.RESET);
			return;
		}

		movesHistory.add(0, next);

		if (userMove) {
			ConsoleColors.greenBoldColor();
			System.out.println("\n!" + next);
			ConsoleColors.resetColor();
		}
		// print the new position after each move
		printGameBoard();
		// find out if the game is over
		if (game.checkCheck()) {
			System.out.println(ConsoleColors.PURPLE_BOLD + "\n" + TextManager.get("cli.youInCheck") + ConsoleColors.RESET);
		}
		if (game.checkWinCondition() != Game.WinCondition.NONE) {
			endGame();
		}
	}

	/**
	 * Outputs the current game board and whose turn it is now
	 */
	private static void printGameBoard() {
		if (game.getCurrentPosition().getTurnColor() == Piece.White) {
			System.out.println(ConsoleColors.BOLD + "\n" + TextManager.get("cli.whiteMove") + ConsoleColors.RESET);
		} else {
			System.out.println(ConsoleColors.BOLD + "\n" + TextManager.get("cli.blackMove") + ConsoleColors.RESET);
		}
		System.out.println("\n" + game.getCurrentPosition().toString());
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
	 * Handles standard commands, like help, english, german, quit
	 * 
	 * @param code command code
	 */
	public static void handleStandardCommands(int code) {
		switch (code) {
			case 101: // help code
				Help.print();
				break;
			case 102: // english lang code
				TextManager.setLocale(Locale.ENGLISH);
				break;
			case 103: // german lang code
				TextManager.setLocale(Locale.GERMAN);
				break;
			case 104: // main menu code
				networkResign();
				CliMenus.runMainMenu();
				break;
			case 105: // quit code
				exitGame();
				break;
		}

		if (code != 104 || code != 105) {
			printGameBoard();
		}

	}

	private static void networkResign() {
		if (runningNetwork) {
			try {
				Client.send("resign");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	 * Test if the user input will have a defined effect
	 * 
	 * @param userInput The user Input
	 * @return whether the user input will have a defined effect
	 */
	public static boolean testUserInputSyntax(String userInput) {
		// Checks if input matches one of valid inputs: move(e7-e8[Q]), beaten, help,
		// quit, reset
		return userInput.matches(
				"^[a-h]{1}[1-8]{1}-[a-h]{1}[1-8]{1}[qrbn]?$|^help$|^hilfe$|^english$|^englisch$|^german$|^deutsch$|^beaten$|^geschlagen$|^restart$|^neustart$|^resign$|^aufgeben$|^save$|^speichern$|^menu$|^menue$|^quit$|^beenden$");
	}

	/**
	 * Prints the list of beaten pieces
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
	 * Ends the game according to win conditions
	 */
	private static void endGame() {
		Game.WinCondition winCondition = game.checkWinCondition();
		printGameOverMessage(winCondition);
		if (winCondition != Game.WinCondition.NONE) {
			printBeatenPieces();
			exitGame();
		}
	}

	/**
	 * Outputs a message about the end of the game, depending on the win condition
	 * 
	 * @param winCondition condition of winning the game
	 */
	private static void printGameOverMessage(Game.WinCondition winCondition) {
		int turnColor = game.getCurrentPosition().getTurnColor();
		System.out.println(ConsoleColors.YELLOW_BOLD);
		if (winCondition == Game.WinCondition.CHECKMATE) {

			if (runningPVP) {
				if (turnColor == Piece.White) {
					System.out.println(TextManager.get("cli.blackWin"));
				} else {
					System.out.println(TextManager.get("cli.whiteWin"));
				}
			} else {
				if (turnColor == Piece.White) {
					System.out.println(TextManager.get("cli.lose"));
				} else {
					System.out.println(TextManager.get("cli.win"));
				}
			}
		} else if (winCondition == Game.WinCondition.REMIS) {
			System.out.println(TextManager.get("cli.remis"));
		} else if (winCondition == Game.WinCondition.RESIGN) {
			if (turnColor == Piece.White) {
				System.out.println(TextManager.get("cli.whiteResign"));
			} else {
				System.out.println(TextManager.get("cli.blackResign"));
			}
		}
		ConsoleColors.resetColor();
	}

	/**
	 * Exits the game with the message
	 */
	static void exitGame() {
		System.out.print(ConsoleColors.CYAN);
		System.out.println("\n" + TextManager.get("cli.exit") + "\n");
		ConsoleColors.resetColor();
		System.exit(0);
	}

	/**
	 * Setter for the Users Color uses in network game
	 * @param usersColor the users Color
	 */
	public static void setUsersColor(int usersColor) {
		Cli.usersColor = usersColor;
	}
}
