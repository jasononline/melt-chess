package chess.cli;

import java.util.List;
import java.util.Scanner;

import chess.util.TextManager;

/**
 * Represents a menu of Cli
 */
public class Menu {

	private int selectedIndex;
	private final String title;
	private final List<String> options;

	/**
	 * Create new Menu instance of
	 * 
	 * @param title   the title of menu
	 * @param options available options
	 */
	public Menu(String title, List<String> options) {
		this.title = title;
		this.options = options;
		this.selectedIndex = 0;
	}

	/**
	 * Prints all available options
	 */
	private void displayOptions() {
		System.out.println(ConsoleColors.CYAN_BOLD + "\n" + title + ConsoleColors.RESET);
		System.out.println("-------------------------");
		for (int i = 0; i < options.size(); i++) {
			System.out.println("[" + (i + 1) + "] - " + options.get(i));
		}
		System.out.println("-------------------------");
		// Test on other console
		System.out.println(TextManager.get("cli.yourChoice") + " ");
	}

	/**
	 * Controls the selection of available options
	 *
	 * @return selectedIndex index of the selected option or code of standard
	 *         command
	 */
	public int run() {

		Scanner scan = new Scanner(System.in);
		displayOptions();
		System.out.flush();
		String choice = scan.next().toLowerCase();

		try {
			ConsoleColors.redBoldColor();
			selectedIndex = Integer.parseInt(choice) - 1;
			if (selectedIndex >= options.size()) {
				System.out.println("\n" + TextManager.get("cli.errorChoice"));
				run();
			}
		} catch (NumberFormatException e) {
			int code = Help.detectStandardCommands(choice);
			if (code != 0) {
				return code;
			}

			System.out.println("\n" + TextManager.get("cli.errorChoice"));
			run();
		}

		ConsoleColors.resetColor();

		return selectedIndex;
	}

}
