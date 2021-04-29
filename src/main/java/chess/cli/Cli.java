package chess.cli;

import chess.model.*;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Starting point of the command line interface
 */
public class Cli {
	private static Game game = new Game();
	private static boolean runningPvP = false;
	private static boolean runningPvPC = false; // for Iteration2
	private static boolean runningSimple = false; // for Test, maybe implement this differently?

	/**
	 * The entry point of the CLI application.
	 *
	 * @param args The command line arguments passed to the application
	 */
	public static void main(String[] args) {

		boolean simpleRun = Arrays.asList(args).contains("--simple");

		if (simpleRun) {

			// Start game person vs person
			runningPvP = true;

		} else {
			// Ask the user to choose an opponent
			String opponent = getUserInput("Choose an opponent: Person, Computer or Network").toLowerCase();

			// Check if opponent is valid
			while (!opponent.matches("^person$|^computer$|^network$")) {
				System.out.println("There is no such opponent. Enter one of these opponents: Person, Computer or Network");
				opponent = getUserInput();
			}

			switch (opponent) {
			case "person":
				// Start game against another player
				runningPvP = true;
				break;
			case "computer":
				// Start game against computer
				runningPvPC = true;
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
	 */
	public static Move parseUserMoveInput(String input) {
		// TODO write tests
		// TODO write function
		return null;
	}

	/**
	 * Start a new game loop
	 */
	public static void runGame() {
		String input = "";
		while (runningPvP) {
			System.out.println(game.getCurrentPosition().toString());
			// Request input until there is defined input
			input = getUserInput();
			while (!testUserInputSyntax(input)) {
				input = getUserInput("!Invalid move");
			}
			// use the input
			performAction(input);
		}
	}

	/**
	 * Perform the Action according to
	 * 
	 * @param userInput
	 */
	public static void performAction(String userInput) {
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
		while (!userInput.matches("^[a-h]{1}[1-8]{1}-[a-h]{1}[1-8]{1}[QRBN]?$")) {
			return false;
		}
		return true;
	}

	/**
	 * Request input from the User
	 * 
	 * @param messageToUser
	 * @return The user input as a String (lowercase)
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
}
