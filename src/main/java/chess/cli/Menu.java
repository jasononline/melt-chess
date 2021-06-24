package chess.cli;

import java.util.Scanner;

import chess.util.TextManager;

/**
 * Controls the behaviour and actions of CLI menus
 */
public class Menu {

	private int selectedIndex;
	private final String title;
	private final String[] options;

	/**
	 * Create new GamePopup instance of
	 * 
	 * @param title  the title of menu
	 * @param options available options
	 */
	public Menu(String title, String[] options) {
		this.title = title;
		this.options = options.clone();
		this.selectedIndex = 0;
	}

	/**
	 * Prints all available options
	 */
	private void displayOptions() {
		System.out.println(ConsoleColors.CYAN_BOLD + "\n" + title + ConsoleColors.RESET);
		System.out.println("-------------------------");
		for (int i = 0; i < options.length; i++) {
			System.out.println("[" + (i + 1) + "] - " + options[i].toUpperCase());
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

		displayOptions();
		Scanner scan = new Scanner(System.in);
		String choice = scan.next().toLowerCase();

		try {
			ConsoleColors.redBold();
			selectedIndex = Integer.parseInt(choice) - 1;
			if (selectedIndex >= options.length) {
				System.out.println("\n" + TextManager.get("cli.errorChoice"));
				run();
			}
		} catch (NumberFormatException e) {
			if (choice.equals(TextManager.get("cli.helpCommandEn")) || choice.equals(TextManager.get("cli.helpCommandDe")))
				return 101;
			if (choice.equals(TextManager.get("cli.enCommandEn")) || choice.equals(TextManager.get("cli.enCommandDe")))
				return 102;
			if (choice.equals(TextManager.get("cli.deCommandEn")) || choice.equals(TextManager.get("cli.deCommandDe")))
				return 103;
			if (choice.equals(TextManager.get("cli.quitCommandEn")) || choice.equals(TextManager.get("cli.quitCommandDe")))
				return 104;

			System.out.println("\n" + TextManager.get("cli.errorChoice"));
			run();
		}

		ConsoleColors.reset();

		return selectedIndex;
	}

}
