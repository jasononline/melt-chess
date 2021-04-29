package chess.cli;

import chess.model.*;
import java.util.Scanner;
import java.util.regex.Pattern;

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
		System.out.println("Hello World");
		// TODO (Miklos) write function
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
			input = getUserInput();
			if (testUserInputSyntax(input)) {
				performAction(input);
			}
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
		if (!Pattern.matches("/^[a-h]{1}[1-8]{1}-[a-h]{1}[1-8]{1}[QRBN]?$/", userInput)) {
			System.out.println("!Invalid move");
			return false;
		}
		return true;
	}

	/**
	 * Request input from the User
	 * 
	 * @param messageToUser
	 * @return The user input as a String
	 */
	public static String getUserInput(String messageToUser) {
		Scanner scan = new Scanner(System.in);
		System.out.println(messageToUser);
		return scan.next();
	}

	/**
	 * Request input from the User
	 * 
	 * @return The user input as a String
	 */
	public static String getUserInput() {
		Scanner scan = new Scanner(System.in);
		return scan.next();
	}
}
