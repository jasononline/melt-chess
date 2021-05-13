package chess.cli;

import chess.model.Coordinate;
import chess.model.Game;
import chess.model.Move;
import chess.model.Piece;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Starting point of the command line interface
 */
public class Cli {
	private static Game game = new Game();
	private static boolean runningPVP = false;
	private static boolean runningPVPC = false; // for Iteration2

	/**
	 * The entry point of the CLI application.
	 *
	 * @param args The command line arguments passed to the application
	 */
	public static void main(String[] args) {

		// System.out.println(parseUserMoveInput("a5-h1").toString());

		boolean simpleRun = Arrays.asList(args).contains("--simple");

		if (simpleRun) {
			// Start game person vs person
			runningPVP = true;
			runningPVPC = false;

		} else {
			// Ask the user to choose an opponent
			String opponent = getUserInput("Choose an opponent: Person, Computer or Network");

			// Check if opponent is valid
			while (!opponent.matches("^person$|^computer$|^network$|^quit$")) {
				if (opponent.equals("help")) {
					opponent = getUserInput(getHelpOutput());
				} else {
					opponent = getUserInput(
							"There is no such opponent. Enter one of these opponents: Person, Computer or Network");
				}
			}

			switch (opponent) {
				case "person":
					// Start game against another player
					runningPVP = true;
					runningPVPC = false;
					break;
				case "computer":
					// Start game against computer
					runningPVP = false;
					runningPVPC = true;
					break;
				case "network":
					// Start network game
					break;
				case "quit":
					System.exit(0);
					break;
			}
		}

		runGame();
	}

	/**
	 * Parse the user input string and return Move object
	 *
	 * @param input User input like "e7-e8[Q]"
	 * @return parsed move object from user input
	 */
	public static Move parseUserMoveInput(String input) {
		String[] squares = input.split("-"); // Split input by '-'
		int startSquare = Coordinate.toIndex(squares[0]); // start position in the board.squares array
		int targetSquare = Coordinate.toIndex(squares[1].substring(0, 2)); // target position in the board.squares array
		String flagString = squares[1].length() > 2 ? "" + squares[1].charAt(2) : "";
		int flag = 0;

		switch (flagString) {
			case "Q":
				flag = Move.PromoteToQueen;
				break;
			case "K":
				flag = Move.PromoteToKnight;
				break;
			case "R":
				flag = Move.PromoteToRook;
				break;
			case "B":
				flag = Move.PromoteToBishop;
				break;
			default:
				break;
		}

		Move move = new Move(startSquare, targetSquare, flag);
		game.addFlag(move);
		return move;
	}

	/**
	 * Start the correct new game loop
	 */
	public static void runGame() {
		System.out.println(game.getCurrentPosition().toString());
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
			// TODO find out if the game is over
		}
	}

	/**
	 * Start a Player versus Computer game
	 */
	public static void gameLoopPVPC() {
		while (runningPVPC) {
			performAction(getValidUserInput());
			// TODO let the PC make a move
			// TODO print the move of the PC
			// TODO print the new Position after the PCs move, maybe implement elsewhere?
			// TODO find out if the game is over
		}
	}

	/**
	 * Perform the Action according to the user input
	 * 
	 * @param userInput The user input
	 */
	public static void performAction(String userInput) {

		switch (userInput) {

			case "quit":
				System.exit(0);
				break;

			case "help":
				System.out.println(getHelpOutput());
				break;

			case "beaten":
				printBeatenPieces();
				break;

			case "reset":
				game = new Game();
				System.out.println(game.getCurrentPosition().toString());
				break;

			default:
				if (!game.attemptMove(parseUserMoveInput(userInput))) {
					System.out.println("!Move not allowed");
				} else {
					System.out.println("!" + userInput);
					System.out.println(game.getCurrentPosition().toString());
					if (game.checkCheck()) {
						System.out.println("You are in check.");
					}
					if (game.checkWinCondition() != 0) {
						endGame();
					}
				}
				break;
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
		return userInput.matches("^[a-h]{1}[1-8]{1}-[a-h]{1}[1-8]{1}[qrbn]?$|^beaten$|^help$|^quit$|^reset$");
	}

	/**
	 * Getter for valid commands
	 *
	 * @return The list of valid commands as a String
	 */
	public static String getHelpOutput() {
		return "\nquit		Exit the game\nbeaten		Displays all captured pieces\nreset		Reset the game\n";
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
		Scanner scan = new Scanner(System.in);
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
			input = getUserInput("!Invalid move");
		}
		return input;
	}

	/**
	 * Print the list of beaten pieces
	 */
	private static void printBeatenPieces() {
		for (int i : game.getCurrentPosition().getCapturedPieces()) {
			System.out.print(Piece.toString(i));
			System.out.println();
		}
	}

	/**
	 * End the game according to win conditions
	 */
	private static void endGame() {
		int winCondition = game.checkWinCondition();
		if (winCondition == 1) {
			System.out.println("CHECKMATE, game over.");
		} else if (winCondition == 2) {
			System.out.println("REMIS, game over.");
		}
		if (winCondition != 0) {
			System.out.println("Beaten pieces:");
			for (int i : game.getCurrentPosition().getCapturedPieces()) {
				System.out.print(Piece.toString(i));
				System.out.println();
			}
			System.exit(0);
		}
	}
}
