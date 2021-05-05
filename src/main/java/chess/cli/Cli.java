package chess.cli;

import chess.model.*;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Starting point of the command line interface
 */
public class Cli {
	private static Game game = new Game();
	private static boolean runningPVP = false;
	private static boolean runningPVPC = false; // for Iteration2
	private static Board board = new Board();

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
			while (!opponent.matches("^person$|^computer$|^network$")) {
				opponent = getUserInput("There is no such opponent. Enter one of these opponents: Person, Computer or Network");
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
		String squares[] = input.split("-"); // Split input by '-'
		int startSquare = Coordinate.toIntex(squares[0]); // start position in the board.squares array
		int targetSquare = Coordinate.toIntex(squares[1].substring(0, 2)); // target position in the board.squares array
		String flagString = squares[1].length() > 2 ? "" + squares[1].charAt(2) : "";
		int flag = 0;

		switch (flagString) {
			case "Q":
				flag = 3;
				break;
			case "K":
				flag = 4;
				break;
			case "R":
				flag = 5;
				break;
			case "B":
				flag = 6;
				break;
			default:
				flag = 0;
				break;
		}
		// assign another flag values

		return new Move(startSquare, targetSquare, flag);
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
	 * @param userInput
	 */
	public static void performAction(String userInput) {

		switch (userInput) {
			case "reset":
				runGame();
			case "exit":
				System.exit(0);
			default:
				if (!game.attemptMove(parseUserMoveInput(userInput))) {
					System.out.println("!Move not allowed");
				}
				;
				System.out.println(game.getCurrentPosition().toString());
		}

		// TODO (Eva) implement function
		assert true; // delete this line as soon as there is real code in this method
	}

	/**
	 * Test if the user input will have a defined effect
	 * 
	 * @param userInput The user Input
	 * @return whether the user input will have a defined effect
	 */
	public static boolean testUserInputSyntax(String userInput) {
		// Checks if input matches one of valid inputs: move(e7-e8[Q]), beaten, help,
		// exit, reset
		if (!userInput.matches("^[a-h]{1}[1-8]{1}-[a-h]{1}[1-8]{1}[qrbn]?$|^beaten$|^help$|^exit$|^reset$"))
			return false;
		return true;
	}

	/**
	 * Request input from the User
	 * 
	 * @param messageToUser
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
		return scan.next().toLowerCase();
	}

	/**
	 * Request valid input from the user until there ist valid input
	 * 
	 * @return user input that has a defined meaning
	 */
	private static String getValidUserInput() {
		String input = "";
		input = getUserInput();
		while (!testUserInputSyntax(input)) {
			input = getUserInput("!Invalid move");
		}
		return input;
	}
}
